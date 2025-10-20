const TooltipCustome=({title,icon})=>{
    return <>
        <div class="relative group inline-block">
            <span class="w-3 h-3 flex items-center justify-center rounded-full border border-gray-400 text-gray-500 text-[10px] cursor-pointer">
                {icon}
            </span> 
            <div class="absolute bottom-full mb-2 hidden group-hover:block w-max max-w-xs px-2 py-1 text-xs text-white bg-gray-800 rounded shadow-lg">
                {title}
            </div>
        </div>
    </>
}
export default TooltipCustome