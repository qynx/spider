export interface PageVO<T> {
    total: number,
    current: number,
    pageSize: number,
    records: T[],
}


export interface CrawlJobVO {

    id?: string,

    name?: string,

    startUrl?: string,

    cron?: string

    sourceEnum?: string

    status?: string

    nextTriggerTimeStr?: string
}

export interface CrawlJobQuery {
    current?: number,
    pageSize?: number
}

export interface CrawlJobSummaryVo {
    totalScheduleCount?: number
    runningScheduleCount?: number
}

export interface ScheduleVO {
    id?: string,
    scheduleTimeStr?: string,
    status?: string
}

export interface ScheduleQuery {
    jobId?: string,
    current?: number,
    pageSize?: number
}