const { default: api } = require("../../config/AxiosConfig")

const getBrandRecommendByCateId=async(cateoryId)=>{
    return await api.get("/common/recommendbrand?categoryIds="+cateoryId).then(v=>{
        return v.data.data
    }).catch(error=>{
        return []
    })
}


const getPropertyRecommendByCateId=async(cateoryId)=>{
    return await api.get("/common/recommendproperty?categoryId="+cateoryId).then(v=>{
        return v.data.data
    }).catch(error=>{
        return []
    })
}


module.exports={
    getBrandRecommendByCateId:getBrandRecommendByCateId,
    getPropertyRecommendByCateId:getPropertyRecommendByCateId
}