<template>
  <div class="container">
    <div class="wrapper">
      <div class="clip-text clip-text_thirteen clip-text--cover">
        Create a Card!
      </div>
    </div>
    <div class="elements-on-page">
      <form v-on:submit.prevent>
        <label for="question"></label>
        <input
          v-model="card.question"
          type="text"
          id="question"
          placeholder="Add a front!(question, prompt, word, etc...)"
        />
        <label for="answer"></label>
        <input
          v-model="card.answer"
          type="text"
          id="answer"
          placeholder="Add a back!(answer, response, definition, etc...)"
        />
        <div class="button">
          <button type="submit" @click="newCard()">Create Card</button>
          <button
            id="view-deck"
            type="button"
            @click="
              $router.push({
                name: 'deck-with-cards',
                params: { deckID: $store.state.currentDeckID },
              })
            "
          >
            Cancel
          </button>
        </div>
      </form>
    </div>
  </div>
</template> 

<script>
import authService from "../services/AuthService";
export default {
  name: "add-card",
  data() {
    return {
      card: {
        userID: this.$store.state.user.id,
        deckID: this.$store.state.currentDeckID,
        question: "",
        answer: "",
        rank: 0,
      },
    };
  },
  methods: {
    newCard() {
      authService
        .addCard(this.card)
        .then((response) => {
          if (response.status == 201) {
            this.$router.push({
              name: "deck-with-cards",
              params: { deckID: this.card.deckID },
            });
          }
        })
        .catch((error) => {
          console.error(error);
        });
    },
  },
};
</script>

<style scoped>
input[type="text"] {
  text-align: center;
  font-size: 24px;
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
  margin-top: 50px;
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

#question {
  border: 1px solid black;
  background: #ffffff;
  width: 400px;
  height: 150px;

  display: block;
  margin-left: auto;
  margin-right: auto;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.15), 0 10px 0 -10px white,
    0 10px 1px -4px rgba(0, 0, 0, 0.15), 12px 20px 0 -10px white,
    13px 29px 1px -12px rgba(0, 0, 0, 0.15), 15px 25px 0 -10px white,
    14px 34px 0px -14px rgba(0, 0, 0, 0.15);
}

#answer {
  border: 1px solid black;
  background: #ffffff;
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

button {
  font-family: "Roboto", sans-serif;
  width: 150px;
  background-color: rgb(127 181 127);
  border-radius: 10px;
  display: block;
  margin-left: auto;
  margin-right: auto;
  margin-top: 30px;
}
button:hover {
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
}

::-webkit-input-placeholder {
  white-space: pre-line;
  position: relative;
  top: -7px;
}

text {
  align-content: center;
}
</style>