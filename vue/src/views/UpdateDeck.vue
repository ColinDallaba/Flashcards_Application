<template>
<div>
  <div>UPDATE DECK</div>
  
  <form>
      <label for="name">Enter your new Deck Name</label>
      <input type="text" id="name" name="name" v-model="deck.name"/>
      <label for="description">Enter your new Deck Description</label>
      <input type="text" id="description" name="description" v-model="deck.description"/> 
      <button type="button" @click="updateDeck(deck.deckID, deck)">Submit Change</button>
  </form>
</div>
</template>

<script>
import authService from '../services/AuthService.js'
export default {
    
 data() {
     return {
         deck: {
             deckID: this.$store.state.currentDeckID,
             name: '',
             description: ''
         }
     }
 }, 
 created() {
     this.getDeckID();
     this.deck.deckID = this.$store.state.currentDeckID;
 }, 
 methods: {
       getDeckID() {
      this.$store.commit("SET_ID", this.$route.params.deckID);
    },
        updateDeck(id, passedDeck) {
            authService
            .updateDeck(id, passedDeck)
            .then(response => {
                if (response.status == 200) {
                    this.$router.push('/');
                    location.reload();
                }
            })
             .catch(error => {
                console.error(error);
            });
        }
    },
name: 'update-deck',
}

</script>

<style>

</style>
