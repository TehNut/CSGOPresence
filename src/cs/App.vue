<template>
<div id="app">
    <Header></Header>
    <h1>Releases</h1>
    <ul class="no-dot">
        <li v-for="release in releases" v-if="releases">
            <Download :object="release"></Download>
            <br />
        </li>
    </ul>
</div>
</template>

<script>
import Header from './components/Header'
import Download from './components/Download'
import Vue from 'vue'
import Axios from 'axios'

Vue.prototype.$http = Axios

export default {
  name: 'App',
  data() {
      return {
          releases: []
      }
  },
  components: {
      Download,
      Header
  },
  created: function() {
      this.$http.get('https://api.github.com/repos/TehNut/CSGOPresence/releases').then((response) => {
          this.releases = response.data;
      }, (err) => {
          console.log(err)
      });
  }
}
</script>

<style scoped>
#app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
}

.no-dot {
    -webkit-padding-start: 0;
    padding: 0;
    list-style-type: none;
}
</style>
