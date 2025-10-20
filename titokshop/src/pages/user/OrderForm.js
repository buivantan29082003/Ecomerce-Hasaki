import LoadingSkeleton from "../../components/Base/Loading";
import ShopGroupOrder from "./OrderCard";
 
const OrderForm= ({ carts, setSumTotal ,isLive}) => {
  return (
    <div className="w-full   mt-2 mx-auto     ">
      <div className="grid grid-cols-12  p-4  border-b pb-2 mb-2 text-gray-700 bg-white">
        <div className="col-span-6 flex items-center gap-2 text-gray-600 text-center ">
          <span>Sản phẩm</span>
        </div>
        <div className="col-span-2 text-gray-600 text-center">Đơn giá</div>
        <div className="col-span-2 text-gray-600 text-center">Số lượng</div>
        <div className="col-span-2 text-gray-600 text-right">Thành tiền</div> 
      </div>
      <LoadingSkeleton isShow={carts===null} type="horizontal"/>
      {carts!=null&& carts.map((shop, sIndex) => (
        <ShopGroupOrder isLive={isLive} sumTotal={setSumTotal} sIndex={sIndex} shop={shop} />
      ))}
    </div>
  );
};

export default OrderForm