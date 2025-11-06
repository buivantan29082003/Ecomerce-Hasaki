import { useEffect, useState } from "react";
import {
  cancelOrder,
  getPaymentUrl,
  search,
} from "../../Service/api/OrderServiceUser";
import getPaymentMethods from "../../Service/api/common/PaymentMethod";
import CustomModal from "../../components/Base/Modal";
import { toast } from "react-toastify";
import handleError from "../../Service/api/HandlError";
import { useNavigate, useSearchParams } from "react-router-dom";
import CustomCheckbox from "../../components/Base/CheckboxCustome";
import AlertBox from "../../components/Base/AlertBox";

const ManageOrder = () => {
  const [data, setData] = useState(
    {
      content: [],
    },
    []
  );
  const [paymentMethods, setPaymentMethods] = useState([]);
  useEffect(() => {
    getPaymentMethods().then((v) => {
      setPaymentMethods(v);
    });
  }, []);

  return (
    <>
      <Filter setData={setData} />
      <div className="w-full mx-auto mt-3  min-h-screen">
        {data.content.map((v) => (
          <OrderCard paymentMethods={paymentMethods} order={v} />
        ))}
      </div>
    </>
  );
};

const Filter = ({ setData }) => {
  const [searchParams] = useSearchParams();
  const tab = searchParams.get("tab");
  const query = searchParams.get("query");

  const [filters, setFilters] = useState({
    keyType: "id",
    tab: tab !== null && !isNaN(Number(tab)) ? Number(tab) : -1,
    page: 0,
    key: query != null ? query : "",
    sortBy: "",
  });

  useEffect(() => {
    setFilters((prev) => ({
      ...prev,
      tab: tab !== null && !isNaN(tab) ? Number(tab) : -1,
      key: query ?? "",
    }));
  }, [searchParams]);

  useEffect(() => {
    try {
      search(filters).then((v) => {
        setData(v);
      });
    } catch (error) {}
  }, [filters]);

  // Danh sách các tab
  const tabs = [
    { id: -1, label: "Tất cả" },
    { id: 9, label: "Chờ thanh toán" },
    { id: 10, label: "Chờ xác nhận" },
    { id: 11, label: "Chuẩn bị hàng" },
    { id: 12, label: "Chờ giao hàng" },
    { id: 13, label: "Giao thành công" },
    { id: 14, label: "Đơn hủy" },
    { id: 16, label: "Đơn hoàn tiền" },
  ];

  return (
    <>
      <div className="flex bg-white py-3 px-2 w-full gap-7 items-center text-md cursor-pointer select-none overflow-x-auto no-scrollbar">
        {tabs.map((tab) => (
          <p
            key={tab.id}
            onClick={() =>
              setFilters({ ...filters, tab:tab.id })
            }
            className={`
            pb-2 border-b-2 transition-all duration-200
            ${
              filters.tab === tab.id
                ? "text-orange-600 border-orange-600"
                : "text-gray-700 border-transparent hover:text-orange-500"
            }
          `}
          >
            {tab.label}
          </p>
        ))}
      </div>
      <div className="flex items-center w-full mt-2 bg-gray-100 px-3 py-2 rounded-md transition">
        {/* icon kính lúp */}
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="w-5 h-5 text-gray-500 mr-2"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth="2"
            d="M21 21l-4.35-4.35m0 0A7.5 7.5 0 104.5 4.5a7.5 7.5 0 0012.15 12.15z"
          />
        </svg>

        {/* ô input */}
        <input
          onChange={(e) => {
            setFilters({ ...filters, key: e.target.value });
          }}
          value={filters.key}
          type="text"
          placeholder="Bạn có thể tìm kiếm theo tên Shop, ID đơn hàng hoặc Tên sản phẩm"
          className="flex-1 bg-transparent outline-none text-gray-700 text-sm placeholder-gray-500"
        />

        {/* ô select */}
        <select
          onChange={(e) => {
            setFilters({ ...filters, keyType: e.target.value });
          }}
          className="ml-2 text-sm  border border-gray-300 text-gray-700  px-2 py-1  "
        >
          <option value="id">ID đơn hàng</option>
          <option value="productName">Tên sản phẩm</option>
          <option value="shopName">Tên shop</option>
        </select>
      </div>
      {(filters.tab === 5 || filters.tab === 14 || filters.tab === 15) && (
        <div className=" mt-2 flex bg-white py-3 px-2 w-full gap-7 items-center text-md cursor-pointer select-none">
          {[
            { id: 14, label: "Hủy bởi người bán" },
            { id: 15, label: "Hủy bởi khách hàng" },
          ].map((tab) => (
            <p
              key={tab.id}
              onClick={() => setFilters({ ...filters, tab: tab.id })}
              className={`
            pb-2 border-b-2 transition-all duration-200
            ${
              filters.tab === tab.id
                ? "text-orange-600 border-orange-600"
                : "text-gray-700 border-transparent hover:text-orange-500"
            }
          `}
            >
              {tab.label}
            </p>
          ))}
        </div>
      )}
    </>
  );
};

function OrderCard({ order, paymentMethods }) {
  return (
    <div className="w-full bg-white shadow-sm mb-4">
      {/* Header */}
      <div className="flex flex-wrap justify-between items-center border-b px-4 py-3">
        <div className="flex items-center gap-2 flex-wrap">
          <span className="bg-[#ee4d2d] text-white text-xs font-semibold px-2 py-1 rounded">
            Yêu thích
          </span>
          <h2 className="font-semibold text-gray-800 text-sm sm:text-base">
            {order.shopName}
          </h2>
          <button className="ml-2 text-gray-600 text-xs sm:text-sm border px-2 py-1 rounded hover:bg-gray-100">
            Xem Shop
          </button>
        </div>
        <span className="text-red-500   text-sm sm:text-base">
          {order.status.statusName.toUpperCase()}
          {/* ĐÃ HỦY */}
        </span>
      </div>

      {/* Order Items */}
      {order.items.map((item) => (
        <div key={item.id} className="flex flex-wrap p-4 border-b">
          <img
            src={item.image}
            alt={item.productName}
            className="w-25 h-20 object-cover  "
          />
          <div className="flex-1 ml-4">
            <h3 className="text-gray-800 text-sm sm:text-base ">
              {item.productName}
            </h3>
            <p className="text-gray-500 text-xs sm:text-sm">
              Phân loại hàng: {item.variantName}
            </p>
            <p className="  text-xs sm:text-sm">x{item.quantity}</p>
          </div>
          <div className="text-right text-sm sm:text-base">
            <p className="line-through text-gray-400">
              {(item.price + item.reducePrice).toLocaleString()}đ
            </p>
            <p className="text-red-500 font-semibold">
              {item.price.toLocaleString()}đ
            </p>
          </div>
        </div>
      ))}

      {/* Footer */}
      <div className="bg-gray-50 px-4 py-3 flex flex-wrap justify-between items-center rounded-b-xl">
        <p className="text-gray-500 text-sm sm:text-base">
          Hình thức thanh toán: {order.payment.paymentName}
        </p>
        <div className="flex flex-wrap items-center gap-2 sm:gap-3 mt-2 sm:mt-0">
          <div className="text-right">
            <span className="text-gray-700 text-sm sm:text-base">
              Thành tiền:{" "}
            </span>
            <span className="text-red-500 font-semibold text-lg sm:text-xl">
              {order.totalFee.toLocaleString()}đ
            </span>
          </div>
          <OrderAction paymentMethods={paymentMethods} order={order} />
        </div>
      </div>
    </div>
  );
}

const OrderAction = ({ order, paymentMethods }) => {
  const navigate = useNavigate();
  const cancelOrderById = () => {
    cancelOrder(order.orderId,reasonCancel)
      .then((v) => {
        toast.success(
          "Hủy đơn hàng thành công (giao dịch sẽ được hoàn tiền nếu đã thanh toán)."
        );
        navigate("/user/dashboard/order?tab=" + 15 + "&query=" + order.orderId);
      })
      .catch((error) => {
        
        toast.error(handleError(error).message);
      });
  };

  const [reasonCancel,setReasonCancel]=useState("")

  const getUrlPayment = (methodCode) => {
    getPaymentUrl(order.orderId, methodCode)
      .then((v) => {
        window.location.href = v;
      })
      .catch((error) => {
        toast.error(handleError(error).message);
      });
  };

  const statusOrder = order.status.id;
  return (
    <>

      {order.status.id===9&&<CustomModal
        witdh={40}
        height={50}
        children={
          <>
            <p className="text-center font-semibold">
              Chọn phương thức thanh toán
            </p>
            <div className="flex gap-4 flex-wrap">
              {paymentMethods.map((v) => (
                <>
                  <div
                    onClick={() => {
                      getUrlPayment(v.paymentCode);
                    }}
                    className="border p-2 rounded-md text-center cursor-pointer border-orange-600"
                  >
                    <img alt="" width={90} height={90} src={v.logo} />
                    <p>{v.paymentName}</p>
                  </div>
                </>
              ))}
            </div>
          </>
        }
        trigger={
          <>
            {statusOrder === 9 && (
              <button className="bg-orange-600 text-white px-4 py-2 rounded border border-orange-700 ">
                Thanh Toán
              </button>
            )}
          </>
        }
      />}

      {(statusOrder === 14 || statusOrder === 15) && (
        <button
          onClick={() => toast.info(order.reasonCancel)}
          className="border px-4 py-2 rounded text-gray-700 hover:bg-gray-100"
        >
          Xem Chi Tiết Hủy Đơn
        </button>
      )}
      {statusOrder !== 13 &&
        statusOrder !== 14 &&
        statusOrder !== 15 &&
        statusOrder !== 16 && (
          <>
            <CustomModal
              witdh={40}
              height={70}
              children={
                <>
                  <p className="pb-3 border-b text-lg font-medium text-gray-700">
                    Chọn lý do hủy đơn
                  </p>

                  {/* nội dung  */}
                  <div className="w-full mt-6 mx-auto bg-white  rounded-xl  ">
                     
                        <AlertBox message={"Vui lòng chọn lí do hủy đơn hàng. Lưu ý: Thao tác này sẽ hủy tất cả các sản phẩm có trong đơn hàng và không thể hoàn tác."}/>
                    
                    {/* <div className=" "> */}
                      {[
                        "Muốn thay đổi địa chỉ giao hàng",
                        "Muốn thêm voucher",
                        " Phương thức thanh toán quá phức tạp",
                        "Muốn đổi sản phẩm mới",
                        "Chỗ khác rẻ hơn",
                        "Khác",
                      ].map((addr) => (
                        <div key={addr.id} className="  pb-4 mt-2 text-sm  ">
                          <div className="flex justify-between items-start">
                            <div>
                              <p className="text-gray-700">{addr}</p>
                            </div>

                            <div className="flex flex-col items-end gap-2">
                              <CustomCheckbox
                                color="green-700"
                                onChange={()=>{
                                  setReasonCancel(addr)

                                } }
                                checked={addr===reasonCancel}
                              />
                            </div>
                          </div>
                        </div>
                      ))}
                    {/* </div> */}
                  </div>
                  <button onClick={cancelOrderById} className="ml-auto text-white px-4 py-2 rounded-sm bg-orange-600">HỦY ĐƠN</button>
                </>
              }
              trigger={
                <button className="border px-4 py-2 rounded text-gray-700 hover:bg-gray-100">
                  Hủy đơn hàng
                </button>
              }
            />
          </>
        )}
    </>
  );
};

export default ManageOrder;
