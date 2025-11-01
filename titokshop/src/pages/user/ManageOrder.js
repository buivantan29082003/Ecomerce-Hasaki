import { useEffect, useState } from "react"
import { search } from "../../Service/api/OrderServiceUser"

const ManageOrder=()=>{
    const [data,setData]=useState({
        content:[]
    },[])

    
    return <>
        <Filter setData={setData}/>
        <div className="w-full mx-auto mt-3  min-h-screen">
            {data.content.map(v=><OrderCard order={v} />)}
            
        </div>
    </>
}


const Filter = ({setData}) => {

    
  const [filters, setFilters] = useState({
    keyType: "id",
    tab: -1,
    page:0,
    key:"",
    sortBy:""
  });

  useEffect(()=>{
        search(filters).then(v=>{
            setData(v)
        })
    },[filters])


  // Danh sách các tab
  const tabs = [
    { id: -1, label: "Tất cả" },
    { id: 0, label: "Chờ thanh toán" },
    { id: 1, label: "Chờ xác nhận" },
    { id: 2, label: "Chuẩn bị hàng" },
    { id: 3, label: "Chờ giao hàng" },
    { id: 4, label: "Giao thành công" },
    { id: 5, label: "Đơn hủy" },
    { id: 6, label: "Đơn hoàn tiền" },
  ];

  return (
    <>
        <div className="flex bg-white py-3 px-2 w-full gap-7 items-center text-md cursor-pointer select-none">
      {tabs.map((tab) => (
        <p
          key={tab.id}
          onClick={() => setFilters({ ...filters, tab: tab.id })}
          className={`
            pb-2 border-b-2 transition-all duration-200
            ${filters.tab === tab.id
              ? "text-orange-600 border-orange-600"
              : "text-gray-700 border-transparent hover:text-orange-500"}
          `}
        >
          {tab.label}
        </p>
      ))}
    </div>
    <div className="">

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
      setFilters({...filters,"key":e.target.value}) 
  }}
    type="text"
    placeholder="Bạn có thể tìm kiếm theo tên Shop, ID đơn hàng hoặc Tên sản phẩm"
    className="flex-1 bg-transparent outline-none text-gray-700 text-sm placeholder-gray-500"
  />

  {/* ô select */}
  <select onChange={e=>{
    setFilters({...filters,keyType:e.target.value})
  }}
    className="ml-2 text-sm  border border-gray-300 text-gray-700  px-2 py-1  "
  > 
    <option value="id">ID đơn hàng</option>
    <option value="productName">Tên sản phẩm</option>
    <option value="shopName">Tên shop</option>
  </select>
</div>

    </>
  );
};
 
function OrderCard({ order }) {
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
          {/* {order.status.statusName.toUpperCase()} */}
          ĐÃ HỦY
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
      <div className="bg-orange-50 px-4 py-3 flex flex-wrap justify-between items-center rounded-b-xl">
        <p className="text-gray-500 text-sm sm:text-base">
          Đã hủy bởi bạn
        </p>
        <div className="flex flex-wrap items-center gap-2 sm:gap-3 mt-2 sm:mt-0">
          <div className="text-right">
            <span className="text-gray-700 text-sm sm:text-base">Thành tiền: </span>
            <span className="text-red-500 font-semibold text-lg sm:text-xl">
              {order.totalFee.toLocaleString()}đ
            </span>
          </div>
          <button className="bg-[#ee4d2d] text-white px-4 py-2 rounded border border-orange-700 hover:bg-red-600">
            Mua Lại
          </button>
          <button className="border px-4 py-2 rounded text-gray-700 hover:bg-gray-100">
            Xem Chi Tiết Hủy Đơn
          </button>
          <button className="border px-4 py-2 rounded text-gray-700 hover:bg-gray-100">
            Liên Hệ Người Bán
          </button>
        </div>
      </div>
    </div>
  );
}


export default ManageOrder;