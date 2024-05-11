<script lang="ts" setup>

import {MenuOption, NIcon} from "naive-ui";
import {Component, h, ref} from "vue";
import {
  BookOutline as BookIcon,
  PersonOutline as PersonIcon,
  WineOutline as WineIcon
} from '@vicons/ionicons5'

function renderIcon(icon: Component) {
  return () => h(NIcon, null, {default: () => h(icon)})
}

import {useRouter} from "vue-router";

const router = useRouter()

const menuOptions: MenuOption[] = [
  {
    label: "任务",
    key: "job",
    icon: renderIcon(BookIcon),
    children: [
      {
        label: "列表",
        key: "CrawlJobPage",
      },
      {
        label: "保存",
        key: "CrawlJobSave",
      }
    ]
  },
  {
    label: "诗词",
    key: "poetry",
    icon: renderIcon(BookIcon),
    children: [
      {
        label: "列表",
        key: "poetryPage",
      },
      {
        label: "保存",
        key: "poetrySave",
      },
      {
        label: "分布",
        key: "poetryGraph"
      }
    ]
  },
  {
    label: "work",
    key: "work",
    icon: renderIcon(BookIcon),
    children: [
      {
        label: "工时",
        key: "workCheckInGraphPage",
      }
    ]
  }
]

const active = ref("spider")

const updateValue = async (key: string, option: any) => {
  await router.push({
    name: key
  })
}

</script>


<template>
  <n-split :default-size="0.8">
    <template #1>
      <n-menu
          v-model:value="active"
          :options="menuOptions"
          mode="horizontal"
          @update-value="updateValue"
          responsive
      >

      </n-menu>
    </template>
  </n-split>
  <router-view></router-view>
</template>