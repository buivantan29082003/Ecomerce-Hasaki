const { default: api } = require("../../../config/AxiosConfig")

const changeStatus=async (voucherId,statusCode)=>{
    return await api.post(`/sale/voucher/status/update?voucherId=${voucherId}&statusCode=${statusCode}`)
}

module.exports={
    changeStatus:changeStatus
}