import React, { useEffect, useState } from "react";
import CheckBox from "../../components/Base/Checkbox";
import {
  claimVoucherCommon,
  getVoucherByProductId,
} from "../../Service/api/voucher";
import Popup from "../../components/Base/Popup";
import { toast } from "react-toastify";
import handleError from "../../Service/api/HandlError";
import { formatVND } from "../../Service/FormatDate";

const ShopGroupOrder = ({ shop, sIndex, sumTotal, isLive }) => {
  const [vouchers, setVouchers] = useState([]);
  const [reload, setReload] = useState(1);
  const getVoucher = () => {
    const result = shop.items.reduce((acc, item) => {
      const total = item.quantity * item.priceAfter;
      acc[item.productId] = (acc[item.productId] || 0) + total;
      return acc;
    }, {});
    getVoucherByProductId(result).then((v) => {
      setVouchers(v);
    });
  };
  const applyVoucher = (voucher) => {
    shop.voucher = voucher;
    sumTotal();
  };
  useEffect(() => {
    getVoucher();
  }, []);
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
          <CartItem sumTotal={sumTotal} item={item} index={index} />
        ))}

        <div className="mt-2 flex w-full">
          <div className="w-4/12"></div>
          <div className="w-8/12 flex justify-between">
            <div className="w-5/12 gap-2 flex items-center">
              <svg
                style={{ width: "25px" }}
                fill="none"
                viewBox="0 0 23 22"
                class="shopee-svg-icon icon-voucher-applied-line"
              >
                <rect
                  x="13"
                  y="9"
                  width="10"
                  height="10"
                  rx="5"
                  fill="#EE4D2D"
                ></rect>
                <path
                  fill-rule="evenodd"
                  clip-rule="evenodd"
                  d="M20.881 11.775a.54.54 0 00-.78.019l-2.509 2.765-1.116-1.033a.542.542 0 00-.74.793l1.5 1.414a.552.552 0 00.844-.106l2.82-3.109a.54.54 0 00-.019-.743z"
                  fill="#fff"
                ></path>
                <path
                  fill-rule="evenodd"
                  clip-rule="evenodd"
                  d="M6.488 16.178h.858V14.57h-.858v1.607zM6.488 13.177h.858v-1.605h-.858v1.605zM6.488 10.178h.858V8.572h-.858v1.606zM6.488 7.178h.858V5.572h-.858v1.606z"
                  fill="#EE4D2D"
                ></path>
                <g filter="url(#voucher-filter1_d)">
                  <path
                    fill-rule="evenodd"
                    clip-rule="evenodd"
                    d="M1 4v2.325a1.5 1.5 0 01.407 2.487l-.013.012c-.117.103-.25.188-.394.251v.65c.145.063.277.149.394.252l.013.012a1.496 1.496 0 010 2.223l-.013.012c-.117.103-.25.188-.394.251v.65c.145.063.277.149.394.252l.013.012A1.5 1.5 0 011 15.876V18h12.528a6.018 6.018 0 01-.725-1H2v-.58c.55-.457.9-1.147.9-1.92a2.49 2.49 0 00-.667-1.7 2.49 2.49 0 00.667-1.7 2.49 2.49 0 00-.667-1.7A2.49 2.49 0 002.9 7.7c0-.773-.35-1.463-.9-1.92V5h16v.78a2.494 2.494 0 00-.874 2.283 6.05 6.05 0 011.004-.062A1.505 1.505 0 0119 6.325V4H1z"
                    fill="#EE4D3D"
                  ></path>
                </g>
                <defs>
                  <filter
                    id="voucher-filter1_d"
                    x="0"
                    y="3"
                    width="20"
                    height="16"
                    filterUnits="userSpaceOnUse"
                    color-interpolation-filters="sRGB"
                  >
                    <feFlood
                      flood-opacity="0"
                      result="BackgroundImageFix"
                    ></feFlood>
                    <feColorMatrix
                      in="SourceAlpha"
                      values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
                    ></feColorMatrix>
                    <feOffset></feOffset>
                    <feGaussianBlur stdDeviation=".5"></feGaussianBlur>
                    <feColorMatrix values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.09 0"></feColorMatrix>
                    <feBlend
                      in2="BackgroundImageFix"
                      result="effect1_dropShadow"
                    ></feBlend>
                    <feBlend
                      in="SourceGraphic"
                      in2="effect1_dropShadow"
                      result="shape"
                    ></feBlend>
                  </filter>
                </defs>
              </svg>
              <p>Voucher của shop</p>
            </div>
            <div className="text-right cursor-pointer w-5/12">
              <Popup
                content={
                  <ContentVoucherCard
                    applyVoucher={applyVoucher}
                    getVoucher={getVoucher}
                    isLive={isLive}
                    vouchers={vouchers}
                  />
                }
                textHover={<p onClick={getVoucher}>Chọn voucher</p>}
              />
            </div>
          </div>
        </div>

        {/* Đánh giá và tổng tiền  */}
        <div className="bg-gray-50 w-full py-6 px-2 mt-3 flex items-start">
          <div className="md:w-6/12 w-full">
            <p>Lời nhắn</p>{" "}
            <input
              onChange={(e) => {
                shop.message = e.target.value;
                setReload(reload + 1);
              }}
              className="p-1 px-6 rounded-sm border border-gray-300"
              value={shop.message}
            />
          </div>
          <div className="md:w-6/12 w-full ">
            <p className="text-right">Tổng số tiền</p>
            <p className="text-right text-lg text-orange-600 font-semibold">
              {formatVND(
                shop.total -
                  (shop.voucher != null ? shop.voucher.priceExpectReduce : 0)
              )}
            </p>
          </div>
        </div>
      </div>
    </>
  );
};

const ContentVoucherCard = ({
  vouchers,
  isLive,
  sumTotal,
  getVoucher,
  applyVoucher,
}) => {
  return (
    <>
      <div className="flex flex-wrap py-3">
        {vouchers.map((v) => {
          return (
            <VoucherCards
              applyVoucher={applyVoucher}
              getVoucher={getVoucher}
              sumTotal={sumTotal}
              isLive={isLive}
              item={v}
            />
          );
        })}

        {/* chọn voucher  */}
        <div className=""></div>
      </div>
    </>
  );
};

const VoucherCards = ({ item, isLive, sumTotal, getVoucher, applyVoucher }) => {
  return (
    <>
      <div className="flex items-center justify-between bg-white border rounded-lg shadow-sm p-3 w-full max-w-lg">
        {/* Logo */}
        <div className="flex items-center gap-3">
          <img
            src={
              "https://down-vn.img.susercontent.com/file/vn-11134004-7ras8-m4re2imocx9s72.webp"
            }
            alt="logo"
            className="w-12 h-12 rounded-full object-cover"
          />
          <div>
            <p className="text-gray-800 font-semibold text-sm">
              {item.voucherName}
            </p>
            <p className="text-gray-800 font-semibold text-sm">
              Giảm {item.discountValue}
            </p>
            <p className="text-gray-600 text-xs">
              Hình thức áp dụng {item.voucherStyle}
            </p>
            <p className="text-gray-500 text-xs mt-1">HSD: {item.endDate} </p>
          </div>
        </div>

        {/* Apply button  */}
        {item.isGain === 1 && item.canApply === true ? (
          <button
            className="bg-orange-500 hover:bg-orange-600 text-white text-sm font-semibold px-3 py-1.5 rounded"
            onClick={() => {
              applyVoucher(item);
            }}
          >
            Apply
          </button>
        ) : (
          `${item.message}`
        )}
        {item.isGain === 0 ? (
          <button
            className="bg-orange-500 hover:bg-orange-600 text-white text-sm font-semibold px-3 py-1.5 rounded"
            onClick={() => {
              if (item.voucherStyle === "COMMON") {
                claimVoucherCommon(item.voucherId)
                  .then((v) => {
                    toast.success("Nhận voucher thành công");
                    getVoucher();
                  })
                  .catch((error) => {
                    toast.error(handleError(error).message);
                  });
              }
            }}
          >
            Lấy voucher
          </button>
        ) : (
          ""
        )}
      </div>
      {item.voucherStyle === "LIVE" && isLive === false ? (
        <p className="text-sm text-orange-600">
          Chỉ áp dụng khi mua hàng trực tiếp trong live
        </p>
      ) : (
        ""
      )}
    </>
  );
};

const CartItem = React.memo(({ item, index, sumTotal, deleteItem }) => {
  return (
    <div
      key={item.id}
      className="grid grid-cols-12 items-center gap-2 py-6 border-b border-gray-200"
    >
      <div className="col-span-6 flex items-center gap-3">
        <img
          alt=""
          src={item.image}
          className="w-16 h-16 object-cover rounded"
        />
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
        {item.priceAfter !== item.price && (
          <p className="text-gray-400 line-through text-xs">
            {item.price.toLocaleString()}₫
          </p>
        )}
        <p className="text-orange-500 font-semibold">
          {item.priceAfter.toLocaleString()}₫
        </p>
      </div>
      <div className="flex col-span-2 justify-center items-center overflow-hidden ">
        <div className="flex-1 py-2 text-center  ">
          {item.quantity} sản phẩm{" "}
        </div>
      </div>
      <div className="col-span-2 justify-center text-right text-orange-500 font-semibold">
        {(item.priceAfter * item.quantity).toLocaleString()}₫
      </div>
    </div>
  );
});
export default ShopGroupOrder;
