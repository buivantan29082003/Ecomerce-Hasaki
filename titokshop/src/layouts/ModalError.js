import { FiAlertCircle } from "react-icons/fi";

const ModalError = () => {
  return (
    <>
      {/* Overlay mờ */}
      <div id="opa" style={{display:"none"}} className="fixed inset-0 bg-black/40 z-40" />

      {/* Card căn giữa tuyệt đối */}
      <div  id="alertError" style={{display:"none"}}
        className="fixed left-1/2 top-1/2 w-[360px] -translate-x-1/2 -translate-y-1/2 z-50 rounded-2xl bg-white p-5 shadow-xl"
        role="dialog"
        aria-labelledby="aw-title"
        aria-describedby="aw-desc"
      >
        {/* Icon cảnh báo */}
        <div className="mx-auto mb-3 flex h-10 w-10 items-center justify-center rounded-full bg-red-50">
          <FiAlertCircle className="h-6 w-6 text-red-500" />
        </div>

        {/* Tiêu đề */}
        <h2 id="aw-title" className="text-center text-lg font-semibold">
          Account warning
        </h2>

        {/* Nội dung */}
        <p id="aw-desc" className="mt-2 text-center text-[13px] leading-5 text-gray-600">
          Your account is at high risk of being restricted due to multiple
          violations. Review our Community Guidelines to avoid losing access.
          Submit an appeal if you think we made a mistake in any of the
          violations.
        </p>

        {/* Nút hành động */}
        <button className="mt-4 w-full rounded-lg bg-[#ff2d55] py-2.5 text-sm font-semibold text-white hover:brightness-95 active:brightness-90">
          See details
        </button>

        {/* Link phụ */}
        <button className="mx-auto mt-2 block text-xs text-gray-500 hover:text-gray-700">
          Got it
        </button>
      </div>
    </>
  )
}

export default ModalError;
