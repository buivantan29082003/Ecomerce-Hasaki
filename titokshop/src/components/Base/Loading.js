import React from "react";

const LoadingSkeleton = ({ type = "grid", count = 5, isShow = false, width = 40, height = 40 }) => {
  if (!isShow) return null;

  const baseClass = "bg-gray-200 animate-pulse rounded-xl overflow-hidden shadow-sm";

  const gridLayout = "grid gap-3 grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5";
  const rowLayout = "flex flex-wrap gap-3";

  // 🔥 Dạng card ngang (horizontal)
  const horizontalItem = (
    <div className="flex gap-4 p-4 bg-white rounded-xl shadow-sm border border-gray-100">
      <div className={`${baseClass}`} style={{ width: 100, height: 100 }}></div>
      <div className="flex flex-col justify-between flex-1">
        <div className={`${baseClass} h-5 w-3/5`}></div>
        <div className={`${baseClass} h-4 w-2/5`}></div>
        <div className={`${baseClass} h-4 w-1/3`}></div>
        <div className="flex gap-2 mt-2">
          <div className={`${baseClass} h-6 w-16`}></div>
          <div className={`${baseClass} h-6 w-24`}></div>
        </div>
      </div>
    </div>
  );

  const gridItem = (
    <div className={`${baseClass}`} style={{ width: width, height: height }}></div>
  );

  const rowItem = (
    <div className={`${baseClass}`} style={{ width: 60, height: 60 }}></div>
  );

  const renderItems = () => {
    switch (type) {
      case "grid":
        return (
          <div className={gridLayout}>
            {[...Array(count)].map((_, i) => (
              <div key={i} className={`${baseClass} aspect-square`}></div>
            ))}
          </div>
        );
      case "row":
        return (
          <div className={rowLayout}>
            {[...Array(count)].map((_, i) => (
              <div key={i}>{rowItem}</div>
            ))}
          </div>
        );
      case "horizontal":
        return (
          <div className="flex flex-col gap-3">
            {[...Array(count)].map((_, i) => (
              <div key={i}>{horizontalItem}</div>
            ))}
          </div>
        );
      default:
        return null;
    }
  };

  return <>{renderItems()}</>;
};

export default LoadingSkeleton;
