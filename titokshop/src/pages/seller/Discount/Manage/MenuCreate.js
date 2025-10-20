import React from "react";
import { Link } from "react-router-dom";
import { greenMain } from "../../../../config/TextColor";

const MenuCreate = React.memo(() => {
  return (
    <div className="w-full   mx-auto mb-4  ">
      <h2 className="text-xl font-semibold">Tạo Khuyến Mãi</h2>
      <p className="text-gray-500 text-sm mb-6">
        Thiết lập các chương trình khuyến mãi riêng của Shop để tăng Doanh số và
        cải thiện tỉ lệ chuyển đổi{" "}
        <Link
          to={"/s"}
          className={`hover:underline ${greenMain} font-semibold`}
        >
          Tìm hiểu thêm
        </Link>
      </p>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        {/* Card 1 */}
        <div className="border rounded-lg shadow-sm p-5 flex flex-col justify-between hover:shadow-md transition">
          <div>
            <div className="flex items-center">
              <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 48 48" fill="none">
     <rect x="4" y="4" width="40" height="40" rx="8" fill="#E8FAF7"/>
     <path d="M16 14h16c1.1 0 2 .9 2 2v16c0 1.1-.9 2-2 2H16c-1.1 0-2-.9-2-2V16c0-1.1.9-2 2-2z" 
          stroke="#000" stroke-width="2" fill="none" stroke-linejoin="round"/>
    <circle cx="20" cy="20" r="2" fill="#000"/>
    <path d="M16 30l6-6 6 6 6-8" 
          stroke="#000" stroke-width="2" fill="none" stroke-linecap="round" stroke-linejoin="round"/>
  </svg>
              <span className="ml-2 text-base font-medium">
                Chương Trình Của Shop
              </span>
            </div>

            <p className="text-gray-600 text-sm">
              Tạo Chương trình của Shop để thiết lập các chương trình giảm giá
              sản phẩm
            </p>
          </div>
          <button className="mt-4 bg-[#009DA6] text-white rounded px-4 py-1 w-fit  ">
            Tạo
          </button>
        </div>

        {/* Card 2 */}
        <div className="border rounded-lg shadow-sm p-5 flex flex-col justify-between hover:shadow-md transition">
          <div>
            <div className="flex items-center">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="40"
                height="40"
                viewBox="0 0 48 48"
                fill="none"
              >
                <rect
                  x="4"
                  y="4"
                  width="40"
                  height="40"
                  rx="8"
                  fill="#E8FAF7"
                />
                <path
                  d="M22 12L18 24h6l-2 12 8-14h-6l4-10z"
                  stroke="#000"
                  stroke-width="2"
                  fill="none"
                  stroke-linejoin="round"
                  stroke-linecap="round"
                />
              </svg>
              <span className="ml-2 text-base font-medium">
                Chương Trình Của Shop
              </span>
            </div>

            <p className="text-gray-600 text-sm">
              Tạo Chương trình của Shop để thiết lập các chương trình giảm giá
              sản phẩm
            </p>
          </div>
          <button className="mt-4 bg-[#009DA6] text-white rounded px-4 py-1 w-fit  ">
            Tạo
          </button>
        </div>
      </div>
    </div>
  );
});

export default MenuCreate;
