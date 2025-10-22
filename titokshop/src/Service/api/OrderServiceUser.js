const { default: api } = require("../../config/AxiosConfig")

const placeAnOrder=async(order)=>{
    return await api.post("/user/order/add",order).then(v=>{

    }) 
}
 
module.exports={
placeAnOrder:placeAnOrder
}