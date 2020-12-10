package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.Deck;
import com.techelevator.model.NewDeckDTO;

@Component
public class deckSqlDAO implements deckDAO {
	private JdbcTemplate jdbc;
	
	public deckSqlDAO(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	

	@Override
	public void createDeck(int userID, String name) {
		String sql = "INSERT INTO decks (user_id, deck_id, name, correct) VALUES (?, DEFAULT, ?, ?)";
		jdbc.update(sql, userID, name, false);
	}

	@Override
	public List<Deck> findAllDecks(int userID) {
		List<Deck> decks = new ArrayList<>();
		String sql = "SELECT deck_id, user_id, name, correct FROM decks WHERE user_id = ?";
		SqlRowSet results = jdbc.queryForRowSet(sql, userID);
		while(results.next()) {
			Deck deck = mapRowToDeckWithUser(results);
            decks.add(deck);
		}
		return decks;
	}


	@Override
	public void updateName(int deckID, String name) {
		String sql = "UPDATE decks SET name = ? WHERE deck_id = ?";
		jdbc.update(sql, name, deckID);
		
	}

	@Override
	public void updateCorrectTrue(int deckID) {
		String sql = "UPDATE decks SET correct = true WHERE deck_id = ?";
		jdbc.update(sql, deckID);
		
	}
	
	@Override
	public void updateCorrectFalse(int deckID) {
		String sql = "UPDATE decks SET correct = false WHERE deck_id = ?";
		jdbc.update(sql, deckID);
	}

	@Override
	public List<Deck> showTrueAndFalse(int userID) {
		List<Deck> decks = new ArrayList<>();
		String sql = "SELECT deck_id, name, correct FROM decks where user_id = ?";
		SqlRowSet results = jdbc.queryForRowSet(sql, userID);
		while(results.next()) {
			Deck deck = mapRowToDeck(results);
			decks.add(deck);
		}
		return decks;
	}
	
	@Override
	public List<Deck> showAllTrue(int userID) {
		List<Deck> decks = new ArrayList<>();
		String sql = "SELECT deck_id, name, correct FROM decks WHERE user_id = ? AND correct = true";
		SqlRowSet results = jdbc.queryForRowSet(sql, userID);
		while(results.next()) {
			Deck deck = mapRowToDeck(results);
			decks.add(deck);
		}
		return decks;
	}

	@Override
	public List<Deck> showAllFalse(int userID) {
		List<Deck> decks = new ArrayList<>();
		String sql = "SELECT deck_id, name, correct FROM decks WHERE user_id = ? AND correct = false";
		SqlRowSet results = jdbc.queryForRowSet(sql, userID);
		while(results.next()) {
			Deck deck = mapRowToDeck(results);
			decks.add(deck);
		}
		return decks;
	}
	
	@Override
	public boolean showOneCorrectness(int deckID) {
		String sql = "SELECT correct FROM decks WHERE deck_id = ?";
		boolean results = jdbc.queryForObject(sql, boolean.class, deckID);
		return results;
	}

	@Override
	public void deleteDeck(int deckID) {
		String sql = "DELETE FROM decks WHERE deck_id = ?";
		jdbc.update(sql, deckID);
	}
	
private Deck mapRowToDeckWithUser(SqlRowSet rs) {
        Deck deck = new Deck();
        deck.setUserId(rs.getInt("user_id"));
        deck.setName(rs.getString("name"));
        deck.setDeckID(rs.getInt("deck_id"));
        deck.setCorrect(rs.getBoolean("correct"));
        return deck;
    }
	private Deck mapRowToDeck(SqlRowSet rs) {
        Deck deck = new Deck();
        deck.setName(rs.getString("name"));
        deck.setDeckID(rs.getInt("deck_id"));
        deck.setCorrect(rs.getBoolean("correct"));
        return deck;
    }



}