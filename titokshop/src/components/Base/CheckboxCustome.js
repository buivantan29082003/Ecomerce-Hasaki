function CustomCheckbox({ checked, onChange,color="orange-500" }) {
  return (
    <label className="relative inline-flex items-center cursor-pointer">
      <input
        type="checkbox"
        checked={checked}
        onChange={onChange}
        className={`peer appearance-none w-5 h-5 border-2 border-${color} rounded-full cursor-pointer transition-all duration-200`}
      />
      {/* Chấm nhỏ giữa khi checked */}
      <span
        className={`absolute w-2.5 h-2.5 bg-${color} rounded-full left-1/2 top-1/2 transform -translate-x-1/2 -translate-y-1/2 scale-0 peer-checked:scale-100 transition-transform duration-200`}
      ></span>
    </label>
  );
}

export default CustomCheckbox