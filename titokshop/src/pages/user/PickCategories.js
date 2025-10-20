import React, { useEffect, useState } from "react";
import { getAllCategories } from "../../Service/api/category";
import { useNavigate } from "react-router-dom";

export default function CategoryMenu() {
  const url = "/common/product/search/";
  const navigate=useNavigate()
  const [active, setActive] = useState(null);
  const [categories, setCategories] = useState([]);
  useEffect(() => {
    getAllCategories().then((v) => {
      setCategories(v);
    });
  }, []); 
  return (
    <div className="flex relative bg-white border shadow-lg">
      {/* Cột trái: danh mục cha */}
      <div className="w-60 border-r">
        {categories.map((cat) => (
          <div
          onClick={()=>{
            navigate(url+cat.id)
          }}
            key={cat.id}
            className={`p-2 cursor-pointer hover:bg-orange-100 ${
              active?.id === cat.id ? "bg-orange-200 font-bold" : ""
            }`}
            onMouseEnter={() => setActive(cat)}
          >
            {cat.categoryName}
          </div>
        ))}
      </div>

      {/* Cột phải: submenu + banner */}
      {active && (
        <div
          className="absolute  border border-orange-700 left-60 top-0 bg-white shadow-lg p-4 w-[600px] min-h-full flex"
          onMouseLeave={() => setActive(null)}
        >
          {/* Submenu */}
          <div className="flex-1 ">
            <h3 className="text-lg font-bold mb-2">{active.categoryName}</h3>
            <div className="grid grid-cols-2 gap-3">
              {active.children?.map((child) => (
                <div
                  key={child.id}
                  onClick={()=>navigate(url+child.id)}
                  className="text-gray-700 hover:text-orange-600 cursor-pointer"
                >
                  {child.categoryName}
                </div>
              ))}
            </div>
          </div>

          {/* Banner bên phải */}
          <div className="w-48 pl-4">
            <img
              src={active.image}
              alt="banner"
              className="w-full h-auto rounded"
            />
          </div>
        </div>
      )}
    </div>
  );
}
