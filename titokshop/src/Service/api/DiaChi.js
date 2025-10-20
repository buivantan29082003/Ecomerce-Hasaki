const { default: api } = require("../../config/AxiosConfig")

const addAddressUser=async(address)=>{
    return await api.post("/user/address/add",address,{ 
    headers: {
      "Content-Type": "application/json",
    }
    }).then(v=>{
        return v.data.data
    })
}


const getAllAddress=async()=>{
    return await api.get("/user/address/all").then(v=>{
        return v.data.data
    }).catch(error=>{
        return []
    })
}


module.exports={
    addAddressUser:addAddressUser,
    getAllAddress:getAllAddress
}