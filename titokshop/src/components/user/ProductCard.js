import { formatVND } from "../../Service/FormatDate";

const ProductCard = ({ product,className}) => {
    
  return (
    <div className={` bg-white rounded-lg shadow hover:shadow-md transition relative overflow-hidden ${className!=null?className:"w-44"}`}>
      {/* Ảnh sản phẩm */}
      <div className="relative">
        <img
          src={product.productImage}
          alt={product.productName}
          className="rounded-t-lg w-full h-48 object-cover"
        />

        {/* Label giảm giá */}
        {product.sale && (
          <div className="absolute top-2 right-2 bg-red-500 text-white text-xs font-bold px-2 py-1 rounded-md">
            -{product.sale.discountValue}%
          </div>
        )}

        {/* Badge trên ảnh */}
        <div className="absolute bottom-2 left-2 flex flex-col gap-1">
          {product.badges?.map((b, i) => (
            <span
              key={i}
              className="bg-green-500 text-white text-[10px] font-semibold px-2 py-0.5 rounded"
            >
              {b}
            </span>
          ))}
        </div>
      </div>

      {/* Nội dung */}
      <div className="p-2">
        {/* Tên shop */}
       
          <span className="bg-yellow-200 text-yellow-800 text-[10px] font-semibold px-1.5 py-0.5 rounded mr-1">
            {product.shopName}
          </span>
       

        {/* Tên sản phẩm */}
        <p className="text-sm font-medium text-gray-800 line-clamp-2">
          {product.productName}
        </p>

        {/* Giá */}
        <div className="mt-1">
          <span className="text-red-500 text-md font-bold">{formatVND(product.minPrice*(product.sale===null?1:1-(product.sale.discountValue/100)))}</span>
         
            <span className="text-gray-400 text-xs line-through ml-2">
              {formatVND(product.minPrice)}
            </span> 
            {product.sale&&<span className="ml-2 inline-block mt-1 bg-pink-100 text-pink-600 text-[10px] font-semibold px-2 py-0.5 rounded">
              {product.sale.discountValue} %
          </span>}
        </div>

        {/* Badge COD */}
        {/* {product.cod && ( */}
          <span className="inline-block mt-1 bg-red-100 text-red-600 text-[10px] font-semibold px-2 py-0.5 rounded">
            COD
          </span>
          
        {/* )} */}

        {/* Rating + Sold */}
        <div className="mt-1 flex items-center text-xs text-gray-500 gap-1">
          <span className="text-yellow-400 text-lg">★</span>
          <span>{product.countStart}</span>
          <span>|</span>
          <span>{product.totalSale} sold</span>
        </div>
      </div>
    </div>
  );
};
export default ProductCard