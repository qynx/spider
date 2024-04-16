<script lang="ts" setup>
import {getBookApi} from "../../api/BookApi";
import {ref} from "vue";

const content = ref("")

const init = async() => {
  var c  = await getBookApi()
  content.value = c
}
init()
</script>


<template>

  <a-layout :style="{alignItems: 'center'}">
    <div v-html="content" class="content">
    </div>
  </a-layout>
</template>

<style>
.content {
  font-family: v-sans, system-ui, -apple-system, "system-ui", '宋体',serif;
  background-color: rgb(233, 230, 210);
  font-size: 22px;
  padding: 5px 5px;
  width: 80%;
  align-content: center;
}
</style>