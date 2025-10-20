import { Outlet } from "react-router-dom";
import Footer from "../pages/user/Footer";
import Header from "../pages/user/Header";

export default function UserLayout() {
  return (
    <div className="[word-spacing:0.1rem]"> 
      <Header/>
      <Outlet />
      <Footer/>
    </div>
  );
}