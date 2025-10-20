import React from "react";

const VoucherCard = ({ 
  item
}) => {
  return (
    <div className="flex items-center justify-between bg-white border rounded-lg shadow-sm p-3 w-full max-w-lg">
      {/* Logo */}
      <div className="flex items-center gap-3">
        <img 
          src={item.image}
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
          <p className="text-gray-600 text-xs">Hình thức áp dụng {item.voucherStyle}</p>
          <p className="text-gray-500 text-xs mt-1">
            HSD: {item.endDate}
          </p>
        </div>
      </div>

      {/* Nút lưu */}
      <button
        // onClick={onSave}
        className="bg-orange-500 hover:bg-orange-600 text-white text-sm font-semibold px-4 py-1.5 rounded"
      >
        Xem {item.totalProduct} đang giảm
      </button>
    </div>
  );
};

export default VoucherCard;
