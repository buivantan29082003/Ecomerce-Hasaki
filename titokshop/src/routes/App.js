import { Routes, Route, Navigate } from "react-router-dom";
import { Provider } from "react-redux";
import { Suspense, lazy } from "react";
import store from "../config/redux/User/Store";
import DashBoard from "../pages/user/dashboard";
import UserAddress from "../pages/user/UserAddress";
import AddAddressForm from "../pages/user/AddAddress";
import ManageOrder from "../pages/user/ManageOrder";
import ProductReport from "../pages/seller/ProductReport";
import ManageAccount from "../pages/user/ManageAccount";
import ManageOrderSeller from "../pages/seller/ManageOrder/ManageOrder";
  
// ✅ Lazy load toàn bộ page & layout
const Loading = lazy(() => import("../pages/Base/Loading"));
const CommonPage = lazy(() => import("../pages/Base/CommonPage"));
const RequireAuth = lazy(() => import("../components/RequireAuth"));

// Layouts
const SellerLayout = lazy(() => import("../layouts/SellerLayout"));
const UserLayout = lazy(() => import("../layouts/UserLayout"));

// Seller pages
const SellerProducts = lazy(() => import("../pages/seller/Products"));
const AddProduct = lazy(() => import("../pages/seller/AddProduct"));
const MainVoucher = lazy(() => import("../pages/seller/Voucher/Main"));
const OveralVoucher = lazy(() =>
  import("../pages/seller/Voucher/OveralVoucher")
);
const OveralDiscount = lazy(() =>
  import("../pages/seller/Discount/Manage/OveralDiscount")
);
const MainDiscount = lazy(() =>
  import("../pages/seller/Discount/Form/Add/Main")
);

// User pages
const UserHome = lazy(() => import("../pages/user/Home"));
const Cart = lazy(() => import("../pages/user/Cart"));
const Order = lazy(() => import("../pages/user/Order"));
const ProductDetail = lazy(() => import("../pages/user/ProductDetail"));
const ProductSearch = lazy(() => import("../pages/user/ProductSearch"));

export default function AppMain() {
  return (
    <Provider store={store}>
      <Suspense
        fallback={
          <div className="w-full h-screen flex items-center justify-center bg-gray-50">
            <Loading />
          </div>
        }
      >
        <Routes>
          {/* Seller routes */}
          <Route
            path="/seller"
            element={
              <RequireAuth>
                <SellerLayout />
              </RequireAuth>
            }
          >
            <Route path="orders" element={<ManageOrderSeller/>}/>
            <Route path="products" element={<SellerProducts />} />
            <Route path="product/add" element={<AddProduct />} />
            <Route path="voucher/add/:type" element={<MainVoucher />} />
            <Route path="voucher/overal" element={<OveralVoucher />} />
            <Route path="discount/overal" element={<OveralDiscount />} />
            <Route path="discount/add/:type" element={<MainDiscount />} />
            <Route path="product/report/:productId" element={<ProductReport />} />
          </Route>

          {/* User routes */}
           <Route path="/user" element={<UserLayout />}>
          <Route index element={<UserHome />} />         {/* /user */}
          <Route path="home" element={<UserHome />} />  {/* /user/home */}
          <Route path="cart" element={<Cart />} />      {/* /user/cart */}
          <Route path="order/:cartIds" element={<Order />} /> {/* /user/order/1,2 */}
          <Route path="dashboard" element={<DashBoard />}>    {/* /user/dashboard */}
            <Route index element={<div>Dashboard Home</div>} />    {/* /user/dashboard */}
            <Route path="address" element={<UserAddress />} />  
            <Route path="address/add" element={<AddAddressForm />} /> 
            <Route path="order" element={<ManageOrder />}></Route> 
            <Route path="account" element={<ManageAccount />}></Route> 
          </Route>
        </Route>

          {/* Common routes */}
          <Route path="/common" element={<CommonPage />}>
            <Route path="product/:productId" element={<ProductDetail />} />
            <Route
              path="product/search"
              element={<ProductSearch />}
            />
          </Route>

          {/* Default redirect */}
          <Route path="*" element={<Navigate to="/user/home" />} />
        </Routes>
      </Suspense>
    </Provider>
  );
}
