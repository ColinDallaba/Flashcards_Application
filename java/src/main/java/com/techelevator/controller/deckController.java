package com.techelevator.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import com.techelevator.dao.deckDAO;
import com.techelevator.model.Deck;
import com.techelevator.model.DeckDTO;
import com.techelevator.model.cardDTO;




@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class deckController {
    private deckDAO myDeckDAO;
    
    
    public deckController(deckDAO deckDAO) {
        this.myDeckDAO = deckDAO;
    }
	
    
	
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/create-deck", method = RequestMethod.POST)
    public void makeNewDeck(@Valid @RequestBody DeckDTO newDeck) {
    	
    	myDeckDAO.createDeck(newDeck.getUserID(), newDeck.getName(), newDeck.getDescription());
    }
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/view-decks/{userID}", method = RequestMethod.GET)
    public List<Deck> findDecks(@Valid @PathVariable("userID") int userID) {
    	
    	return myDeckDAO.findAllDecks(userID);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/update-deck/{deckID}", method = RequestMethod.PUT)
    public void updateCard(@Valid @PathVariable("deckID") int deckID, @RequestBody DeckDTO newdeck) {
    	myDeckDAO.updateDeck(deckID, newdeck.getName(), newdeck.getDescription());
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{deckID}", method = RequestMethod.DELETE)
    public void deleteDeck(@Valid @PathVariable("deckID") int deckID) {
    	myDeckDAO.deleteDeck(deckID);
    }

}