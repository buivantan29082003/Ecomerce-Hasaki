import React, { useEffect, useState } from "react";
import {
  getAllProvices,
  getDistrictByProvinceId,
  getWardCodeByDistrictId,
} from "../../Service/api/GHNApi";
import { toast } from "react-toastify";
import { validatePhoneNumber } from "../../Service/VaidateReget";
import { addAddressUser } from "../../Service/api/DiaChi";
import handleError from "../../Service/api/HandlError";
import { useNavigate } from "react-router-dom";

const AddAddressForm = () => {
    const navigate=useNavigate()
  const submit = () => {
    if(address.fullName.length<5){
        toast.error("Vui lòng nhập ít nhất 5 ký tự cho tên")
        return 
    }
    if(address.provinceId===null){
        toast.error("Vui lòng chọn tỉnh thành phố")
        return 
    }
    if(address.districtId===null){
        toast.error("Vui lòng chọn quận/ huyện")
        return 
    }
    if(address.wardCode===null){
        toast.error("Vui lòng chọn tỉnh xã/phường")
        return 
    }
    if(validatePhoneNumber(address.phoneNumber)===false){
        toast.error("Vui lòng nhập số điện thoại đúng định dạng")
        return 
    }
    addAddressUser(address).then(v=>{
        toast.success("Thêm thành công địa chỉ")
        navigate("/user/dashboard/address")
    }).catch(error=>{
        toast.error(handleError(error))
    })

  };

  const [data, setData] = useState({
    provinces: [],
    districts: [],
    wardCodes: [],
  });

  useEffect(() => {
    getAllProvices().then((v) => {
      setData({ ...data, provinces: v });
    });
  }, []);

  const getDistrictByProvinceIds = (provinceId) => {
    getDistrictByProvinceId(provinceId).then((v) => {
      setData({ ...data, districts: v });
    });
  };

  const getWardCodeByDistrictIds = (districtId) => {
    getWardCodeByDistrictId(districtId).then((v) => {
      setData({ ...data, wardCodes: v });
    });
  };

  const [address, setAddress] = useState({
    fullName: "",
    phoneNumber: "",
    districtId: null,
    provinceId: null,
    wardCode: null,
    fullAddress: "",
    addressNote: " ",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setAddress({ ...address, [name]: value });
  };

  return (
    <div className="w-full bg-white   shadow-sm   text-sm">
      <h2 className="text-xl font-semibold mb-6">Thêm địa chỉ mới</h2>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {/* Cột trái */}
        <div>
          <label className="block font-semibold mb-1">Tên:</label>
          <input
            type="text"
            name="fullName"
            value={address.fullName}
            onChange={handleChange}
            className="border w-full p-2 rounded-sm mb-4 focus:outline-none focus:ring-1 focus:ring-green-600"
            placeholder="Nhập họ và tên"
          />

          <label className="block font-semibold mb-1">Số điện thoại</label>
          <input
            type="text"
            name="phoneNumber"
            value={address.phoneNumber}
            onChange={handleChange}
            className="border w-full p-2 rounded-sm mb-3 focus:outline-none focus:ring-1 focus:ring-green-600"
            placeholder="Nhập số điện thoại"
          />

          <p className="text-sm text-gray-600 mt-1">
            Hệ thống sẽ liên hệ số điện thoại này để giao hàng đến bạn và kiểm
            tra tình trạng đơn hàng, đổi trả hàng khi cần.
          </p>
        </div>

        {/* Cột phải */}
        <div>
          <label className="block font-semibold mb-1">Tỉnh/ Thành phố</label>
          <select
            onChange={(e) => {
              let province = data.provinces[Number(e.target.value)]
              data.districts = [];
              data.wardCodes = [];
              address.provinceId =Number( province.ProvinceID);
              address.districtId=null
              address.wardCode=null
              address.fullAddress = province.ProvinceName + ", ";
              getDistrictByProvinceIds(province.ProvinceID);
            }}
            name="province"
            className="border w-full p-2 rounded-sm mb-4 focus:outline-none focus:ring-1 focus:ring-green-600"
          >
            <option value="">-- Chọn tỉnh/thành --</option>
            {data.provinces.map((v, index) => (
              <option key={index} value={index}>
                {" "}
                {v.ProvinceName}{" "}
              </option>
            ))}
          </select>

          <label className="block font-semibold mb-1">Quận/ Huyện</label>
          <select
            name="district"
            onChange={(e) => {
                let district = data.districts[Number(e.target.value)];
              data.wardCodes = [];
              address.districtId = Number(district.DistrictID);
              address.wardCode=null
              
              address.fullAddress += district.DistrictName + ", ";
              getWardCodeByDistrictIds(district.DistrictID);
            }}
            className="border w-full p-2 rounded-sm mb-4 focus:outline-none focus:ring-1 focus:ring-green-600"
          >
            <option value="">-- Chọn quận/huyện --</option>

            {data.districts.map((v, index) => (
              <option key={v.DistrictID} value={index}>
                {v.DistrictName}
              </option>
            ))}
          </select>

          <label className="block font-semibold mb-1">Phường/ Xã</label>
          <select
            name="wardCode"
            onChange={(e) => {
            let wardCode=data.wardCodes[Number(e.target.value)]
              address.wardCode = Number(wardCode.WardCode);
              setAddress({
                ...address,
                fullAddress:
                  address.fullAddress +
                  wardCode.WardName +
                  "",
              });
            }}
            className="border w-full p-2 rounded-sm mb-4 focus:outline-none focus:ring-1 focus:ring-green-600"
          >
            <option value="">-- Chọn Phường xã --</option>

            {data.wardCodes.map((v, index) => (
              <option key={v.WardCode} value={index}>
                {v.WardName}
              </option>
            ))}
          </select>

          <label className="block font-semibold mb-1">Toàn bộ địa chỉ</label>
          <input
            type="text"
            disabled={true}
            value={address.fullAddress}
            className="border w-full p-2 rounded-sm focus:outline-none focus:ring-1 focus:ring-green-600"
          />

          <label className="block font-semibold mb-1">Ghi chú địa chỉ</label>
          <input
            type="text"
            name="addressNote"
            value={address.addressNote}
            onChange={handleChange}
            placeholder="Nhập địa chỉ cụ thể"
            className="border w-full p-2 rounded-sm focus:outline-none focus:ring-1 focus:ring-green-600"
          />
        </div>
      </div>

      {/* Nút hành động */}
      <div className="flex justify-end mt-6 gap-3">
        <button
          type="button"
          className="border px-4 py-2 rounded-sm text-gray-600 hover:bg-gray-100"
          //   onClick={() => setForm({ fullName: "", phone: "", province: "", district: "", ward: "", address: "" })}
        >
          Thoát
        </button>
        <button
          type="submit"
            onClick={submit}
          className="bg-green-700 text-white px-5 py-2 rounded-sm hover:bg-green-800"
        >
          Thêm địa chỉ
        </button>
      </div>
    </div>
  );
};

export default AddAddressForm;
