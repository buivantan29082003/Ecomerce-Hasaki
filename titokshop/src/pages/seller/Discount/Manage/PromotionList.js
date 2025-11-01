import React, { useEffect, useState } from "react";
import { greenMainColor } from "../../../../config/TextColor";
import api from "../../../../config/AxiosConfig";
import { BiSearch } from "react-icons/bi";
import PromoProgress from "../../../../components/Base/ProgessTime";

export default function PromotionList() {
   const [data,setData]=useState({
      totalPage: 0,
      content: [],
   })

  const getPromotion = () => {
    api
      .get(`/sale/promotion/findall?${filter.typeDiscount!=null?`typeDiscount=${filter.typeDiscount}`:""}&${filter.key!=""?`promotionName=${filter.key}`:""}&${filter.productName!=""?`productName=${filter.productName}`:""}& page=${filter.currentPage-1}`)
      .then((v) => {
        filter.currentPage = 1;
        setData(v.data.data);
      })
      .catch((error) => { 

      });
  };
  const [filter, setfilter] = useState({
    key: "",
    currentPage: 1,
    productName: "",
    typeDiscount:null
  });

  const [status, setStatus] = useState(0);

  useEffect(() => {
    getPromotion();
  }, []);
  const getStatusColor = (status) => {
    switch (status) {
      case "Upcoming":
        return "text-blue-500";
      case "Deactivated":
        return "text-red-500";
      case "Ongoing":
        return "text-green-500";
      case "Ended":
        return "text-gray-500";
      default:
        return "text-gray-700";
    }
  };

  return (
    <div className="overflow-x-auto border rounded-lg shadow-sm text-sm">
      <div className="bg-white mt-5 mb-3 flex gap-6 ml-4">
        {["All program", "On going", "Upcomming", "Deactive", "Ended"].map(
          (v, i) => (
            <>
              <span
                onClick={() => {
                  setStatus(i);
                }}
                className={`font-semibold cursor-pointer ${
                  i === status ? `border-b-2 pb-2 border-${greenMainColor}` : ""
                }`}
              >
                {v}
              </span>
            </>
          )
        )}
      </div>

      <div className="flex gap-5">
        <div className="ml-4 mt-2 mb-4">
          <div className="relative w-72">
            <input
              onChange={(e) => {
                filter.key = e.target.value;
                getPromotion();
              }}
              value={filter.key}
              type="text"
              placeholder="Tìm kiếm tên, mã voucher"
              className="hover:border-[#009DA6] border rounded px-3 py-1 w-[280px] focus:outline-none focus:ring-1 focus:ring-[#009DA6]"
            />
            <BiSearch className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 w-5 h-5" />
          </div>
        </div>
        <div className="ml-4 mt-2 mb-4">
          <div className="relative w-72">
            <input
              onChange={(e) => {
                filter.productName = e.target.value;
                getPromotion();
              }}
              value={filter.productName}
              type="text"
              placeholder="Tìm kiếm tên sản phẩm"
              className="hover:border-[#009DA6] border rounded px-3 py-1 w-[280px] focus:outline-none focus:ring-1 focus:ring-[#009DA6]"
            />
            <BiSearch className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 w-5 h-5" />
          </div>
        </div>

        <div className="ml-4 mt-2 mb-4">
          <div className="relative w-72">
            <select
              onChange={(e) => {
                filter.typeDiscount = e.target.value;
                getPromotion()
              }}
              value={filter.typeDiscount}
              type="text"
              placeholder="Loại"
              className="hover:border-[#009DA6] border rounded px-3 py-1 w-[280px] focus:outline-none focus:ring-1 focus:ring-[#009DA6]"
            >
              <option value={null } >All</option>
              <option value={"PRODUCT"}>product</option>
              <option value={"FLASHSALE"}>flashsale</option>
            </select>
           </div>
        </div>
      </div>

      <table className="min-w-full border-collapse relative">
        <thead>
          <tr className="bg-gray-50 text-left text-gray-500 font-semibold">
            <th className="px-4 py-2 whitespace-nowrap">Promotion name</th>
            <th className="px-4 py-2 whitespace-nowrap">Status</th>
            <th className="px-4 py-2 whitespace-nowrap">Start time (GMT+8)</th>
            <th className="px-4 py-2 whitespace-nowrap">End time (GMT+8)</th>
            <th className="px-4 py-2 whitespace-nowrap">Processing</th>
            {/* Sticky Action column */}
            <th className="px-4 py-2 whitespace-nowrap sticky right-0 bg-white shadow-md">
              Action
            </th>
          </tr>
        </thead>
        <tbody>
          {data.content.map((row, idx) => (
            <tr key={idx} className="border-b   text-gray-600">
              <td className="px-4 py-2 py-5">
                <p className="font-semibold">{row.promotionName}</p> <p>{row.promotionType}</p>
              </td>
              <td
                className={`px-4 py-2 font-medium ${getStatusColor(
                  row.status
                )}`}
              >
                {row.status}
              </td>
              <td className="px-4 py-2 font-semibold">{row.startDate}</td>
              <td className="px-4 py-2 font-semibold">{row.endDate}</td>
              <td className="px-4 py-2 ">
                <PromoProgress promo={row} />
              </td>
              {/* Sticky Action cell */}
              <td className="px-4 py-2 sticky right-0 bg-white shadow-md">
                <button className="font-inter bg-gray-100 border rounded px-3 py-1 hover:bg-gray-200">
                  View
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
