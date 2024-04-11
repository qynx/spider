<script lang="ts" setup>
import {saveCrawlJobApi} from "../../api/CrawlJobApi";
import {ref} from "vue";
import {CrawlJobVO} from "../../api/BaseVoDef";
import {useMessage} from "naive-ui";
import {useRouter} from "vue-router";
import {getCrawlJobApi} from "../../api/CrawlJobApi";

const router = useRouter()

const id = router.currentRoute.value.query.id

const form = ref<CrawlJobVO>({})
const msg = useMessage()

const sbt = async function () {
  await saveCrawlJobApi(form.value)
  msg.success("提交成功")
}

const init = async function () {
  if (id) {
    form.value = (await getCrawlJobApi(id))
  }
}

init()

</script>

<template>

  <a-space direction="vertical">
    <a-input v-model:value="form.name" placeholder="name"></a-input>
    <a-input v-model:value="form.cron" placeholder="cron"></a-input>
    <a-input v-model:value="form.sourceEnum"  placeholder="source"></a-input>
    <a-input v-model:value="form.startUrl" placeholder="startUrl"></a-input>
    <a-button @click="sbt">提交</a-button>
  </a-space>

</template>