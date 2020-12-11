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
    	
    	myDeckDAO.createDeck(newDeck.getUserId(), newDeck.getName(), newDeck.getDescription());
    }
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/view-decks/{id}", method = RequestMethod.GET)
    public List<Deck> findDecks(@Valid @PathVariable("id") int userID) {
    	
    	return myDeckDAO.findAllDecks(userID);
    }

}