import {cli} from "./BaseCli";
import {PoetryVO} from "./PoetryVoDef";
import {GraphQuery, GraphRsp} from "./BaseVoDef";


export const reqLogGraphApi = async function (vo: GraphQuery): Promise<GraphRsp> {
    const url = "/api/zix/req_log/graph"
    return (await cli.post(url, vo)).data.data
}