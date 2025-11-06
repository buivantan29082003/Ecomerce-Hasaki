import api from "../../config/AxiosConfig"

const getInfoAccount= async()=>{
    return await api.get("/user/account/info").then(v=>{
        return v.data.data;
    })
}

export default getInfoAccount

// module.exports={
//     getInfoAccount:getInfoAccount
// }