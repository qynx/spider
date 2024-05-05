import {cli} from "./BaseCli";
import {PoetryQuery, PoetryVO} from "./PoetryVoDef";
import {PageVO} from "./BaseVoDef";

export const listPoetryApi = async function (q: PoetryQuery): Promise<PageVO<PoetryVO>> {
    const url = "/api/zix/poetry/page"
    return (await cli.post(url, q)).data.data
}

export const pushPoetryApi = async function (id: string): Promise<PageVO<PoetryVO>> {
    const url = "/api/zix/poetry/push"
    return (await cli.get(url, {params: {"id": id}})).data.data
}

export const poetryAddApi = async function (vo: PoetryVO): Promise<PoetryVO> {
    const url = "/api/zix/poetry/save"
    return (await cli.post(url, vo)).data.data
}


export const savePoetryBySourceApi = async function (link: string): Promise<PoetryVO> {
    const url = "/api/zix/poetry/save_source"
    return (await cli.post(url, {"link": link})).data.data
}

export const getPoetryApi = async function (id: string): Promise<PoetryVO> {
    const url = "/api/zix/poetry/get"
    return (await cli.get(url, {params: {"id": id}})).data.data
}

export const delPoetryApi = async function (id: string) : Promise<any> {
    const url = "/api/zix/poetry/del"
    return (await cli.post(url, {}, {params: {"id": id}})).data.data
}