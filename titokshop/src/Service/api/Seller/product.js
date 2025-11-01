const { default: api } = require("../../../config/AxiosConfig")

const changeStatusProduct=async (productId,statusId)=>{
    return await api.post(`sale/product/update/status?productId=${productId}&status=${statusId}`)
}


module.exports={
    changeStatusProduct:changeStatusProduct
}