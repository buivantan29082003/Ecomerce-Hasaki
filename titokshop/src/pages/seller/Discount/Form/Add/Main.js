import { BiChevronLeft } from "react-icons/bi";
import { useParams } from "react-router-dom";
import BaseInfo from "./BaseInfo";
import ProductApply from "./ProductApply";
import { useState } from "react";
import { greenMainColor } from "../../../../../config/TextColor";
import { autoAlertError } from "../../../../../Service/HandleAlert";
import api from "../../../../../config/AxiosConfig";
import IconSpinner from "../../../../../components/Base/IconSpinner";

const MainDiscount = () => {
  const [voucher, setVoucher] = useState(() => {
    const now = new Date();
    const start = new Date(now.getTime() + 30 * 60 * 1000);
    const end = new Date(start.getTime() + 60 * 60 * 1000);
    return {
      promotionName: "",
      startDate: start,
      endDate: end,
    };
  });
  const [loading,setLoading]=useState(false)
  const params = useParams();
  const [productMap, setProductMap] = useState(new Map());
  const onsubmit = () => {
    let mapResult = new Map();
    productMap.forEach((v, k) => {
      mapResult.set(
        k,
        v.productVariants.map((v) => {
          return {
            isLimitUse: v.isLimitUse,
            limitUse: v.limitUse,
            productVariantId: v.id,
            discountValue: v.discountValue,
            isApply: v.isApply,
          };
        })
      );
    });
    console.log(mapResult);
    let urlPlus = "";
    if (params.type != null && params.type === "product") {
      urlPlus = "product";
    } else if (params.type != null && params.type === "flashsale") {
      urlPlus = "falshsale";
    } else {
      autoAlertError(
        "Thông tin không hợp lệ !",
        "Type của promotion không hợp lệ"
      );
      return;
    }
    setLoading(true)
    api
      .post("/sale/promotion/add/" + urlPlus, {
        promotion: voucher,
        productIds: Object.fromEntries(mapResult),
      })
      .then((v) => {
        alert("Thêm thành công");
      })
      .catch((error) => {
        autoAlertError("Dữ liệu", error.response.data?.message);
      }).finally(()=>{
        setLoading(false)
      })
  };
  const onchangeProductMap = (product) => {
    product.forEach((v) => {
      v.productVariants.forEach((element) => {
        element.discountValue = 1;
        element.isLimitUse = 0;
        element.limitUse = 1;
        element.isApply = true;
      });
    });
    setProductMap((prev) => {
      const newMap = new Map(prev);  
      product.forEach((v) => {
        newMap.set(v.id, v);
      });
      return newMap;
    });
  };

  const deleteProduct = (key) => {
    setProductMap((prev) => {
      const newMap = new Map(prev);
      newMap.delete(key);
      return newMap;
    });
  };

  const onchangeProp = (value, key) => {
    setVoucher({ ...voucher, [key]: value });
  };
  return (
    <>
      <div className="flex gap-5">
        <p className="flex items-center text-gray-500 ml-5 font-semibold">
          <BiChevronLeft /> <span>Manage Discount</span>
        </p>
        <div
          onClick={() => {
            onsubmit();
          }}
          className={`font-semibold flex gap-3 items-center text-white px-3 py-1 cursor-pointer rounded-sm bg-${greenMainColor}`}
        >
         {loading===true&&<IconSpinner/>}  <span> Đồng ý và đăng</span>
        </div>

        {/* <IconSpinner/> */}
      </div>
      <BaseInfo
        onchangeProp={onchangeProp}
        voucher={voucher}
        setVoucher={setVoucher}
        productMap={productMap}
        setProductMap={setProductMap}
      />

      <ProductApply
        deleteProduct={deleteProduct}
        onchangeProductMap={onchangeProductMap}
        productMap={productMap}
        voucher={voucher}
        setVoucher={setVoucher}
      />
    </>
  );
};
export default MainDiscount;
