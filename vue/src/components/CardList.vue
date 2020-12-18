<template>
  <div class="container">
    <div class="wrapper">
      <div class="clip-text clip-text_thirteen clip-text--cover">Your Deck</div>
    </div>

    <button type="button" class="addCard" @click="checkIf10Cards()">
      Add Card
    </button>
    <button
      type="button"
      class="startSession"
      @click="$router.push('/view-session')"
    >
      Start Session
    </button>
    <div class="buttonWrapper">
      <button type="button" class="createDeck" @click="$router.push('/')">
        Choose Another Deck
      </button>
    </div>

    <div
      class="cards"
      v-for="card in this.$store.state.cards"
      v-bind:key="card.cardID"
    >
      <div
        class="flip-box-inner"
        v-on:click="toggleCard($store.state.cards[currentCardIndex])"
      >
        <div class="flip-box-front">
          {{ card.question }}

          <div class="rank">Rank: {{ cardRank(card.rank) }}</div>
        </div>
        <div class="flip-box-back">
          {{ card.answer }}
        </div>
      </div>
      <br />
      <div class="editDelete">
        <button
          type="button"
          class="updateCard"
          @click="
            $router.push({
              name: 'update-card',
              params: { cardID: card.cardID },
            })
          "
        >
          Edit
        </button>
        <button
          type="button"
          class="deleteCard"
          @click="removeCards(card.cardID)"
        >
          Delete
        </button>
      </div>
    </div>
  </div>
</template>


<script>
import authService from "../services/AuthService";
export default {
  components: {},
  data() {
    return {
      card: {
        deckID: this.$store.state.currentDeckID,
        question: "",
        answer: "",
        rank: "",
      },
    };
  },
  created() {
    this.retrieveCards();
    this.getDeckID();
  },
  computed: {
    findIndex() {
      let ID = "";
      this.$store.state.decks.forEach((deck) => {
        if (deck.deckID == this.$store.state.currentDeckID) {
          ID = this.$store.state.decks.indexOf(deck);
        }
      });
      return ID;
    },
  },
  name: "card-list",
  methods: {
    cardRank(rank) {
      let skillLevel = "";
      if (rank === 1) {
        skillLevel = "Novice";
      } else if (rank === 2) {
        skillLevel = "Intermediate";
      } else if (rank === 3) {
        skillLevel = "Advanced";
      } else if (rank === 4) {
        skillLevel = "Expert";
      } else if (rank === 5) {
        skillLevel = "Master";
      } else {
        skillLevel = "Beginner";
      }
      return skillLevel;
    },
    checkIf10Cards() {
      if (this.$store.state.cards.length == 10) {
        alert("Decks can not have more than 10 cards!");
      } else {
        this.$router.push("/create-card");
      }
    },
    getDeckID() {
      this.$store.commit("SET_ID", this.$route.params.deckID);
    },
    toggleCard(card) {
      card.flipped = !card.flipped;
    },
    retrieveCards() {
      authService.getCards(this.$route.params.deckID).then((response) => {
        this.$store.commit("SET_CARDS", response.data);
      });
    },
    removeCards(cardID) {
      authService.deleteCard(cardID).then((response) => {
        if (response.status === 204) {
          location.reload();
        }
      });
    },
    randomBackgroundColor() {
      return this.generateHexCode();
    },
    generateHexCode() {
      var bg = Math.floor(Math.random() * 16777215).toString(16);
      if (bg.length !== 6) bg = this.generateHexCode();
      return bg;
    },
  },
};
</script>

<style scoped>
.rank {
  font-family: "Roboto", sans-serif;
  font-size: 17px;
  padding-top: 25px;
}
.container {
  position: absolute;
  z-index: -3;

  background-image: linear-gradient(
    0deg,
    rgb(239 128 102 / 79%) 40%,
    rgba(3 83 99 / 2%) 64%
  );
  min-height: 100%;
  min-width: 100%;

  width: 100%;
  height: auto;

  top: 0;
  left: 0;
}
.wrapper {
  text-align: center;
  padding-top: 50px;
}

.clip-text {
  font-size: 6em;
  font-weight: bold;
  line-height: 1;
  position: relative;
  display: inline-block;
  margin: 0.25em;
  padding: 0.1em 0.3em;
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
.addCard {
  font-family: "Roboto", sans-serif;
  width: 150px;

  background-color: rgb(127 181 127);
  border-radius: 10px;
  display: block;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 10px;
}

.startSession {
  font-family: "Roboto", sans-serif;
  width: 150px;

  background-color: rgba(248, 198, 126, 1);
  border-radius: 10px;
  display: block;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 10px;
}
.randomSession {
  font-family: "Roboto", sans-serif;
  width: 150px;

  background-color: rgb(127 181 127);
  border-radius: 10px;
  margin-right: 10px;
}
.lightningSession {
  font-family: "Roboto", sans-serif;
  width: 150px;

  background-color: rgb(127 181 127);
  border-radius: 10px;
  margin-right: 10px;
}

.createDeck {
  font-family: "Roboto", sans-serif;
  width: 150px;

  background-color: skyblue;
  border-radius: 10px;
}
.buttonWrapper {
  margin-left: auto;
  margin-right: auto;
  text-align: center;
  padding-top: 6px;
  margin-bottom: 80px;
}

.cards {
  background-color: transparent;
  width: 300px;
  height: 200px;

  perspective: 1000px;
  margin-right: 50px;
  margin-left: 80px;
  margin-top: -40px;
  position: relative;
  display: inline-block;
  margin-bottom: 150px;
}

.flip-box-inner {
  position: relative;
  width: 100%;
  height: 100%;
  text-align: center;
  transition: transform 0.8s;
  transform-style: preserve-3d;
}

.cards:active .flip-box-inner {
  transform: rotateY(180deg);
}

.flip-box-front,
.flip-box-back {
  position: absolute;
  width: 100%;
  height: 100%;
  font-family: Comic sans ms;
  font-size: 30px;
  backface-visibility: hidden;
  border-radius: 25px;
}

.flip-box-front {
  background-color: rgb(106, 90, 205);

  padding: 50px 10px 10px 20px;
}

.flip-box-back {
  background-color: rgba(248, 198, 126, 1);

  transform: rotateY(180deg);
}
button:hover {
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
}

.updateCard {
  font-family: "Roboto", sans-serif;
  width: 100px;

  background-color: rgb(127 181 127);
  border-radius: 10px;
  margin-left: 45px;
  margin-top: 10px;
  margin-right: 10px;
}
.deleteCard {
  font-family: "Roboto", sans-serif;
  width: 100px;
  background-color: rgb(127 181 127);
  border-radius: 10px;
}
</style>