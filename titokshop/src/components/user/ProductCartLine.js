import React from "react";
import { useNavigate } from "react-router-dom";
import { formatVND } from "../../Service/FormatDate";

const ProductCard = ({ product, patternLink }) => {
  const navigate = useNavigate(); 
  return (
    <div
      className="
        w-full border border-gray-200 rounded-lg bg-white overflow-hidden
        transition-all duration-200 cursor-pointer
        hover:border-orange-500 hover:shadow-md
      "
      onClick={() => navigate(`${patternLink}${product.productId}`)}
    >
      {/* Ảnh */}
      <div className="relative">
        <img
          src={product.productImage}
          alt={product.productName}
          className="w-full  object-cover"
        />
        {/* Badge giảm giá */}
        {product.sale && (
          <div className="absolute top-2 right-2 bg-orange-500 text-white text-xs font-bold px-1.5 py-0.5 rounded">
            -{product.sale.discountValue}%
          </div>
        )}
      </div>

      {/* Nội dung */}
      <div className="p-3">
        {/* Giá */}
        <div className="flex items-center gap-2">
          <span className="text-red-600 font-bold text-lg">
            {formatVND(
              product.minPrice *
                (product.sale !== null
                  ? 1 - product.sale.discountValue / 100
                  : 1)
            )}
            {product.sale && (
              <span className="text-gray-400 ml-2 text-sm line-through">
                {formatVND(product.minPrice)}
              </span>
            )}
          </span>
          {product.minPrice && product.maxPrice !== product.minPrice && (
            <span className="text-gray-400 line-through text-sm">
              {formatVND(product.maxPrice)}
            </span>
          )}
        </div>

        {/* Thương hiệu */}
        {true && (
          <p className="text-xs text-green-800 font-bold mt-1">{"Hasaku"}</p>
        )}
        <p className="text-xs text-green-800 font-bold mt-1">
          {product.totalSale} Đã bán
        </p>

        {/* Tên sản phẩm */}
        <h3
          className="text-sm text-gray-800 mt-1 h-10 overflow-hidden"
          style={{
            display: "-webkit-box",
            WebkitLineClamp: 2,
            WebkitBoxOrient: "vertical",
          }}
        >
          {product.productName}
        </h3>

        {/* Rating + Sold */}
        <div className="flex items-center justify-between text-xs text-gray-600 mt-1">
          <span className="text-white font-bold bg-orange-500 rounded-sm px-2">
            {product.countStart} ☆
          </span>
          <div className="flex items-center gap-2">
            <Cart />
            <span>{product.cartPerMonth}/ Tháng</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductCard;

const Cart = () => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      width="10"
      height="11"
      viewBox="0 0 10 11"
      fill="none"
      class="w-3 h-3 text-icon"
    >
      <path
        fill-rule="evenodd"
        clip-rule="evenodd"
        d="M6.55328 8.13907C6.36827 8.14006 6.1877 8.19589 6.03442 8.29951C5.88114 8.40312 5.76202 8.54985 5.69213 8.72116C5.62225 8.89248 5.60472 9.08067 5.64178 9.26193C5.67885 9.4432 5.76884 9.6094 5.90037 9.73953C6.03189 9.86965 6.19904 9.95786 6.38069 9.99298C6.56235 10.0281 6.75034 10.0086 6.9209 9.93686C7.09145 9.86515 7.23691 9.74447 7.33887 9.59008C7.44084 9.4357 7.49474 9.25455 7.49375 9.06954C7.4931 8.94669 7.46824 8.82517 7.42063 8.71193C7.37301 8.59868 7.30356 8.49592 7.21622 8.40952C7.12889 8.32311 7.02539 8.25476 6.91164 8.20836C6.7979 8.16195 6.67613 8.13841 6.55328 8.13907ZM0.310153 0.0100033C0.227438 0.0113164 0.148556 0.0451018 0.0905299 0.104064C0.032504 0.163025 -1.04196e-05 0.242438 2.50472e-09 0.325163C2.50472e-09 0.407422 0.0326699 0.486308 0.0908352 0.544473C0.149001 0.602638 0.227894 0.635316 0.310153 0.635316H1.61081L2.8114 7.25363C2.82537 7.32558 2.86398 7.39043 2.92059 7.43699C2.9772 7.48356 3.04826 7.50893 3.12156 7.50876H7.49375C7.56987 7.49911 7.63984 7.46203 7.69057 7.40447C7.74129 7.34691 7.76928 7.27282 7.76928 7.1961C7.76928 7.11938 7.74129 7.04529 7.69057 6.98773C7.63984 6.93017 7.56987 6.89309 7.49375 6.88344H3.37669L3.15157 5.63782H8.11406C8.17574 5.63792 8.236 5.61934 8.2869 5.58451C8.33781 5.54968 8.37696 5.50025 8.3992 5.44272L9.96499 1.69585C9.98504 1.64806 9.9928 1.59601 9.98755 1.54445C9.98231 1.49289 9.96422 1.44347 9.93496 1.4007C9.90781 1.35763 9.87015 1.32218 9.82552 1.29767C9.78088 1.27317 9.73076 1.26042 9.67984 1.26063H2.36118L2.17609 0.260128C2.16425 0.187665 2.12707 0.121756 2.0712 0.0741223C2.01532 0.0264891 1.94436 0.000222976 1.87093 0L0.310153 0.0100033ZM4.05702 8.13907C3.87179 8.13907 3.69073 8.19407 3.53679 8.29708C3.38284 8.40009 3.26295 8.54649 3.1923 8.71771C3.12165 8.88894 3.10342 9.07729 3.13992 9.25889C3.17642 9.44048 3.26602 9.60716 3.39734 9.73779C3.52867 9.86841 3.69582 9.95711 3.87761 9.99264C4.0594 10.0282 4.24765 10.0089 4.4185 9.93735C4.58934 9.86578 4.73509 9.7451 4.83727 9.59061C4.93946 9.43612 4.99349 9.25476 4.9925 9.06954C4.99118 8.8223 4.89204 8.58564 4.71674 8.41129C4.54145 8.23693 4.30426 8.13907 4.05702 8.13907Z"
        fill="currentColor"
      ></path>
    </svg>
  );
};
