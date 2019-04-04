<template>
  <div id="app">
    <img src="./assets/logo.png">
    <a-button type="primary">Button</a-button>
    <a-rate :defaultValue="2.5" allowHalf/>
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
