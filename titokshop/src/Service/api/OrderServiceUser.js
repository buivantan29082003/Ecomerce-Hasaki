const { default: api } = require("../../config/AxiosConfig");

const placeAnOrder = async (order) => {
  return await api.post("/user/order/add", order).then((v) => {});
};

const search = async (filter) => {
  return await api
    .get(
      `/user/order/search?${filter.key === "" ? "" : `key=${filter.key}`}&${
        filter.keyType === "" ? "" : `keyType=${filter.keyType}`
      }&sortBy=${filter.sortBy}&page=${filter.page}&status=${filter.tab}`
    )
    .then((v) => {
      return v.data.data;
    })
    .catch((error) => {
      return {};
    });
};

const getPaymentUrl = async (orderId,methodCode) => {
  return await api
    .get("/user/order/geturlpayment?orderId=" + orderId+"&methodCode="+methodCode)
    .then((v) => {
      return v.data.data;
    });
};

const cancelOrder=async(orderId,reasonCancel)=>{
  return await api.post("/user/order/cancel?orderId="+orderId+"&reasonCancel="+reasonCancel)
}

module.exports = {
  placeAnOrder: placeAnOrder,
  search: search,
  getPaymentUrl: getPaymentUrl,
  cancelOrder:cancelOrder
};
