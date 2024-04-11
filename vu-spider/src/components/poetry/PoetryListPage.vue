<script setup lang="ts">

import {h, ref} from "vue";
import {PoetryQuery, PoetryVO} from "../../api/PoetryVoDef";
import {listPoetryApi, pushPoetryApi, delPoetryApi} from "../../api/PoetryApi";
import {NEllipsis} from "naive-ui";
import {useRouter} from "vue-router";

const router = useRouter()
const loading = ref(true)

const columns = ref([
  {
    title: 'no',
    dataIndex: 'id'
  },
  {
    title: "诗人",
    dataIndex: "author",
    key: "author"
  },
  {
    title: "诗名",
    dataIndex: "title",
    key: "title"
  },
  {
    title: "内容",
    dataIndex: "content",
    key: "content",
    customRender: ({text, record, idx, col}) => {
      return h(NEllipsis, {
        style: {
          "max-width": "180px",
        }
      }, text)
    }
  },
  {
    title: "操作",
    key: "action"
  }
])
const page = ref({
  current: 1,
  pageSize: 5,
  total: 0,
  showSizeChanger: true,
  showTotal: (t) => `total ${t} items`,
  onChange: async (p, c) => change(p, c),
  onShowSizeChange: async (p, c) => change(p, c)
})
const query = ref<PoetryQuery>({current: page.value.current, pageSize: page.value.pageSize})
const data = ref<Array<PoetryVO>>([])

const init = async function () {
  await i2(page.value.current, page.value.pageSize)
}

const i2 = async function (current: number, pageSize: number) {
  query.value.current = current
  query.value.pageSize = pageSize
  loading.value = true
  try {
    const rsp = await listPoetryApi(query.value)
    data.value = rsp.records
    page.value.total = rsp.total
    page.value.current = rsp.current
    page.value.pageSize = pageSize
  } finally {
    loading.value = false
  }
}

const change = async function (current: number, pageSize: number) {
  await i2(current, pageSize)
}

const push = async function (id: string) {
  await pushPoetryApi(id)
  window.msg.success("推送成功")
}

const del = async function (id: string) {
  await delPoetryApi(id)
  window.msg.success("删除成功")
}

const edit = async function (id?: string) {
  if (!id) {
    await router.push({
      name: "poetrySave"
    })
  } else {
    await router.push({
      name: "poetrySave",
      query: {"id": id}
    })
  }

}

init()

</script>

<template>
  <a-layout :style="{paddingLeft: '5px', paddingTop: '20px'}">
    <a-space>
      <a-input v-model:value="query.title" placeholder="题目"></a-input>
      <a-input v-model:value="query.author" placeholder="作者"></a-input>
      <a-button @click="init()" :loading="loading">查询</a-button>
      <a-button type="primary" @click="e => edit(undefined)">新增</a-button>
    </a-space>
    <a-divider></a-divider>
    <a-table :pagination="page" :columns="columns" :data-source="data">
      <template #bodyCell="{ column, record }">
        <template v-if="column.key == 'action'">
          <a-button @click="() => push(record.id)" type="link">推送</a-button>
          <a-button @click="() => edit(record.id)" type="link">编辑</a-button>
          <n-button quaternary type="error" @click="() => del(record.id)">删除</n-button>
        </template>
      </template>
    </a-table>
  </a-layout>
</template>