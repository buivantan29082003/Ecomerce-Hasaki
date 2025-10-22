import Banner from "./Banner";
import ProductFlashSale from "./FlashSaleList";
import TopSale from "./TopSale";
import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation } from "swiper/modules";
import { useEffect, useState } from "react";
import { getAllCategoriesWithNotChild } from "../../Service/api/category";
import { FaChevronLeft, FaChevronRight } from "react-icons/fa";
import LoadingSkeleton from "../../components/Base/Loading";

export default function UserHome() {
  const [categories, setCategories] = useState(null);
   
  useEffect(() => {
    getAllCategoriesWithNotChild().then((v) => {
      setCategories(v);
    }) 
  }, []);

  return (
    <div>
      <Banner />
      <div className="md:mx-12 px-5 mt-5">
        <div className="p-2 bg-white rounded shadow relative">
          <h2 className="text-lg font-bold text-green-700 mb-3">Danh mục</h2>

          {/* Nút custom prev */}
          <div className="swiper-button-prev-custom absolute top-1/2 left-2 z-10 -translate-y-1/2 cursor-pointer text-white bg-black/40 p-2 rounded-full hover:bg-black/60">
            <FaChevronLeft size={20} />
          </div>

          {/* Nút custom next */}
          <div className="swiper-button-next-custom absolute top-1/2 right-2 z-10 -translate-y-1/2 cursor-pointer text-white bg-black/40 p-2 rounded-full hover:bg-black/60">
            <FaChevronRight size={20} />
          </div>

          <Swiper
            modules={[Navigation]}
            navigation={{
              prevEl: ".swiper-button-prev-custom",
              nextEl: ".swiper-button-next-custom",
            }}
            spaceBetween={16}
            slidesPerView={6}
            breakpoints={{
              320: { slidesPerView: 2 },
              640: { slidesPerView: 3 },
              1024: { slidesPerView: 6 },
            }}
          >
            <LoadingSkeleton type="row" count={7} isShow={categories===null} />
            {categories!=null&& categories.map((cat) => (
              <SwiperSlide key={cat.id}>
                <div className="flex flex-col items-center justify-center border rounded-xl p-1 hover:shadow-md transition cursor-pointer">
                  <img
                    src={cat.image || "https://via.placeholder.com/80"}
                    alt={cat.categoryName}
                    className="w-40 h-40 object-contain mb-2"
                  />
                  <span className="text-sm font-medium text-gray-700">
                    {cat.categoryName}
                  </span>
                </div>
              </SwiperSlide>
            ))}
          </Swiper>
        </div>
        <div className="flex flex-col lg:flex-row justify-between gap-4   mt-5 mb-3  ">
          <img
            src="https://media.hcdn.vn/hsk/1750219191unilever-sub-banner-home-427-x-140-18062025.jpg"
            alt="Banner 1"
            className="w-full lg:w-[32%] rounded-lg object-cover"
          />
          <img
            src="https://media.hcdn.vn/hsk/1753783880l-oreal-cpd-sub-banner-home-427x140-v2.jpg"
            alt="Banner 2"
            className="w-full lg:w-[32%] rounded-lg object-cover"
          />
          <img
            src="https://media.hcdn.vn/hsk/1750652045rohto-sub-banner-home-427-x-140-23062025.jpg"
            alt="Banner 3"
            className="w-full lg:w-[32%] rounded-lg object-cover"
          />
        </div>

        <ProductFlashSale />
        <TopSale />
      </div>
    </div>
  );
}
