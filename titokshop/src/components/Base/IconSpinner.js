import React from "react";
export default function IconSpinner({ size = 30 }) {
  return (
    <svg
      width={size}
      height={size}
      viewBox="0 0 50 50"
      role="img"
      aria-label="Loading"
    >
      <defs>
        <linearGradient id="tiktokGradient" x1="0" x2="1">
          <stop stopColor="#00f2ff" offset="0%" />
          <stop stopColor="#ff2ea6" offset="100%" />
        </linearGradient>
      </defs>
      <circle
        cx="25"
        cy="25"
        r="20"
        stroke="url(#tiktokGradient)"
        strokeWidth="5"
        strokeLinecap="round"
        fill="none"
        strokeDasharray="90"
        strokeDashoffset="0"
      >
        <animateTransform
          attributeName="transform"
          type="rotate"
          from="0 25 25"
          to="360 25 25"
          dur="1s"
          repeatCount="indefinite"
        />
      </circle>
    </svg>
  );
}