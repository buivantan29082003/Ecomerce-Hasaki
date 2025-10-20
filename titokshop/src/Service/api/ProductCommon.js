const { default: api } = require("../../config/AxiosConfig")
 
const getRecommendByCategoryIds=async(cateoryIds)=>{
    let a=cateoryIds.join(",")
    return await api.get("commond/product/recommand_cate?categoryIds="+a).then(v=>{
        return v.data.data
    }).catch(error=>{
        return {content:[]}
    })
}

const getProductDetail=async(productId)=>{
    return await api.get("/common/product/detail?productId="+productId).then((v) => {
          v.data.data.productImages.push(v.data.data.productImage);
          return v.data.data
    });
}
module.exports={
    getRecommendByCategoryIds:getRecommendByCategoryIds,
    getProductDetail:getProductDetail
}