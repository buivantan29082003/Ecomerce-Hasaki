import { BiChevronLeft } from "react-icons/bi"
import BaseInfoVoucher from "./BaseInfo"
import VoucherSetting from "./VoucherSetting"
import ProductApply from "./ProductApply"
import { useState } from "react"
import { greenMainColor } from "../../../config/TextColor"
import api from "../../../config/AxiosConfig"
import { useParams } from "react-router-dom"
import { autoAlertError } from "../../../Service/HandleAlert"

const MainVoucher=()=>{
  const [voucher, setVoucher] = useState(() => {
        const now = new Date(); 
        const start = new Date(now.getTime() + 30 * 60 * 1000); 
        const end = new Date(start.getTime() + 60 * 60 * 1000); 
        return {
          voucherName: "",
          voucherCode: "",
          startDate: start,
          endDate: end,
          discountValue: 0,
          limitUsage: 0,
          limitValue: 0,
          isActive: 1,
          minimumPurchase: 0,
          quantityPer:0,
          voucherStyle: "hello",
          voucherType:"PRICE"
        };
    });
    const params = useParams();
    const [productMap,setProductMap]=useState(new Map())
    const onchangeProductMap = (product) => {
  setProductMap((prev) => {
    const newMap = new Map(prev); // copy để React nhận thay đổi
    if (newMap.has(product.id)) {
      newMap.delete(product.id); // nếu đã có thì bỏ
    } else {
      newMap.set(product.id, product); // nếu chưa có thì thêm
    }
    return newMap;
  });
};

    const onchangeProp=(value,key)=>{ 
      setVoucher({ ...voucher, [key]: value })  
    }
    return <>
        
        <div className="flex gap-5">
          <p className="flex items-center text-gray-500 ml-5 font-semibold">
            <BiChevronLeft /> <span>Manage Voucher</span>
          </p> 
          <button onClick={()=>{ 
            let type="common";
            switch (params.type) {
              case "live":
                type="live"
                break;
              case "newcustomer":
                type="newcustomer"
                break;
              case "buyback":
                type="buyback"
                break;
              case "follower":
                type="follower"
                break;
              default:
                type="common"
                break;
            }
            const a={"voucher":voucher,"productIds":Array.from(productMap.keys())}
            api.post("/sale/voucher/add/"+type,a)
            .then(v => {
              alert("Thêm thành công");
            })
            .catch(error => {
                autoAlertError("Dữ liệu",error.response.data?.message) 
            });

          }} className={`font-semibold text-white px-3 py-1 rounded-sm bg-${greenMainColor}`}>Đồng ý và đăng</button>
        </div>
        <BaseInfoVoucher  onchangeProp={onchangeProp} voucher={voucher} setVoucher={setVoucher}/>
        <VoucherSetting onchangeProp={onchangeProp} voucher={voucher} setVoucher={setVoucher}/>
        <ProductApply onchangeProductMap={onchangeProductMap} productMap={productMap} voucher={voucher} setVoucher={setVoucher}/>
    </>
}
export default MainVoucher