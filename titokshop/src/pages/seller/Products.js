import { useEffect, useState } from "react";
import CustomModal from "../../components/Base/Modal";
import CategorySelector from "../../components/Base/CategorySelector";
import api from "../../config/AxiosConfig";
import product, { changeStatusProduct } from "../../Service/api/Seller/product";
import { toast } from "react-toastify";
import LoadingSkeleton from "../../components/Base/Loading";
import Pagination from "../../components/Base/Page";
 
export default function SellerProducts() {
  const [activeTab, setActiveTab] = useState(null);
  const tabs = [
    { name: "Tất cả 52", value: null },
    { name: "Đang hoạt động 12", value: 1 },
    { name: "Đã ẩn 22", value: 0 },
    { name: "Đã xóa 12", value:2 },
  ];
  const [sampleProducts, setSampleProducts] = useState({
    content: [],
  });
  const [filters, setFilters] = useState({
    key: "",
    minPrice: null,
    maxPrice: null,
    status: null,
    categoriesId: null,
    isLive: false,
    currentPage:0
  });
  const [isLoading,setIsLoading]=useState(false)

  function getAllIds(node) {
    let ids = [node.id]; // lấy id hiện tại
    if (node.children && node.children.length > 0) {
      node.children.forEach((child) => {
        ids = ids.concat(getAllIds(child)); // nối id con
      });
    }
    return ids;
  }

  const [cateString, setCatString] = useState("");

  const getProducts = () => {
    setIsLoading(true)
    api
      .get(
        `/sale/product/findall?${
          filters.status != null ? `status=${filters.status}` : ""
        }&${
          filters.categoriesId != null
            ? `categories=${filters.categoriesId}`
            : ""
        }&${filters.minPrice != null ? `minPrice=${filters.minPrice}` : ""}&${
          filters.maxPrice != null ? `maxPrice=${filters.maxPrice}` : ""
        }&key=${filters.key}&isLive=${filters.isLive}&page=${filters.currentPage}`
      )
      .then((v) => {
        setSampleProducts(v.data.data);
      })
      .catch(() => {}).finally(()=>{
        setIsLoading(false)
      })
  };

  useEffect(() => {
    getProducts();
  }, [filters]);

  return (
    <div className="bg-gray-100 min-h-screen p-6 text-sm text-gray-800">
      <div className="flex items-center justify-between mb-4">
        <h1 className="text-2xl font-semibold cursor-pointer">
          Quản lý sản phẩm
        </h1>
        <button className="bg-[#009DA6] font-bold text-white px-4 py-2 rounded hover:bg-[#007b85] transition">
          Thêm sản phẩm mới
        </button>
      </div>

      {/* Tabs */}
      <div className="flex gap-6 border-b mb-4 text-sm font-medium">
        {tabs.map((tab, idx) => (
          <div
            key={idx}
            onClick={() => {
              filters.status = tab.value;
              setActiveTab(tab.value);
              getProducts();
            }}
            className={`cursor-pointer pb-2 ${
              activeTab === tab.value
                ? "text-[#009DA6] font-semibold border-b-2 border-[#009DA6]"
                : "text-gray-600 hover:text-black"
            }`}
          >
            {tab.name}
          </div>
        ))}
      </div>

      {/* Bộ lọc */}
      <div className="bg-white p-4 rounded border mb-6 shadow-sm">
        <div className="flex flex-wrap gap-3 mb-3">
          <input
            value={filters.key}
            onChange={(v) => {
              filters.key = v.target.value;
              getProducts();
            }}
            type="text"
            placeholder="Tìm kiếm tên sản phẩm, ID hoặc SKU người bán"
            className="hover:border-[#009DA6] border rounded px-3 py-2 w-[280px] focus:outline-none focus:ring-1 focus:ring-[#009DA6]"
          />
          <div className="relative inline-block group">
            <div className="border border-[#009DA6] rounded px-4 py-2 w-[120px] text-center text-gray-700 cursor-pointer">
              Giá
            </div>
            <div className="absolute left-0 top-full w-72 bg-white border border-gray-200 rounded-lg shadow-md p-4 z-50 hidden group-hover:block">
              <input
                value={filters.minPrice}
                onChange={(v) => {
                  filters.minPrice = v.target.value;
                  getProducts();
                }}
                type="number"
                placeholder="Giá tối thiểu"
                className="w-full border border-gray-300 rounded px-3 py-2 text-sm mb-3 focus:outline-none focus:border-[#009DA6]"
              />
              <div className="h-px bg-gray-200 my-2"></div>
              <input
                value={filters.maxPrice}
                onChange={(v) => {
                  filters.maxPrice = v.target.value;
                  getProducts();
                }}
                type="number"
                placeholder="Giá tối đa"
                className="w-full border border-gray-300 rounded px-3 py-2 text-sm focus:outline-none focus:border-[#009DA6]"
              />
            </div>
          </div>

          <CustomModal
            trigger={
              <input
                type="text"
                placeholder="Hạng mục"
                value={cateString}
                className="hover:border-[#009DA6] border rounded px-3 py-2 w-full focus:outline-none focus:ring-1 focus:ring-[#009DA6] inline-block"
              />
            }
          >
            <CategorySelector
              setIfEnd={false}
              setCategory={(cat) => {
                filters.categoriesId = getAllIds(cat);
                getProducts();
              }}
              setCategoryList={setCatString}
            />
          </CustomModal>

          <button className="border rounded px-4 py-2 bg-white hover:bg-gray-50 text-gray-700">
            Xem thêm bộ lọc
          </button>
          <button
            onClick={() =>
            {
              setFilters({
                key: "",
                minPrice: null,
                maxPrice: null,
                status: null,
                categoriesId: null,
                isLive: false,
              })
              getProducts()
            }
            }
            className="border rounded px-4 py-2 text-gray-700 hover:bg-gray-100"
          >
            Đặt lại
          </button>
        </div>
        <div className="flex gap-2 items-center mt-2">
          <span className="text-gray-800 mr-2 font-sans font-medium">
            Đã chọn: 1/2
          </span>
          <button
            onClick={() => {
              filters.isLive = !filters.isLive;
              getProducts();
            }}
            className="text-gray-400 cursor-not-allowed"
          >
            Đang live {filters.isLive === true ? "(Đã chọn)" : ""}
          </button>
        </div>
      </div> 
      <div className="grid grid-cols-[40px_1.8fr_1fr_1fr_1fr_1.2fr_1fr_1fr] bg-gray-50 font-medium text-gray-800 border-b">
        <div className="flex justify-center items-center py-2">
          <input type="checkbox" />
        </div>
        <div className="py-2">Product</div>
        <div className="py-2 text-right">Quantity</div>
        <div className="py-2 text-right">Retail Price</div>
        <div className="py-2 text-right">Ưu đãi</div>
        <div className="py-2 text-center">Status</div>
        <div className="py-2 text-right">Action</div>
      </div>
      <LoadingSkeleton isShow={isLoading } type="horizontal"/>
      {sampleProducts.content.map((v) => (
        <ProductItem getProduct={getProducts} key={v.id} product={v} />
      ))}
      <Pagination
                currentPage={filters.currentPage+1}
                onPageChange={(p) => {
                  if((p-1)!==filters.currentPage){
                    filters.currentPage = p-1;
                  getProducts()
                  }
                }}
                totalPages={product.totalPages}
              />
    </div>
  );
}

function ProductItem({ product,getProduct }) {
  return (
    <div className="grid grid-cols-[40px_1.8fr_1fr_1fr_1fr_1.2fr_1fr_1fr] border-b bg-white hover:bg-gray-50 transition text-gray-800">
      {/* Checkbox */}
      <div className="flex justify-center items-center py-3">
        <input type="checkbox" />
      </div>

      {/* Product info */}
      <div className="flex gap-3 items-center py-3">
        <img
          src={product.productImage}
          alt={product.productName}
          className="w-12 h-12 object-cover rounded border"
        />
        <div>
          <div className="font-medium">
            {product.productName.length > 60
              ? product.productName.slice(0, 57) + "..."
              : product.productName}
          </div>
          <div className="text-xs text-gray-500">ID: {product.id} </div>
          {product.multiWholesale && (
            <span className="text-[10px] bg-gray-200 px-1.5 py-0.5 rounded mt-1 inline-block text-gray-700">
              MULTI-WHOLESALE
            </span>
          )}
        </div>
      </div>

      {/* Quantity */}
      <div className="py-3 text-right">{product.quantity}</div>

      {/* Retail Price */}
      <div className="py-3 text-right font-medium">
        Rp{Number(product.minPrice).toLocaleString("id-ID")}
      </div>

      {/* Ưu đãi */}
      <div className="py-3 text-right text-xs text-gray-500">2 Ưu đãi</div>

      {/* Status */}
      <div className="py-3  text-center  ">
        <span
          className={`px-2 py-1  inline-block rounded font-semibold ${
            product.status === 1
              ? "bg-green-100 text-green-600"
              : "bg-red-100 text-red-600"
          }`}
        >
          {product.status === 1 ? "Active" :product.status === 2 ? "Delete" : "Hidden"}
        </span>
      </div>

      {/* Action */}
      <div className="py-3 text-right font-bold space-y-1 text-[#00B3B0]">
        {product.status!==2&&<>
        <div className="hover:underline cursor-pointer">Edit</div>
        <div onClick={()=>changeStatusProduct(product.id,"DELETE").then(v=>{
          toast.success("Cập nhật thành công")
          getProduct();
        }).catch(error=>{
          toast.error("Cập nhật không thành công, vui lòng thử lại")
        })} className="hover:underline cursor-pointer">Delete</div>
        <div onClick={()=>{
          changeStatusProduct(product.id,product.status === 1 ? "HIDDEN" : "ACTIVE").then(v=>{
          toast.success("Cập nhật thành công")
          getProduct();
        }).catch(error=>{
          toast.error("Cập nhật không thành công, vui lòng thử lại")
        })
        }} className="hover:underline cursor-pointer">
          {product.status === 1 ? "Deactivate" : "Activate"}
        </div>
        </>} 
        <div className="hover:underline cursor-pointer">View</div>
      </div>
    </div>
  );
}
