<script lang="ts" setup>

import {CrawlJobVO} from "../../api/BaseVoDef";

interface  Props {
  job: CrawlJobVO
}

const props = defineProps<Props>()
</script>

<template>
  <span>
    <a-tag v-if="props.job.status =='RUNNING'" color="green">{{props.job.status.substring(0,1).toUpperCase()}}</a-tag>
    <a-tag v-if="props.job.status !=='RUNNING'" color="red">{{props.job.status.substring(0,1).toUpperCase()}}</a-tag>
    {{ props.job.name }}
  </span>
</template>