import { useState, useRef, useEffect } from "react";

export default function CustomModal({
  trigger,
  children,
  customeFunction,
  height = 70, // số %
  witdh = 70,  // số %
}) {
  const [isOpen, setIsOpen] = useState(false);
  const overlayRef = useRef();

  // Convert number => vh/vw
  const finalWidth = `${witdh}vw`;
  const finalHeight = `${height}vh`;

  // ESC close
  useEffect(() => {
    const handleEsc = (e) => {
      if (e.key === "Escape") setIsOpen(false);
    };
    document.addEventListener("keydown", handleEsc);
    return () => document.removeEventListener("keydown", handleEsc);
  }, []);

  // Click outside
  const handleClickOutside = (e) => {
    if (e.target === overlayRef.current) {
      customeFunction?.();
      setIsOpen(false);
    }
  };

  return (
    <>
      <div onClick={() => setIsOpen(true)} className="  cursor-pointer">
        {trigger}
      </div>

      {isOpen && (
        <div
          ref={overlayRef}
          onClick={handleClickOutside}
          className="fixed inset-0 bg-black/40 backdrop-blur-sm z-[99999] 
                     flex items-center justify-center"
        >
          <div
            className="
              bg-white rounded-lg shadow-xl p-6 relative
              overflow-y-auto overflow-x-hidden
              scrollbar-thin scrollbar-thumb-gray-400 scrollbar-track-gray-200
            "
            style={{
              width: finalWidth,
              height: finalHeight,
              maxWidth: "95vw",
              maxHeight: "95vh",
            }}
          >
            {/* Close button */}
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
