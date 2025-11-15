const { default: api } = require("../../config/AxiosConfig");

const searchOrderSaler = async (filter) => {
  return await api
    .get(
      `/sale/order/search?${filter.key === "" ? "" : `key=${filter.key}`}&${
        filter.keyType === "" ? "" : `keyType=${filter.keyType}`
      }&sortBy=${filter.sortBy}&page=${filter.page}&status=${filter.tab}&methodPayment=${filter.payment}`
    )
    .then((v) => {
      return v.data.data;
    })
    .catch((error) => {
      return {};
    });
};



const continueProcess=async(orderId)=>{
  return await api.post("/sale/order/continue?orderId="+orderId)
}

const cancelOrder=async(orderId,reasonCancel)=>{
  return await api.post("/sale/order/cancel?orderId="+orderId+"&reasonCancel="+reasonCancel)
}
module.exports={searchOrderSaler,cancelOrder,continueProcess}