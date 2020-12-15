package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.Card;
import com.techelevator.model.Deck;

@Component
public class deckSqlDAO implements deckDAO {
	private JdbcTemplate jdbc;
	
	public deckSqlDAO(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	

	@Override
	public void createDeck(int userID, String name, String description) {
		String sql = "INSERT INTO decks (user_id, deck_id, name, correct, description, rank) VALUES (?, DEFAULT, ?, DEFAULT, ?, DEFAULT)";
		jdbc.update(sql, userID, name, description);
	}

	@Override
	public List<Deck> findAllDecks(int userID) {
		List<Deck> decks = new ArrayList<>();
		String sql = "SELECT deck_id, user_id, name, correct, description, rank FROM decks WHERE user_id = ?";
		SqlRowSet results = jdbc.queryForRowSet(sql, userID);
		while(results.next()) {
			Deck deck = mapRowToDeckWithUser(results);
            decks.add(deck);
		}
		return decks;
	}
	
	@Override
	public void updateDeck(int deckID, String name, String description) {
		String sql = "UPDATE decks SET name = ?, description = ? WHERE deck_id = ?";
		jdbc.update(sql, name, description, deckID);
		
		//calculate percentage
		String rankPercent = "SELECT rank FROM cards WHERE deck_id = ?";
		SqlRowSet results = jdbc.queryForRowSet(rankPercent, deckID);
		int rankCount = 0;
		int numOfCard = 0;
			while(results.next()) {
				Card card = new Card();
				card.setRank(results.getInt("rank"));
				rankCount += card.getRank();
				numOfCard++;
			}
		int totalCardsRankPossible = numOfCard * 5;
		double calculatedPercentage = (double)rankCount / (double)totalCardsRankPossible;
		double tempPercent = calculatedPercentage * 100;
		int finalPercent = (int)tempPercent;
		
		String updateRank = "UPDATE decks SET rank = ? WHERE deck_id = ?";
		jdbc.update(updateRank, finalPercent, deckID);
		
		//changes correct status based on rank average
		String percentage = "SELECT rank FROM decks WHERE  deck_id = ?";
		int result = jdbc.queryForObject(percentage, int.class, deckID);
			if(result < 100) {
				String updateToFalse = "UPDATE decks SET correct = false WHERE deck_id = ?";
				jdbc.update(updateToFalse, deckID);
			} else {
				String updateToTrue = "UPDATE decks SET correct = true WHERE deck_id = ?";
				jdbc.update(updateToTrue, deckID);
			}
		
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
		String sql2 = "DELETE FROM cards WHERE deck_id = ?";
		jdbc.update(sql2, deckID);
		String sql = "DELETE FROM decks WHERE deck_id = ?";
		jdbc.update(sql, deckID);
	}
	
private Deck mapRowToDeckWithUser(SqlRowSet rs) {
        Deck deck = new Deck();
        deck.setUserId(rs.getInt("user_id"));
        deck.setName(rs.getString("name"));
        deck.setDeckID(rs.getInt("deck_id"));
        deck.setCorrect(rs.getBoolean("correct"));
        deck.setRank(rs.getInt("rank"));
        deck.setDescription(rs.getString("description"));
        return deck;
    }
	private Deck mapRowToDeck(SqlRowSet rs) {
        Deck deck = new Deck();
        deck.setName(rs.getString("name"));
        deck.setDeckID(rs.getInt("deck_id"));
        deck.setCorrect(rs.getBoolean("correct"));
        deck.setRank(rs.getInt("rank"));
        deck.setDescription(rs.getString("description"));
        return deck;
    }



}
