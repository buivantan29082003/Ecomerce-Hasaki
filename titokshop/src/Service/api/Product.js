const { default: api } = require("../../config/AxiosConfig");



const productSearch = async (filter,key) => {
  try { 
    if(key!==""){

    }
    const params = new URLSearchParams(); 
    if (filter.minPrice && filter.maxPrice) {
      params.set("minPrice", filter.minPrice);
      params.set("maxPrice", filter.maxPrice);
    } 
    if(key!==""&&key!==undefined){
      params.set("keyword",key);
    }
    if (filter.typeSort !== undefined) {
      params.set("typeSort", filter.typeSort);
    } 
    if (filter.propertyValueIds && filter.propertyValueIds.size > 0) {
      params.set(
        "propertyValueIds",
        Array.from(filter.propertyValueIds.keys()).join(",")
      );
    } 
    if (filter.brandIds && filter.brandIds.size > 0) {
      params.set("brandId", Array.from(filter.brandIds.keys()).join(","));
    }

    params.set("page", filter.currentPage ?? 0);

    const res = await api.get(`/common/product/search?${params.toString()}`);
    return res.data.data;
  } catch (error) {
    return [];
  }
};


const getProductNew=async ()=>{
  return await api.get("/common/product/new?page=0").then((v) => {
      return v.data.data
  }) 
} 



module.exports = {
  productSearch: productSearch,
  getProductNew:getProductNew
};
