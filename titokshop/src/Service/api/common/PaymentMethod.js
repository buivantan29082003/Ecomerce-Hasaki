import api from "../../../config/AxiosConfig"

const getPaymentMethods=async ()=>{
    return await api.get("/common/paymentmethods").then(v=>{
        return v.data.data
    }).catch(error=>{
        return []
    })
}



export default getPaymentMethods