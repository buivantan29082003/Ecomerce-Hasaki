import LoadingSkeleton from "../../components/Base/Loading";
import ShopGroup from "./ShopGroupCart";

const Carts = ({ carts, setSumTotal }) => {
  return (
    <div className="w-full   mt-2 mx-auto     ">
      <div className="grid grid-cols-12  p-4  border-b pb-2 mb-2 text-gray-700 bg-white">
        <div className="col-span-5 flex items-center gap-2 text-gray-600 text-center ">
          <span>Sản phẩm</span>
        </div>
        <div className="col-span-2 text-gray-600 text-center">Đơn giá</div>
        <div className="col-span-2 text-gray-600 text-center">Số lượng</div>
        <div className="col-span-2 text-gray-600 text-center">Số tiền</div>
        <div className="col-span-1 text-gray-600 text-center">Thao tác</div>
      </div>
      <LoadingSkeleton isShow={carts===null} type="horizontal"/>
      {carts!==null&&carts.map((shop, sIndex) => (
        <ShopGroup sumTotal={setSumTotal} sIndex={sIndex} shop={shop} />
      ))}
    </div>
  );
};

export default Carts