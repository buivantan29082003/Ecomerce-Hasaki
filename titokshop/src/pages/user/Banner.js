import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation, Pagination, Autoplay } from "swiper/modules";
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";
import banner2 from "../../assets/image/Banner/banner_bien.avif";
import banner3 from "../../assets/image/Banner/banner_do.avif";
import banner4 from "../../assets/image/Banner/banner_hong.avif";
import banner5 from "../../assets/image/Banner/banner_xanh.avif";
import { FaChevronLeft, FaChevronRight } from "react-icons/fa";
const banner1 = "https://media.hcdn.vn/hsk/1732069393web.jpg";
const Banner = () => {
  return (
    <div className="w-full max-w-[1400px] mx-auto  h-[250px] flex mt-3">
      {/* Swiper Banner */}
      <Swiper
        modules={[Navigation, Pagination, Autoplay]}
        spaceBetween={0}
        slidesPerView={1}
        navigation={{
          prevEl: ".swiper-button-prev-custom",
          nextEl: ".swiper-button-next-custom",
        }}
        pagination={{ clickable: true }}
        // autoplay={{ delay: 10000 }}
        loop={false}
        className="w-full md:w-3/4 h-full rounded-lg overflow-hidden"
      >
        {/* Nút custom prev */}
        <div className="swiper-button-prev-custom absolute top-1/2 left-2 z-10 -translate-y-1/2 cursor-pointer text-white bg-black/40 p-2 rounded-full hover:bg-black/60">
          <FaChevronLeft size={20} />
        </div>

        {/* Nút custom next */}
        <div className="swiper-button-next-custom absolute top-1/2 right-2 z-10 -translate-y-1/2 cursor-pointer text-white bg-black/40 p-2 rounded-full hover:bg-black/60">
          <FaChevronRight size={20} />
        </div>

        <SwiperSlide>
          <img
            src={banner1}
            alt="Banner 1"
            className="w-full h-full object-cover"
          />
        </SwiperSlide>
        <SwiperSlide>
          <img
            src={banner2}
            alt="Banner 2"
            className="w-full h-full object-cover"
          />
        </SwiperSlide>
        <SwiperSlide>
          <img
            src={banner3}
            alt="Banner 3"
            className="w-full h-full object-cover"
          />
        </SwiperSlide>
        <SwiperSlide>
          <img
            src={banner4}
            alt="Banner 4"
            className="w-full h-full object-cover"
          />
        </SwiperSlide>
        <SwiperSlide>
          <img
            src={banner5}
            alt="Banner 5"
            className="w-full h-full object-cover"
          />
        </SwiperSlide>
      </Swiper>

      {/* Ảnh bên phải - ẩn trên màn hình nhỏ */}
      <div className="hidden  md:block w-1/4 overflow-hidden">
        <img
          className=" "
          src={
            "https://media.hcdn.vn/hsk/1739420045nowfree-4-846x250-13022025.jpg"
          }
          alt="Banner Right"
        />
        <img
          className=" "
          src={
            "https://media.hcdn.vn/hsk/1653555653banner-check-gia-web-v2-435x128.jpg"
          }
          alt="Banner Right"
        />
        <img
          className=" "
          src={
            "https://media.hcdn.vn/hsk/1739420045nowfree-4-846x250-13022025.jpg"
          }
          alt="Banner Right"
        />
      </div>
    </div>
  );
};

export default Banner;
