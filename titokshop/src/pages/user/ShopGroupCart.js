import { useEffect, useState } from "react";
import CheckBox from "../../components/Base/Checkbox";
import {
  deleteCart,
  getAllCartNoneGroup,
  updateCart,
} from "../../Service/api/Cart";
import { getAllVoucherByShop } from "../../Service/api/voucher";
import Popup from "../../components/Base/Popup";
import VoucherCard from "./VoucherCart";
import { toast } from "react-toastify";
import handleError from "../../Service/api/HandlError";
import { useDispatch } from "react-redux";

const ShopGroup = ({ shop, sIndex, sumTotal }) => {
  const [vouchers, setVouchers] = useState([]);
  useEffect(() => {
    getAllVoucherByShop(shop.shopId).then((v) => {
      setVouchers(v);
    });
  }, []);

  const deleteItem = (index) => {
    shop.items.splice(index, 1);
    sumTotal();
  };
  return (
    <>
      <div key={sIndex} className="  p-4 bg-white mb-3">
        {/* Shop Header */}
        <div className="flex items-center gap-2 mb-3 pb-2 border-b border-gray-100">
          <CheckBox isChecked={true} />
          <span>{shop.shopName}</span>
          {/* {shop.favorite && ( */}
          <span className="ml-2 px-2 py-0.5 text-xs bg-green-800 text-white rounded-sm">
            Follow
          </span>
          {/* )} */}
        </div>
        {shop.items.map((item, index) => (
          <CartItem
            deleteItem={deleteItem}
            sumTotal={sumTotal}
            item={item}
            index={index}
          />
        ))}
        <div className="mt-3 flex gap-3 items-center text-md">
          {vouchers.length > 0 ? (
            <>
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="32"
                height="32"
                fill="none"
                stroke="#ff3b00"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
              >
                <path d="M3 9a2 2 0 0 0 2-2V5a1 1 0 0 1 1-1h12a1 1 0 0 1 1 1v2a2 2 0 0 0 0 4v2a2 2 0 0 0 0 4v2a1 1 0 0 1-1 1H6a1 1 0 0 1-1-1v-2a2 2 0 0 0 0-4V9z" />
                <line x1="12" y1="5" x2="12" y2="19" />
              </svg>{" "}
              <span>Nhiều voucher đang được áp dụng</span>
              <Popup
                content={<ContentVoucherCard vouchers={vouchers} />}
                textHover={<span className="text-blue-600">Xem tất cả</span>}
              />
            </>
          ) : (
            ""
          )}
        </div>
      </div>
    </>
  );
};

const ContentVoucherCard = ({ vouchers }) => {
  return (
    <>
      <div className="flex flex-wrap py-3">
        {vouchers.map((v) => {
          return <VoucherCard item={v} />;
        })}
      </div>
    </>
  );
};

const CartItem = ({ item, index, sumTotal, deleteItem }) => {
  const dispatch = useDispatch();
  const updateCartItem = (quantity) => {
    updateCart(item.variantId, quantity)
      .then((v) => {
        toast.success("Cập nhật thành công !");
        item.quantity = quantity; 
        sumTotal(); 
        getAllCartNoneGroup().then((v) => {
          dispatch({ type: "LOAD_CART", data: v });
        });
      })
      .catch((error) => {
        toast.error(handleError(error).message);
      });
  };
  return (
    <div
      key={item.id}
      className="grid grid-cols-12 items-center gap-2 py-6 border-b border-gray-200"
    >
      <div className="col-span-5 flex items-center gap-3">
        <CheckBox
          isDisable={item.isActive === 0 || item.productStatus !== 1}
          isChecked={item.isChecked}
          onchange={() => {
            if (item.isChecked === true) {
              item.isChecked = false;
            } else {
              item.isChecked = true;
            }
            sumTotal();
          }}
        />
        <img src={item.image} className="w-16 h-16 object-cover rounded" />
        <div>
          <p className="px-2 rounded-md bg-gray-300 inline-block">
            {item.isActive === 0 || item.productStatus !== 1
              ? "Sản phẩm ngừng bán"
              : ""}
          </p>
          <p className="text-sm font-medium">{item.productName}</p>
          <p className="text-xs text-gray-500">
            Phân loại: {item.variantName} <Popup />
          </p>
          {item.promotion != null && (
            <>
              <p className="text-orange-500 text-sm">
                {item.promotion.promotionName} Giảm lên đến{" "}
                {item.promotion.discountValue} %
              </p>
              <p className="text-orange-500 text-sm">
                {item.promotion.promotionType === "FLASHSALE"
                  ? `Flashsale bắt đầu lúc ${item.promotion.startTime} -  ${item.promotion.endTime}`
                  : ""}
              </p>
            </>
          )}
        </div>
      </div>
      <div className="col-span-2 flex justify-center gap-2 items-center  ">
        <p className="text-gray-400 line-through text-xs">
          {item.price.toLocaleString()}₫
        </p>
        <p className="text-orange-500 font-semibold">
          {item.price.toLocaleString()}₫
        </p>
      </div>
      <div className="flex col-span-2 justify-center items-center border rounded-md overflow-hidden ">
        <button
          onClick={updateCartItem.bind(null, item.quantity - 1)}
          className="flex-1 py-2 text-center hover:bg-gray-100"
        >
          -
        </button>
        <div className="flex-1 py-2 text-center border-x">{item.quantity}</div>
        <button
          onClick={updateCartItem.bind(null, item.quantity + 1)}
          className="flex-1 py-2 text-center hover:bg-gray-100"
        >
          +
        </button>
      </div>
      <div className="col-span-2 justify-center text-center text-orange-500 font-semibold">
        {(item.price * item.quantity).toLocaleString()}₫
      </div>
      <div className="col-span-1 flex justify-center mt-1">
        <button
          onClick={() => {
            deleteCart(item.variantId)
              .then((v) => {
                deleteItem(index);
                toast.success("Xóa thành công giỏ hàng !");
              })
              .catch((v) => {
                alert("Require is not valid");
              });
          }}
          className="text-gray-600  flex items-center gap-1 text-md hover:underline"
        >
          Xóa
        </button>
      </div>
    </div>
  );
};

export default ShopGroup;
