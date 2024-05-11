import {cli} from "./BaseCli";
import {GraphQuery, GraphRsp} from "./BaseVoDef";


export const workCheckInCurrMonGraphApi = async function (vo: GraphQuery): Promise<GraphRsp> {
    const url = "/api/zix/work/check_in/mul_month/graph"
    return (await cli.post(url, vo)).data.data
}