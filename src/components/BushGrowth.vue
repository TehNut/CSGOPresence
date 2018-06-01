<template>
    <div class="container">
        <div class="text-container">
            <h3>Bush Growth</h3>

            Max Produce Drop: <input v-model.number="growth.maxProduceDrop" v-on:input="$emit('input', growth)" type="number" name="maxProduceDrop"/><br />
            Max Light: <input v-model.number="growth.maxLight" v-on:input="$emit('input', growth)" type="number" name="maxLight"/><br />
            Min Light: <input v-model.number="growth.minLight" v-on:input="$emit('input', growth)" type="number" name="minLight"/><br />
        </div>
    </div>
</template>

<script>
export default {
      name: 'BushGrowth',
      data() {
          return {
              growth: this.default
          }
      },
      props: [
          'default'
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
