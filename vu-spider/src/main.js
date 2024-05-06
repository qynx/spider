import { createApp } from 'vue'
import './style.css'
import Boot from './Boot.vue'
import {router} from "./routes/route";
import naive from 'naive-ui'
import 'ant-design-vue/dist/reset.css';
import "echarts"
import ECharts from 'vue-echarts'
// 通用字体
import 'vfonts/Lato.css'
// 等宽字体
import 'vfonts/FiraCode.css'

import Antd from 'ant-design-vue';

createApp(Boot)
    .component("v-chart", ECharts)
    .use(router)
    .use(naive)
    .use(Antd)
    .mount('#app')
