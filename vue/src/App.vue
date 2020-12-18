<template>
  <div id="app">
    <div id="nav">
      <img
        class="logo"
        src="https://st2.depositphotos.com/5040187/9455/v/950/depositphotos_94550400-stock-illustration-monogram-negative-space-letter-logo.jpg"
      />
      <ul>
        <li class="home">
          <router-link
            v-bind:to="{ name: 'home' }"
            v-if="$store.state.token != ''"
          >
            Home</router-link
          >&nbsp;|&nbsp;
        </li>
        <li class="logout">
          <router-link
            v-bind:to="{ name: 'logout' }"
            v-if="$store.state.token != ''"
            >Logout</router-link
          >
        </li>
      </ul>
    </div>
    <transition name="fade" mode="out-in">
      <router-view />
    </transition>
  </div>
</template>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition-duration: 0.3s;
  transition-property: opacity;
  transition-timing-function: ease;
}

.fade-enter,
.fade-leave-active {
  opacity: 0;
}
ul {
  margin: 0;
  padding: 5px;
  list-style-type: none;
  background-color: #000;
  opacity: 0.8;
}

li {
  display: inline;
}

ul li a {
  text-decoration: none;
  padding: 0.2em 1em;
  color: #fff;
  background-color: #000;
}

ul li a:hover {
  color: #000;
  background-color: #fff;
}
.logo {
  position: absolute;
  right: 0;
  top: 0;
  display: block;
  height: 34px;
  width: 100px;
  z-index: 1;
}
</style>
