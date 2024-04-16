
import {createRouter, createWebHashHistory, createWebHistory, RouteRecordRaw} from 'vue-router'
import Zix from "../page/Zix.vue";
import CrawlJobSave from "../components/crawl_job/CrawlJobSave.vue";
import CrawlJobPage from "../components/crawl_job/CrawlJobPage.vue";
import CrawlJobSummary from "../components/crawl_job/CrawlJobSummary.vue";
import PoetryAddPage from "../components/poetry/PoetryAddPage.vue";
import PoetryListPage from "../components/poetry/PoetryListPage.vue";
import BookPage from "../components/book/BookPage.vue";


const routes: RouteRecordRaw[] = [
    {
        path: "/zix",
        component: Zix,
        children: [
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
            }
        ]
    }
]

export const router = createRouter({
    history: createWebHashHistory(),
    routes
})

