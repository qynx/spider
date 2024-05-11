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
    downloadCnt?: number
}

export interface ScheduleVO {
    id?: string,
    scheduleTimeStr?: string,
    status?: string
    processRatio?: string
}

export interface ScheduleQuery {
    jobId?: string,
    current?: number,
    pageSize?: number
}

export interface GraphBarVO {
    type?: string,
    title?: string,
    vy?: Array<string>,
    hx?: Array<string>,
    vyInt: Array<number>
}

export interface GraphQuery {
    dateStart?: string,
    dateEnd?: string,
}

export interface GraphItemVO {
    value?: any,
    name?: string
}

export interface GraphSeriesVO {
    name?: string,
    data?: Array<any>
    type?: string
    radius?: string
}

export interface GraphToolTipVo {
    trigger?: string,
    formatter?: any,
}

export interface GraphRsp {
    tooltip: GraphToolTipVo,
    bar?: GraphBarVO;
    series?: Array<GraphSeriesVO>
}