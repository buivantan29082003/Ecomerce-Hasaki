import React, { useEffect, useState } from "react";
import { formatVND } from "../../Service/FormatDate";
import api from "../../config/AxiosConfig";

const ProductFlashSale = () => {
  const [productOnsale, setProductOnsale] = useState([]); 
  useEffect(() => {
    api.get("/common/product/onsale").then((v) => {
      setProductOnsale(v.data.data);
    });
  }, []);
  return (
    <>
      {ProductFlashSale.length > 0 && (
        <>
          <div className="inline-flex items-center gap-4 text-lg">
            {/* icon sấm sét */}
            <svg
              className="w-6 h-6 text-red-500"
              viewBox="0 0 24 24"
              aria-hidden="true"
              role="img"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M13.5 2L3 13h7l-1 9L21 11h-7l-.5-9z"
                fill="currentColor"
              />
            </svg>

            {/* chữ Flash Sale */}
            <span className="font-bold text-lg tracking-tight">
              <span className="text-red-500 mr-1">Flash</span>
              <span className="text-black">Sale</span>
            </span>
          </div>
          <div className="flex gap-5 items-center overflow-x-auto no-scrollbar">
            {productOnsale.map((v, i) => (
              <FlashSaleItem key={i} item={v} />
            ))}
          </div>
        </>
      )}
    </>
  );
};

const FlashSaleItem = ({ item }) => {
  const [timeLeft, setTimeLeft] = useState("");

  useEffect(() => {
    if (!item.startTime || !item.endTime) return;

    const updateCountdown = () => {
      const now = new Date();

      // Tách giờ phút giây từ start và end
      const [sh, sm, ss] = item.startTime.split(":").map(Number);
      const [eh, em, es] = item.endTime.split(":").map(Number);

      const startDate = new Date(
        now.getFullYear(),
        now.getMonth(),
        now.getDate(),
        sh,
        sm,
        ss
      );
      const endDate = new Date(
        now.getFullYear(),
        now.getMonth(),
        now.getDate(),
        eh,
        em,
        es
      );

      if (now < startDate) {
        // Nếu chưa tới giờ bắt đầu → đếm ngược đến giờ bắt đầu
        const diff = startDate - now;
        const h = Math.floor(diff / (1000 * 60 * 60));
        const m = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
        const s = Math.floor((diff % (1000 * 60)) / 1000);
        setTimeLeft(
          "Bắt đầu sau " +
            [h, m, s].map((v) => String(v).padStart(2, "0")).join(":")
        );
        return;
      }

      if (now > endDate) {
        setTimeLeft("Đã kết thúc");
        return;
      }

      // Đếm ngược đến endTime
      const diff = endDate - now;
      const h = Math.floor(diff / (1000 * 60 * 60));
      const m = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
      const s = Math.floor((diff % (1000 * 60)) / 1000);

      setTimeLeft([h, m, s].map((v) => String(v).padStart(2, "0")).join(":"));
    };

    updateCountdown();
    const timer = setInterval(updateCountdown, 1000);
    return () => clearInterval(timer);
  }, [item.startTime, item.endTime]);

  return (
    <div className="w-40 relative">
      {/* Khung ảnh */}
      <div className="relative">
        <img
          src={item.productImage}
          alt={item.productName}
          className="rounded-lg w-full h-40 object-cover"
        />

        {/* Khung giờ absolute */}
        {timeLeft && (
          <div className="absolute top-2 right-2 bg-black text-white text-xs font-bold px-2 py-1 rounded-md flex space-x-1">
            {timeLeft.includes(":") ? (
              timeLeft.split(":").map((t, i) => (
                <React.Fragment key={i}>
                  <span>{t}</span>
                  {i < 2 && <span>:</span>}
                </React.Fragment>
              ))
            ) : (
              <span>{timeLeft}</span>
            )}
          </div>
        )}

        {/* Tag Hot */}

        <span
          style={{ zIndex: 9999 }}
          className="absolute bottom-2 left-0 bg-red-500 text-white text-xs px-1 py-0.5 rounded-tr-md"
        >
          Hot
        </span>
      </div>

      {/* Giá */}
      <div className="mt-2 text-black font-semibold">
        {formatVND(item.price)}
      </div>
    </div>
  );
};

export default ProductFlashSale;
