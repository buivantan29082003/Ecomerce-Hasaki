const CheckBox=({isChecked,onchange, isDisable})=>{
    return <>
    <label className="relative flex items-center cursor-pointer select-none">
                  <input
                    type="checkbox"
                    checked={isChecked}
                    onChange={onchange}
                    disabled={isDisable}
                    className="peer hidden"
                  />
                  <span className="w-4 h-4 flex items-center justify-center rounded-sm border border-gray-400 peer-checked:bg-green-700 peer-checked:border-green-700 transition">
                    {/* Dấu tick */}
                    <svg
                      className={`w-3 h-3 text-white ${
                        true ? "opacity-100" : "opacity-0"
                      } transition`}
                      fill="none"
                      stroke="currentColor"
                      strokeWidth="3"
                      viewBox="0 0 24 24"
                    >
                      <path d="M5 13l4 4L19 7" />
                    </svg>
                  </span>
                </label>
    </>
}

export default CheckBox