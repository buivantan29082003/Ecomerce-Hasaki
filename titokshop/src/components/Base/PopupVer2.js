import React, { useState } from "react";
 
export default function PopupV2({content,title}) {
  const [isHovering, setIsHovering] = useState(false);

  const handleMouseLeave = (e) => {
    const related = e.relatedTarget;
    const icon = document.getElementById("cart-icon");
    const popup = document.getElementById("cart-popup");
    if (!icon?.contains(related) && !popup?.contains(related)) {
      setIsHovering(false);
    }
  };

  return (
    <div className="relative flex items-center justify-center">
      {/* ICON GIỎ HÀNG */}
      <div
        id="cart-icon"
        className="cursor-pointer text-3xl  "
        onMouseEnter={() => setIsHovering(true)}
        onMouseLeave={handleMouseLeave}
      >
        {title}
      </div>

      {/* KHUNG POPUP */}
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
        {/* BÊN TRONG TRỐNG - BẠN CÓ THỂ THÊM GÌ MUỐN Ở ĐÂY */}
        <div className="flex items-center justify-center text-gray-400 w-90 h-80 overflow-auto">
          {content}
        </div>

        {/* MŨI TÊN */}
        <div className="absolute top-[-8px] right-3 w-4 h-4 bg-white rotate-45" />
      </div>
    </div>
  );
}
