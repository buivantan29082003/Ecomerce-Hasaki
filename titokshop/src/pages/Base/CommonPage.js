import { Outlet } from "react-router-dom"
import Footer from "../user/Footer"
import Header from "../user/Header"

const CommonPage=()=>{
    return <>
        <Header/>
        <div className="w-full bg-gray-100">
            <Outlet/>
        </div>
        <Footer/>
    </>
}

export default CommonPage