<template>
<div class="page">
    <div class="wrapper">
       <div class="clip-text clip-text_thirteen clip-text--cover">
      <h3>Create a Flash Card!</h3> 
      </div>
    </div>
      <form v-on:submit.prevent> 
       <label for="question"> Add the front of your flash card...
           (This can be a question, a prompt, a vocabulary word, etc.) </label>

       <input v-model="card.question" type="text" id="question"/>

       <label for="answer"> Add the back of your flash card...
           (This can be an answer, the response, the definition, etc.) </label>

       <input v-model="card.answer" type="text" id="answer"/>
       <div class="create">
       <button type="submit" @click="newCard()">Create New Flash Card</button>
        </div>
      </form>
  </div>
</template>

<script>
import authService from '../services/AuthService';
export default {
    name: 'add-card',
    data(){
        return{
            card: {
                userID: this.$store.state.user.id,
                deckID: this.$store.state.currentDeckID,
                question: '',
                answer: '',
                rank: 0
                
        }
    };
    },
    methods: {
        newCard() {
            authService
            .addCard(this.card)
            .then(response => {
                if (response.status == 201) {
                    this.$router.push({name: "deck-with-cards", params: {currentDeckId: this.currentDeckID}});
                    location.reload();
                }
            })
             .catch(error => {
                console.error(error);
            });
        }
    }




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
.wrapper {
  text-align: left;
  padding-top: 30px;
}
/* Clip text element */
.clip-text {
  font-size: 6em;
  font-weight: bold;
  line-height: 1;
  position: relative;
  display: inline-block;
  margin: 0em;
  padding: 0.5em 0.75em;
  text-align: center;
  color: rgb(0, 0, 0);
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
  background-color: rgba(0, 0, 0, 0.699);
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
.create {
font-family: "Roboto", sans-serif;
white-space: nowrap;
position: absolute;
/* left: 65%; */
}
input [type=text] {
  font-family: "Roboto", sans-serif;
  width: 100%;
  padding: 12px;
  margin: 8px 0;
  background: linear-gradient(to right, #ffffff 0%, #fafafa 50%, #ffffff 99%);
  border-radius: 10px;
  box-sizing: border-box;
}
input:hover {
  border: 1px solid #aaa;
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
}
</style>