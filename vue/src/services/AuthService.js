import axios from 'axios';

export default {

  login(user) {
    return axios.post('/login', user)
  },

  register(user) {
    return axios.post('/register', user)
  },

  addDeck(deck) {
    return axios.post('/create-deck', deck);
  },

  getDeck(userID) {
    return axios.get(`/view-decks/${userID}`);
  },

  deleteDeck(deckID) {
    return axios.delete(`/${deckID}`);
  },

  getCards(deckID) {
    return axios.get(`/deck-with-cards/${deckID}`)
  },

  getCard(cardID) {
    return axios.get(`/cards/${cardID}`)
  },

  addCard(card) {
    return axios.post('/create-card', card);
  },

  updateCard(cardID, card) {
    return axios.put(`/update-card/${cardID}`, card);
  },

  deleteCard(cardID) {
    return axios.delete(`/card/${cardID}`);
  },
  updateDeck(deckID, deck) {
    return axios.put(`/update-deck/${deckID}`, deck);
  }

}
