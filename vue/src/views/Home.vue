<template>

  <div class="home">
  
    <view-decks />
    
    <!-- <router-view :key="$route.fullPath" class="routerView" /> -->
    <!-- <div class="button">
      
      <button class="edit" @click="$router.push('edit')">Edit Deck</button>
      <button class="delete" @click="$router.push('delete')">Delete Deck</button>
      </div> -->
  </div>

</template>

<script>
import ViewDecks from '@/views/ViewDecks.vue';


export default {
  name: "home",
  components: {
    ViewDecks,
    
 }
};
</script>


