const { default: api } = require("../../config/AxiosConfig")

const getAllProvices=async()=>{
    return await api.get("/provinces").then(v=>{
        return v.data.data
    }).catch(error=>{
        return []
    })
}

const getDistrictByProvinceId=async(provinceId)=>{
    return api.get("/districts?provinceId="+provinceId).then(v=>{
        return v.data.data
    }).catch(error=>{
        return []
    })
}

const getWardCodeByDistrictId=async(districtId)=>{
    return api.get("/wards?districtId="+districtId).then(v=>{
        return v.data.data
    }).catch(error=>{
        return []
    })
}

module.exports={
    getAllProvices:getAllProvices,
    getDistrictByProvinceId:getDistrictByProvinceId,
    getWardCodeByDistrictId:getWardCodeByDistrictId
}