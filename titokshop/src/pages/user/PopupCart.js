import React, { useState } from "react";
import { CgShoppingCart } from "react-icons/cg";
import { formatVND } from "../../Service/FormatDate";
import { useNavigate } from "react-router-dom";

export default function CartPopup({ carts }) {
  const [isHovering, setIsHovering] = useState(false);
    const navigate=useNavigate()
  const handleMouseLeave = (e) => {
    const related = e.relatedTarget;
    const icon = document.getElementById("cart-icon");
    const popup = document.getElementById("cart-popup");
    if (!icon?.contains(related) && !popup?.contains(related)) {
      setIsHovering(false);
    }
  };

  // 🧩 Giới hạn hiển thị tối đa 4 sản phẩm
  const visibleCarts = carts.slice(0, 4);
  const hiddenCount = carts.length - visibleCarts.length;

  return (
    <div className="relative flex items-center justify-center">
      {/* ICON GIỎ HÀNG */}
      <div
        id="cart-icon"
        className="cursor-pointer text-3xl text-white"
        onMouseEnter={() => setIsHovering(true)}
        onMouseLeave={handleMouseLeave}
      >
        <button className="relative">
          <CgShoppingCart size={22} />
          {carts.length > 0 && (
            <span className="absolute -top-2 -right-2 bg-orange-500 text-xs rounded-full px-1">
              {carts.length}
            </span>
          )}
        </button>
      </div>

      {/* POPUP */}
      <div
        id="cart-popup"
        onMouseEnter={() => setIsHovering(true)}
        onMouseLeave={handleMouseLeave}
        className={`absolute right-0 top-10 bg-white shadow-xl w-80 p-6 z-50 transform transition-all duration-300 ease-out origin-top-right
          ${
            isHovering
              ? "opacity-100 scale-100 translate-y-0"
              : "opacity-0 scale-95 -translate-y-2 pointer-events-none"
          }`}
      >
        {/* NỘI DUNG */}
        {carts.length === 0 ? (
          <div className="flex flex-col items-center justify-center gap-3 py-8">
            <div className="w-20 h-20 rounded-full bg-green-100 flex items-center justify-center">
              <svg
                fill="none"
                viewBox="0 0 24 24"
                className="w-10 h-10 text-green-500"
                stroke="currentColor"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="2"
                  d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-1 5h12l-1-5M9 21h6"
                />
              </svg>
            </div>
            <p className="text-gray-700 font-medium">Chưa Có Sản Phẩm</p>
          </div>
        ) : (
          <div className="flex flex-col gap-3 w-full">
            {/* TIÊU ĐỀ */}
            <p className="text-sm text-gray-400 font-medium">
              Sản Phẩm Mới Thêm
            </p>

            {/* DANH SÁCH SẢN PHẨM */}
            <div className="max-h-60 overflow-y-auto">
              {visibleCarts.map((item, index) => (
                <div
                  key={index}
                  className="flex justify-between items-center py-2 border-b border-gray-100"
                >
                  <div className="flex items-center gap-3">
                    <img
                      src={item.image}
                      alt={item.productName}
                      className="w-10 h-10 object-cover rounded"
                    />
                    <p className="text-sm text-gray-700 line-clamp-1">
                      {item.productName}
                    </p>
                  </div>
                  <p className="text-sm text-orange-500 font-medium whitespace-nowrap">
                    {formatVND(item.price*item.quantity)}
                  </p>
                </div>
              ))}
            </div>

            {/* PHẦN DƯỚI */}
            <div className="flex items-center justify-between pt-3 border-t border-gray-100">
              <p className="text-xs text-gray-500">
                {hiddenCount > 0
                  ? `+${hiddenCount} Thêm Hàng Vào Giỏ`
                  : `${carts.length} Thêm Hàng Vào Giỏ`}
              </p>
              <button onClick={()=>{
                navigate("/user/cart")
              }} className="bg-orange-500 text-white text-sm px-4 py-1 rounded hover:bg-orange-600 transition">
                Xem Giỏ Hàng
              </button>
            </div>
          </div>
        )}

        {/* MŨI TÊN */}
        <div className="absolute top-[-8px] right-3 w-4 h-4 bg-white rotate-45" />
      </div>
    </div>
  );
}
