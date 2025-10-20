import { useRef, useState, useEffect } from "react";

const Popup = ({ textHover, content }) => {
  const popupRef = useRef(null);
  const containerRef = useRef(null);
  const [visible, setVisible] = useState(false);
  const [placement, setPlacement] = useState("bottom");

  const handlePosition = () => {
    if (!containerRef.current || !popupRef.current) return;
    const rect = containerRef.current.getBoundingClientRect();

    const spaceBelow = window.innerHeight - rect.bottom;
    const spaceAbove = rect.top;

    if (spaceBelow < 150 && spaceAbove > spaceBelow) {
      setPlacement("top");
    } else {
      setPlacement("bottom");
    }
  };

  useEffect(() => {
    window.addEventListener("resize", handlePosition);
    return () => window.removeEventListener("resize", handlePosition);
  }, []);

  const showPopup = () => {
    handlePosition();
    setVisible(true);
  };

  const hidePopup = () => {
    setVisible(false);
  };

  return (
    <div
      ref={containerRef}
      className="relative inline-block"
      onMouseEnter={showPopup}
      onMouseLeave={hidePopup}
    >
      {/* Trigger text */}
      <div className="text-gray-700 text-sm cursor-pointer select-none">
        {textHover}
      </div>

      {/* Popup content */}
      {visible && (
        <div
          ref={popupRef}
          className={`absolute bg-white shadow-xl border rounded-lg w-72 sm:w-96 p-4 z-50 transition-all duration-200 
          ${placement === "top" ? "bottom-full mb-2" : "top-full mt-2"}
          left-1/2 -translate-x-1/2 animate-fadeIn`}
        >
          {content}
        </div>
      )}
    </div>
  );
};

export default Popup;
