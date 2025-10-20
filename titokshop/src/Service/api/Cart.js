const { default: api } = require("../../config/AxiosConfig");
 
const getAllCart = async () => {
  return await api
    .get("/user/cart/findall")
    .then((v) => {
      return v.data.data;
    })
    .catch((error) => {
      return null;
    });
}; 
const getAllCartNoneGroup=async(productId,quantity)=>{
  return await api
    .get("/user/cart/findall/nonegroup")
    .then((v) => {
      return v.data.data;
    })
    .catch((error) => {
      return null;
    });
}

const addToCart=async(productId,quantity)=>{
  return await api.post(`user/cart/addtocart?productId=${productId}&quantity=${quantity}`)
}


const updateCart=async(productId,quantity)=>{
  return await api.post(`user/cart/update?productId=${productId}&quantity=${quantity}`).then(v=>{
    return v.data.data
  })
}

const getCartInList=async(cartIdsFormat)=>{
  return await api.get("user/cart/findinids?cartIds="+cartIdsFormat).then(v=>{
    return v.data.data
  }).catch(error=>{
    return [];
  })
}

const deleteCart=async(productId)=>{
 return await api.post("user/cart/delete?productId="+productId).then(v=>{
    return null
  })
}

module.exports = {
  getAllCart: getAllCart,
  deleteCart:deleteCart,
  updateCart:updateCart,
  getAllCartNoneGroup:getAllCartNoneGroup,
  addToCart:addToCart,
  getCartInList:getCartInList
};
