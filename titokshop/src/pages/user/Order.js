import { useNavigate, useParams } from "react-router-dom";
import HasakiLogo from "./HasakiLogo";
import { useEffect, useState } from "react";
import { getCartInList } from "../../Service/api/Cart";
import OrderForm from "./OrderForm";
import line_order from "../../assets/image/line_order.png";
import CustomModal from "../../components/Base/Modal";
import { getAllAddress } from "../../Service/api/DiaChi";
import CustomCheckbox from "../../components/Base/CheckboxCustome";
import { placeAnOrder } from "../../Service/api/OrderServiceUser";
import { toast } from "react-toastify";
import handleError from "../../Service/api/HandlError";
const Order = ({ isLive = false }) => {
  const param = useParams();
  const navigate = useNavigate();
  const [carts, setCarts] = useState(null);
  const [sumary, setSumary] = useState({
    totalReduce: 0,
    totalPrice: 0,
  });

  const [address, setAddress] = useState(null);
  const sumTotal = () => {
    if (carts !== null) {
      var totalReduce = 0;
      var totalPrice = 0;
      carts.forEach((cart) => {
        cart.total = 0;
        cart.items.forEach((item) => {
          let price = item.quantity * item.priceAfter;
          totalPrice += price;
          cart.total += price;
        });
        if (cart.voucher !== undefined) {
          totalReduce += cart.voucher.priceExpectReduce;
        }
      });
      setSumary({
        ...sumary,
        totalPrice: totalPrice,
        totalReduce: totalReduce,
      });
    }
  };
  useEffect(sumTotal, [carts]);
  useEffect(() => {
    const re = /^\d+(?:,\d+)*$/;
    const test = re.test(param.cartIds);
    if (test === true) {
      getCartInList(param.cartIds).then((v) => {
        if (v.length < 1) {
          navigate("/user/cart");
        }
        setCarts(v);
      });
    } else {
      navigate("/user/cart");
    }
  }, []);

  const order = () => {
    const data = {
      cartItems: [],
      shops: [],
      address: null,
    };
    if (address !== null) {
      data.addressId = address.id;
      carts.forEach((v) => {
        let shop = { shopId: v.shopId };
        if (v.voucher != null) {
          shop.voucherId = v.voucher.voucherId;
          shop.voucherStyle = v.voucher.voucherStyle;
        }
        data.shops.push(shop);
        v.items.forEach((item) => {
          data.cartItems.push(item.variantId);
        });
      });
    }
    placeAnOrder(data)
      .then((v) => {
        toast.success("Đặt hàng thành công");
      })
      .catch((error) => {
        toast.error(handleError(error).message);
      });
  };

  return (
    <>
      <div className="w-full  bg-gray-50">
        <div className="py-3 bg-white mt-3 w-full md:w-10/12 mx-auto px-14 text-lg flex gap-3 items-center ">
          <HasakiLogo color={"green"} />
          <p className="text-3xl text-green-700">|</p>
          <p className="text-lg text-green-700">THANH TOÁN</p>
        </div>
        <AddressSelected address={address} setAddress={setAddress} />

        <div className="md:w-10/12 w-full md:px-10 mt-2 mx-auto   ">
          <OrderForm isLive={isLive} setSumTotal={sumTotal} carts={carts} />
        </div>
        <div
          className="sticky mt-2 bottom-0 bg-white shadow-[0_-4px_6px_-1px_rgba(0,0,0,0.1)] 
     mb-2 w-full md:w-10/12 mx-auto md:px-14 px-3 py-5 flex justify-between gap-3 items-center"
        >
          <div className=" "></div>
          <div className="flex gap-3 items-center">
            <p>Tổng cộng 3 sản phẩm</p>
            <button
              onClick={() => {
                order();
              }}
              className="text-white text-lg bg-[#306e51] py-2 px-5 rounded-sm"
            >
              ĐẶT HÀNG
            </button>
          </div>
        </div>
        <div className="py-3 w-full md:w-10/12 mx-auto px-14 text-lg  "></div>
      </div>
    </>
  );
};

const AddressSelected = ({ address, setAddress }) => {
  const [addresses, setAddresses] = useState([]);
  useEffect(() => {
    getAllAddress().then((v) => {
      setAddresses(v);
      if (v.length > 0) {
        setAddress(v[0]);
      }
    });
  }, []);

  return (
    <div className="bg-white  overflow-hidden py-3 bg-white mt-3 w-full md:w-10/12 mx-auto px-14   ">
      {/* Dải màu đỏ - xanh trên cùng */}
      <img src={line_order} alt="" className="w-full" />

      <div className="py-4 px-1">
        {/* Tiêu đề */}
        <div className="flex items-center text-[#ee4d2d] font-medium text-lg mb-2">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="w-5 h-5 mr-1 fill-[#ee4d2d]"
            viewBox="0 0 24 24"
          >
            <path d="M12 2C8.1 2 5 5.1 5 9c0 5.3 7 13 7 13s7-7.7 7-13c0-3.9-3.1-7-7-7zm0 9.5c-1.4 0-2.5-1.1-2.5-2.5S10.6 6.5 12 6.5s2.5 1.1 2.5 2.5S13.4 11.5 12 11.5z" />
          </svg>
          <span className="">Địa Chỉ Nhận Hàng</span>
        </div>

        {/* Thông tin địa chỉ */}
        <div className="flex flex-wrap items-center justify-between text-gray-800">
          {address !== null ? (
            <div className="flex flex-wrap items-center gap-x-2">
              <span className="font-semibold">{address.fullName} |</span>
              <span className="font-semibold">{address.phoneNumber}</span>
              <span>{address.fullAddress}</span>
            </div>
          ) : (
            <p className="text-gray-600">Bạn chưa chọn địa chỉ</p>
          )}

          <CustomModal
            witdh={40}
            height={70}
            children={
              <>
                <p className="pb-3 border-b text-lg font-medium text-gray-700">
                  Địa chỉ của tôi
                </p>

                {/* nội dung  */}
                <div className="w-full mt-6 mx-auto bg-white  rounded-xl  ">
                  <div className=" ">
                    {addresses.map((addr) => (
                      <div
                        key={addr.id}
                        className="border-b pb-4 mt-2 text-sm last:border-b "
                      >
                        <div className="flex justify-between items-start">
                          <div>
                            <p className="font-semibold text-gray-900 text-md">
                              {addr.fullName}
                              <span className="mx-2 text-gray-500 font-normal">
                                |
                              </span>
                              <span className="text-gray-700">
                                {addr.phoneNumber}
                              </span>
                            </p>

                            <p className="text-gray-700 mt-1">
                              {addr.addressNote}
                            </p>
                            <p className="text-gray-700">{addr.fullAddress}</p>

                            <div className="mt-2 flex items-center space-x-2">
                              {addr.isDefault && (
                                <span className="border border-red-500 text-red-500 text-xs px-2 py-[1px] rounded">
                                  Mặc định
                                </span>
                              )}
                              {addr.isPickup && (
                                <span className="border border-gray-400 text-gray-600 text-xs px-2 py-[1px] rounded">
                                  Địa chỉ lấy hàng
                                </span>
                              )}
                            </div>
                          </div>

                          <div className="flex flex-col items-end gap-2">
                            <CustomCheckbox
                              color="green-700"
                              onChange={() => {
                                setAddress(addr);
                              }}
                              checked={addr.id === address.id}
                            />
                          </div>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>
              </>
            }
            trigger={
              <button className="text-blue-600 hover:underline text-sm mt-2 sm:mt-0">
                Thay Đổi
              </button>
            }
          />
        </div>
      </div>
    </div>
  );
};

export default Order;
