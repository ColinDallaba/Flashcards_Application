<template>
  <div class="page">
    <div class="wrapper">
      <div class="clip-text clip-text_thirteen clip-text--cover">
        Your Deck List
      </div>
      <div class="add">
    <button class="createDeck" @click="$router.push('create-deck')">Add Deck</button>
    </div>
      </div>
   
    <div class="container">
      <table class="table">
        <thead>
          <tr>
            <th>Deck Name</th>
            <th>Description</th>
            <th>Delete</th>
          </tr>
        </thead>
        <tbody>
          <tr class="decks" 
            v-for="deck in this.$store.state.decks"
            v-bind:key="deck.userID">
            <td>{{ deck.name }}</td>
            <td>{{ deck.description }}</td>
            <td>{{ deck.deckID }}
            <td>
              <button type="button" class="updateDeck" @click="$router.push({name: 'update-deck', params:{deckID: deck.deckID}})">EDIT</button>
              <button type="button" class="delete-icon" @click="removeDecks(deck.deckID)">DELETE</button>
            <button id="view-deck" type="button" @click="$router.push({
   name: 'deck-with-cards', params: {deckID: deck.deckID }})">View Cards</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>

</template>

<script>
import authService from '../services/AuthService';

export default {
  data(){
    return {
    deck: {
      name:'',
      description: '',
      deckID: this.$route.params.deckID,
    }
    }
  },
  created() {
    this.retrieveDecks();
  },
  name: "deck-list",
  methods: {
    retrieveDecks() {
      authService.getDeck(this.$store.state.user.id).then(response => {
        this.$store.commit("SET_DECKS", response.data);
      });
    },
    removeDecks(removedDeckID) {
      authService.deleteDeck(removedDeckID).then(response => {
        if (response.status === 204) {
         // alert("Deck deleted!");
          // this.$store.commit(removedDeckID);
          this.$router.push("/");
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

<style scoped>
.page {
  position: fixed;
  z-index: -3;

  background-image: linear-gradient(
    0deg,
    rgb(252, 220, 226) 9%,
    rgba(255, 255, 255, 1) 64%
  );
  min-height: 100%;
  min-width: 1024px;

  width: 100%;
  height: auto;

  top: 0;
  left: 0;
}

/* Clip text element */
.clip-text {
  font-size: 3em;
  font-weight: bold;
  line-height: 1;
  position: relative;
  display: inline-block;
  margin: 0.45em;
  padding: 0.5em 0.75em;
  text-align: center;
  color: #fff;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}
.clip-text:before,
.clip-text:after {
  position: absolute;
  content: "";
}

.clip-text:before {
  z-index: -2;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  background-image: inherit;
}

.clip-text:after {
  position: absolute;
  z-index: -1;
  top: 0.125em;
  right: 0.125em;
  bottom: 0.125em;
  left: 0.125em;
  background-color: #000;
  opacity: 0.8;
}

.clip-text--cover,
.clip-text--cover:before {
  background-size: cover;
  background-position: 50% 50%;
}
.clip-text_thirteen {
  background-image: url(https://i.ytimg.com/vi/MU3qrgR2Kkc/maxresdefault.jpg);
}

.wrapper {
  text-align:center;
  position: relative;
  padding-top: 50px;
}
.createDeck {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0;
  position: absolute;
  top: 50%;
  left: 50%;
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
}
.add {
  white-space: nowrap;
  position: absolute;
  top: 110%;
  right: 45%;
  left: 50%;
   -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
}
.container {
  margin-top: 6%;
}
/* .decks {
  display: grid;
  grid-template-rows: repeat(2, 1fr);
  grid-gap: 20px;
}
.delete-icon{

} */

</style>





