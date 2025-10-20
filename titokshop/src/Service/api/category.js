const { default: api } = require("../../config/AxiosConfig")

const getAllCategories= async ()=>{
    return await api.get("/sale/categories/getall").then(v=>{
      return (v.data.data)
    }).catch(error=>{
        return []
    })
}

const getAllCategoriesWithNotChild= async ()=>{
    return await api.get("common/categories/getall/notchild").then(v=>{
      return (v.data.data)
    }).catch(error=>{
        return []
    })
}


const getRouteCateGory=async(categoryId)=>{
    return await api.get("/common/brandlist?categoryId="+categoryId).then(v=>{
        return v.data.data
    }).catch(v=>{
        return null
    })
}


module.exports={
    getAllCategories:getAllCategories,
    getAllCategoriesWithNotChild:getAllCategoriesWithNotChild,
    getRouteCateGory:getRouteCateGory
}