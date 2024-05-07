<script lang="ts" setup>

import {poetryGraphApi} from "../../api/PoetryApi";
import GraphBase from "../graph/GraphBase.vue";
import {ref} from "vue";

const graph = ref(null)

const init = async () => {
  const rsp = await poetryGraphApi({})
  console.log(graph.value)
  graph.value.refresh(rsp)
}

init()

</script>

<template>
  <div style="height: 400px">
   <GraphBase ref="graph"></GraphBase>
  </div>

</template>