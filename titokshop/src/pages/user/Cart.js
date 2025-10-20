import HasakiLogo from "./HasakiLogo";
import CheckBox from "../../components/Base/Checkbox";
import { formatVND } from "../../Service/FormatDate";
import { useEffect, useState } from "react";
import { getAllCart } from "../../Service/api/Cart";
import ProductCard from "../../components/user/ProductCartLine";
import { getRecommendByCategoryIds } from "../../Service/api/ProductCommon";
import Carts from "./CartForm";
const Cart = () => {
  const [carts, setCarts] = useState(null);
  const [sumary, setSumary] = useState({
    totalSelected: 0,
    amount: 0,
  });
  const sumTotal = () => {
    let amount = 0;
    carts.forEach((v) => {
      v.items.forEach((vv) => {
        if (vv.isChecked === true) {
          amount += vv.price * vv.quantity;
        }
      });
    });
    setSumary({ ...sumary, amount: amount });
  };
  useEffect(() => {
    getAllCart().then((v) => {
      setCarts(v);
      
    });
  }, []);
  return (
    <div className="w-full  bg-gray-50">
      <div className="py-3 w-full md:w-10/12 mx-auto px-14 text-lg flex gap-3 items-center ">
        <HasakiLogo color={"green"} />
        <p className="text-3xl text-green-700">|</p>
        <p className="text-lg text-green-700">Giỏ hàng</p>
      </div>
      <div className="md:w-10/12 w-full md:px-10 mt-2 mx-auto   ">
        <Carts setSumTotal={sumTotal} carts={carts} />
      </div>
      <div
        className="sticky mt-2 bottom-0 bg-white shadow-[0_-4px_6px_-1px_rgba(0,0,0,0.1)] 
     mb-2 w-full md:w-10/12 mx-auto md:px-14 px-3 py-5 flex justify-between gap-3 items-center"
      >
        <div className="flex gap-4">
          <div className="flex gap-2">
            <CheckBox
              onchange={() => {
                carts.forEach((v) => {
                  v.items.forEach((vv) => {
                    vv.isChecked = true;
                  });
                });
                sumTotal();
              }}
            />
            <p>Chọn tất cả (2)</p>
          </div>
          <p>Xóa tất cả</p>
        </div>
        <div className="flex gap-3 items-center">
          <p>Tổng cộng 3 sản phẩm</p>
          <p className="text-3xl text-[#306e51]">{formatVND(sumary.amount)}</p>
          <button className="text-white text-lg bg-[#306e51] py-2 px-5 rounded-sm">
            Mua hàng
          </button>
        </div>
      </div>
      <div className="py-3 w-full md:w-10/12 mx-auto px-14 text-lg  ">
        <p className="text-gray-700 mb-5">CÓ THỂ BẠN CŨNG THÍCH</p>
        <ListRecommend carts={carts} />
      </div>
    </div>
  );
};
const ListRecommend = ({ carts }) => {
  const [products, setProducts] = useState({
    content: [],
  });

  useEffect(() => {
   if(carts!=null){
     const categoryIds = carts.flatMap((shop) =>
      shop.items.map((item) => item.categoryId)
    );
    if (categoryIds.length > 0) {
      getRecommendByCategoryIds(categoryIds).then((v) => {
        setProducts(v);
      });
    }
   }
  }, [carts]);

  return (
    <>
      <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-3 lg:grid-cols-4 md:gap-4">
        {products.content.map((product, i) => (
          <ProductCard
            patternLink={"/common/product/"}
            key={i}
            product={product}
          />
        ))}
      </div>
    </>
  );
};

export default Cart;
