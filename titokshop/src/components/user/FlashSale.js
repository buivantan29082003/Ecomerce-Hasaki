import React, { useEffect, useState } from "react";

export default function FlashSale({ promotion }) {
  const [timeLeft, setTimeLeft] = useState({ hours: 0, minutes: 0, seconds: 0 });

  // Ghép giờ theo ngày hôm nay (nếu chỉ có HH:mm:ss)
  const today = new Date().toISOString().split("T")[0]; 
  const endTime = new Date(`${today}T${promotion.endTime}`);
  
  useEffect(() => {
    const interval = setInterval(() => {
      const now = new Date().getTime();
      const distance = endTime.getTime() - now;

      if (distance <= 0) {
        clearInterval(interval);
        setTimeLeft({ hours: 0, minutes: 0, seconds: 0 });
      } else {
        const hours = Math.floor((distance / (1000 * 60 * 60)) % 24);
        const minutes = Math.floor((distance / (1000 * 60)) % 60);
        const seconds = Math.floor((distance / 1000) % 60);

        setTimeLeft({ hours, minutes, seconds });
      }
    }, 1000);

    return () => clearInterval(interval);
  }, [promotion.endTime]);

  const format = (num) => (num < 10 ? `0${num}` : num);

  return (
    <div className="w-full">
      {/* Thanh flash sale */}
      <div className="flex items-center justify-between bg-orange-500 text-white font-bold px-4 py-2 rounded-md">
        <div className="flex items-center gap-2">
          <span className="bg-white text-orange-500 font-bold px-2 py-1 rounded-sm">⚡</span>
          <span>{promotion.promotionName}</span>
        </div>
        <div className="flex items-center gap-2">
          <span>KẾT THÚC TRONG</span>
          <div className="flex gap-1">
            <span className="bg-black px-2 py-1 rounded text-white">{format(timeLeft.hours)}</span> :
            <span className="bg-black px-2 py-1 rounded text-white">{format(timeLeft.minutes)}</span> :
            <span className="bg-black px-2 py-1 rounded text-white">{format(timeLeft.seconds)}</span>
          </div>
        </div>
      </div>

      {/* Text phụ */}
      <p className="text-orange-500 text-sm mt-2 px-2">
        Giảm {promotion.discountValue}% • {promotion.startTime} - {promotion.endTime}
      </p>
    </div>
  );
}
