<template>
<div class="download-wrapper">
    <ul class="no-dot">
        <li v-for="(archive, index) in object.assets">
            <div>
                <span v-if="archive.name.endsWith('-all.jar')">Fat jar: </span>
                <span v-else>Slim jar: </span>
                <a :href="archive.browser_download_url">{{archive.name}}</a>
            </div>
            <br v-if="index != object.assets.length - 1"/>
        </li>
    </ul>
</div>
</template>

<script>
export default {
  name: 'Download',
  props: {
      object: {
          type: Object,
          default: function() {
              return {};
          }
      }
  }
}
</script>

<style scoped>
.download-wrapper {
    width: 275px;
    padding: 5px;
    border: 2px solid black;
    margin-left: auto;
    margin-right: auto;
}

.no-dot {
    -webkit-padding-start: 0;
    padding: 0;
    list-style-type: none;
}
</style>
