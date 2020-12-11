package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.Card;
import com.techelevator.model.Deck;

@Component
public class cardSqlDAO implements cardDAO {
	private JdbcTemplate jdbc;
	
	public cardSqlDAO(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	

	@Override
	public void createCard(int deckID, int userID, String question, String answer) {
		String sql = "INSERT INTO cards (card_id, deck_id, user_id, question, answer, correct, rank) VALUES (DEFAULT, ?, ?, ?, ?, DEFAULT, DEFAULT)";
		jdbc.update(sql, deckID, userID, question, answer);
	}

	
	@Override
	public List<Card> findAllCards(int userID) {
		List<Card> cards = new ArrayList<>();
		String sql = "SELECT card_id, deck_id, user_id, question, answer, correct, rank FROM cards WHERE user_id = ?";
		SqlRowSet results = jdbc.queryForRowSet(sql, userID);
		while(results.next()) {
			Card card = mapRowToCardWithUser(results);
			cards.add(card);
		}
		return cards;
	}


//should not need but just incase
	@Override
	public void addQuestion(String question) {
		String sql = "INSERT INTO cards (question) VALUES (?)";
		jdbc.update(sql, question);
		
	}

//should not need but just incase
	@Override
	public void addAnswer(String answer) {
		String sql = "INSERT INTO cards (answer) VALUES (?)";
		jdbc.update(sql, answer);
	}

	@Override
	public void updateCorrectTrue(int cardID) {
		String sql = "UPDATE cards SET correct = true, rank = rank + 1 WHERE card_id = ?";
		jdbc.update(sql, cardID);
		
	}
	
	@Override
	public void updateCorrectFalse(int cardID) {
		String sql = "UPDATE cards SET correct = false, rank = rank - 1 WHERE card_id = ?";
		jdbc.update(sql, cardID);
	}

	@Override
	public List<Card> showTrueAndFalse(int userID, int deckID) {
		List<Card> cards = new ArrayList<>();
		String sql = "SELECT deck_id, card_id, question, answer, correct, rank FROM cards WHERE user_id = ? AND deck _id = ?";
		SqlRowSet results = jdbc.queryForRowSet(sql, userID, deckID);
		while(results.next()) {
			Card card = mapRowToCardWithUser(results);
			cards.add(card);
		}
		return cards;
	}

	@Override
	public List<Card> showTrue(int userID, int deckID) {
		List<Card> cards = new ArrayList<>();
		String sql = "SELECT deck_id, card_id, question, answer, correct, rank FROM cards WHERE user_id = ? AND deck_id = ? AND correct = true";
		SqlRowSet results = jdbc.queryForRowSet(sql, userID, deckID);
		while(results.next()) {
			Card card = mapRowToCardWithUser(results);
			cards.add(card);
		}
		return cards;
	}


	@Override
	public List<Card> showFalse(int userID, int deckID) {
		List<Card> cards = new ArrayList<>();
		String sql = "SELECT deck_id, card_id, question, answer, correct, rank FROM cards WHERE user_id = ? AND deck_id = ? AND correct = false";
		SqlRowSet results = jdbc.queryForRowSet(sql, userID, deckID);
		while(results.next()) {
			Card card = mapRowToCardWithUser(results);
			cards.add(card);
		}
		return cards;
	}



	@Override
	public void updateQuestion(String question, int cardID) {
		String sql = "UPDATE cards SET question = ? WHERE card_id = ?";
		jdbc.update(sql, question, cardID);
		
	}

	@Override
	public void updateAnswer(String answer, int cardID) {
		String sql = "UPDATE cards SET answer = ? WHERE card_id = ?";
		jdbc.update(sql, answer, cardID);
		
	}



	@Override
	public void deleteCard(int userID, int deckID, int cardID) {
		String sql = "DELETE FROM cards WHERE user_id = ? AND deck_id =? AND card_id = ?";
		jdbc.update(sql, userID, deckID, cardID);
	}
	
	private Card mapRowToCardWithUser(SqlRowSet rs) {
        Card card = new Card();
        card.setCardID(rs.getInt("card_id"));
        card.setUserID(rs.getInt("user_id"));
        card.setDeckID(rs.getInt("deck_id"));
        card.setQuestion(rs.getString("question"));
        card.setAnswer(rs.getString("answer"));
        card.setCorrect(rs.getBoolean("correct"));
        card.setRank(rs.getInt("rank"));
        return card;
    }
	
	
}
