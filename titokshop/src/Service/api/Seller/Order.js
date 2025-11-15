import api from "../../../config/AxiosConfig";

const searchOrderSaler = async (filter) => {
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
 
module.exports={searchOrderSaler}