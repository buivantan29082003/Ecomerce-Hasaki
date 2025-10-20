// src/config/redux/User/Reducers.js

// ✅ Không cần import react-router-dom ở đây
const initialStateCart = []; // giỏ hàng mặc định rỗng
const initialStateUserInfo = {}; // user mặc định chưa đăng nhập

// 🛒 Reducer cho giỏ hàng
function carts(state = initialStateCart, action) {
  switch (action.type) {
    case "LOAD_CART":
      return action.data; // ví dụ: mảng sản phẩm
    default:
      return state; // luôn phải return state mặc định
  }
}

// 👤 Reducer cho thông tin user
function userInfo(state = initialStateUserInfo, action) {
  switch (action.type) {
    case "LOGIN":
      return action.data; // ví dụ: { id: 1, name: "KaKa" }
    case "LOGOUT":
      return null;
    default:
      return state;
  }
}

module.exports = {
  userInfo,
  carts,
};
