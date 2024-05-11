<script setup lang="ts">
import GraphBase from "../graph/GraphBase.vue";
import {workCheckInCurrMonGraphApi} from "../../api/WorkCheckInApi";
import {ref} from "vue";

const base1 = ref(null)

const init = async (): Promise<void> => {
  const rsp = await workCheckInCurrMonGraphApi({});
  rsp.tooltip.formatter = (param:any) => {
    var v =  param.data
    return `${v}m(${(v/60).toFixed(2)}h)`
  }
  base1.value.refresh(rsp)
}

init()
</script>

<template>

  <GraphBase ref="base1"></GraphBase>
</template>