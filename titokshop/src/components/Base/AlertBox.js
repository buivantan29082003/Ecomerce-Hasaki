const AlertBox = ({ message, title }) => (
  <div
    className="w-full rounded-md border border-yellow-200 bg-yellow-50 px-4 py-3 text-sm text-gray-800"
    role="alert"
  >
    <div className="flex gap-3 items-start">
      {/* Icon */}
      <div className="flex-shrink-0 mt-0.5">
        <div className="w-6 h-6 rounded-full bg-yellow-100 flex items-center justify-center">
          <svg
            width="14"
            height="14"
            viewBox="0 0 24 24"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
            aria-hidden="true"
          >
            <circle
              cx="12"
              cy="12"
              r="10"
              stroke="#F59E0B"
              strokeWidth="1.6"
              fill="#FFF7ED"
            />
            <path
              d="M12 7v6"
              stroke="#92400E"
              strokeWidth="1.6"
              strokeLinecap="round"
              strokeLinejoin="round"
            />
            <path
              d="M12 17h.01"
              stroke="#92400E"
              strokeWidth="1.6"
              strokeLinecap="round"
              strokeLinejoin="round"
            />
          </svg>
        </div>
      </div>

      {/* Nội dung */}
      <div className="flex-1 leading-relaxed">
        {title && (
          <p className="font-semibold text-sm text-yellow-800 mb-1">
            {title}
          </p>
        )}
        <p className="text-md">{message}</p>
      </div>
    </div>
  </div>
);

export default AlertBox;