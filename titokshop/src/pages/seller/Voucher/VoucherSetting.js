import { useState } from "react";
import TooltipCustome from "../../../components/Base/TooltipCustome";
import { greenMain, greenMainColor } from "../../../config/TextColor";
import { format } from "date-fns";

const VoucherSetting = ({voucher,setVoucher,onchangeProp}) => {

  const [limit,setLimit]=useState(true)

  return (
    <div className="flex flex-col md:flex-row  ">
      {/* LEFT: Nội dung chính */}
      <div
        className="w-full md:w-2/3 p-6 overflow-y-auto"
        style={{ scrollbarWidth: "none", msOverflowStyle: "none" }}
      >
        <h2 className="font-semibold text-2xl mb-4">Setting</h2>

        {/* Chọn loại giảm giá */}
        <div className="flex gap-4 mb-6">
          <div className={`flex-1 border rounded-lg p-4 cursor-pointer border-${greenMainColor} `}>
            <input onClick={()=>onchangeProp("PRICE","voucherType")} checked={voucher.voucherType==="PRICE"} type="radio" className={`mr-2 accent-${greenMainColor} `}/><span className={`inline-block ml-1 font-semibold ${greenMain}`}>Số tiền giảm</span>
            <div className="w-full border border-gray-300 p-2 rounded-md flex items-center justify-between mt-1">
              <div className="w-3/5">
                  <p className="text-xs"> Từ của hàng thú cưng</p>
                  <p className="font-bold text-red-700"> Giảm {voucher.discountValue} đ</p>
                  <p className="text-xs"> Đơn tối thiểu chỉ từ: 1000đ</p>
              </div>
              <span className="font-medium w-1/5 text-xs p-1 font-semibold text-center rounded-sm text-white bg-red-600">Nhận</span>
            </div>
          </div>

          <div className={`flex-1 border rounded-lg p-4 cursor-pointer `}>
            <input onClick={()=>onchangeProp("PERCENT","voucherType")} checked={voucher.voucherType==="PERCENT"} type="radio" className={`mr-2 accent-${greenMainColor} `}/><span className={`inline-block ml-1 font-semibold ${greenMain}`}>Phần trăm giảm</span>
            <div className="w-full border border-gray-300 p-2 rounded-md flex items-center justify-between mt-1">
              <div className="w-3/5">
                  <p className="text-xs"> Từ của hàng thú cưng</p>
                  <p className="font-bold text-red-700"> Giảm  {voucher.discountValue} %</p>
                  <p className="text-xs"> Đơn tối thiểu chỉ từ: 1000đ</p>
              </div>
              <span className="font-medium w-1/5 text-xs p-1 font-semibold text-center rounded-sm text-white bg-red-600">Nhận</span>
            </div>
          </div>
        </div>

        {/* Form nhập thông tin */}
        <div className="space-y-4">
          <div>
            <label className="block font-semibold mb-1">
              Discount value <TooltipCustome icon={"?"} title={"Giá trị giảm cho đơn hàng này."}/>
            </label>
            <input
              type="number"
              value={voucher.discountValue}
              onChange={(e)=>{onchangeProp(e.target.value,"discountValue")}}
              placeholder="enter value discount"
              className="w-full hover:border-[#009DA6] border mt-1 rounded px-3 py-1 focus:outline-none focus:ring-1 focus:ring-[#009DA6] pr-12"
            />
          </div>

          <div>
            <label className="block font-semibold mb-1">
              Minimum order <TooltipCustome icon={"?"} title={"Giá trị giảm cho đơn hàng này."}/>
            </label>
            <div className="flex gap-4">
              <label className="flex items-center gap-2">
                <input onChange={()=>setLimit(!limit)} checked={limit} type="radio" className={`mr-2 accent-${greenMainColor} `} name="minSpend" defaultChecked /> Đặt giá trị
              </label>
              <label className="flex items-center gap-2">
                <input onChange={()=>setLimit(!limit)} checked={!limit} type="radio" className={`mr-2 accent-${greenMainColor} `} name="minSpend" /> Không có mức chi tối
                thiểu
              </label>
            </div>
            <input
              type="number"
              value={voucher.minimumPurchase}
              onChange={e=>onchangeProp(e.target.value,"minimumPurchase")}
              disabled={!limit}
              placeholder="enter minimum total amount"
              className="w-full hover:border-[#009DA6] border mt-2 rounded px-3 py-1 focus:outline-none focus:ring-1 focus:ring-[#009DA6] pr-12"
            />
          </div>

          <div>
            <label className="block font-semibold mb-1">
              Limited quantity <TooltipCustome icon={"?"} title={"Giá trị giảm cho đơn hàng này."}/>
            </label>
            <input
              type="number"
              value={voucher.limitUsage}
              min={voucher.quantityPer}
              onChange={e=>{ 
                if(voucher.quantityPer<e.target.value){
                  onchangeProp(e.target.value,"limitUsage")
                }
              }}
              placeholder="enter minimum total amount"
              className="w-full hover:border-[#009DA6] border mt-2 rounded px-3 py-1 focus:outline-none focus:ring-1 focus:ring-[#009DA6] pr-12"
            />
          </div>

          <div>
            <label className="block font-semibold mb-1">
              Quantity revice <TooltipCustome icon={"?"} title={"Giá trị giảm cho đơn hàng này."}/>
            </label>
            <div className="flex items-center gap-2">
              <button onClick={()=>{
                if(voucher.quantityPer-1>0){
                                    onchangeProp(voucher.quantityPer-1,"quantityPer")
                  } 
              }} className="px-3 py-1 border rounded">-</button>
              <input
                type="number"
                max={voucher.limitUsage}
                min={1}
                value={voucher.quantityPer}
                onChange={e=>{
                  if(voucher.limitUsage>=e.target.value){
                                    onchangeProp(e.target.value,"quantityPer")
                  }  
                }}
                className="w-16 text-center border rounded"
              />
              <button onClick={()=>{
                if(voucher.limitUsage>=voucher.quantityPer+1){
                                    onchangeProp(voucher.quantityPer+1,"quantityPer")
                  } 
              }} className="px-3 py-1 border rounded">+</button>
            </div>
          </div>
        </div>
      </div>

      {/* RIGHT: Preview */}
      <div className="w-full md:w-1/3 hidden md:block  overflow-y-auto   p-4"></div>
    </div>
  );
};

export default VoucherSetting;
