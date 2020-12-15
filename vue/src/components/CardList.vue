<template>
  <div>
    
     <h2> {{ $store.state.decks[findIndex].name }} </h2>
      <h3> {{ $store.state.decks[findIndex].description }} </h3>
     
      <h1>Your card list</h1>
      
      <button type="button" class="createCard" @click="$router.push('/create-card')">Add Card</button>
      <button type="button" class="viewSession" @click="$router.push('/view-session')">Start Session</button>
      <!-- <td><input type="text" id="questionFilter" v-model="filter.question"/></td>
       <tr v-for="card in filteredList" v-bind:key="card.cardID" > -->
        <div class="cards"
        v-for="card in this.$store.state.cards" 
        v-bind:key="card.cardID">
        {{ card.cardID }}
        {{ card.question }}
        {{ card.answer }}
        {{ card.rank }}
        <button type="button" class="updateCard" @click="$router.push({name: 'update-card', params:{cardID: card.cardID}})">EDIT</button>
        <button type="button" @click="removeCards(card.cardID)">DELETE</button>
        <!-- </tr> -->
        </div>
  </div>
</template>

<script>
import authService from '../services/AuthService';
// import Search from '../components/Search.vue'
export default {
 data(){
    return {
      // filter: {
      //   question: '',
      //   answer: ''
      // },
        card: {
            deckID: this.$store.state.currentDeckID,
            
            question: '',
            answer: '',
            rank: 0
            
        },
    }  
  }, 
   created(){
        this.retrieveCards();
        this.getDeckID();
   },
  //  components: {
  //    Search
  //  },
   computed: {
    //  filteredList() {
    //   let filteredCards = this.$store.state.cards;
    //   if( this.filter.question != "" ) {
    //     filteredCards = filteredCards.filter(card => card.question.toLowerCase().includes(this.filter.question.toLowerCase()))
    //   } 
    //   if( this.filter.answer != "" ) {
    //     filteredCards = filteredCards.filter(card => card.answer.toLowerCase().includes(this.filter.answer.toLowerCase()))
    //   }
    //   return filteredCards;
    // },
      findIndex() {
      let ID = ''
      this.$store.state.decks.forEach(deck => {
       if(deck.deckID == this.$store.state.currentDeckID) {
            ID = this.$store.state.decks.indexOf(deck)
        }
      });
      return ID;
    },
   },
  name: "card-list",
  methods: {
    
    getDeckID() {
      this.$store.commit("SET_ID", this.$route.params.deckID);
    },
    retrieveCards() {
      authService.getCards(this.$route.params.deckID).then((response) => {
        this.$store.commit("SET_CARDS", response.data);
      });
    },
    removeCards(cardID) {
      authService.deleteCard(cardID).then(response => {
        if (response.status === 204) {
          // this.$store.commit("DELETE_CARDS", cardID);
          location.reload();
        }
      });
    },
    randomBackgroundColor() { 
    return this.generateHexCode();
    },
    generateHexCode() {
      var bg = Math.floor(Math.random()*16777215).toString(16);
      if (bg.length !== 6) bg = this.generateHexCode();
      return bg;
    }
  },
  
}



</script>

<style>

</style>