import {cli} from "./BaseCli";


export const getBookApi = async():Promise<string> => {
    const url = "/api/zix/book/get"
    return (await cli.get(url)).data.data
}