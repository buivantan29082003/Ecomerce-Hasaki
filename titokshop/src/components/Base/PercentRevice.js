import React from "react";

export default function CircularProgress({ value = 20, size = 120, strokeWidth = 10 }) {
  const radius = (size - strokeWidth) / 2;
  const circumference = 2 * Math.PI * radius;
  const offset = circumference - (value / 100) * circumference;

  return (
    <div className="flex items-center justify-center">
      <svg width={size} height={size}>
        {/* Vòng nền */}
        <circle
          stroke="#e5e7eb" // màu xám nhạt (Tailwind gray-200)
          fill="transparent"
          strokeWidth={strokeWidth}
          r={radius}
          cx={size / 2}
          cy={size / 2}
        />
        {/* Vòng progress */}
        <circle
          stroke="#0d9488" // màu teal-600
          fill="transparent"
          strokeWidth={strokeWidth}
          strokeLinecap="round"
          strokeDasharray={circumference}
          strokeDashoffset={offset}
          r={radius}
          cx={size / 2}
          cy={size / 2}
          style={{ transition: "stroke-dashoffset 0.5s ease" }}
        />
        {/* Text % */}
        <text
          x="50%"
          y="50%"
          textAnchor="middle"
          dy="0.3em"
          className="text-sm font-semibold fill-black"
        >
          {value}% complete
        </text>
      </svg>
    </div>
  );
}
