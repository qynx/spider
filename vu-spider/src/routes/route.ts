
import {createRouter, createWebHashHistory, createWebHistory, RouteRecordRaw} from 'vue-router'
import Zix from "../page/Zix.vue";
import CrawlJobSave from "../components/crawl_job/CrawlJobSave.vue";
import CrawlJobPage from "../components/crawl_job/CrawlJobPage.vue";
import CrawlJobSummary from "../components/crawl_job/CrawlJobSummary.vue";
import PoetryAddPage from "../components/poetry/PoetryAddPage.vue";
import PoetryListPage from "../components/poetry/PoetryListPage.vue";
import BookPage from "../components/book/BookPage.vue";
import ReqLogGraph from "../components/req_log/ReqLogGraph.vue";
import MenuPage from '../page/MenuPage.vue';
import PoetryGraph from "../components/poetry/PoetryGraph.vue";
import WorkCheckInGraphPg from '../components/work/WorkCheckInGraphPg.vue';

const allChildren = [
    {
        path: "poetry_save",
        name: "poetrySave",
        component: PoetryAddPage
    },
    {
        path: "poetry_page",
        name: "poetryPage",
        component: PoetryListPage
    },
    {
        path: "poetry_graph",
        name: "poetryGraph",
        component: PoetryGraph
    },
    {
        path: "crawl_job_save",
        name: "CrawlJobSave",
        component: CrawlJobSave
    },
    {
        path: "crawl_job_page",
        name: "CrawlJobPage",
        component: CrawlJobPage
    },
    {
        path: "crawl_job_summary",
        name: "CrawlSummaryPage",
        component: CrawlJobSummary
    },
    {
        path: "book",
        name: "BookPage",
        component: BookPage
    },
    {
        path: 'req_log_graph',
        name: "ReqLogGraphPage",
        component: ReqLogGraph
    },
    {
        path: "work_check_in_graph",
        name: "workCheckInGraphPage",
        component: WorkCheckInGraphPg
    }
]

const routes: RouteRecordRaw[] = [
    {
        path: "/zix",
        component: Zix,
        children: allChildren,
    },
    {
        path: "/spider",
        component: MenuPage,
        redirect: {name: "poetryGraph"},
        children: allChildren,
    }
]

export const router = createRouter({
    history: createWebHashHistory(),
    routes
})

