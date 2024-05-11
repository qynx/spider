import axios from "axios";

export const cli = axios.create({
    baseURL: "http://127.0.0.1:10008"
})

cli.interceptors.response.use(async (rsp) => {
    const data = rsp.data
    if (!data.success) {
        window.msg.warning("接口错误🙅: " + (data.msg || "未知错误❌"))
        return Promise.reject({
            "error": "自定义",
            data: rsp.data
        })
    }
    return rsp;
}, async (err) => {
    if (err.error == "自定义") {
        /* const data = err.data
         window.msg.warning("接口错误🙅: " + data.errorMsg)*/
    } else {
        const data = err.response?.data
        console.log(err.response)
        if (err.message.indexOf("Network Error") > -1) {
            window.msg.error("服务失联啦🥵， 请检查🛜信息")
        } else {
            let msg = data?.msg;
            if (msg) {
                window.msg.error( msg + " 😢" )
            } else {
                window.msg.error("请求错误 ")
            }
        }
    }
    throw err
})
