import React, { useEffect, useState } from "react";
import { MdBookmarkBorder } from "react-icons/md";
import { redMain } from "../../config/TextColor";
import { BsShieldCheck, BsShop, BsTruck } from "react-icons/bs";
import IconSpinner from "../../components/Base/IconSpinner";
import api from "../../config/AxiosConfig";
import { RiCoupon2Fill } from "react-icons/ri";
import { formatVND } from "../../Service/FormatDate";
import { Link, useNavigate, useParams } from "react-router-dom";
import ProductCard from "../../components/user/ProductCard";
import ReactStars from "react-stars";
import Pagination from "../../components/Base/Page";
import { addToCart, getAllCartNoneGroup } from "../../Service/api/Cart";
import { toast } from "react-toastify";
import handleError from "../../Service/api/HandlError";
import { getProductDetail } from "../../Service/api/ProductCommon";
import FlashSale from "../../components/user/FlashSale";
import { useDispatch } from "react-redux";
export default function ProductDetail() {
  
  return (
    <div className="max-w-10xl   mx-[14px]">
      <div className="grid grid-cols-1 lg:grid-cols-12 gap-1">
        {/* Bên trái */}
        <div className="lg:col-span-9 bg-gray-100 px-1 py-2 rounded-lg">
          <ProductDetails />
        </div>

        {/* Bên phải */}
        <div className="lg:col-span-3 mt-1  p-1 rounded-lg">
          <div className="bg-white rounded-xl shadow p-4 w-full">
            {/* Tiêu đề */}
            <h2 className="text-center font-semibold text-green-700 border-b pb-2 mb-4">
              - MIỄN PHÍ VẬN CHUYỂN -
            </h2>

            {/* Item 1 */}
            <div className="flex items-start gap-3 mb-4">
              <img
                src="https://media.hcdn.vn/hsk/icons/delivery-120-minutes-100x100.png"
                alt=""
                className="w-15 h-15 object-contain"
              />
              <div>
                <p className="font-medium text-sm text-gray-800">
                  Giao Nhanh Miễn Phí 2H.
                </p>
                <p className="text-sm text-gray-600">Trễ tặng 100K</p>
              </div>
            </div>

            {/* Item 2 */}
            <div className="flex items-start gap-3 mb-4">
              <img
                src="https://media.hcdn.vn/hsk/icons/img_quality_3_100x100.png"
                alt=""
                className="w-15 h-15 object-contain"
              />
              <div>
                <p className="font-medium text-sm text-gray-800">
                  Hasaki đền bù 100%
                </p>
                <p className="text-sm text-gray-600">
                  hàng đền bù 100% nếu phát hiện hàng giả
                </p>
              </div>
            </div>

            {/* Item 3 */}
            <div className="flex items-start gap-3 mb-4">
              <img
                src="https://media.hcdn.vn/hsk/icons/img_quality_2_100x100.png"
                alt=""
                className="w-15 h-15 object-contain"
              />
              <div>
                <p className="font-medium text-sm text-gray-800">
                  Giao Hàng Miễn Phí
                </p>
                <p className="text-sm text-gray-600">
                  (từ 90K tại 60 Tỉnh Thành trừ huyện, toàn Quốc từ 249K)
                </p>
              </div>
            </div>

            {/* Item 4 */}
            <div className="flex items-start gap-3 mb-4">
              <img
                src="https://media.hcdn.vn/hsk/icons/img_quality_44_100x100.png"
                alt=""
                className="w-15 h-15 object-contain"
              />
              <div>
                <p className="font-medium text-sm text-gray-800">Đổi trả</p>
                <p className="text-sm text-gray-600">trong 30 ngày</p>
              </div>
            </div>

            {/* Nút xem thêm */}
            <button className="mt-2 text-green-600 text-sm font-medium w-full text-center">
              Xem thêm
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

function ProductDetails() {
  const [index, setIndex] = useState(0);
  const [product, setProduct] = useState(null);
  const navigate = useNavigate();
  const param = useParams();
  const [quantity, setQuantity] = useState(1);
  const dispatch=useDispatch()
  useEffect(() => {
    getProductDetail(param.productId)
      .then((v) => {
        setProduct(v);
      })
      .catch((error) => {
        toast.error(handleError(error).message);
        navigate("/user/home");
      });
  }, []);
  const addToCarts = () => {
    addToCart(product.productVariants[variantChoose].id, quantity)
      .then((v) => {
        toast.success("Thêm thành công vào giỏ hàng");
        getAllCartNoneGroup().then((v) => {
                  dispatch({ type: "LOAD_CART", data: v });
                });
      })
      .catch((error) => {
        toast.error(handleError(error).message);
      });
  };
  const changeQuantity = (type) => {
    if (variantChoose !== -1) {
      if (type === "-") {
        if (quantity > 1) {
          setQuantity(quantity - 1);
        }
      } else {
        if (product.productVariants[variantChoose].quantity > quantity) {
          setQuantity(quantity + 1);
        }
      }
    }
  };

  const prev = () =>
    setIndex(
      (i) =>
        (i - 1 + product.productImages.length) % product.productImages.length
    );
  const next = () => setIndex((i) => (i + 1) % product.productImagesges.length);
  const [variantChoose, setVariantChoose] = useState(-1);
  return (
    <>
      <div className="max-w-6xl bg-white  mx-auto p-2 px-6 mt-1 grid grid-cols-1 lg:grid-cols-2  gap-8">
        {product != null ? (
          <>
            <div className="col-span-full">
              <nav className="text-sm text-gray-600">
                <ol className="flex flex-wrap items-center gap-1">
                  <li>
                    <Link to="#" className="text-blue-600 hover:underline">
                      Shopee
                    </Link>
                  </li>
                  <li>{">"}</li>
                  <li>
                    <Link to="#" className="text-blue-600 hover:underline">
                      Sắc Đẹp
                    </Link>
                  </li>
                  <li>{">"}</li>
                  <li>
                    <Link to="#" className="text-blue-600 hover:underline">
                      Chăm sóc da mặt
                    </Link>
                  </li>
                  <li>{">"}</li>
                  <li>
                    <Link to="#" className="text-blue-600 hover:underline">
                      Tinh chất dưỡng
                    </Link>
                  </li>
                  <li>{">"}</li>
                  <li className="text-gray-800">
                    Serum Phục Hồi Da Tối Ưu Trong 1 Tuần Lancome Genifique
                    Ultimate B115ml
                  </li>
                </ol>
              </nav>
            </div>
            <div className=" ">
              <div className="relative bg-white rounded-md shadow-sm overflow-hidden">
                {/* Main image */}
                <div className="w-full h-[450px]  flex items-center justify-center relative">
                  <img
                    src={product.productImages[index]}
                    alt={`Product ${index + 1}`}
                    className="max-h-full max-w-full object-contain transition-all duration-300"
                    style={{ width: "100%" }}
                  />

                  {/* Sticker góc trái */}
                  <div className="absolute bottom-0 left-0">
                    <svg
                      width="200"
                      height="50"
                      viewBox="0 0 200 50"
                      xmlns="http://www.w3.org/2000/svg"
                    >
                      <defs>
                        <linearGradient
                          id="saleGradient"
                          x1="0%"
                          y1="0%"
                          x2="100%"
                          y2="0%"
                        >
                          <stop offset="0%" stopColor="#00E0FF" />
                          <stop offset="100%" stopColor="#000000" />
                        </linearGradient>
                      </defs>

                      <rect
                        width="200"
                        height="50"
                        rx="8"
                        fill="url(#saleGradient)"
                      />

                      <text
                        x="10"
                        y="20"
                        fill="white"
                        fontSize="14"
                        fontWeight="bold"
                      >
                        XTRA
                      </text>
                      <text x="10" y="38" fill="white" fontSize="12">
                        Freeship*
                      </text>

                      <text
                        x="90"
                        y="20"
                        fill="white"
                        fontSize="14"
                        fontWeight="bold"
                      >
                        SIÊU SALE
                      </text>
                      <text
                        x="120"
                        y="38"
                        fill="white"
                        fontSize="16"
                        fontWeight="bold"
                      >
                        3.3
                      </text>

                      <polygon points="170,15 178,20 170,25" fill="#00E0FF" />
                      <polygon points="185,15 193,20 185,25" fill="#00E0FF" />
                    </svg>
                  </div>
                </div>

                {/* Prev/Next buttons for desktop */}
                <button
                  onClick={prev}
                  aria-label="Previous image"
                  className="hidden lg:absolute left-2 top-1/2 -translate-y-1/2 bg-white/80 backdrop-blur-sm shadow-md rounded-full p-2 hover:bg-white"
                >
                  ‹
                </button>
                <button
                  onClick={next}
                  aria-label="Next image"
                  className="hidden lg:absolute right-2 top-1/2 -translate-y-1/2 bg-white/80 backdrop-blur-sm shadow-md rounded-full p-2 hover:bg-white"
                >
                  ›
                </button>
              </div>

              {/* Thumbnails row */}
              <div className="mt-4 flex gap-3 overflow-x-auto no-scrollbar">
                {product.productImages.map((src, i) => (
                  <button
                    key={src}
                    onClick={() => setIndex(i)}
                    className={`flex-none rounded-lg overflow-hidden border-2 ${
                      i === index ? "border-pink-600" : "border-gray-200"
                    }`}
                    aria-current={i === index}
                  >
                    <img
                      src={src}
                      alt={`Thumb ${i + 1}`}
                      className="w-28 h-20 object-cover"
                    />
                  </button>
                ))}
              </div>
            </div>

            {/* Right: placeholder product info (you can replace with your own markup) */}
            <div className="flex flex-col gap-2">
              <img
                class="w-[75px] h-auto"
                src="https://media.hcdn.vn/hsk/icons/icon_nowfree_162x26.png"
                alt="now free icon"
                width="93"
                height="15"
              ></img>
              <div className="flex lex-wrap gap-2 items-start">
                <h1 className="text-md lg:text-xl font-semibold leading-tight">
                  <span className="inline-block bg-yellow-300 rounded-full px-2  text-sm  mr-1">
                    XTRA Freeship
                  </span>
                  {product.productName}
                </h1>
                <MdBookmarkBorder color="black" size={24} />
              </div>

              <div className="flex items-center gap-4">
                <div className="flex items-center gap-2">
                  <div className="text-yellow-400 tex-md">★</div>
                  <div className="  text-md font-semibold">4.8/5 (105)</div>
                </div>
                |
                <div className="text-md font-semibold text-gray-600">
                  Đã bán:{" "}
                </div>
                <div className="">265</div>
              </div>
              {product.promotion != null &&
              product.promotions.promotionType === "FLASHSALE" ? (
                <>
                  <FlashSale promotion={product.promotions} />
                </>
              ) : (
                ""
              )}

              {/* price  */}
              <div className="flex items-center gap-2">
                <div className="flex items-center gap-2">
                  <div className="text-[#ff2255]  text-xl  font-semibold">
                    {variantChoose != -1 ? (
                      <>
                        {formatVND(
                          product.productVariants[variantChoose].price *
                            (1 -
                              (product.promotions != null
                                ? product.promotions.discountValue / 100
                                : 0))
                        )}
                      </>
                    ) : (
                      <>
                        {formatVND(
                          product.minPrice *
                            (1 -
                              (product.promotions != null
                                ? product.promotions.discountValue / 100
                                : 0))
                        )}
                      </>
                    )}
                  </div>
                </div>
                {product.promotions != null && (
                  <>
                    |
                    <div className="text-md font-semibold line-through text-gray-500">
                      {product.minPrice} - {product.maxPrice}
                    </div>
                    <p
                      className={`bg-pink-100 p-1 px-2 font-semibold text-md rounded-md text-${redMain}`}
                    >
                      -{product.promotions.discountValue}%
                    </p>
                  </>
                )}
              </div>

              {product.vouchers.length > 0 && (
                <div className="flex flex-wrap items-center gap-2">
                  {product.vouchers.map((v) => (
                    <>
                      <TagVoucher />
                    </>
                  ))}
                </div>
              )}

              {/* decoraate  */}
              <div className="bg-white  border-t pt-4 border-gray-200 space-y-3 text-sm">
                {/* Free shipping */}
                <div className="flex items-center gap-2">
                  <BsTruck className="w-4 h-4 text-gray-600" />
                  <span className="text-[#00BFA5] font-medium bg-green-100 rounded-sm p1 px-2">
                    Free shipping
                  </span>
                  <span className="text-gray-600">
                    Đảm bảo giao: <b>5 - 7 Thg 3</b>
                  </span>
                </div>

                {/* Voucher late delivery */}
                <p className="text-gray-600">
                  Nhận voucher nếu đơn hàng bị giao muộn
                  <span className="inline-block ml-1 text-gray-400">ⓘ</span>
                </p>

                {/* Shipping fee */}
                <p className="text-gray-400 line-through">
                  Phí vận chuyển: 17.100đ
                </p>

                {/* Divider */}
                <div className="border-t border-gray-200 pt-2">
                  <div className="flex items-center gap-2 text-gray-700">
                    <BsShieldCheck className="w-4 h-4 text-gray-600" />
                    <span>
                      Thanh toán khi giao hàng · Hủy đơn dễ dàng · Đội ngũ hỗ
                      trợ
                    </span>
                  </div>
                </div>
              </div>

              {/* phan loai  */}
              <div>
                <p> Phân loại</p>
                <div className="flex items-center mt-2 flex-wrap gap-3">
                  {product.productVariants.map((v, index) => (
                    <>
                      <div
                        onClick={() => {
                          setQuantity(1);
                          setVariantChoose(index);
                        }}
                        className={`p-2 px-3 flex gap-2 border-2 cursor-pointer border-${
                          variantChoose == index ? "red" : "gray"
                        }-200 rounded-sm`}
                      >
                        <img style={{ width: "30px" }} src={v.image} />
                        <span>{v.variantName}</span>
                      </div>
                    </>
                  ))}
                </div>
              </div>

              <div className="mt-1">
                <p>Số lượng</p>
                <div class="flex items-center gap-3 mt-1">
                  <div class="flex border rounded overflow-hidden">
                    <button
                      onClick={() => changeQuantity("-")}
                      class="px-3 py-1 text-gray-500 hover:bg-gray-100"
                    >
                      −
                    </button>
                    <span class="px-4 py-1 text-red-600 border-l border-r">
                      {quantity}
                    </span>
                    <button
                      onClick={() => changeQuantity("+")}
                      class="px-3 py-1 text-gray-500 hover:bg-gray-100"
                    >
                      +
                    </button>
                  </div>
                  {variantChoose != -1 && (
                    <span class="text-gray-700 ">
                      Còn {product.productVariants[variantChoose].quantity} sản
                      phẩm
                    </span>
                  )}
                </div>
              </div>

              {/* CTA area */}
              <div className="mt-auto flex gap-3">
                <button
                  onClick={addToCarts}
                  className="flex gap-2 px-3 items-center rounded-md py-1 font-semibold bg-orange-600 text-white"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="19"
                    height="19"
                    viewBox="0 0 19 19"
                    fill="none"
                    class="w-5 h-5 text-white"
                  >
                    <g clip-path="url(#clip0_13739_8981)">
                      <path
                        d="M7.0625 16.5273C7.285 16.5273 7.50251 16.4614 7.68752 16.3377C7.87252 16.2141 8.01672 16.0384 8.10186 15.8329C8.18701 15.6273 8.20929 15.4011 8.16588 15.1829C8.12248 14.9646 8.01533 14.7642 7.858 14.6068C7.70066 14.4495 7.50021 14.3424 7.28198 14.299C7.06375 14.2556 6.83755 14.2778 6.63198 14.363C6.42641 14.4481 6.25071 14.5923 6.1271 14.7773C6.00348 14.9623 5.9375 15.1798 5.9375 15.4023C5.9375 15.7007 6.05603 15.9869 6.267 16.1978C6.47798 16.4088 6.76413 16.5273 7.0625 16.5273Z"
                        fill="currentColor"
                      ></path>
                      <path
                        d="M14.3601 16.5273C14.5826 16.5273 14.8001 16.4614 14.9851 16.3377C15.1701 16.2141 15.3143 16.0384 15.3995 15.8329C15.4846 15.6273 15.5069 15.4011 15.4635 15.1829C15.4201 14.9646 15.3129 14.7642 15.1556 14.6068C14.9983 14.4495 14.7978 14.3424 14.5796 14.299C14.3614 14.2556 14.1352 14.2778 13.9296 14.363C13.724 14.4481 13.5483 14.5923 13.4247 14.7773C13.3011 14.9623 13.2351 15.1798 13.2351 15.4023C13.2351 15.7007 13.3536 15.9869 13.5646 16.1978C13.7756 16.4088 14.0617 16.5273 14.3601 16.5273Z"
                        fill="currentColor"
                      ></path>
                      <path
                        d="M14.1501 5.27749H17.0151L15.1551 11.7725C15.0909 12.0081 14.9513 12.2162 14.7576 12.365C14.5596 12.5102 14.3207 12.589 14.0751 12.59H7.32511C7.08204 12.5879 6.84583 12.5091 6.65011 12.365C6.45444 12.2166 6.3123 12.0087 6.24511 11.7725L3.71011 2.86999C3.6732 2.75863 3.60239 2.66161 3.50761 2.59249C3.4105 2.51897 3.2919 2.47944 3.17011 2.47999H1.98511"
                        stroke="currentColor"
                        stroke-width="1.5"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      ></path>
                      <path
                        d="M9.59741 7.11496V3.33496"
                        stroke="currentColor"
                        stroke-width="1.5"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      ></path>
                      <path
                        d="M11.495 5.2251H7.70752"
                        stroke="currentColor"
                        stroke-width="1.5"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      ></path>
                    </g>
                    <defs>
                      <clipPath id="clip0_13739_8981">
                        <rect
                          width="18"
                          height="18"
                          fill="currentColor"
                          transform="translate(0.5 0.5)"
                        ></rect>
                      </clipPath>
                    </defs>
                  </svg>
                  GIỎ HÀNG
                </button>
                <button className="flex-1 rounded-lg py-3 font-semibold text-white bg-[#326e51]">
                  Mua ngay
                </button>
              </div>

              <div className="text-sm text-gray-500 mt-4">
                Secure payment • 30-day returns
              </div>
            </div>
            <div>
              <div className="flex items-center gap-3">
                <img
                  style={{ width: "80px" }}
                  className="rounded-full"
                  src={product.shop.image}
                />
                <div>
                  <p>{product.shop.shopName}</p>
                  <div className="flex  items-center  gap-2 mt-2 px-2 py-1 border border-gray-400 rounded-sm">
                    <BsShop color={"orange"} /> Xem shop
                  </div>
                </div>
              </div>
            </div>
            <InfomationDetails product={product} />
            <Reviews product={product} />
          </>
        ) : (
          <div className="flex w-full  justify-center">
            <IconSpinner />
          </div>
        )}
      </div>
    </>
  );
}

const InfomationDetails = React.memo(({ product }) => {
  const [quantity, setQuantity] = useState(0);
  const [productShops, setProductShops] = useState([]);

  useEffect(() => {
    api
      .get("/common/product/topsaleofshop?shopId=" + product.shop.id)
      .then((v) => {
        setProductShops(v.data.data.content);
      })
      .catch((error) => {});
    let a = 0;
    product.productVariants.forEach((v) => {
      a += v.quantity;
    });
    setQuantity(a);
  }, []);
  return (
    <>
      <div className="col-span-full justify-between flex w-full  gap-4">
        <div className="  w-full lg:basis-2/3">
          <p className="text-xl font-light">CHI TIẾT SẢN PHẨM </p>
          <div className="w-full  bg-white text-sm">
            <div className="grid grid-cols-3 gap-4  py-4 ">
              <span className="text-gray-500">Danh Mục</span>
              <span className="col-span-2 text-blue-600">
                Shopee &gt; Sắc Đẹp &gt; Chăm sóc da mặt &gt; Tinh chất dưỡng
              </span>
            </div>
            <div className="grid grid-cols-3 gap-4  py-4 ">
              <span className="text-gray-500">Thương hiệu</span>
              <span className="col-span-2 text-blue-600">{product.brand}</span>
            </div>
            <div className="grid grid-cols-3 gap-4  py-4 ">
              <span className="text-gray-500">Tình trạng</span>
              <span className="col-span-2 ">
                {quantity < 1
                  ? "Hết hàng"
                  : quantity > 10
                  ? `${quantity} Sản phẩm`
                  : "Gần hết hàng"}
              </span>
            </div>

            <div className="grid grid-cols-3 gap-4  py-4 ">
              <span className="text-gray-500">Khuyến mãi</span>
              <span className="col-span-2 ">
                {product.promotions != null
                  ? "Có khuyến mãi"
                  : "Không có chương trình"}
              </span>
            </div>

            <div className="grid grid-cols-3 gap-4  py-4 ">
              <span className="text-gray-500">Vouchers</span>
              <span className="col-span-2 ">
                {product.vouchers.length} Voucher đang áp dụng
              </span>
            </div>

            {product.propertyItems.map((v) => (
              <>
                <div className="grid grid-cols-3 gap-4  py-4 ">
                  <span className="text-gray-500">{v.propertyName}</span>
                  <span className="col-span-2  ">
                    {v.propertyValue} &gt;{" "}
                    <Link to={"#"} className="text-blue-600">
                      Sản phẩm công dụng tương tự
                    </Link>
                  </span>
                </div>
              </>
            ))}
          </div>
        </div>
        <div className="hidden lg:block   lg:basis-1/4">
          <p className="text-xl font-semibold mb-2">TOP BÁN CHẠY CỦA SHOP</p>
          <div
            className="w-full "
            style={{ overflow: "auto", height: "600px" }}
          >
            {productShops.map((v) => (
              <>
                <ProductCard
                  className={"w-full hover:border-1-pink-600"}
                  product={v}
                />
              </>
            ))}
          </div>
        </div>
      </div>
      <div className=" col-span-full ">
        <div dangerouslySetInnerHTML={{ __html: product.discription }} />
      </div>
    </>
  );
});

const TagVoucher = React.memo(() => {
  return (
    <>
      <div className="flex items-center p-1 px-2 gap-2 bg-red-50 inline-block">
        <RiCoupon2Fill color="red" /> Giảm 10%
      </div>
    </>
  );
});

const Reviews = React.memo(({ product }) => {
  const [data, setData] = useState({
    AVGStar: 4.5,
    groupStar: {
      1: 4,
      2: 2,
      3: 5,
      4: 0,
      5: 0,
    },
    reviews: {
      totalElements: 0,
      totalPage: 0,
      content: [],
    },
  });

  const [page, setPage] = useState(1);

  const getReviews = (isOveral) => {
    api
      .get(
        `/common/product/reviews?page=${page - 1}&productId=311&${
          isOveral == null ? "" : "isOveral=1"
        }`
      )
      .then((v) => {
        if (isOveral) {
          data.groupStar = v.data.data.overal;
          let sum = 0;
          let count = 1;
          sum +=
            data.groupStar["1"] * 1 +
            data.groupStar["2"] * 2 +
            data.groupStar["3"] * 3 +
            data.groupStar["4"] * 4 +
            data.groupStar["5"] * 5;
          count +=
            data.groupStar["1"] +
            data.groupStar["2"] +
            data.groupStar["3"] +
            data.groupStar["4"] +
            data.groupStar["5"];
          data.AVGStar = sum / count;
        }
        setData({
          ...data,
          reviews: v.data.data.reviews,
        });
      })
      .catch((error) => {});
  };

  useEffect(() => {
    getReviews(1);
  }, []);

  useEffect(() => {
    getReviews(null);
  }, [page]);
  return (
    <>
      <div className="col-span-full   bg-red-50 rounded-md">
        <div className="flex gap-6 items-center">
          {/* Điểm trung bình */}
          <div className="flex flex-col items-center w-38">
            <div>
              <span className="text-3xl font-bold text-red-600">
                {Math.round(data.AVGStar * 2) / 2}
              </span>{" "}
              <span className="text-red-600 text-md">Trên 5</span>
            </div>

            <div className="flex mt-1 text-red-500">
              <ReactStars
                count={5} // tổng số sao
                value={Math.round(data.AVGStar * 2) / 2} // số sao hiện tại (float)
                isHalf={true}
                size={30}
                color2={"#ca0c0cff"}
                edit={false}
              />
            </div>
          </div>

          {/* Các nút lọc theo sao */}
          <div className="flex flex-wrap gap-4">
            <button className="rounded-sm bg-white border px-3 py-1">
              {1} Sao ({data.groupStar["1"]})
            </button>
            <button className="rounded-sm bg-white border px-3 py-1">
              {2} Sao ({data.groupStar["2"]})
            </button>
            <button className="rounded-sm bg-white border px-3 py-1">
              {3} Sao ({data.groupStar["3"]})
            </button>
            <button className="rounded-sm bg-white border px-3 py-1">
              {4} Sao ({data.groupStar["4"]})
            </button>
            <button className="rounded-sm bg-white border px-3 py-1">
              {5} Sao ({data.groupStar["5"]})
            </button>
          </div>
        </div>
        <div className="flex   w-full flex-wrap">
          {data.reviews.content.map((v) => (
            <ReviewCard review={v} />
          ))}
        </div>
      </div>
      <Pagination
        totalPages={data.reviews.totalPage}
        onPageChange={(v) => {
          setPage(v);
        }}
        currentPage={page}
      />
    </>
  );
});

function ReviewCard({ review }) {
  const { account, rating, content, media, mediaType, createdDate } = review;

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleString("vi-VN", {
      year: "numeric",
      month: "2-digit",
      day: "2-digit",
      hour: "2-digit",
      minute: "2-digit",
    });
  };

  return (
    <div className="   w-full p-4  bg-white  ">
      {/* Header */}
      <div className="flex items-start space-x-3">
        <img
          src="https://tse2.mm.bing.net/th/id/OIP.GzggTND5SbALtKQgF-ZdwwHaHv?pid=Api&P=0&h=180"
          alt="avatar"
          className="w-10 h-10 rounded-full"
        />
        <div>
          <p className="font-semibold text-sm">{account?.fullName}</p>
          <p className="text-xs text-gray-500">{formatDate(createdDate)}</p>
          <ReactStars
            count={5}
            value={3.5}
            size={24}
            color2={"#ca0c0cff"}
            edit={false}
          />
          <p className="mt-3 text-sm text-gray-800 leading-relaxed">
            {content}
          </p>
        </div>
      </div>
      {/* Media */}
      {media && (
        <div className="flex gap-2 mt-3">
          {mediaType === "video" ? (
            <video
              src={media}
              controls
              className="w-32 h-32 rounded-lg object-cover"
            />
          ) : (
            <img
              src={media}
              alt="review"
              className="w-32 h-32 rounded-lg object-cover"
            />
          )}
        </div>
      )}
    </div>
  );
}
