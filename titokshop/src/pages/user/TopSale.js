import { useEffect, useState } from "react";
import ProductCard from "../../components/user/ProductCartLine";
import LoadingSkeleton from "../../components/Base/Loading";
import { getProductNew } from "../../Service/api/Product";

const TopSale = () => {
  const [product, setProduct] = useState({
    page: 0,
    totalPage: 10,
    content: null,
  });
  useEffect(() => {
    getProductNew().then((v) => {
      console.log(v)
      setProduct(v);
    }).catch(error=>{
      setProduct({...product,content:[]})
    })
  }, []);
  return (
    <> 
      <LoadingSkeleton count={10} isShow={product.content===null}  />
      <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-4 mt-5 w-full">
        
        {product.content!=null&& product.content.map((v, i) => (
          <ProductCard key={i} product={v} />
        ))}
      </div>
    </>
  );
};



<div className="inline-flex mt-4 items-center gap-2 text-lg">
        {/* icon sấm sét */}
        <svg
          className="w-6 h-6 text-red-500"
          viewBox="0 0 24 24"
          aria-hidden="false"
          role="img"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path d="M13.5 2L3 13h7l-1 9L21 11h-7l-.5-9z" fill="currentColor" />
        </svg>

        {/* chữ Flash Sale */}
        <span className="font-bold text-lg tracking-tight">
          <span className="text-red-500 mr-1">Top</span>
          <span className="text-black">New</span>
        </span>
      </div>

export default TopSale;
