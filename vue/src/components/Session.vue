<template>
  <div class="page">
    <div class="wrapper">
      <div class="clip-text clip-text_thirteen clip-text--cover">
        Study Session
      </div>
    </div>
    <div class="containers">
      <div class="child">
      <div class="flip-card">
        <div class="flip-card-inner">
          <div class="flip-card-front">
            <div>{{ this.$store.state.cards[currentCardIndex].question }}</div>
          </div>
          <div class="flip-card-back">
            <div>
              {{ this.$store.state.cards[currentCardIndex].answer }}
            </div>
          </div>
        </div>
      </div>
        </div>
    </div>
  <div class="buttons">
    <button type="button" v-on:click="getNextCard()">
      Next Card
    </button>

    <button type="button" v-on:click="markCorrect()">Mark Correct</button>

    <button type="button" v-on:click="endSession()">End Session</button>

    <button
      type="button"
      class="viewResults"
      @click="$router.push('/view-results')"
    >
      View Results
    </button>
    </div>
  </div>
</template>

<script>
import authService from "../services/AuthService";
export default {
  data() {
    return {
      currentCardIndex: 0,
      isCorrect: false,
      correctNumber: 0,
    };
  },
  created() {
    this.$store.commit("SET_NUMBER_OF_CORRECT", this.correctNumber);
  },
  methods: {
    changeCardIndex() {
      if (this.currentCardIndex + 1 <= this.$store.state.cards.length - 1) {
        this.currentCardIndex += 1;
      } else {
        this.endSession();
      }
    },
    toggleCard(card) {
      card.flipped = !card.flipped;
    },
    markCorrect() {
      this.isCorrect = true;
    },
    getNextCard() {
      if (this.isCorrect == true) {
        this.update(this.$store.state.cards[this.currentCardIndex].cardID);
        this.correctNumber += 1;
      }
      this.$store.commit("SET_NUMBER_OF_CORRECT", this.correctNumber);
      this.changeCardIndex();
      this.isCorrect = false;
    },
    endSession() {
      this.$router.push("/view-results");
    },
    update(id) {
      authService
        .markCardCorrect(id)
        .then((response) => {
          if (response.status === 200) {
          alert("Deck updated!");
        }
        })
        .catch((error) => {
          console.error(error);
        });
    },
  },
  computed: {},
};
</script>

<style scoped>

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

.wrapper {
  text-align: center;
  position: relative;
  padding-top: 50px;
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

/* The flip card container - set the width and height to whatever you want. We have added the border property to demonstrate that the flip itself goes out of the box on hover (remove perspective if you don't want the 3D effect */
.flip-card {
  background-color: transparent;
  width: 700px;
  height: 350px;
  perspective: 1000px; /* Remove this if you don't want the 3D effect */
}

/* This container is needed to position the front and back side */
.flip-card-inner {
  position: absolute;
  width: 100%;
  height: 100%;
  text-align: center;
  transition: transform 0.8s;
  transform-style: preserve-3d;
}

/* Do an horizontal flip when you move the mouse over the flip box container */
.flip-card:hover .flip-card-inner {
  transform: rotateY(180deg);
}

/* Position the front and back side */
.flip-card-front,
.flip-card-back {
  text-align: center;
  position: absolute;
  width: 100%;
  height: 100%;
  -webkit-backface-visibility: hidden; /* Safari */
  backface-visibility: hidden;
}

/* Style the front side (fallback if image is missing) */
.flip-card-front {
  border-radius: 25px;
  background-color: rgb(106, 90, 205);
  color: rgba(248, 198, 126, 1);
}

/* Style the back side */
.flip-card-back {
  border-radius: 25px;
  background-color: rgba(248, 198, 126, 1);
  color: rgb(106, 90, 205);
  transform: rotateY(180deg);
}

button {
  font-family: "Roboto", sans-serif;
  width: 150px;
  margin-right: 10px;
  background-color: rgb(127, 181, 127);
  border-radius: 50px;
}

button:hover {
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
  text-align: center;
}

.buttons {
  text-align: center;
  padding-top: 50px;
  padding-left: 40px;
}

.containers {
  display: flex;
  justify-content: center;
  align-items: center;
}

.childer {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
</style>