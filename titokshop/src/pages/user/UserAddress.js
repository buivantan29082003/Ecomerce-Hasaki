import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getAllAddress } from "../../Service/api/DiaChi";

const UserAddress = () => {
  const [addresses, setAddresses] = useState([]);
  useEffect(() => {
    getAllAddress().then((v) => {
      setAddresses(v);
    });
  }, []);
  return (
    <>
      <div className="flex items-center justify-between">
        <span className="font-bold text-xl text-gray-700">Sổ địa chỉ</span>
        <Link
          to={"/user/dashboard/address/add"}
          className="inline-block px-3 py-1 bg-[#306e51] text-white rounded-full"
        >
          + Thêm địa chỉ mới
        </Link>
        
      </div>
      <div className="w-full mt-6 mx-auto bg-white  rounded-xl  ">
 
          <div className=" ">
            {addresses.map((addr) => (
              <div key={addr.id} className="border-b pb-4 mt-2 text-sm last:border-b ">
                <div className="flex justify-between items-start">
                  <div>
                    <p className="font-semibold text-gray-900 text-md">
                      {addr.fullName}
                      <span className="mx-2 text-gray-500 font-normal">|</span>
                      <span className="text-gray-700">{addr.phoneNumber}</span>
                    </p>

                    <p className="text-gray-700 mt-1">{addr.addressNote}</p>
                    <p className="text-gray-700">{addr.fullAddress}</p>

                    <div className="mt-2 flex items-center space-x-2">
                      {addr.isDefault && (
                        <span className="border border-red-500 text-red-500 text-xs px-2 py-[1px] rounded">
                          Mặc định
                        </span>
                      )}
                      {addr.isPickup && (
                        <span className="border border-gray-400 text-gray-600 text-xs px-2 py-[1px] rounded">
                          Địa chỉ lấy hàng
                        </span>
                      )}
                    </div>
                  </div>

                  <div className="flex flex-col items-end gap-2"></div>
                </div>
              </div>
            ))}
          </div>
        </div>
    </>
  );
};

export default UserAddress;
