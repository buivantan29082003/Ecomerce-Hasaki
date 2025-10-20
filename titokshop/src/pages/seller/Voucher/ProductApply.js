import CustomModal from "../../../components/Base/Modal";
import { greenMain, greenMainColor } from "../../../config/TextColor";
import Pagination from "../../../components/Base/Page";
import { useEffect, useState } from "react";
import api from "../../../config/AxiosConfig";
import { formatVND } from "../../../Service/FormatDate";

const ProductApply = ({
  voucher,
  setVoucher,
  productMap,
  onchangeProductMap,
}) => {
  const [currentPage, setCurrentPage] = useState(1);
  const [filters, setFilters] = useState({
    key: "",
    minPrice: null,
    maxPrice: null,
    status: null,
    categoriesId: null,
    isLive: false,
  });
  const [product, setProduct] = useState({
    totalPage: 0,
    totalElements: 0,
    content: [],
  });
  const getProducts = () => {
    api
      .get(
        `/sale/product/findall?${
          filters.minPrice != null ? `minPrice=${filters.minPrice}` : ""
        }&${
          filters.maxPrice != null ? `maxPrice=${filters.maxPrice}` : ""
        }&key=${filters.key}&isLive=${filters.isLive}&page=${currentPage}`
      )
      .then((v) => {
        setProduct(v.data.data);
      })
      .catch(() => {});
  };

  useEffect(() => {
    getProducts();
  }, [currentPage,filters]);

  useEffect(() => {
    getProducts();
  }, []);
  return (
    <>
      <div className="flex flex-col md:flex-row  ">
        {/* LEFT: Nội dung chính */}
        <div
          className="w-full md:w-2/3 p-6 overflow-y-auto"
          style={{ scrollbarWidth: "none", msOverflowStyle: "none" }}
        >
          <h2 className="font-semibold text-2xl mb-4">Products</h2>
          <p className="text-xs text-gray-400">
            Chỉ có thể áp dụng voucher này cho toàn bộ cửa hàng hoặc một vài sản
            phẩm cụ thể. Do giới hạn hiện tại trong hệ thống, mỗi đơn hàng chỉ
            có thể áp dụng một voucher của người bán.
          </p>
           
          <CustomModal
            trigger={
              <button className="hover:border-[#009DA6] mb-4 border rounded px-3 py-1 focus:outline-none focus:ring-1 focus:ring-[#009DA6]  mt-4">
                Chọn sản phẩm
              </button>
            }
          >
            <div className="p-4 w-full">
              {/* Header */}
              <h2 className="text-lg font-semibold mb-4">Chọn sản phẩm</h2>

              {/* Bộ lọc */}
              <div className="flex gap-3 mb-4">
                {/* Select danh mục */}
                <select className="border rounded px-3 py-2 w-48 focus:outline-none focus:ring-1 focus:ring-teal-500">
                  <option>Tất cả hạng mục</option>
                  <option>Danh mục 1</option>
                  <option>Danh mục 2</option>
                </select>

                {/* Input tìm kiếm */}
                <input
                  type="text"
                  value={filters.key}
                  onChange={(e)=>{
                    setFilters({...filters,"key":e.target.value})
                  }}
                  placeholder="ID sản phẩm (tối đa 500 ID, cách dấu phẩy)"
                  className="flex-1 border rounded px-3 py-2 focus:outline-none focus:ring-1 focus:ring-teal-500"
                />
              </div>

              {/* Bảng sản phẩm */}
              <div className="border rounded-md overflow-hidden">
                <div className="max-h-[300px] overflow-y-auto">
                  <table className="w-full text-left border-collapse">
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
                            <div className="flex flex-col items-center">
                              <div className="w-16 h-16 bg-gray-200 rounded-md mb-3"></div>
                              Danh sách trống
                            </div>
                          </td>
                        </tr>
                      ) : (
                        product.content.map((v, i) => (
                          <tr key={i} className="border-t text-sm">
                            <td className="p-3">
                              <input
                                onClick={() => {
                                  onchangeProductMap(v);
                                }}
                                type="checkbox"
                                checked={productMap.has(v.id)}
                                className={`w-4 h-4 mr-2 accent-${greenMainColor} `}
                              />
                            </td>
                            <td className="p-3">
                              <div className="flex items-center gap-2">
                                <img
                                  src={v.productImage}
                                  alt={v.productName}
                                  className="w-12 h-12 border border-gray-300 rounded-md object-cover"
                                />
                                <span className="truncate max-w-xs">
                                  {v.productName}
                                </span>
                              </div>
                            </td>

                            <td className="p-3">
                              {formatVND(v.minPrice)}{" "}
                              <span className="text-gray-500 fotn-semibold">
                                {" "}
                                đến{" "}
                              </span>{" "}
                              {formatVND(v.maxPrice)}
                            </td>
                            <td className="p-3">{v.quantity} sản phẩm</td>
                          </tr>
                        ))
                      )}
                    </tbody>
                  </table>
                </div>

                {/* Pagination */}
                <div className="w-full p-2 mt-2 flex justify-end">
                  <Pagination
                    currentPage={currentPage}
                    totalPages={product.totalPage}
                    onPageChange={setCurrentPage}
                    // onPageSizeChange={setTotalPage}
                  />
                </div>
              </div>
              {/* Footer */}
              <div className="flex justify-end gap-3 mt-4">
                <button className="px-4 py-2 border rounded hover:bg-gray-100">
                  Hủy
                </button>
                <button className="px-4 py-2 bg-teal-500 text-white rounded hover:bg-teal-600">
                  Xong
                </button>
              </div>
            </div>
          </CustomModal>
          <div className="border rounded-md overflow-hidden">
                <div className="max-h-[300px] overflow-y-auto">
                  <table className="w-full text-left border-collapse">
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
                            <div className="flex flex-col items-center">
                              <div className="w-16 h-16 bg-gray-200 rounded-md mb-3"></div>
                              Danh sách trống
                            </div>
                          </td>
                        </tr>
                      ) : (
                        
                        Array.from(productMap.values()).map((v, i) => (
                          <tr key={i} className="border-t text-sm">
                            <td className="p-3">
                              <input
                                onClick={() => {
                                  onchangeProductMap(v);
                                }}
                                type="checkbox"
                                checked={productMap.has(v.id)}
                                className={`w-4 h-4 mr-2 accent-${greenMainColor} `}
                              />
                            </td>
                            <td className="p-3">
                              <div className="flex items-center gap-2">
                                <img
                                  src={v.productImage}
                                  alt={v.productName}
                                  className="w-12 h-12 border border-gray-300 rounded-md object-cover"
                                />
                                <span className="truncate max-w-xs">
                                  {v.productName}
                                </span>
                              </div>
                            </td>

                            <td className="p-3">
                              {formatVND(v.minPrice)}{" "}
                              <span className="text-gray-500 fotn-semibold">
                                {" "}
                                đến{" "}
                              </span>{" "}
                              {formatVND(v.maxPrice)}
                            </td>
                            <td className="p-3">{v.quantity} sản phẩm</td>
                          </tr>
                        ))
                      )}
                    </tbody>
                  </table>
                </div> 
              </div>
        </div>

        {/* RIGHT: Preview */}
        <div className="w-full md:w-1/3 hidden md:block  overflow-y-auto   p-4"></div>
      </div>
    </>
  );
};
export default ProductApply;
