import React from "react";

const Footer = () => {
  return (
    <footer className="bg-white border-t mt-10">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-10 py-10 grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-8 text-sm">
        {/* DỊCH VỤ KHÁCH HÀNG */}
        <div>
          <h3 className="font-bold mb-3">DỊCH VỤ KHÁCH HÀNG</h3>
          <ul className="space-y-2 text-gray-700">
            <li>Trung Tâm Trợ Giúp Shopee</li>
            <li>Shopee Blog</li>
            <li>Shopee Mall</li>
            <li>Hướng Dẫn Mua Hàng/Đặt Hàng</li>
            <li>Hướng Dẫn Bán Hàng</li>
            <li>Ví ShopeePay</li>
            <li>Shopee Xu</li>
            <li>Đơn Hàng</li>
            <li>Trả Hàng/Hoàn Tiền</li>
            <li>Liên Hệ Shopee</li>
            <li>Chính Sách Bảo Hành</li>
          </ul>
        </div>

        {/* SHOPEE VIỆT NAM */}
        <div>
          <h3 className="font-bold mb-3">SHOPEE VIỆT NAM</h3>
          <ul className="space-y-2 text-gray-700">
            <li>Về Shopee</li>
            <li>Tuyển Dụng</li>
            <li>Điều Khoản Shopee</li>
            <li>Chính Sách Bảo Mật</li>
            <li>Shopee Mall</li>
            <li>Kênh Người Bán</li>
            <li>Flash Sale</li>
            <li>Tiếp Thị Liên Kết</li>
            <li>Liên Hệ Truyền Thông</li>
          </ul>
        </div>

        {/* THANH TOÁN */}
        <div>
          <h3 className="font-bold mb-3">THANH TOÁN</h3>
          <div className="flex flex-wrap gap-2">
            <img className="h-6" src="https://upload.wikimedia.org/wikipedia/commons/0/04/Visa.svg" alt="Visa" />
            <img className="h-6" src="https://upload.wikimedia.org/wikipedia/commons/0/0e/Mastercard-logo.png" alt="Mastercard" />
            <img className="h-6" src="https://upload.wikimedia.org/wikipedia/commons/b/b7/UnionPay_logo.svg" alt="UnionPay" />
            <img className="h-6" src="https://upload.wikimedia.org/wikipedia/commons/a/a4/American_Express_logo.svg" alt="Amex" />
            {/* Thêm các logo khác tương tự */}
          </div>
        </div>

        {/* ĐƠN VỊ VẬN CHUYỂN */}
        <div>
          <h3 className="font-bold mb-3">ĐƠN VỊ VẬN CHUYỂN</h3>
          <div className="flex flex-wrap gap-3">
            <img className="h-6" src="https://upload.wikimedia.org/wikipedia/commons/2/2d/Viettel_Post_logo.svg" alt="Viettel" />
            <img className="h-6" src="https://upload.wikimedia.org/wikipedia/commons/0/09/Vietnam_Post_logo.svg" alt="VNPost" />
            {/* Thêm logo NinjaVan, J&T, GrabExpress... */}
          </div>
        </div>

        {/* THEO DÕI + APP */}
        <div>
          <h3 className="font-bold mb-3">THEO DÕI SHOPEE</h3>
          <ul className="space-y-2 text-gray-700 mb-4">
            <li>Facebook</li>
            <li>Instagram</li>
            <li>LinkedIn</li>
          </ul>

          <h3 className="font-bold mb-3">TẢI ỨNG DỤNG SHOPEE</h3>
          <div className="flex items-center gap-3">
            <img className="w-20 h-20" src="https://upload.wikimedia.org/wikipedia/commons/8/8a/QR_code_version_1.svg" alt="QR" />
            <div className="flex flex-col gap-2">
              <img className="h-8" src="https://upload.wikimedia.org/wikipedia/commons/5/5f/Available_on_the_App_Store_%28black%29.png" alt="App Store" />
              <img className="h-8" src="https://upload.wikimedia.org/wikipedia/commons/7/78/Google_Play_Store_badge_EN.svg" alt="Google Play" />
              <img className="h-8" src="https://upload.wikimedia.org/wikipedia/commons/6/67/AppGallery.svg" alt="AppGallery" />
            </div>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
