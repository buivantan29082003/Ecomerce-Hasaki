import { useEffect } from "react"

const VoucherBuyBack=({voucher,onchangeProp})=>{
    useEffect(()=>{ 
        onchangeProp(25,"latestOrderDays") 
    },[])
    return <>
         
        <div className="mt-3">
          <label className="block font-semibold mb-1">
            Ngày tối thiểu mua lại
            <span className="ml-1 text-gray-400">(?)</span>
          </label>
          <p className="text-sm text-gray-400 font-light mb-3">
            Recommended length:{" "}
            <span className="font-semibold text-[#009DA6]">25 characters</span>{" "}
            or more. Will be identified according to product name.
          </p>
          <div className="relative w-full">
            <input
              type="number" 
              placeholder="Enter average day purchase"
              value={voucher.latestOrderDays}
              onChange={(e)=>{
                onchangeProp(e.target.value,"latestOrderDays")
              } }
              min={25}
              className="w-full hover:border-[#009DA6] border rounded px-3 py-1 focus:outline-none focus:ring-1 focus:ring-[#009DA6] pr-12"
            />
            <span className="absolute right-2 bottom-1 text-xs text-gray-400">
              0/255
            </span>
          </div>
        </div>
    </>
}

export default VoucherBuyBack
 