import React, { useEffect, useState } from "react";
import { greenMain, greenMainColor } from "../../../../../config/TextColor";
import CustomModal from "../../../../../components/Base/Modal";
import { formatVND } from "../../../../../Service/FormatDate";
import Pagination from "../../../../../components/Base/Page";
import api from "../../../../../config/AxiosConfig";
import IconSpinner from "../../../../../components/Base/IconSpinner";

const ProductApply = ({
  voucher,
  setVoucher,
  productMap,
  onchangeProductMap,
  linkPlus,
  deleteProduct,
}) => {
  const [currentPage, setCurrentPage] = useState(1);
  const [filters, setFilters] = useState({
    key: "",
    minPrice: null,
    maxPrice: null,
    status: null,
    categoriesId: null,
  });
  const [loading,setLoading]=useState(false)
  const [reload, setReload] = useState(true);
  const [product, setProduct] = useState({
    totalPage: 0,
    totalElements: 0,
    content: [],
  });

  const getProducts = () => {
    let startDate = voucher.startDate.toISOString();
    let endDate = voucher.startDate.toISOString();
    setLoading(true)
    api
      .get(
        `/sale/product/findallwithvariant?${
          filters.minPrice != null ? `minPrice=${filters.minPrice}` : ""
        }&${
          filters.maxPrice != null ? `maxPrice=${filters.maxPrice}` : ""
        }&key=${
          filters.key
        }&page=${currentPage}&startDate=${startDate.substring(
          0,
          startDate.length - 1
        )}&endDate=${endDate.substring(0, endDate.length - 1)}`
      )
      .then((v) => {
        setProduct(v.data.data);
      })
      .catch(() => {}).finally(()=>{
        setLoading(false)
      })
  };

  useEffect(() => {
    getProducts();
  }, [currentPage, filters]);

  useEffect(() => {
    getProducts();
  }, []);

  return (
    <div className="flex flex-col md:flex-row">
      {/* LEFT */}
      <div className="w-full md:w-4/4 p-6 overflow-y-auto">
        <h2 className="font-semibold text-2xl mb-2">Products</h2>
        <p className="text-sm text-gray-400 font-light mb-3">
          Số lượng sản phẩm tối thiểu
          <span className="font-semibold text-[#009DA6]">
            {" "}
            1 sản phẩm được áp dụng
          </span>
        </p>

        {/* Modal chọn sản phẩm */}
        <CustomModal
          customeFunction={() => { 
            api
              .post(
                "/sale/product/getallvariantbyproduct",
                product.content
                  .filter((v) => v.checked === true)
                  .map((v) => v.id)
              )
              .then((vv) => {
                let a = product.content
                  .filter((v) => v.checked === true)
                  .map((v) => {
                    v.productVariants = vv.data.data["" + v.id];
                    return v;
                  });
                onchangeProductMap(a);
              })
              .catch((error) => {}) 
          }}
          trigger={
            <button className="hover:border-[#009DA6] mb-4 border rounded px-3 py-1 focus:ring-1 focus:ring-[#009DA6] mt-2 text-black">
              Chọn và đóng
            </button>
          }
        >
          <div className="p-4 w-full">
            <h2 className="text-lg font-semibold mb-4">Chọn sản phẩm</h2>
            {/* Bộ lọc */}
            <div className="flex gap-3 mb-4">
              <select className="border rounded px-3 py-2 w-48">
                <option>Tất cả hạng mục</option>
              </select>
              <input
                type="text"
                value={filters.key}
                onChange={(e) =>
                  setFilters({ ...filters, key: e.target.value })
                }
                placeholder="ID sản phẩm..."
                className="flex-1 border rounded px-3 py-2"
              />
            </div>
            {/* Table */}
            <div className="border rounded-md overflow-hidden">
              <div className="max-h-[300px] overflow-y-auto">
                 {loading===true?<div className="flex mt-10 p-2 justify-center">
                  <IconSpinner/>
                </div>:<table className="w-full text-left border-collapse">
                  <thead className="bg-gray-100 sticky top-0 z-10">
                    <tr>
                      <th className="p-3 w-12"></th>
                      <th className="p-3">Tên sản phẩm</th>
                      <th className="p-3">Giá gốc</th>
                      <th className="p-3">Hàng có sẵn</th>
                    </tr>
                  </thead>
                  <tbody>
                    {product.content.length === 0 ? (
                      <tr>
                        <td
                          colSpan={4}
                          className="py-10 text-center text-gray-500"
                        >
                          Danh sách trống
                        </td>
                      </tr>
                    ) : (
                      product.content.map((v, i) => (
                        <tr key={i} className="border-t text-sm">
                          <td className="p-3">
                            <input
                              //
                              onClick={() => {
                                v.checked = v.checked === true ? null : true;
                                setReload(!reload);
                              }}
                              disabled={productMap.has(v.id)}
                              type="checkbox"
                              checked={v.checked === true}
                              className={`w-4 h-4 accent-${greenMainColor}`}
                            />
                          </td>
                          <td className="p-3">
                            <div className="flex items-center gap-2">
                              <img
                                src={v.productImage}
                                alt={v.productName}
                                className="w-12 h-12 border rounded-md object-cover"
                              />
                              {/* Cho phép xuống dòng */}
                              <span className="max-w-xs break-words whitespace-normal">
                                {v.productName}
                              </span>
                            </div>
                          </td>
                          <td className="p-3">
                            {formatVND(v.minPrice)} - {formatVND(v.maxPrice)}
                          </td>
                          <td className="p-3">{v.quantity} sản phẩm</td>
                        </tr>
                      ))
                    )}
                  </tbody>
                </table>
                }
              </div>
              <div className="w-full p-2 mt-2 flex justify-end">
                <Pagination
                  currentPage={currentPage}
                  totalPages={product.totalPage}
                  onPageChange={setCurrentPage}
                />
              </div>
            </div>
          </div>
        </CustomModal>

        {/* Product Table */}
        <div className="w-full border rounded-lg shadow-sm bg-white text-sm overflow-x-auto">
          <div className="grid grid-cols-[2fr_1fr_2fr_1fr_1fr_1fr_1fr_auto] bg-gray-100 font-medium p-3 border-b">
            <div className="flex items-center gap-2">
              <input type="checkbox" />
              <span>Product Name</span>
            </div>
            <span>Original Price</span>
            <span>Deal Price</span>
            <span>Stock</span>
            <span>Total Purchase Limit</span>
            <span>Buyer Purchase</span>
            <span> </span>
          </div>

          {Array.from(productMap.values()).map((p, i) => (
            <ProductRow deleteProduct={deleteProduct} key={i} product={p} />
          ))}
        </div>
      </div>
    </div>
  );
};

export default ProductApply;

const ProductRow = ({ product, deleteProduct }) => {
  return (
    <>
      {/* Parent */}
      <div className="grid grid-cols-[2fr_1fr_2fr_1fr_1fr_1fr_1fr_auto] items-center p-3 border-b bg-gray-50">
        <div className="flex items-start gap-2">
          <input type="checkbox" />
          <img
            src={product.productImage}
            alt={product.productName}
            className="w-12 h-12 rounded object-cover"
          />
          <div>
            {/* Cho phép xuống dòng */}
            <p className="font-medium max-w-[200px] break-words whitespace-normal">
              {product.productName}
            </p>
            <p className="text-xs text-gray-500">ID:{product.id}</p>
          </div>
        </div>
        <p> </p>
        <div className="flex items-center gap-2"></div>
        <p>{product.quantity}</p>
        <p></p>
        <p></p>
        <button
          onClick={() => {
            deleteProduct(product.id);
          }}
          className={`${greenMain} font-bold`}
        >
          Delete
        </button>
      </div>

      {/* Child (variants) */}
      {product.productVariants?.map((v, i) => (
        <ProductRowChild v={v} i={i} />
      ))}
    </>
  );
};

const ProductRowChild = ({ v, i }) => {
  const [reload, setReload] = useState(false);
  return (
    <>
      <div
        key={i}
        className={`grid font-medium text-gray-600 grid-cols-[2fr_1fr_2fr_1fr_1fr_1fr_1fr_auto] items-center p-3 border-b ${
          v.isApply ? "" : "text-gray-300"
        }`}
      >
        <div className="flex items-start gap-2">
          <input style={{ visibility: "hidden" }} type="checkbox" />
          <img
            style={{ visibility: "hidden" }}
            src={""}
            alt={""}
            className="w-12 h-12 rounded object-cover"
          />
          <div>
            <p className="font-medium max-w-[200px] break-words whitespace-normal">
              {v.variantName}
            </p>
            <p className="text-xs text-gray-500">ID:{v.id}</p>
          </div>
        </div>
        <p>{formatVND(v.price)}</p>
        <div className="flex items-center gap-2">
          <input
            className="w-20 border rounded px-2 py-1"
            value={formatVND(v.price * (1 - v.discountValue / 100))}
          />
          <span className="text-gray-400">/ </span>
          <input
            type="number"
            value={v.discountValue}
            onChange={(e) => {
              const val = Number(e.target.value);
              if (val > 0 && val < 101) {
                v.discountValue = val;
                setReload(!reload);
              }
            }}
            className="w-16 border rounded px-2 py-1"
            defaultValue=""
          />
          % off
        </div>
        <p>{v.quantity}</p>
        <select
          onChange={() => {
            v.isLimitUse = v.isLimitUse === 1 ? 0 : 1;
            setReload(!reload);
          }}
          className="border rounded px-2 py-1 text-sm"
        >
          <option>No limit</option>
          <option>limited</option>
        </select>
        <input
          type="number"
          value={v.limitUse}
          onChange={(e) => {
            const val = Number(e.target.value);
            if (val > 0) {
              v.limitUse = val;
              setReload(!reload);
            }
          }}
          className="border ml-1 w-[60px] rounded px-2 py-1 text-sm"
          disabled={v.isLimitUse === 0}
        />
        <button
          type="button"
          onClick={() => {
            v.isApply = !v.isApply;
            setReload(!reload);
          }}
          className={`  relative   inline-flex h-6 w-11  items-center rounded-full transition-colors duration-200 focus:outline-none ${
            v.isApply ? "bg-teal-500" : "bg-gray-300"
          }`}
          style={{ margin: "0 auto" }}
        >
          <span
            className={`inline-block h-4 w-4 transform rounded-full bg-white transition-transform duration-200 ${
              v.isApply ? "translate-x-6" : "translate-x-1"
            }`}
          />
        </button>
      </div>
    </>
  );
};
