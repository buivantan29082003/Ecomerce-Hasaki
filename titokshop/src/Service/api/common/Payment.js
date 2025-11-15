import api from "../../../config/AxiosConfig"

const getPayments=async()=>{
    return await api.get("common/payments").then(v=>{
        return v.data.data
    }).catch(error=>{
        return []
    })
}

export default getPayments