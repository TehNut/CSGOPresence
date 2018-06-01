<template>
    <div class="container">
        <div class="text-container">
            hello there from {{value.name}}
        </div>
    </div>
</template>

<script>
export default {
      name: 'EatenEffect',
      data() {
          return {
              value = this.value
          }
      },
      props: [
          'value'
      ]
}
</script>

<style scoped>
.container {
    width: 100%;
    height: 200px;
}

.text-container {
    height: 100%;
    color: black;
}
</style>
