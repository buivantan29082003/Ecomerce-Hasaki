import { useEffect, useState } from "react";
import { greenMain, greenMainColor } from "../../../config/TextColor";
import { BiCopy, BiSearch } from "react-icons/bi";
import api from "../../../config/AxiosConfig";
import { statusColor } from "../../../config/StatusConfig";
import Pagination from "../../../components/Base/Page";
import { formatVND } from "../../../Service/FormatDate";

const ManageVoucher = () => {
  const [voucher, setVoucher] = useState({
    totalPage: 0,
    content: [],
  });
  const getVoucher = () => {
    api
      .get(
        `/sale/voucher/findall?status=${status}&key=${filter.key}&page=${filter.currentPage}&productName=${filter.productName}`
      )
      .then((v) => {
        filter.currentPage = 1;
        setVoucher(v.data.data);
      });
  };

  const [status, setStatus] = useState(0);
  const [filter, setfilter] = useState({
    key: "",
    currentPage: 1,
    productName: "",
  });
  useEffect(getVoucher, [status]);

  return (
    <>
      <Overal />
      {/* table  */}

      <div className="overflow-x-auto relative bg-white rounded-md mt-4">
        <div className="bg-white mt-5 mb-3 flex gap-6 ml-4">
          {["All program", "On going", "Upcomming", "Deactive", "Ended"].map(
            (v, i) => (
              <>
                <span
                  onClick={() => {
                    setStatus(i);
                  }}
                  className={`font-semibold cursor-pointer ${
                    i === status
                      ? `border-b-2 pb-2 border-${greenMainColor}`
                      : ""
                  }`}
                >
                  {v}
                </span>
              </>
            )
          )}
        </div>
        {/* search */}
        <div className="flex gap-5">
          <div className="ml-4 mt-2 mb-4">
            <div className="relative w-72">
              <input
                onChange={(e) => {
                  filter.key = e.target.value;
                  getVoucher();
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
                  getVoucher();
                }}
                value={filter.productName}
                type="text"
                placeholder="Tìm kiếm tên sản phẩm"
                className="hover:border-[#009DA6] border rounded px-3 py-1 w-[280px] focus:outline-none focus:ring-1 focus:ring-[#009DA6]"
              />
              <BiSearch className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 w-5 h-5" />
            </div>
          </div>
        </div>
        <table className="min-w-full border-collapse m-2 rounded-md border border-gray-100 mt-3">
          {/* Header */}
          <thead>
            <tr className=" bg-gray-300 py-7 border border-gray-100  text-left text-sm font-medium text-gray-700">
              <th className="px-4 py-2 whitespace-nowrap">Promotion name</th>
              <th className="px-4 py-2 whitespace-nowrap">Status</th>
              <th className="px-4 py-2 whitespace-nowrap">
                Start time (GMT+8)
              </th>
              <th className="px-4 py-2 whitespace-nowrap">End time (GMT+8)</th>
              <th className="px-4 py-2 whitespace-nowrap">Type</th>
              <th className="bg-gray-300 p-4 border border-gray-100 px-4 py-2 whitespace-nowrap sticky right-0 bg-gray-50 shadow-md">
                Action
              </th>
            </tr>
          </thead>

          {/* Body */}
          <tbody className="font-medium">
            {voucher.content.map((item, idx) => (
              <tr key={idx} className="border-b text-gray-700 text-sm py-5">
                <td className="px-4 py-2">
                  <p>{item.voucherName}</p>
                  <p className="flex gap-2 items-center mt-1 text-sm">
                    <span className={greenMain}>{item.voucherCode}</span>
                    <BiCopy size={13} color="gray" />
                  </p>
                </td>
                <td className="px-4 py-2">
                  <span
                    className={`px-2 py-1 rounded-md text-xs font-medium ${
                      statusColor[item.isActive].color
                    }`}
                  >
                    {statusColor[item.isActive].lable}
                  </span>
                </td>
                <td className="px-4 py-2"> {item.startDate}</td>
                <td className="px-4 py-2">{item.endDate}</td>
                <td className="px-4 py-2">
                  <p>{item.voucherStyle}</p>
                  <p>{item.voucherType}</p>
                </td>
                <td className="px-4 py-2 sticky right-0 bg-white shadow-md">
                  <button className="border rounded px-3 py-1 text-sm font-medium hover:bg-gray-100">
                    View ⌄
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <Pagination
          currentPage={filter.currentPage}
          onPageChange={(p) => {
            filter.currentPage = p;
            getVoucher();
          }}
          totalPages={voucher.totalPage}
        />
      </div>
    </>
  );
};
export default ManageVoucher;

const Overal = () => {
  const [beforeDate, setBeforeDate] = useState(7);
  const [data, setData] = useState([0, 0, 0, 0]);
  useEffect(() => {
    api
      .get("http://localhost:8080/sale/voucher/overal?beforeDate=" + beforeDate)
      .then((v) => {
        setData(v.data.data);
      })
      .catch((error) => {
        console.log("Có lỗi");
      });
  }, [beforeDate]);
  return (
    <div className="bg-white rounded-xl shadow-sm p-4 w-full ">
      {/* Header */}
      <div className="flex flex-col md:flex-row md:items-center md:justify-between mb-4 gap-2">
        <h2 className="text-lg font-semibold">Phân tích</h2>
        <div className="flex flex-wrap items-center gap-4 text-sm text-gray-600">
          <div className="flex items-center gap-2">
            <span>Ngày</span>
            <select
              onChange={(e) => setBeforeDate(e.target.value)}
              className="border rounded px-2 py-1 text-sm"
            >
              <option value={7}>7 ngày qua</option>
              <option value={30}>30 ngày qua</option>
              <option value={180}>6 tháng qua</option>
            </select>
          </div>
          <button className="text-teal-600 font-medium hover:underline">
            Xem thêm →
          </button>
        </div>
      </div>

      {/* Content */}
      <div className="grid grid-cols-1 sm:grid-cols-4 gap-4 text-center">
        {/* GMV */}
        <div className={` hover:border-${greenMainColor} rounded-lg p-4`}>
          <div className="font-semibold text-gray-700 flex justify-center items-center gap-1">
            GMV
            <span className="text-gray-400 cursor-pointer">?</span>
          </div>
          <div className="mt-2 text-xl font-semibold">
            {data[0]} Số đơn hàng
          </div>
        </div>
        <div className="  rounded-lg p-4">
          <div className="font-semibold text-gray-700 flex justify-center items-center gap-1">
            Tổng tiền đơn hàng
            <span className="text-gray-400 cursor-pointer">?</span>
          </div>
          <div className="mt-2 text-xl font-semibold">
            {" "}
            {formatVND(data[1])} tổng tiền đơn
          </div>
        </div>
        <div className={` hover:border-${greenMainColor} rounded-lg p-4`}>
          <div className="font-semibold text-gray-700 flex justify-center items-center gap-1">
            GMV
            <span className="text-gray-400 cursor-pointer">?</span>
          </div>
          <div className="mt-2 text-xl font-semibold">
            {formatVND(data[2])} Tổng tiền giảm
          </div>
        </div>
        {/* Khách hàng */}
        <div className="  rounded-lg p-4">
          <div className="font-semibold text-gray-700 flex justify-center items-center gap-1">
            Khách hàng
            <span className="text-gray-400 cursor-pointer">?</span>
          </div>
          <div className="mt-2 text-xl font-semibold">
            {" "}
            {data[3]} khách hàng
          </div>
        </div>
      </div>
    </div>
  );
};
