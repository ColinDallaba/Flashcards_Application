<template>
  <div>
      <h1>UPDATE</h1> 
     
  <form>
      <label for="question">Enter your new question,  prompt, word, etc...</label>
      <input type="text" id="question" name="question" v-model="card.question"/>
      <label for="answer">Enter your new answer, response, definiton, etc..</label>
      <input type="text" id="answer" name="answer" v-model="card.answer"/> 
      <button type="button" @click="update(card.cardID, card)">Submit Change</button>
  </form>
  </div>
</template>

<script>
import authService from '../services/AuthService.js'
export default {
    data() {
        return {
            card: {
                cardID: '',
                question: '',
                answer: '',
                correct: null,
            },
        };
    },
    created() {
        this.getCardID();
        this.card.cardID = this.$store.state.currentCardID;
    },
  
    methods: {
       getCardID() {
      this.$store.commit("SET_CARD_ID", this.$route.params.cardID);
    },
        update(id, passedCard) {
            authService
            .updateCard(id, passedCard)
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
name: 'edit-card',
}
</script>

<style>

</style>