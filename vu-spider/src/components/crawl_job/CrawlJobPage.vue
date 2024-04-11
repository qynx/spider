<script setup lang="ts">

import {ref} from "vue";
import {CrawlJobQuery, CrawlJobVO} from "../../api/BaseVoDef";
import {
  delCrawlJobApi,
  pageCrawlJobApi,
  recoverCrawlJobApi,
  runCrawlJobApi,
  suspendCrawlJobApi
} from "../../api/CrawlJobApi";
import {useRouter} from "vue-router";
import {message} from "ant-design-vue";
import CrawlJobNameRender from "./CrawlJobNameRender.vue";
import CrawlJobCronRender from "./CrawlJobCronRender.vue";
import {DownOutlined,} from '@ant-design/icons-vue';

const router = useRouter()
const ds = ref<Array<CrawlJobVO>>([])
const query = ref<CrawlJobQuery>({
  current: 1,
  pageSize: 5,
})
const loading = ref(false)

const page = ref({
  current: 1,
  pageSize: 5,
  total: 0,
  showSizeChanger: true,
  showTotal: (t) => `total ${t} items`,
  onChange: async (p, c) => change(p, c),
  onShowSizeChange: async (p, c) => change(p, c)
})

const columns = [
  {
    "title": "name",
    "key": "name",
    "dataIndex": "name",
  },
  {
    "title": "cron",
    "key": "cron",
    "dataIndex": "cron",
  },
  {
    "title": "source",
    "key": "sourceEnum",
    "dataIndex": "sourceEnum"
  }
  ,
  {
    "title": "startUrl",
    "key": "startUrl",
    "dataIndex": "startUrl"
  },
  {
    "title": "action",
    "key": "action"
  }
]


const i2 = async function (current: number, pageSize: number) {
  query.value.current = current
  query.value.pageSize = pageSize
  loading.value = true
  try {
    const rsp = await pageCrawlJobApi(query.value)
    ds.value = rsp.records
    page.value.total = rsp.total
    page.value.current = rsp.current
    page.value.pageSize = pageSize
  } finally {
    loading.value = false
  }
}

const init = async function () {
  await i2(page.value.current, page.value.pageSize)
}


const change = async function (current: number, pageSize: number) {
  await i2(current, pageSize)
}

init()

const toSave = async function (id: string | null) {
  if (!id) {
    await router.push({
      name: "CrawlJobSave",
    })
    return
  }
  await router.push({
    name: "CrawlJobSave",
    query: {
      "id": id
    }
  })
}

const start = async function (id: string) {
  await runCrawlJobApi(id)
  message.info("运行成功 请等待")
}

const suspend = async function (id: string) {
  await suspendCrawlJobApi(id)
  message.info("suspend成功")
}

const del2 = async function (id: string) {
  console.log("enter del")
  await delCrawlJobApi(id)
  message.error("删除成功")
}

const recover = async function (id: string) {
  await recoverCrawlJobApi(id)
  message.info("恢复成功")
}

const summaryPage= async function (id:string) {
  await router.push({
    name: "CrawlSummaryPage",
    query: {
      id: id
    }
  })
}

</script>

<template>
  <a-layout :style="{padding: '16px'}">

    <a-space>
      <a-input placeholder="name"></a-input>
      <a-button type="primary" @click="init" :loading="loading">Search</a-button>
      <a-button @click="toSave(null)" :loading="loading">Add</a-button>
    </a-space>

    <a-divider></a-divider>

    <a-table :data-source="ds" :columns="columns" :pagination="page">
      <template #bodyCell="{ column, record }">

        <template v-if="column.key =='name'">
          <CrawlJobNameRender :job="record"></CrawlJobNameRender>
        </template>

        <template v-else-if="column.key =='cron'">
          <CrawlJobCronRender :job="record"></CrawlJobCronRender>
        </template>

        <template v-if="column.key == 'action'">
          <n-button type="success" quaternary @click="start(record.id)">Start</n-button>
          <n-button type="success" quaternary @click="summaryPage(record.id)">View</n-button>
          <n-button type="info" quaternary @click="toSave(record.id)">Edit</n-button>
          <a-dropdown>
            <a class="ant-dropdown-link">
              More
              <down-outlined/>
            </a>
            <template #overlay>
              <a-menu>
                <a-menu-item>
                  <n-button type="info" quaternary @click="recover(record.id)">Recover</n-button>
                </a-menu-item>
                <a-menu-item>
                  <n-button type="warning" quaternary @click="suspend(record.id)">Suspend</n-button>
                </a-menu-item>
                <a-menu-item>
                  <n-button type="error" quaternary @click="del2(record.id)">Delete</n-button>
                </a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </template>
      </template>
    </a-table>


  </a-layout>

</template>