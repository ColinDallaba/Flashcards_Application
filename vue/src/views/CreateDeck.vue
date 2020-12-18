<template>
  <div class="page">
    <div class="wrapper">
      <div class="clip-text clip-text_thirteen clip-text--cover">
        Create Deck
      </div>
    </div>
    <form @submit.prevent="newDeck()">
      <input
        type="text"
        id="answer"
        placeholder="Name"
        v-model="deck.name"
        required
      />

      <input
        type="text"
        id="name"
        placeholder="Description"
        v-model="deck.description"
        required
      />
      <button type="submit">Create Deck</button>

      <button type="button" class="createCard" @click="$router.push('/')">
        Cancel
      </button>
    </form>
  </div>
</template>
<script>
import authService from "../services/AuthService";
export default {
  data() {
    return {
      deck: {
        userID: this.$store.state.user.id,
        name: "",
        description: "",
      },
    };
  },
  methods: {
    newDeck() {
      authService
        .addDeck(this.deck)
        .then((response) => {
          console.log("HERE");
          if (response.status == 201) {
            this.$router.push("/");
            location.reload();
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
form {
  text-align: center;
  padding-top: 20px;
  padding-left: 40px;
}
label {
  padding-right: 16px;
}
input {
  font-family: "Roboto", sans-serif;
  width: 200px;
  margin: 20px auto;
  background: rgb(248 200 126);
  border-radius: 10px;
}
input:hover {
  border: 1px solid #aaa;
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
}
#answer {
  margin-right: 30px;
}

#name {
  margin-right: 30px;
}

button {
  font-family: "Roboto", sans-serif;
  width: 250px;
  margin-right: 10px;
  background-color: rgb(127 181 127);
  border-radius: 10px;
}
button:hover {
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
}

.page {
  position: fixed;
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
</style>