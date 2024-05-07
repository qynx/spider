
export interface PoetryVO {
    id?: string,
    title?: string,
    content?: string,
    author?: string
    createdAtStr?: string
}

export interface PoetryQuery {
    id?: string,
    title?: string,
    author?: string,
    current: number,
    pageSize: number,
}

export interface PoetryGraphReq {
    groupBy?: string
}

