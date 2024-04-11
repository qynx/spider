<script setup lang="ts">
import {ScheduleVO} from "../../api/BaseVoDef";

interface  Props {
  job: ScheduleVO
}

const props = defineProps<Props>()

const mapColor = (status:string) => {
  if (status == 'FAIL') {
    return 'red'
  }
  if (status == 'SUCCESS') {
    return 'green'
  }
  if (status == 'RUNNING') {
    return  'yellow'
  }
  return null
}
</script>

<template>
    <a-tag :color="mapColor(props.job.status)">{{props.job.status}}</a-tag>
</template>