<template>
   <div class="search-box">
       <td><input type="text" id="questionFilter" v-model="filter.question"/></td>
       <tr v-for="card in filteredList" v-bind:key="card.cardID" >
        
        
       
      </tr>
    </div>
</template>

<script>
export default {
    name: "Search",
  data() {
    return {
      filter: {
        question: '',
        answer: ''
      },
      search: this.search || ""
    };
  },
   computed: {
    filteredList() {
      let filteredCards = this.$store.state.cards;
      if( this.filter.question != "" ) {
        filteredCards = filteredCards.filter(card => card.question.toLowerCase().includes(this.filter.question.toLowerCase()))
      } 
      if( this.filter.answer != "" ) {
        filteredCards = filteredCards.filter(card => card.answer.toLowerCase().includes(this.filter.answer.toLowerCase()))
      }
      return filteredCards;
    },
  methods: {
    searchCards() {
      this.$emit("searchTrigger", this.search);
    }
  }
  },
}
</script>
<style  scoped>

</style>