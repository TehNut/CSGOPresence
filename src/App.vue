<template>
<div id="app">
    <Header></Header>
    <br />
    <Builder></Builder>
</div>
</template>

<script>
import Header from './components/Header'
import Builder from './components/Builder'
import Vue from 'vue'
import Axios from 'axios'

export default {
  name: 'App',
  data() {
      return {
      }
  },
  components: {
      Header,
      Builder
  }
}
</script>

<style scoped>
</style>
