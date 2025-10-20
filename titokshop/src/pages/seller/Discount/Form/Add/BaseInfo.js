import { useEffect, useState } from "react";
import { BiCalendar } from "react-icons/bi";
import { useParams } from "react-router-dom";
import { formatDateTimeLocal } from "../../../../../Service/FormatDate";
import TooltipCustome from "../../../../../components/Base/TooltipCustome";
import FlashSale from "./FlashsaleType";
import api from "../../../../../config/AxiosConfig";

const BaseInfo = ({
  voucher,
  setVoucher,
  onchangeProp,
  setProductMap,
  productMap,
}) => {
  const params = useParams();
  const [type, setType] = useState("product");
  useEffect(() => {
    const type = params.type;
    if (type === "product" || type === "flashsale") {
      setType(type);
    } else {
      setType("product");
    }
  }, [params.type]);

  const checkOnSale = () => {
    let startDate = voucher.startDate.toISOString();
    let endDate = voucher.startDate.toISOString();
    let productIds=(Array.from(productMap.keys())) 
    if(productIds.length>0){
      api
      .get(
        `/sale/promotion/checkonsale?startDate=${startDate.substring(
          0,
          startDate.length - 1
        )}&endDate=${endDate.substring(
          0,
          endDate.length - 1
        )}&productIds=${productIds}`
      )
      .then((v) => {
        let array = v.data.data;
        setProductMap((prev) => {
          const newMap = new Map(prev);
          array.forEach((vv) => {
            newMap.delete(vv);
          });
          return newMap;
        });
      })
    }
    
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
            Promotion name{" "}
            <span className="ml-1 text-gray-400">
              {" "}
              <TooltipCustome icon={"?"} title={"Tên khuyến mãi"} />{" "}
            </span>
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
              placeholder="Enter promotion name"
              value={voucher.promotionName}
              onChange={(e) => onchangeProp(e.target.value, "promotionName")}
              maxLength={255}
              className="w-full hover:border-[#009DA6] border rounded px-3 py-1 focus:outline-none focus:ring-1 focus:ring-[#009DA6] pr-12"
            />
            <span className="absolute right-2 bottom-1 text-xs text-gray-400">
              {/* {voucher.discountName.length}/255 */}
            </span>
          </div>
        </div>

        {/* Thời gian áp dụng */}
        <div>
          <label className="block font-semibold mb-1 mt-3">
            Thời gian áp dụng{" "}
            <span className="ml-1 text-gray-400">
              <TooltipCustome icon={"?"} title={"Tên khuyến mãi"} />
            </span>
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
                onChange={(e) => { 
                  voucher.startDate=new Date(e.target.value)
                  checkOnSale()
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
                onChange={(e) => {
                  voucher.endDate=new Date(e.target.value)
                  checkOnSale()
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

        {type === "flashsale" ? (
          <FlashSale voucher={voucher} setVoucher={setVoucher}></FlashSale>
        ) : (
          <p className="text-sm mt-3 text-gray-400 font-light mb-3">
            Khuyến mãi thông thường áp dụng cho các sản phẩm của shop
          </p>
        )}
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
