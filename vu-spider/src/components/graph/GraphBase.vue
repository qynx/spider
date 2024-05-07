<script lang="js" setup>

import {use} from "echarts/core";//导入use函数 用于注册ecahts所需的渲染器和图标组件
import {CanvasRenderer} from "echarts/renderers";//渲染器 用于绘制图表
import {PieChart} from "echarts/charts";//导入饼图
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent
} from "echarts/components";//导入一些组件 用于显示图标的标题 提示框 不同系列的标识和名称
import VChart, {THEME_KEY} from "vue-echarts"; //引入vchart组件和主题 主题可以自定义图标的样式
import {ref, provide} from "vue";

import {reqLogGraphApi} from "../../api/ReqLogApi";
import {poetryGraphApi} from "../../api/PoetryApi";

use([
  CanvasRenderer,
  PieChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent
]);//通过use来注册所需要的渲染器和图表组件可以确保在渲染的时候使用这些组件

provide(THEME_KEY, "light");//提供主题 dark暗色主题 light亮色


var data = {
  title: {
    text: "",
    left: "center"
  },
  series:  []
}
const option = ref(data);

const mycharts = ref(null)

const init = async () => {
  const rsp = await poetryGraphApi({})
  data.series = rsp.series
  option.value = data
  //option.value.xAxis.data = rsp.bar.hx
  //option.value = option.value
  //charts.value.set(option.value)
  console.log(option.value.series)
  mycharts.value.setOption(data)
}

const refresh = async function(option) {
  mycharts.value.setOption(option)
}
defineExpose({ refresh })

</script>

<template>
  <div style="height: 400px">
    <v-chart style="height: 380px" autoresize :loading="false" :option="option" ref="mycharts">
    </v-chart>
  </div>

</template>