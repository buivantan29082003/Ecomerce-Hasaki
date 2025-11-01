import React from "react";
import { Outlet } from "react-router-dom";

export default function DashBoard() {
  return (
    <div className="w-full bg-gray-50">
        <div className="flex mx-10 p-2 flex-col md:flex-row min-h-screen bg-gray-50">
       
      <aside className="w-full md:w-1/4 bg-white border-r px-6 py-8 text-gray-700">
        <div className="flex items-center gap-3 mb-6">
          <div className="w-12 h-12 rounded-full bg-gray-200"></div>
          <div>
            <p className="font-semibold">Chào bạn</p>
            <p className="text-sm text-gray-500">Chỉnh sửa tài khoản</p>
          </div>
        </div>

        <ul className="space-y-3 text-gray-600 font-medium text-sm">
          <li className="hover:text-green-700 cursor-pointer [word-spacing:0.1rem]">Quản lý tài khoản</li>
          <li className="hover:text-green-700 cursor-pointer">Hasaki tích điểm</li>
          <li className="hover:text-green-700 cursor-pointer">Thông tin tài khoản</li>
          <li className="hover:text-green-700 cursor-pointer">Đơn hàng của tôi</li>
          <li className="hover:text-green-700 cursor-pointer">Booking của tôi</li>
          <li className="text-orange-500 font-semibold">
            Sổ địa chỉ nhận hàng
          </li>
          <li className="hover:text-green-700 cursor-pointer">Danh sách yêu thích</li>
          <li className="hover:text-green-700 cursor-pointer">Mua lại</li>
          <li className="hover:text-green-700 cursor-pointer">Hỏi đáp</li>
        </ul>
      </aside>

      {/* Main content */}
      <main className="flex-1  ml-4 "> 
        <div className="  rounded-lg shadow-sm min-h-[300px] px-8 py-2 ">
          <Outlet/>
        </div>
      </main>
    </div>
    </div>
  );
}
