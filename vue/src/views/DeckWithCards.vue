<template>
  <div class="cards">
   <!-- <search /> -->
      <card-list />
  </div>
</template>

<script>
import CardList from '../components/CardList.vue'
//import Search from '@/components/Search.vue';
export default {
    name: "deck-with-cards",
    components: {
      //'search': Search,
    'card-list': CardList
 },
}
</script>

<style>

</style>