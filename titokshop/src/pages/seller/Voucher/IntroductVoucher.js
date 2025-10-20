import { Link } from "react-router-dom";
import { greenMainColor } from "../../../config/TextColor";

const IntroductVoucher=()=>{
    const vouchers = [
    {
      title: "Voucher",
      desc: "Khuyến khích khách gia tăng tổng giá trị đơn hàng và thúc đẩy doanh số tổng thể.",
      route:"common",
      icon:<svg xmlns="http://www.w3.org/2000/svg" className="w-6 h-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M7 7h.01M17 17h.01M7 17l10-10" />
            </svg> 
    },
    {
      title: "Voucher cho khách mới của người bán",
      desc: "Voucher chỉ cho những khách chưa bao giờ mua từ cửa hàng của bạn.",
      route:"newcustomer",
      icon:<svg xmlns="http://www.w3.org/2000/svg" className="w-6 h-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M5 13l4 4L19 7l-4-4L5 13zM12 12l9 9" />
            </svg>

    },
    {
      title: "Voucher follower",
      desc: "Gửi voucher độc quyền cho khách hàng đã theo dõi shop.",
      route:"follower",
      icon:<svg xmlns="http://www.w3.org/2000/svg" className="w-6 h-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 12c2.7 0 5-2.3 5-5s-2.3-5-5-5-5 2.3-5 5 2.3 5 5 5zm0 2c-3.3 0-10 1.7-10 5v2h20v-2c0-3.3-6.7-5-10-5z" />
            </svg>

    },
    {
      title: "Voucher cho khách mua tiếp",
      desc: "Voucher cho những khách đã mua nhiều lần từ cửa hàng của bạn.",
      route:"buyback",
      icon:<svg xmlns="http://www.w3.org/2000/svg" className="w-6 h-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M17 20h5v-5m0 0l-5 5m5-5a9 9 0 11-3-7.3" />
        </svg>

    }, 
    {
      title: "Voucher cho khách mua trong live",
      desc: "Voucher cho những khách đã mua trong live từ cửa hàng của bạn.",
      route:"live",
      icon:<svg xmlns="http://www.w3.org/2000/svg" className="w-6 h-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M20 12V5a2 2 0 00-2-2h-7l-7 7v7a2 2 0 002 2h7l7-7z" />
            </svg> 
    }
  ];
 
    return <>
    <p className=" mt-7 mb-2 font-bold">
        Khám phá các công cụ khuyến mãi
      </p>
        <p className="text-gray-600 mb-6">
        Trao tặng voucher để khuyến khích khách tăng giá trị đơn hàng
      </p>

      {/* Grid voucher */}
      <div className="grid sm:grid-cols-2 lg:grid-cols-3 gap-4 mb-10">
        {vouchers.map((item, i) => (
            <div
                key={i}
                className="border rounded-xl p-4 shadow-sm hover:shadow-md transition bg-white"
                >
                <h3 className="font-semibold text-lg mb-2">{item.title}</h3>
                <p className="text-gray-600 text-sm">{item.desc}</p>
                <div className="flex justify-end mt-2">
                    <Link to={"/seller/voucher/add/"+item.route} className={`px-3 cursor-pointer py-1 font-semibold text-sm text-white rounded-sm bg-${greenMainColor}`}>Tạo</Link>
                </div>
            </div> 
        ))}
      </div> 
    </> 
}
export default IntroductVoucher;