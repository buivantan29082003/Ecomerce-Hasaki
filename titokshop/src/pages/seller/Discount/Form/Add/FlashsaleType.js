import { useEffect } from "react";
import { BiCalendar } from "react-icons/bi";

const FlashSale = ({ voucher, setVoucher }) => {
  useEffect(() => {
    setVoucher({
      ...voucher,
      startTime: "07:00", // mặc định nửa đêm
      endTime: "12:00",
    });
  }, []);

  // Hàm so sánh thời gian HH:mm
  const isEndAfterStart = (start, end) => {
    return end > start; // vì string "HH:mm" so sánh trực tiếp vẫn đúng theo thứ tự
  };

  return (
    <div>
      <label className="block font-semibold mb-1 mt-3">
        Thời gian áp dụng <span className="ml-1 text-gray-400">(?)</span>
      </label>
      <p className="text-sm text-gray-400 font-light mb-3">
        Some categories are invite-only and can't be selected. To add these
        categories, click here to submit the{" "}
        <span className="font-semibold text-[#009DA6]">
          invite only application
        </span>
        . Don't upload{" "}
        <span className="font-semibold text-[#009DA6]">prohibit</span> or{" "}
        <span className="font-semibold text-[#009DA6]">restricted</span>{" "}
        products.
      </p>

      <div className="flex flex-col md:flex-row gap-4">
        {/* Start time */}
        <div className="relative w-full">
          <input
            type="time"
            value={voucher.startTime}
            onChange={(e) => {
              const newStart = e.target.value;
              // Nếu endTime < startTime thì reset endTime = startTime
              if (!isEndAfterStart(newStart, voucher.endTime)) {
                setVoucher({ ...voucher, startTime: newStart, endTime: newStart });
              } else {
                setVoucher({ ...voucher, startTime: newStart });
              }
            }}
            className="w-full border rounded px-3 py-2 pr-10 hover:border-[#009DA6] focus:outline-none focus:ring-1 focus:ring-[#009DA6]"
          />
          <span className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-500">
            <BiCalendar />
          </span>
        </div>

        {/* End time */}
        <div className="relative w-full">
          <input
            type="time"
            value={voucher.endTime}
            onChange={(e) => {
              const newEnd = e.target.value;
              // chỉ cho đổi nếu endTime > startTime
              if (isEndAfterStart(voucher.startTime, newEnd)) {
                setVoucher({ ...voucher, endTime: newEnd });
              }
            }}
            className="w-full border rounded px-3 py-2 pr-10 hover:border-[#009DA6] focus:outline-none focus:ring-1 focus:ring-[#009DA6]"
          />
          <span className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-500">
            <BiCalendar />
          </span>
        </div>
      </div>
    </div>
  );
};

export default FlashSale;
