<template>
    <div class="container">
        <div class="text-container">
            <h1>Harvest Builder</h1>

            Name: <input v-model="harvest.name" type="text"/><br />

            Hunger Provided: <input v-model.number="harvest.hungerProvided" type="number"/><br />

            Saturation Modifier: <input v-model.number="harvest.saturationModifier" type="number"><br />

            Growth Type:
            <select v-model="harvest.growthType">
                <option value="NONE">None</option>
                <option value="CROP">Crop</option>
                <option value="BUSH">Bush</option>
            </select>

            <span v-if="harvest.growthType == 'CROP'"><CropGrowth v-bind:default="harvest.cropGrowth" v-model="harvest.cropGrowth"></CropGrowth></span>
            <span v-else-if="harvest.growthType == 'BUSH'"><BushGrowth v-bind:default="harvest.bushGrowth" v-model="harvest.bushGrowth"></BushGrowth></span>

            Consumption Style:
            <select v-model="harvest.consumptionStyle">
                <option value="NONE">None</option>
                <option value="EAT">Eat</option>
                <option value="DRINK">Drink</option>
            </select>

            Always Edible: <input v-model="harvest.alwaysEdible" type="checkbox"><br />

            Time to Eat: <input v-model.number="harvest.timeToEat" type="number"><br />

            Ore Dictionary Names
            <form v-on:submit.prevent="addNewOreDict">
                <label>Add a new name</label> <input v-model="newOreDictName" id="newOreDictName"/>
                <button>Add</button>
            </form>
            <ul>
                <li v-for="(name, index) in harvest.oreDictionaryNames">
                    {{name}}
                    <button v-on:click="harvest.oreDictionaryNames.splice(index, 1)">Remove</button>
                </li>
            </ul>

            Effects
            <form v-on:submit.prevent="addNewEffect">
                <label>Add a new effect</label>  <EatenEffect v-model="newEffect"></EatenEffect>
                <button>Add</button>
            </form>
            <ul>
                <li v-for="(effect, index) in harvest.effects">
                    {{effect.potion}} | {{effect.amplifier}} | {{effect.duration}} | {{effect.chance}}
                    <button v-on:click="harvest.effects.splice(index, 1)">Remove</button>
                </li>
            </ul>

            <button v-on:click="output = JSON.stringify(harvest, null, 2)">Generate</button>

            <span v-if="output != ''">
                <div class="output">
                    {{output}}
                </div>
                <button v-on:click="output = ''">Clear</button>
            </span>
        </div>
    </div>
</template>

<script>
import CropGrowth from './CropGrowth'
import BushGrowth from './BushGrowth'
import EatenEffect  from './EatenEffect'

export default {
    name: 'Builder',
    components: {
        CropGrowth,
        BushGrowth,
        EatenEffect
    },
    data() {
        return {
            harvest: {
                name: "example_harvest",
                hungerProvided: 4,
                saturationModifier: 0.4,
                growthType: "CROP",
                cropGrowth: {
                    maxProduceDrop: 1,
                    maxSeedDrop: 3,
                    stages: 5,
                    canFertilize: true,
                    minLight: 8,
                    maxLight: 15
                },
                bushGrowth: {
                    maxProduceDrop: 3,
                    minLight: 9,
                    maxLight: 15
                },
                consumptionStyle: "EAT",
                alwaysEdible: false,
                timeToEat: 32,
                oreDictionaryNames: [],
                effects: [
                    {
                        potion: "minecraft:speed",
                        amplifier: 5,
                        duration: 100,
                        chance: 1.0
                    }
                ]
            },
            newOreDictName: "",
            newEffect: {},
            output: ""
        }
    },
    methods: {
        addNewOreDict: function() {
            this.harvest.oreDictionaryNames.push(this.newOreDictName);
            this.newOreDictName = '';
        },
        addNewEffect: function() {
            this.harvest.effects.push(this.newEffect);
            this.newEffect = {};
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
