import { greenMainColor } from "../../config/TextColor";

const PromoProgress = ({ promo }) => {
  const now = new Date();
  const start = new Date(promo.startDate); 
  const end = new Date(promo.endDate);     

  let percent = 0;
  if (now < start) percent = 0;
  else if (now > end) percent = 100;
  else percent = ((now - start) / (end - start)) * 100;

  return (
    <div className="w-full max-w-md p-3 border rounded shadow-sm">
      <div className="relative w-full bg-gray-200 rounded-full h-3">
        <div
          className={`h-3 rounded-full transition-all duration-500 bg-${greenMainColor}`}
          style={{ width: `${percent}%` }}
        ></div>
        {/* Hiển thị % trên bar */}
        <span className="absolute inset-0 flex items-center justify-center text-[10px] text-white font-medium">
          {percent.toFixed(0)}% of time
        </span>
      </div>

      <div className="flex justify-between text-xs text-gray-500 mt-2">
        <span>{start.toLocaleString()}</span>
        <span>{end.toLocaleString()}</span>
      </div>
    </div>
  );
};


export default PromoProgress