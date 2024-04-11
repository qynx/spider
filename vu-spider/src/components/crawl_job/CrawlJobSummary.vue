<script lang="ts" setup>

import {useRouter} from "vue-router";
import CrawlJobSummary from "./CrawlJobSummary.vue";
import {ref} from "vue";
import {summaryCrawlJobApi, pageScheduleApi} from "../../api/CrawlJobApi";
import {CrawlJobSummaryVo, ScheduleQuery, ScheduleVO} from "../../api/BaseVoDef";
import CrawlJobNameRender from "./CrawlJobNameRender.vue";
import ScheduleStatusRender from "../tags/ScheduleStatusRender.vue";

const router = useRouter()
const id = router.currentRoute.value.query.id

const summary = ref<CrawlJobSummaryVo>()
const ds = ref<Array<ScheduleVO>>()
const query = ref<ScheduleQuery>({current: 1, pageSize: 10})
query.value.jobId = id

const page = ref(
    {
      current: query.value.current,
      pageSize: query.value.pageSize,
      total: 0,
      showSizeChanger: true,
      showTotal: (t) => `total ${t} items`,
      onChange: async (p, c) => doQ(p, c),
      onShowSizeChange: async (p, c) => doQ(p, c)
    }
)

const cols = [
  {
    "key": "id",
    "dataIndex": "id",
    "title": "id"
  },
  {
    "key": "status",
    "dataIndex": "status",
    "title": "status"
  },
  {
    "key": "time",
    "dataIndex": "scheduleTimeStr",
    "title": "time"
  }
]

const init = async function () {
  summary.value = await summaryCrawlJobApi(id)
  await doQ(query.value.current, query.value?.pageSize)
}

const doQ = async (current: number, pageSize: number) => {
  query.value.current = current
  query.value.pageSize = pageSize
  const res = await pageScheduleApi(query.value)
  ds.value = res.records
  page.value.total = res.total
}


init()

</script>

<template>
  <a-layout :style="{padding: '15px'}">
    <a-empty v-if="summary == null">

    </a-empty>
    <a-layout v-if="summary">
      <a-card :title="'调度明细'">
        <h3>{{ '总调度次数🔥 ' + summary.totalScheduleCount }}</h3>
        <h3>运行中🔥 {{ summary.runningScheduleCount }}</h3>
      </a-card>
    </a-layout>

    <a-divider/>
    <a-empty v-if="ds.length <= 0" description="还未调度过...">

    </a-empty>
    <a-table  :data-source="ds" :columns="cols" :pagination="page" v-if="ds.length > 0">
      <template #bodyCell="{ column, record }">

        <template v-if="column.key =='status'">
          <ScheduleStatusRender :job="record"></ScheduleStatusRender>
        </template>

      </template>
    </a-table>
  </a-layout>
</template>