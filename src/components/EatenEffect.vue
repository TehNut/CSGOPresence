<template>
    <div class="container">
        <div class="text-container">
            <h3>Eaten Effect</h3>

            Potion: <input v-model="effect.potion" v-on:input="$emit('input', effect)"/><br />
            Amplifier: <input v-model.number="effect.amplifier" v-on:input="$emit('input', effect)" type="number"/><br />
            Duration: <input v-model.number="effect.duration" v-on:input="$emit('input', effect)" type="number"/><br />
            Chance: <input v-model.number="effect.chance" v-on:input="$emit('input', effect)" type="number"/><br />
        </div>
    </div>
</template>

<script>
export default {
      name: 'EatenEffect',
      data() {
          return {
              effect: {
                  potion: "minecraft:speed",
                  amplifier: 0,
                  duration: 100,
                  chance: 1.0
              }
          }
      }
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
