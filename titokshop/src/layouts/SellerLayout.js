import { Outlet, NavLink, useLocation } from "react-router-dom";
import React from "react";
import { FiAlertCircle } from "react-icons/fi";
import ModalError from "./ModalError";

const navItems = [
  {
    label: "Tổng quan",
    path: "/seller",
    icon: (active) => (
      <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none"
        stroke={active ? "#009DA6" : "#000"}
        strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
        <path d="M3 10.5L12 3l9 7.5V21a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1z" />
        <path d="M10 21v-4a2 2 0 0 1 4 0v4" stroke="#009DA6" />
        <path d="M10 17a2 2 0 0 1 4 0" stroke="#009DA6" />
      </svg>
    ),
  },
  {
    label: "Đơn hàng",
    path: "/seller/orders",
    icon: (active) => (
      <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none"
        stroke={active ? "#009DA6" : "#000"}
        strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round">
        <rect x="5" y="4" width="14" height="16" rx="2" ry="2" />
        <path d="M9 4V3h6v1" />
        <rect x="9" y="2" width="6" height="3" rx="1" ry="1" />
        <line x1="8" y1="10" x2="16" y2="10" stroke="#009DA6" strokeWidth="2" />
        <line x1="8" y1="14" x2="14" y2="14" stroke="#009DA6" strokeWidth="2" />
      </svg>
    ),
  },
  {
    label: "Sản phẩm",
    path: "/seller/products",
    icon: (active) => (
      <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
        viewBox="0 0 24 24" fill="none"
        stroke={active ? "#009DA6" : "#000"}
        strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round">
        <path d="M6 8 L5 20 H19 L18 8 Z" />
        <path d="M9 10 a3 3 0 0 1 6 0" stroke="#009DA6" />
      </svg>
    ),
  },
  {
    label: "Marketing - Voucher",
    path: "/seller/voucher/overal",
    icon: (active) => (
      <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
        viewBox="0 0 24 24" fill="none"
        stroke={active ? "#009DA6" : "#000"}
        strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
        <path d="M3 9v6h3l7 4V5l-7 4H3z" />
        <path d="M14 10l5-2v8l-5-2" />
        <rect x="6" y="15" width="2" height="4" rx="1" stroke="#009DA6" />
      </svg>
    ),
  },
  {
    label: "Marketing - Discount",
    path: "/seller/discount/overal",
    icon: (active) => (
      <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
        viewBox="0 0 24 24" fill="none"
        stroke={active ? "#009DA6" : "#000"}
        strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
        <path d="M3 9v6h3l7 4V5l-7 4H3z" />
        <path d="M14 10l5-2v8l-5-2" />
        <rect x="6" y="15" width="2" height="4" rx="1" stroke="#009DA6" />
      </svg>
    ),
  },
  {
    label: "LIVE - bán hàng",
    path: "/seller/live",
    icon: (active) => (
      <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
        viewBox="0 0 24 24" fill="none"
        stroke={active ? "#009DA6" : "#000"}
        strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
        <rect x="3" y="7" width="13" height="10" rx="2" ry="2" />
        <polygon points="16 8 22 5 22 19 16 16" />
        <polygon
          points="9 10 13 12 9 14"
          fill={active ? "#FFFFFF" : "#009DA6"}
          stroke="#009DA6"
          strokeWidth="1"
        />
      </svg>
    ),
  },
  {
    label: "Đánh giá",
    path: "/seller/dev",
    icon: (active) => (
      <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
        viewBox="0 0 24 24" fill="none"
        stroke={active ? "#009DA6" : "#000"}
        strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
        <line x1="8" y1="3" x2="12" y2="8" />
        <line x1="16" y1="3" x2="12" y2="8" />
        <circle cx="12" cy="14" r="5" />
        <polygon
          points="12,12 12.6,13.6 14.3,13.6 13,14.7 13.5,16.3 12,15.3 10.5,16.3 11,14.7 9.7,13.6 11.4,13.6"
          fill={active ? "#FFFFFF" : "#009DA6"}
          stroke="#009DA6"
          strokeWidth="1"
        />
      </svg>
    ),
  },
  {
    label: "Trafic",
    path: "/seller/product/add",
    icon: (active) => (
      <svg width="20" height="20" viewBox="0 0 40 40"
        xmlns="http://www.w3.org/2000/svg">
        <rect x="2" y="2" width="12" height="12"
          fill={active ? "#FFFFFF" : "#66C4CA"}
          stroke={active ? "#66C4CA" : "none"}
          strokeWidth={active ? "2" : "0"}
        />
        <rect x="18" y="2" width="12" height="12"
          fill={active ? "#66C4CA" : "#FFFFFF"}
          stroke="#66C4CA" strokeWidth="2" />
        <rect x="2" y="18" width="12" height="12"
          fill={active ? "#FFFFFF" : "#66C4CA"}
          stroke={active ? "#66C4CA" : "none"}
          strokeWidth={active ? "2" : "0"}
        />
        <rect x="18" y="18" width="12" height="12"
          fill={active ? "#FFFFFF" : "#66C4CA"}
          stroke={active ? "#66C4CA" : "none"}
          strokeWidth={active ? "2" : "0"}
        />
      </svg>
    ),
  },
];

export default function SellerLayout() {
  const location = useLocation();

  return (
    <>
     {/* modal ở đây */}
       {/* Overlay mờ (tuỳ thích) */}
      
      <ModalError/>
      <div className="w-full bg-black text-white px-6 py-4 flex justify-between items-center">
        <div className="flex items-center space-x-4">
          <img
            src="data:image/png;base64,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"
            alt="TikTok Logo"
            className="w-8 h-8"
          />
          <span className="font-semibold text-lg">TikTok Shop</span>
          <div className="w-px h-6 bg-gray-600 mx-2"></div>
          <span className="text-lg font-semibold">Trung tâm nhà bán hàng</span>
        </div>
        <div className="flex items-center space-x-6">
          <div className="flex items-center space-x-1 cursor-pointer hover:opacity-80">
            <span className="text-sm">Trợ giúp</span>
          </div>
          <div className="w-8 h-8 bg-gray-100 rounded-full flex items-center justify-center cursor-pointer hover:opacity-80">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
     viewBox="0 0 24 24" fill="none"
     stroke="#000" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
   <path d="M3 10.5L7.5 6h9L21 10.5V21a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1z"/>
   <path d="M5 10.5
           Q6 11.5 7 10.5
           Q8 11.5 9 10.5
           Q10 11.5 11 10.5
           Q12 11.5 13 10.5
           Q14 11.5 15 10.5
           Q16 11.5 17 10.5
           Q18 11.5 19 10.5" />
   <rect x="10" y="14" width="4" height="6" rx="1" fill="#009DA6" />
</svg>

          </div>
        </div>
      </div>

      <div className="flex h-screen">
        <aside className="bg-white border-r shadow-sm p-4 
                  w-16 md:w-64 transition-all duration-300">
  <ul className="space-y-2">
    {navItems.map((item, index) => {
      const isActive = location.pathname === item.path;
      return (
        <li key={index}>
          <NavLink
            to={item.path}
            className={`flex items-center md:gap-3 justify-center md:justify-start p-2 rounded transition-colors
              ${isActive
                ? "bg-[#66C4CA]/10 text-[#009DA6] font-semibold"
                : "text-black hover:bg-gray-100"
              }`}
          >
            {item.icon(isActive)}
            {/* Ẩn label trên màn hình nhỏ */}
            <span className="hidden md:inline text-[14px]">{item.label}</span>
          </NavLink>
        </li>
      );
    })}
  </ul>
</aside>


        <main className="flex-1 p-6 overflow-auto">
          <Outlet />
        </main>
      </div>
    </>
  );
}
