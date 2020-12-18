<template>
  <div class="page">
    <div class="wrapper">
      <div class="clip-text clip-text_thirteen clip-text--cover">
        Your Deck List
      </div>
      <div class="add">
        <button class="createDeck" @click="$router.push('create-deck')">
          Add Deck
        </button>
      </div>
    </div>

    <div class="container">
      <div
        class="decks"
        v-for="deck in this.$store.state.decks"
        v-bind:key="deck.userID"
      >
        <div class="deckCard">
          <div class="deckName">
            {{ deck.name }}
          </div>
          <div class="deckDesc">
            {{ deck.description }}
          </div>
          <div class="rank">Rank: {{ deckRank(deck.rank) }}</div>
        </div>
        <div class="buttonWrapper">
          <button
            type="button"
            class="updateDeck"
            @click="
              $router.push({
                name: 'update-deck',
                params: { deckID: deck.deckID },
              })
            "
          >
            Edit
          </button>
          <button
            type="button"
            class="delete-icon"
            @click="removeDecks(deck.deckID)"
          >
            Delete
          </button>
          <button
            id="view-deck"
            type="button"
            @click="
              $router.push({
                name: 'deck-with-cards',
                params: { deckID: deck.deckID },
              })
            "
          >
            View Cards
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  data() {
    return {
      deck: {
        name: "",
        description: "",
        deckID: this.$route.params.deckID,
        rank: "",
      },
    };
  },
  created() {
    this.retrieveDecks();
  },
  name: "deck-list",
  methods: {
    deckRank(rank) {
      let skillLevel = "";
      if (rank >= 20 && rank <= 39) {
        skillLevel = "Novice";
      } else if (rank >= 40 && rank <= 59) {
        skillLevel = "Intermediate";
      } else if (rank >= 60 && rank <= 80) {
        skillLevel = "Advanced";
      } else if (rank >= 80 && rank < 100) {
        skillLevel = "Expert";
      } else if (rank === 100) {
        skillLevel = "Master";
      } else {
        skillLevel = "Beginner";
      }
      return skillLevel;
    },
    retrieveDecks() {
      authService.getDeck(this.$store.state.user.id).then((response) => {
        this.$store.commit("SET_DECKS", response.data);
      });
    },
    removeDecks(removedDeckID) {
      authService.deleteDeck(removedDeckID).then((response) => {
        if (response.status === 204) {
          alert("Deck deleted!");
          this.retrieveDecks();
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
.deckName {
  font-size: 40px;
  margin-bottom: 10px;
  margin-top: 10px;
  font-family: Comic sans ms;
}
.deckDesc {
  font-size: 26px;
}
.rank {
  font-size: 17px;
  margin-top: 5px;
}
.updateDeck {
  font-family: "Roboto", sans-serif;
  width: 150px;

  background-color: rgb(127 181 127);
  border-radius: 10px;
}

.delete-icon {
  font-family: "Roboto", sans-serif;
  width: 150px;

  background-color: rgba(248, 198, 126, 1);
  border-radius: 10px;
}

#view-deck {
  font-family: "Roboto", sans-serif;
  width: 150px;

  background-color: rgb(127 181 127);
  border-radius: 10px;
}

.buttonWrapper {
  display: block;
  margin-left: auto;
  margin-right: auto;
  text-align: center;
  padding-top: 6px;
  margin-bottom: 80px;
}
.deckCard {
  text-align: center;
  font-size: 24px;
  border: 1px solid black;
  background: #ffffff;
  margin-bottom: 30px;
  width: 400px;
  height: 150px;
  display: block;
  margin-left: auto;
  margin-right: auto;

  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.15), 0 10px 0 -10px white,
    0 10px 1px -4px rgba(0, 0, 0, 0.15), 12px 20px 0 -10px white,
    13px 29px 1px -12px rgba(0, 0, 0, 0.15), 15px 25px 0 -10px white,
    14px 39px 1px -14px rgba(0, 0, 0, 0.15);
}

.page {
  position: absolute;
  z-index: -3;

  background-image: linear-gradient(
    0deg,
    rgb(239 128 102 / 79%) 40%,
    rgba(3 83 99 / 2%) 64%
  );
  min-height: 100%;
  min-width: 1024px;

  width: 100%;
  height: auto;

  top: 0;
  left: 0;
}

.clip-text {
  font-size: 5em;
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
  text-align: center;
  position: relative;
  padding-top: 50px;
}

.createDeck {
  display: flex;
  justify-content: center;
  align-items: center;

  position: absolute;
  top: 50%;
  left: 50%;
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  font-family: "Roboto", sans-serif;
  width: 400px;
  height: 50px;

  background-color: skyblue;
  border-radius: 10px;
  margin-top: 20px;
  margin-bottom: 90px;
}
.add {
  white-space: nowrap;
  position: absolute;

  right: 45%;
  left: 50%;
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
}
.container {
  margin-top: 70px;
}

button:hover {
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
}
</style>





