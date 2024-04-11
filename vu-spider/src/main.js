import { createApp } from 'vue'
import './style.css'
import Boot from './Boot.vue'
import {router} from "./routes/route";
import naive from 'naive-ui'
import 'ant-design-vue/dist/reset.css';

import Antd from 'ant-design-vue';

createApp(Boot)
    .use(router)
    .use(naive)
    .use(Antd)
    .mount('#app')
