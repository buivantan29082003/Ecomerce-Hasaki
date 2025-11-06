import React, { useEffect, useState } from "react";
import { BiMenu, BiPhone, BiStore, BiUser, BiX } from "react-icons/bi";
import { BsShieldCheck } from "react-icons/bs";
 import CategoryMenu from "./PickCategories";
import HasakiLogo from "./HasakiLogo";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { getAllCartNoneGroup } from "../../Service/api/Cart";
import CartPopup from "./PopupCart"; 
export default function Header() {
  const carts = useSelector((state) => state.carts);
  const user = useSelector((state) => state.userInfo); 
  const [keySearch,setKeySearch]=useState("")
  const dispatch=useDispatch()
  useEffect(()=>{
    if(user!==null){
      getAllCartNoneGroup().then(v=>{
        dispatch({type:"LOAD_CART",data:v})
      }) 
    }  
  },[])
  const navigate=useNavigate()

  const [isOpen, setIsOpen] = useState(false); 
  return (
    <header className="w-full sticky top-0 " style={{ zIndex: "1001010100" }}> 
      <div className="bg-purple-300 text-center flex justify-center text-sm   text-black">
        <img
          alt="anh"
          src="https://media.hcdn.vn/hsk/1743413617banner-event-nowfree3-1320x50-3103.jpg"
          className="w-full"
        />
      </div> 
      <div className=" bg-[#306e51] p-3 text-white">
        <div  className="flex cursor-pointer items-center justify-between px-4 md:px-10 py-3">
          {/* Logo */}
          <div  onClick={()=>{navigate("/user/home")}} className="flex items-center space-x-2">
            <HasakiLogo  color={"white"} />
            <span className="hidden md:block text-sm">
              Chất lượng thật - Giá trị thật
            </span>
          </div> 
          <div className="flex items-center bg-gray-100 rounded-full px-3 py-2 w-full max-w-xl">
            <input
              type="text" 
              value={keySearch}
              onChange={e=>{
                setKeySearch(e.target.value)
              }}
              placeholder="Tìm kiếm tên sản phẩm mà bạn mong muốn"
              className="flex-1 bg-transparent outline-none text-sm text-gray-700 placeholder-gray-400"
            />
            <svg onClick={()=>{
              dispatch({type:"SET_KEY",data:keySearch})
              navigate("/common/product/search") 
            }}
              xmlns="http://www.w3.org/2000/svg"
              className="w-5 h-5 text-gray-500 cursor-pointer"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
              strokeWidth="2"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M21 21l-4.35-4.35m0 0A7.5 7.5 0 1010.5 18.5a7.5 7.5 0 006.15-1.85z"
              />
            </svg>
          </div>
 
          <div className="flex items-center space-x-4 ">
            <button onClick={()=>{navigate("/user/dashboard/order")}} className="hidden md:flex items-center space-x-1">
              <BiUser size={20} />{" "}
              <span className="hidden lg:block">Đăng nhập</span>
            </button>
            <button className="hidden md:flex items-center space-x-1">
              <BiStore size={20} />{" "}
              <span className="hidden lg:block">Cửa hàng</span>
            </button>
            <button className="hidden md:flex items-center space-x-1">
              <BsShieldCheck size={20} />{" "}
              <span className="hidden lg:block">Bảo hành</span>
            </button>
            <button className="hidden md:flex items-center space-x-1">
              <BiPhone size={20} />{" "}
              <span className="hidden lg:block">Hỗ trợ</span>
            </button>
            {user!==null&&<CartPopup carts={carts}/>}
 
            <button className="md:hidden" onClick={() => setIsOpen(!isOpen)}>
              {isOpen ? <BiX size={24} /> : <BiMenu size={24} />}
            </button>
          </div>
        </div>
 
      </div>
      <div className="flex items-center px-4 md:px-10 bg-green-100 font-semibold text-[13px]">  
        <div className="relative group">
          <button className="flex items-center space-x-2 px-2 py-1 text-green-700  rounded-t-md">
            <BiMenu />
            <span>DANH MỤC</span>
          </button>
 
          <div className="absolute left-0 top-full hidden group-hover:block bg-white shadow-lg w-64 z-50">
            <CategoryMenu />
          </div>
        </div>
        <div className="relative group">
          <button className="flex items-center space-x-2 px-2 py-1 text-green-700  rounded-t-md">
            <span>THƯƠNG HIỆU</span>
          </button>
        </div>
        <div className="relative group">
          <button className="flex items-center space-x-2 px-2 py-1 text-green-700 rounded-t-md">
            <span>HOT VOUCHER</span>
          </button>
        </div>
        <div className="relative group">
          <button className="flex items-center space-x-2 px-2 py-1 text-green-700 rounded-t-md">
            <span>LIVE</span>
          </button>
        </div>
        <div className="relative group">
          <button className="flex items-center space-x-2 px-2 py-1 text-green-700  rounded-t-md">
            <span>THƯƠNG HIỆU</span>
          </button>
        </div>
        <div className="relative group">
          <button className="flex items-center space-x-2 px-2 py-1 text-green-700 rounded-t-md">
            <span>HOT VOUCHER</span>
          </button>
        </div>
        <div className="relative group">
          <button className="flex items-center space-x-2 px-2 py-1 text-green-700 rounded-t-md">
            <span>LIVE</span>
          </button>
        </div>
      </div>
    </header>
  );
}
