package com.techelevator.dao;

import java.util.List;

import com.techelevator.model.Deck;

public interface deckDAO {
	
	List<Deck> findAllDecks(int userID);
	
	void createDeck(int userID, String name, String description);
	
	void updateDeck(int deckID, String name, String description);
	
	void updateName(int deckID, String name);
	
	void updateCorrectTrue(int deckID);
	
	void updateCorrectFalse(int deckID);
	
	List<Deck> showTrueAndFalse(int userID);
	
	List<Deck> showAllFalse(int userID);
	
	List<Deck> showAllTrue(int userID);
	
	boolean showOneCorrectness(int deckID);
	
	void deleteDeck(int deckID);

	
	
	

}
