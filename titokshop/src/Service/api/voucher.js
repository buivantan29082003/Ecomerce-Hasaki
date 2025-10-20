const { default: api } = require("../../config/AxiosConfig")

const getAllVoucherByShop=async(shopId)=>{
   return await api.get("/common/shop/vouchers?shopId="+shopId).then(v=>{
        return v.data.data
    }).catch(error=>{
        return []
    })
}

const getVoucherByProductId=async (products)=>{
    return await api.post("user/voucher/allvoucherrecommend",products).then(v=>{
        return v.data.data 
    }).catch(error=>{
        return []
    })
}

const claimVoucherCommon=async(voucherId)=>{
    return await api.get("user/voucher/claim/common?voucherId="+voucherId).then(v=>{
        return v.data.data
    })
}

module.exports={
    getAllVoucherByShop:getAllVoucherByShop,
    getVoucherByProductId:getVoucherByProductId,
    claimVoucherCommon:claimVoucherCommon
}

