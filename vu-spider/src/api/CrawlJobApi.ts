import {cli} from "./BaseCli";
import {CrawlJobQuery, CrawlJobSummaryVo, CrawlJobVO, PageVO, ScheduleQuery, ScheduleVO} from "./BaseVoDef";


export const pageCrawlJobApi = async function (b: CrawlJobQuery): Promise<PageVO<CrawlJobVO>> {
    const url = "/api/zix/crawl_job/page"
    return (await cli.post(url, b)).data.data
}

export const saveCrawlJobApi = async function (b: CrawlJobVO): Promise<CrawlJobVO> {
    const url = "/api/zix/crawl_job/save"
    return (await cli.post(url, b)).data.data
}

export const getCrawlJobApi = async function (id: string): Promise<CrawlJobVO> {
    const url = "/api/zix/crawl_job/get"
    return (await cli.get(url, {params: {"id": id, "time": new Date().getTime()}})).data.data
}

export const runCrawlJobApi = async function (id: string): Promise<CrawlJobVO> {
    const url = "/api/zix/crawl_job/run"
    return (await cli.get(url, {params: {"id": id, "time": new Date().getTime()}})).data.data
}

export const suspendCrawlJobApi = async function (id: string): Promise<CrawlJobVO> {
    const url = "/api/zix/crawl_job/suspend"
    return (await cli.get(url, {params: {"id": id, "time": new Date().getTime()}})).data.data
}

export const delCrawlJobApi = async function (id: string): Promise<CrawlJobVO> {
    const url = "/api/zix/crawl_job/del"
    return (await cli.post(url, {}, {params: {"id": id, "time": new Date().getTime()}})).data.data
}

export const recoverCrawlJobApi = async function (id: string): Promise<CrawlJobVO> {
    const url = "/api/zix/crawl_job/recover"
    return (await cli.post(url, {"id": id}, {params: {"id": id, "time": new Date().getTime()}})).data.data
}

export const summaryCrawlJobApi = async function (id: string): Promise<CrawlJobSummaryVo> {
    const url = "/api/zix/crawl_job/summary"
    return (await cli.get(url,  {params: {"id": id, "time": new Date().getTime()}})).data.data
}

export const pageScheduleApi = async function (query: ScheduleQuery): Promise<PageVO<ScheduleVO>> {
    const url = "/api/zix/schedule/page"
    return (await cli.post(url, query)).data.data
}