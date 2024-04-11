<script lang="ts" setup>

import {ref} from "vue";
import {PoetryVO} from "../../api/PoetryVoDef";
import {getPoetryApi, poetryAddApi, delPoetryApi} from "../../api/PoetryApi";
import {useRouter} from "vue-router";

const form = ref<PoetryVO>({})
const route = useRouter()
const add = async function () {
  await poetryAddApi(form.value)
  if(form.value.id) {
    window.msg.success("update success !")
  } else {
    window.msg.success("add success !")
  }
}

const init = async function () {
  const id = route.currentRoute.value.query.id
  if (id) {
    form.value = await getPoetryApi(id as string)
  } else {

  }
}

init()

</script>

<template>
  <a-layout style="padding: 13px 13px">
    <h3>录入诗词</h3>

    <div :style="{background: 'white', width: '50%', padding: '4px'}">
      <a-space direction="vertical" :style="{width: '300px'}">
        <a-input placeholder="标题" v-model:value="form.title"></a-input>
        <a-input placeholder="作者" v-model:value="form.author"></a-input>
        <a-textarea
            v-model:value="form.content"
            placeholder="内容"
            auto-size
        ></a-textarea>
        <a-button type="primary" @click="add">提交</a-button>
      </a-space>
    </div>

  </a-layout>
</template>