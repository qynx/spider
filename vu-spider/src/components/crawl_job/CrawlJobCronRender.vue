<script lang="ts" setup>
import {CrawlJobVO} from "../../api/BaseVoDef";

interface  Props {
  job: CrawlJobVO
}

const props = defineProps<Props>()

</script>

<template>
  <n-tooltip trigger="hover">
    <template #trigger>
      <n-tag type="info"> {{props.job.cron}} </n-tag>
    </template>
    下次调度时间 {{props.job.nextTriggerTimeStr}}
  </n-tooltip>
</template>