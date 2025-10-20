import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getRouteCateGory } from "../../Service/api/category";
import { FaChevronRight } from "react-icons/fa";
import {
  getBrandRecommendByCateId,
  getPropertyRecommendByCateId,
} from "../../Service/api/commonService";
import ProductCard from "../../components/user/ProductCartLine";
import { productSearch } from "../../Service/api/Product";
import LoadingSkeleton from "../../components/Base/Loading";
const ProductSearch = () => {
  const param = useParams();
  const [isLoading, setIsLoading] = useState(true);
  const [filter, setFilter] = useState({
    minPrice: null,
    maxPrice: null,
    typeSort: 0,
    propertyValueIds: new Map(),
    brandIds: new Map(),
    currentCategory: null,
    currentPage: 0,
  });
  const [cate, setCate] = useState(null);
  const [data, setData] = useState({
    totalElements: 0,
    totalPage: 0,
    content: [],
    cateList: null,
    brands: null,
    properties: null,
  });

  useEffect(() => {
    let c = param.categoryId;
    if (c !== undefined) {
      setCate(c);
    }
  }, []);

  useEffect(() => {
    setIsLoading(true);
    productSearch(filter)
      .then((v) => {
        setData((prev) => ({
          ...prev,
          totalElements: v.totalElements,
          totalPage: v.totalPage,
          content: v.content,
        }));
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, [filter]);

  useEffect(() => {
    if (cate != null) {
      Promise.all([
        getBrandRecommendByCateId(cate),
        getPropertyRecommendByCateId(cate),
      ]).then(([brands, properties]) => {
        setData((prev) => ({
          ...prev,
          brands,
          properties,
        }));
      });
    }
  }, [cate]);
  return (
    <div className="w-full md:px-12  min-h-screen bg-gray-50">
      <CateRoute cate={cate} />
      {cate != null ? (
        data.brands !== null ? (
          <>
            <div className="flex-1 w-full  bg-white flex py-2 gap-6 items-start">
              {data.brands.map((v) => {
                return <img alt="" src={v.image} />;
              })}
            </div>
          </>
        ) : (
          <LoadingSkeleton
            height={10}
            type="row"
            count={7}
            isShow={data.brands === null}
          />
        )
      ) : (
        <></>
      )}

      {/* Main content */}
      <div className="flex flex-col md:flex-row   py-6  ">
        {/* Sidebar */}
        <aside className="w-full md:w-1/4  border-right lg:w-1/6 bg-white rounded-sm shadow p-4">
          <Filter filter={filter} setFilter={setFilter} data={data} />
        </aside>

        {/* Products section */}
        <main className="flex-1 bg-white px-2 py-2">
          <h2 className="text-md text-gray-800 font-bold mt-5 mb-3">
            TÌM THẤY <span className="text-red-600">{data.totalElements}</span>{" "}
            SẢN PHẨM TRÙNG KHỚP
          </h2>
          {/* Tabs filter */}
          <TabSort filter={filter} setFilter={setFilter} />

          {/* Grid sản phẩm */}

          {isLoading ? (
            <LoadingSkeleton isShow={isLoading} count={10} type="grid" />
          ) : (
            <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-3 lg:grid-cols-4 md:gap-4">
              {data.content.map((product, i) => (
                <ProductCard
                  patternLink={"/common/product/"}
                  key={i}
                  product={product}
                />
              ))}
            </div>
          )}
        </main>
      </div>
    </div>
  );
};

export default ProductSearch;

const Filter = ({ data, setFilter, filter }) => {
  const [prices, setPrices] = useState({
    minPrice: null,
    maxPrice: null,
  });
  return (
    <div>
      <h2 className="text-md text-gray-800 font-bold mt-4 mb-3">KHOẢNG GIÁ</h2>

      <div className="flex items-center gap-2 mb-3">
        <input
          type="number"
          placeholder="đ TỪ"
          value={prices.minPrice}
          onChange={(e) => setPrices({ ...prices, minPrice: e.target.value })}
          className="w-full border rounded-md px-2 py-1 text-sm focus:outline-none focus:ring-1 focus:ring-green-600"
        />
        <span className="text-gray-500">—</span>
        <input
          type="number"
          placeholder="đ ĐẾN"
          value={prices.maxPrice}
          onChange={(e) => setPrices({ ...prices, maxPrice: e.target.value })}
          className="w-full border rounded-md px-2 py-1 text-sm focus:outline-none focus:ring-1 focus:ring-green-600"
        />
      </div>

      <button
        onClick={() => {
          setFilter({
            ...filter,
            minPrice: Math.min(prices.minPrice, prices.maxPrice),
            maxPrice: Math.max(prices.minPrice, prices.maxPrice),
          });
        }}
        className="w-full bg-green-900 text-white font-semibold py-2 rounded-md hover:bg-green-800 transition"
      >
        Áp dụng
      </button>
      <h2 className="text-md text-gray-800 font-bold mt-4 mb-3">THƯƠNG HIỆU</h2>
      <ul className="max-h-64 overflow-y-auto space-y-2 text-gray-700 pr-2">
        {data.brands &&
          data.brands.map((brand, idx) => (
            <li key={idx}>
              <label
                htmlFor={`brand-${idx}`}
                className="flex items-center gap-2 cursor-pointer"
              >
                <input
                  type="checkbox"
                  checked={filter.brandIds.has(brand.id)}
                  onChange={() => {
                    const newBrandIds = new Map(filter.brandIds); // clone Map cũ
                    if (newBrandIds.has(brand.id)) {
                      newBrandIds.delete(brand.id);
                    } else {
                      newBrandIds.set(brand.id, "h");
                    }
                    console.log(newBrandIds);
                    setFilter({ ...filter, brandIds: newBrandIds });
                  }}
                  id={`brand-${idx}`}
                  className="peer hidden"
                />

                {/* Custom checkbox */}
                <div className="w-5 h-5 border border-gray-400 rounded-sm flex items-center justify-center peer-checked:bg-green-700 peer-checked:border-green-700">
                  {/* Dấu tick */}
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="white"
                    strokeWidth="3"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    className="w-4 h-4 opacity-0 peer-checked:opacity-100 transition-opacity"
                  >
                    <path d="M5 13l4 4L19 7" />
                  </svg>
                </div>

                {/* Text */}
                <span className="hover:text-green-600 text-[13px]">
                  {brand.brandName}
                </span>
              </label>
            </li>
          ))}
      </ul>
      {data.properties &&
        data.properties.map((v) => {
          return (
            <>
              <h2 className="text-md text-gray-800 mb-3 mt-4 font-bold uppercase">
                {v.propertyName}
              </h2>
              <ul className="max-h-64 overflow-y-auto space-y-2 text-gray-700 pr-2">
                {v.propertyValues.map((property, idx) => (
                  <li key={idx}>
                    <label className="flex items-center gap-2 cursor-pointer">
                      <input
                        checked={
                          filter.propertyValueIds.get(property.id) != null
                        }
                        onClick={() => {
                          if (
                            filter.propertyValueIds.get(property.id) != null
                          ) {
                            filter.propertyValueIds.delete(property.id);
                          } else {
                            filter.propertyValueIds.set(property.id, "h");
                          }
                          setFilter({
                            ...filter,
                            propertyValueIds: filter.propertyValueIds,
                          });
                        }}
                        type="checkbox"
                        className="peer hidden"
                      />

                      {/* Custom checkbox */}
                      <div className="w-5 h-5 border border-gray-400 rounded-sm flex items-center justify-center peer-checked:bg-green-700 peer-checked:border-green-700">
                        {/* Dấu tick */}
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          viewBox="0 0 24 24"
                          fill="none"
                          stroke="white"
                          strokeWidth="3"
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          className="w-4 h-4 opacity-0 peer-checked:opacity-100 transition-opacity"
                        >
                          <path d="M5 13l4 4L19 7" />
                        </svg>
                      </div>

                      {/* Text */}
                      <span className="hover:text-green-600 text-[13px]">
                        {property.propertyValue}
                      </span>
                    </label>
                  </li>
                ))}
              </ul>
            </>
          );
        })}
    </div>
  );
};

const TabSort = ({ filter, setFilter }) => {
  const changeTypeSort = (type) => {
    setFilter({ ...filter, typeSort: type });
  };

  return (
    <>
      <div className="flex md:gap-6 gap-4 mt-3   pb-2 mb-4 text-md ">
        <span
          onClick={changeTypeSort.bind(null, 0)}
          className={`cursor-pointer ${
            0 === filter.typeSort
              ? "text-orange-500 border-b-2 border-orange-500"
              : ""
          }`}
        >
          Mới nhất
        </span>
        <span
          onClick={changeTypeSort.bind(null, 1)}
          className={`cursor-pointer ${
            1 === filter.typeSort
              ? "text-orange-500 border-b-2 border-orange-500"
              : ""
          }`}
        >
          Đánh giá cao
        </span>
        <span
          onClick={changeTypeSort.bind(null, 2)}
          className={`cursor-pointer ${
            2 === filter.typeSort
              ? "text-orange-500 border-b-2 border-orange-500"
              : ""
          }`}
        >
          Bán chạy nhất
        </span>
        <span
          onClick={changeTypeSort.bind(null, 3)}
          className={`cursor-pointer ${
            3 === filter.typeSort
              ? "text-orange-500 border-b-2 border-orange-500"
              : ""
          }`}
        >
          Giá thấp đến cao
        </span>
        <span
          onClick={changeTypeSort.bind(null, 4)}
          className={`cursor-pointer ${
            4 === filter.typeSort
              ? "text-orange-500 border-b-2 border-orange-500"
              : ""
          }`}
        >
          Giá cao đến thấp
        </span>
      </div>
    </>
  );
};

const CateRoute = ({ cate }) => {
  const [route, setRoute] = useState(undefined);

  useEffect(() => {
    if (cate != null) {
      getRouteCateGory(cate).then((v) => {
        setRoute(v);
      });
    }
  }, [cate]);
  return (
    <>
      <nav className="flex py-3 items-center text-sm text-gray-400 space-x-2">
        <a href="/" className="hover:text-gray-600">
          Trang chủ
        </a>
        <FaChevronRight className="text-xs" />
        {route != undefined &&
          route.map((v) => {
            return (
              <>
                <a href="/category" className="hover:text-gray-600">
                  {v.categoryName}
                </a>
                <FaChevronRight className="text-xs" />
              </>
            );
          })}
      </nav>
    </>
  );
};
