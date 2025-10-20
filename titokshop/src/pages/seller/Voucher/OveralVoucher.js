import { useState } from "react";
import { greenMainColor } from "../../../config/TextColor";
import IntroductVoucher from "./IntroductVoucher";
import ManageVoucher from "./ManageVoucher";

const OveralVoucher = () => { 
  const [tap,setTap]=useState(0)
  return (
    <div className="p-4 bg-gray-50">
      <h2 className="font-semibold text-3xl mb-4">Voucher</h2>
      <div className="flex gap-7 mb-4">
        <p onClick={()=>setTap(0)} className={`cursor-pointer font-semibold ${tap==0?`  border-b-2 border-${greenMainColor}`:""}`}>Overview</p>
        <p onClick={()=>setTap(1)} className={`cursor-pointer font-semibold ${tap==1?`  border-b-2 border-${greenMainColor}`:""}`}>Manage your voucher</p>
      </div>
      {tap===0?<IntroductVoucher/>:<ManageVoucher/>}
    </div>
  );
};

export default OveralVoucher;
