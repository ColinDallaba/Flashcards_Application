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
	
	
//controller done
	@Override
	public void createCard(int deckID, int userID, String question, String answer) {
		String sql = "INSERT INTO cards (card_id, deck_id, user_id, question, answer, correct, rank) VALUES (DEFAULT, ?, ?, ?, ?, DEFAULT, DEFAULT)";
		jdbc.update(sql, deckID, userID, question, answer);
	}

//controller done
	@Override
	public List<Card> findAllCards(int deckID) {
		List<Card> cards = new ArrayList<>();
		String sql = "SELECT card_id, deck_id, user_id, question, answer, correct, rank FROM cards WHERE deck_id = ?";
		SqlRowSet results = jdbc.queryForRowSet(sql, deckID);
		while(results.next()) {
			Card card = mapRowToCardWithUser(results);
			cards.add(card);
		}
		return cards;
	}
	
	@Override
	public void updateCard(int cardID, String question, String answer, boolean correct) {
		String sql = "UPDATE cards SET question = ?, answer = ?, correct = ? WHERE card_id = ?";
		jdbc.update(sql, question, answer, correct, cardID);
		
		String checkCorrect = "SELECT correct FROM cards WHERE card_id = ?";
		boolean results = jdbc.queryForObject(checkCorrect, boolean.class, cardID);
		
		if(results == true) {
			String updateRank = "UPDATE cards SET rank = rank + 1 WHERE card_id = ?";
			jdbc.update(updateRank, cardID);
			
		} 
	}
		
	
	@Override
	public void deleteCard(int cardID) {
		String sql = "DELETE FROM cards WHERE card_id = ?";
		jdbc.update(sql, cardID);
	}
//===================ANYTHING BELOW THIS LINE WILL PROBABLY BE DELETED=============================
	
	
//controller done
	@Override
	public void updateCorrectTrue(int cardID) {
		String sql = "UPDATE cards SET correct = true, rank = rank + 1 WHERE card_id = ?";
		jdbc.update(sql, cardID);
		
	}
	
//controller done
	@Override
	public void updateCorrectFalse(int cardID) {
		String rankCheck = "SELECT rank FROM cards WHERE card_id = ?";
		int result = jdbc.queryForObject(rankCheck, int.class, cardID);
		if(result > 0) {
			String sql = "UPDATE cards SET correct = false, rank = rank - 1 WHERE card_id = ?";
			jdbc.update(sql, cardID);
		} else {
			String noNegs = "UPDATE cards SET correct = false, rank = 0 WHERE card_id = ?";
			jdbc.update(noNegs, cardID);
		}
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
	
	public int showRank(int cardID) {
		String sql = "SELECT rank FROM cards WHERE card_id = ?";
		int results = jdbc.queryForObject(sql, int.class, cardID);
		return results;
	}
	
	public List<Card> showAllRanks(int deckID) {
		List<Card> cards = new ArrayList<>();
		String sql = "SELECT deck_id, card_id, rank FROM cards WHERE deck_id = ?";
		SqlRowSet results = jdbc.queryForRowSet(sql, deckID);
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




//this stays -- helper method to get data into object --
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

//end brace
}
