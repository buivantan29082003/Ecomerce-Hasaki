import React from "react";
import { Link, Outlet } from "react-router-dom";

export default function DashBoard() {
  return (
    <div className="w-full bg-gray-50 flex justify-center">
      <div className="flex flex-col md:flex-row min-h-screen w-11/12 lg:w-10/12 bg-gray-50 ">
        {/* Menu bên trái */}
        <aside className="w-full md:w-1/4 bg-white border-r px-6 py-8 text-gray-700 mb-4 md:mb-0 rounded-md md:rounded-none">
          <div className="flex items-center gap-3 mb-6">
            <div className="w-12 h-12 rounded-full bg-gray-200"></div>
            <div>
              <p className="font-semibold">Chào bạn</p>
              <p className="text-sm text-gray-500">Chỉnh sửa tài khoản</p>
            </div>
          </div>

          <ul className="space-y-3 text-gray-600 font-medium text-sm">
            <li>
              <Link
                to="/user/dashboard/account"
                className="hover:text-green-700 cursor-pointer"
              >
                Thông tin tài khoản
              </Link>
            </li>
            <li>
              <Link
                to="/user/dashboard/order"
                className="hover:text-green-700 cursor-pointer"
              >
                Đơn hàng của tôi
              </Link>
            </li>
            <li>
              <Link
                to="/dashboard/myvoucher"
                className="hover:text-green-700 cursor-pointer"
              >
                Voucher của tôi
              </Link>
            </li>
            <li>
              <Link
                to="/user/dashboard/address"
                className="hover:text-green-700 cursor-pointer"
              >
                Sổ địa chỉ
              </Link>
            </li>
            <li>
              <Link
                to="/dashboard/wishlist"
                className="hover:text-green-700 cursor-pointer"
              >
                Yêu thích
              </Link>
            </li>
            <li>
              <Link
                to="/dashboard/rating"
                className="hover:text-green-700 cursor-pointer"
              >
                Đánh giá - phản hồi
              </Link>
            </li>
          </ul>
        </aside>

        {/* Main content */}
        <main className="flex-1 md:ml-4">
          <div className="bg-white rounded-lg shadow-sm min-h-[300px] px-4 md:px-8 py-1">
            <Outlet />
          </div>
        </main>
      </div>
    </div>
  );
}
