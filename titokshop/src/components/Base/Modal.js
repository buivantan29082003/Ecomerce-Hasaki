import { useState, useRef, useEffect } from "react";

export default function CustomModal({ trigger, children, customeFunction,height=70, witdh=70 }) {
  const [isOpen, setIsOpen] = useState(false);
  const overlayRef = useRef();

  // Đóng khi nhấn phím Escape
  useEffect(() => {
    const handleEsc = (e) => {
      if (e.key === "Escape") setIsOpen(false);
    };
    document.addEventListener("keydown", handleEsc);
    return () => document.removeEventListener("keydown", handleEsc);
  }, []);

  // Đóng khi click ra ngoài
  const handleClickOutside = (e) => {
    if (e.target === overlayRef.current) {
      customeFunction?.();
      setIsOpen(false);
    }
  };

  return (
    <>
      {/* Trigger mở modal */}
      <div onClick={() => setIsOpen(true)} className="inline-block cursor-pointer">
        {trigger}
      </div>

      {/* Overlay modal */}
      {isOpen && (
        <div
          ref={overlayRef}
          onClick={handleClickOutside}
          className="fixed inset-0 bg-black/40 backdrop-blur-sm z-[1001010101] flex items-center justify-center"
        >
          {/* Nội dung modal */}
          <div
            className={`bg-white w-[${witdh}vw] h-[${height}vh] rounded-lg shadow-xl p-6 relative`}
            style={{ zIndex: 10000 }}
          >
            {/* Nút đóng nhanh */}
            <button
              onClick={() => setIsOpen(false)}
              className="absolute top-3 right-3 text-gray-600 hover:text-black text-xl"
            >
              &times;
            </button>

            {children}
          </div>
        </div>
      )}
    </>
  );
}
