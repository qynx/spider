<script setup>
import {useMessage} from "naive-ui";

const msg = useMessage()
window.msg = msg
</script>

<template>
  <router-view></router-view>
</template>

<style scoped>
</style>
