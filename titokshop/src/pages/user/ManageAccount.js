// ProfileForm.jsx
import React, { useEffect, useState } from "react";
import getInfoAccount from "../../Service/api/Account";

export default function ProfileForm() {
  // Dữ liệu lấy từ object thật
  const apiData = {
    id: 5,
    email: "buivantan43211235@gmail.com",
    fullName: "Lê Thu Huyền",
    phoneNumber: "0243536353",
    gender: 1,
    dateOfBirth: "2025-09-25T10:01:34.000+00:00",
    status: 1,
    role: 1,
  };

  useEffect(()=>{
    getInfoAccount().then(v=>{
        setFormData(v)
    }).catch(error=>{

    })
  })

  const formatDate = (isoString) => {
    const date = new Date(isoString);
    const day = String(date.getDate()).padStart(2, "0");
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const year = date.getFullYear();
    return `${day}/${month}/${year}`;
  };

  const [formData, setFormData] = useState({
    username: `user${apiData.id}`, // hoặc để trống nếu bạn không có username
    name: apiData.fullName,
    email: apiData.email,
    phone: apiData.phoneNumber,
    gender: apiData.gender === 1 ? "Nam" : "Nữ",
    birthday: formatDate(apiData.dateOfBirth),
  });

  return (
    <div className="max-w-6xl mx-auto bg-white p-8">
      <h1 className="text-2xl font-semibold mb-1">Hồ Sơ Của Tôi</h1>
      <p className="text-gray-500 mb-6">
        Quản lý thông tin hồ sơ để bảo mật tài khoản
      </p>

      <div className="flex gap-8">
        {/* LEFT: form */}
        <div className="flex-1">
          <div className="space-y-6">
            {/* Row: Tên đăng nhập */}
            <div className="flex items-start">
              <div className="w-40 flex-shrink-0 pr-4">
                <label className="block text-right">Tên đăng nhập</label>
              </div>
              <div className="flex-1">
                <input
                  type="text"
                  className="w-full border rounded-lg px-3 py-2 bg-gray-100 text-gray-700"
                  value={formData.username}
                  readOnly
                />
                <p className="text-sm text-gray-400 mt-1">
                  Tên Đăng nhập chỉ có thể thay đổi một lần.
                </p>
              </div>
            </div>

            {/* Row: Tên */}
            <div className="flex items-center">
              <div className="w-40 flex-shrink-0 pr-4">
                <label className="block text-right">Tên</label>
              </div>
              <div className="flex-1">
                <input
                  type="text"
                  className="w-full border rounded-lg px-3 py-2"
                  value={formData.name}
                  onChange={(e) =>
                    setFormData({ ...formData, name: e.target.value })
                  }
                />
              </div>
            </div>

            {/* Row: Email */}
            <div className="flex items-center">
              <div className="w-40 flex-shrink-0 pr-4">
                <label className="block text-right">Email</label>
              </div>
              <div className="flex-1 flex items-center gap-3">
                <input
                  type="email"
                  className="flex-1 border rounded-lg px-3 py-2 bg-gray-100 text-gray-700"
                  value={formData.email}
                  readOnly
                />
              </div>
            </div>

            {/* Row: Số điện thoại */}
            <div className="flex items-center">
              <div className="w-40 flex-shrink-0 pr-4">
                <label className="block text-right">Số điện thoại</label>
              </div>
              <div className="flex-1 flex items-center gap-3">
                <input
                  type="text"
                  className="flex-1 border rounded-lg px-3 py-2 bg-gray-100 text-gray-700"
                  value={formData.phone}
                  readOnly
                />
              </div>
            </div>

            {/* Row: Giới tính */}
            <div className="flex items-center">
              <div className="w-40 flex-shrink-0 pr-4">
                <label className="block text-right">Giới tính</label>
              </div>
              <div className="flex-1">
                <div className="inline-block px-3 py-2 border rounded-lg">
                  {formData.gender}
                </div>
              </div>
            </div>

            {/* Row: Ngày sinh */}
            <div className="flex items-center">
              <div className="w-40 flex-shrink-0 pr-4">
                <label className="block text-right">Ngày sinh</label>
              </div>
              <div className="flex-1">
                <div className="inline-block px-3 py-2 border rounded-lg">
                  {formData.birthday}
                </div>
              </div>
            </div>

            {/* Lưu button aligned with inputs */}
            <div className="flex items-center">
              <div className="w-40 flex-shrink-0 pr-4" />
              <div className="flex-1">
                <button className="bg-[#ee4d2d] text-white px-6 py-2 rounded-md hover:bg-[#d94424]">
                  Lưu
                </button>
              </div>
            </div>
          </div>
        </div>

        {/* RIGHT: avatar */}
        <div className="w-64 flex-shrink-0 border-l pl-8">
          <div className="flex flex-col items-center">
            <img
              src="https://cdn.tgdd.vn/2020/06/GameApp/Genshin-Impact-ava-600x600-1.jpg"
              alt="avatar"
              className="w-40 h-40 rounded-full object-cover border"
            />
            <button className="mt-4 px-4 py-2 border rounded-md hover:bg-gray-50">
              Chọn Ảnh
            </button>
            <p className="text-sm text-gray-500 mt-3 text-center">
              Dung lượng file tối đa 1 MB
              <br />
              Định dạng:.JPEG, .PNG
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
