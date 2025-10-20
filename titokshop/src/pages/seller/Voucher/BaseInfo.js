import { useEffect, useState } from "react";
import { BiCalendar } from "react-icons/bi";
import { useParams } from "react-router-dom";
import { formatDateTimeLocal } from "../../../Service/FormatDate";

import VoucherCommon from "./VoucherCommon";
import VoucherBuyBack from "./VoucherBuyBack";
import VoucherLive from "./VoucherLive";
import VoucherFollower from "./VoucherFollower";
import VoucherNewCustomer from "./VoucherNewCustomer";

// Các loại voucher hợp lệ
const types = new Set(["common", "buyback", "live", "follower", "newcustomer"]);

const BaseInfo = ({ voucher, setVoucher, onchangeProp }) => {
  const params = useParams();
  const [validType, setValidType] = useState("common");

  // Kiểm tra và gán type hợp lệ
  useEffect(() => {
    const t = params.type;
    if (!t || !types.has(t)) {
      setValidType("common");
    } else {
      setValidType(t);
    }
  }, [params.type]);

  // Chọn component voucher dựa vào type
  const chooseTVoucher = () => {
    if (validType === "common") return <VoucherCommon />;
    if (validType === "buyback") return <VoucherBuyBack voucher={voucher} onchangeProp={onchangeProp} />;
    if (validType === "live") return <VoucherLive />;
    if (validType === "follower") return <VoucherFollower />;
    if (validType === "newcustomer") return <VoucherNewCustomer />;
    return <VoucherCommon />; // fallback
  };

  return (
    <div className="flex flex-col md:flex-row">
      {/* LEFT: Nội dung chính */}
      <div
        className="w-full md:w-3/5 p-6 overflow-y-auto"
        style={{ scrollbarWidth: "none", msOverflowStyle: "none" }}
      >
        <h2 className="font-semibold text-2xl mb-4">Basic info</h2>

        {/* Voucher Name */}
        <div>
          <label className="block font-semibold mb-1">
            Voucher name <span className="ml-1 text-gray-400">(?)</span>
          </label>
          <p className="text-sm text-gray-400 font-light mb-3">
            Recommended length:{" "}
            <span className="font-semibold text-[#009DA6]">25 characters</span>{" "}
            or more. Will be identified according to product name.
          </p>
          <div className="relative w-full">
            <input
              type="text"
              name="voucherName"
              placeholder="Enter voucher name"
              value={voucher.voucherName}
              onChange={(e) => onchangeProp(e.target.value, "voucherName")}
              maxLength={255}
              className="w-full hover:border-[#009DA6] border rounded px-3 py-1 focus:outline-none focus:ring-1 focus:ring-[#009DA6] pr-12"
            />
            <span className="absolute right-2 bottom-1 text-xs text-gray-400">
              {voucher.voucherName.length}/255
            </span>
          </div>
        </div>
        
        {/* voucher code */}
        <div>
          <label className="block font-semibold mb-1">
            Voucher code <span className="ml-1 text-gray-400">(?)</span>
          </label>
          <p className="text-sm text-gray-400 font-light mb-3">
            Recommended length:{" "}
            <span className="font-semibold text-[#009DA6]">25 characters</span>{" "}
            or more. Will be identified according to product name.
          </p>
          <div className="relative w-full">
            <input
              type="text"
              name="voucherName"
              placeholder="Enter voucher code"
              value={voucher.voucherCode}
              onChange={(e) => onchangeProp(e.target.value, "voucherCode")}
              maxLength={255}
              className="w-full hover:border-[#009DA6] border rounded px-3 py-1 focus:outline-none focus:ring-1 focus:ring-[#009DA6] pr-12"
            />
            <span className="absolute right-2 bottom-1 text-xs text-gray-400">
              {voucher.voucherName.length}/255
            </span>
          </div>
        </div>

        {/* Thời gian áp dụng */}
        <div>
          <label className="block font-semibold mb-1 mt-3">
            Thời gian áp dụng <span className="ml-1 text-gray-400">(?)</span>
          </label>
          <p className="text-sm text-gray-400 font-light mb-3">
            Some categories are invite-only and can't be selected. To add these
            categories, click here to submit the{" "}
            <span className="font-semibold text-[#009DA6]">
              invite only application
            </span>
            . Don't upload{" "}
            <span className="font-semibold text-[#009DA6]">prohibit</span> or{" "}
            <span className="font-semibold text-[#009DA6]">restricted</span>{" "}
            products.
          </p>
          <div className="flex flex-col md:flex-row gap-4">
            <div className="relative w-full">
              <input
                value={formatDateTimeLocal(voucher.startDate)}
                min={formatDateTimeLocal(new Date())}
                onChange={(e)=>{
                  setVoucher({...voucher,"startDate":new Date(e.target.value)})
                }}
                type="datetime-local"
                className="w-full border rounded px-3 py-2 pr-10 hover:border-[#009DA6] focus:outline-none focus:ring-1 focus:ring-[#009DA6]"
              />
              <span className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-500">
                <BiCalendar />
              </span>
            </div>
            <div className="relative w-full">
              <input
                value={formatDateTimeLocal(voucher.endDate)}
                min={formatDateTimeLocal(new Date())}
                onChange={(e)=>{
                  setVoucher({...voucher,"endDate":new Date(e.target.value)})
                }}
                type="datetime-local"
                className="w-full border rounded px-3 py-2 pr-10 hover:border-[#009DA6] focus:outline-none focus:ring-1 focus:ring-[#009DA6]"
              />
              <span className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-500">
                <BiCalendar />
              </span>
            </div>
          </div>
        </div>

        {/* Loại voucher */}
        {chooseTVoucher()}
      </div>

      {/* RIGHT: Preview */}
      <div className="w-full md:w-3/5 hidden md:block overflow-y-auto   p-4">
        <h4 className="font-semibold text-lg mb-4">Preview</h4>
        <div className="grid grid-cols-3 gap-4 text-center">
          <div>
            <img
              src="https://p16-oec-sg.ibyteimg.com/tos-alisg-i-aphluv4xwc-sg/787555ad8a234ed3b4c4ba758b059428~tplv-aphluv4xwc-origin-image.image"
              alt="Trang giỏ hàng"
              className="w-full rounded-lg shadow"
            />
            <p className="mt-2 text-sm text-gray-600">Trang giỏ hàng</p>
          </div>
          <div>
            <img
              src="https://p16-oec-sg.ibyteimg.com/tos-alisg-i-aphluv4xwc-sg/488312a3aa87404a9037fcbb1dd558e0~tplv-aphluv4xwc-origin-image.image"
              alt="Trang chi tiết sản phẩm"
              className="w-full rounded-lg shadow"
            />
            <p className="mt-2 text-sm text-gray-600">
              Trang chi tiết sản phẩm
            </p>
          </div>
          <div>
            <img
              src="https://p16-oec-sg.ibyteimg.com/tos-alisg-i-aphluv4xwc-sg/fa283ec2fa4a40c6aeaf0bd58400e049~tplv-aphluv4xwc-origin-image.image"
              alt="LIVE"
              className="w-full rounded-lg shadow"
            />
            <p className="mt-2 text-sm text-gray-600">LIVE</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default BaseInfo;
