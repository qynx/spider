<script lang="ts">


import {defineComponent} from "vue";
import App from "./App.vue";

export default defineComponent({
  components: {App}
})
</script>

<template>

  <n-message-provider>
    <App></App>
  </n-message-provider>
</template>