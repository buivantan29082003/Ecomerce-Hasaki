import React, { useEffect, useState } from "react";
import TooltipCustome from "../../components/Base/TooltipCustome";
import { BiChevronLeft } from "react-icons/bi";
import { greenMain } from "../../config/TextColor";
import CategorySelector from "../../components/Base/CategorySelector";
import CustomModal from "../../components/Base/Modal";
import {
  FaBolt,
  FaImage,
  FaListOl,
  FaListUl, 
  FaShoppingCart,
  FaVideo,
} from "react-icons/fa";
import CircularProgress from "../../components/Base/PercentRevice";
import { ImSpinner2 } from "react-icons/im";
import api from "../../config/AxiosConfig";
import { uploadMultiImage, uploadSingleImage } from "../../Service/UploadImage";
import { autoAlertError } from "../../Service/HandleAlert";
import { CKEditor } from "@ckeditor/ckeditor5-react";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";

export default function AddProduct() {
  const [product, setProduct] = useState({
    productName: "Máy tính xách tay đời mới",
    discription: "<div></div>",
    productImage: "string",
    video: "string",
    minPrice: 0,
    maxPrice: 0,
    createdDate: "2025-08-14",
    brand: {
      id: 3,
    },
    category: {
      id: 4,
    },
    productImages: [],
    productVariants: [],
    variantTiers: [ 
    ],
    properties: [ 
    ],
  });
  const onchangeProperty = (propertyName, value) => {
    setProduct({ ...product, productName: value.target.value });
  };

  return (
    <>
      <p className="flex items-center text-gray-500 ml-5 font-semibold">
        <BiChevronLeft /> <span>Manage product</span>
      </p>
      <h3 className="flex items-center ml-7 mt-2 text-2xl font-semibold">
        {" "}
        Add new product <button onClick={()=>{
            api.post("/sale/product/add",product).then(v=>{
              alert("Thêm thành công")
              alert(document.getElementById("alert"))
            }).catch(error=>{  
              autoAlertError()
            })
        }}>Thêm</button>
      </h3>

      <BaseInfo
        setProduct={setProduct}
        product={product}
        onchangeProperty={onchangeProperty}
      />
    </>
  );
}

function MediaSection({ product, setProduct }) {
  const [mainImage, setMainImage] = useState(null);
  const [loadingOther, setLoadingOther] = useState(false);
  const [loadingMain, setLoadingMain] = useState(false);
  const [loadingVideo, setLoadingVideo] = useState(false);

 
  const handleMainImageChange = async (e) => {
    setLoadingMain(true)
    uploadSingleImage(e).then(v => {
      if(v!=null){
        setMainImage(v); 
        setProduct({ ...product, productImage: v });
      } 
      setLoadingMain(false)
    }) 
    
  };
 
  const handleOtherImagesChange = async (e) => {
    setLoadingOther(true) 
    uploadMultiImage(e).then(v=>{
      if(v!=null){
       product.productImages.push(...v);
       setProduct({ ...product, productImages: product.productImages });
      }
      setLoadingOther(false)
    }) 
  };
 
  const handleVideoChange = async (e) => {
    setLoadingVideo(true) 
    uploadMultiImage(e).then(v=>{
      if(v!=null){
       setProduct({ ...product, video: v });
      }
      setLoadingVideo(false)
    }) 
     
  };

  return (
    <div className="grid grid-cols-1 md:grid-cols-3 ">
      {/* LEFT: chiếm 2/3 */}
      <div className="col-span-2 space-y-6">
        <h3 className="font-semibold text-2xl mb-2 mt-4">Media</h3>

        {/* Main Image */}
        <div>
          <label className="block font-semibold mb-2">
            Product image{" "}
            <TooltipCustome
              icon={"?"}
              title={"Please enter more than 25 character."}
            />
          </label>
          <p className="text-sm text-gray-400 mb-3">
            Recommended to upload at least 3 images (1200×1200 px).
          </p>

          <div className="grid grid-cols-2 sm:grid-cols-6 gap-4">
            {/* Main image */}
            <div className="relative aspect-square border rounded-lg flex items-center justify-center bg-gray-50 overflow-hidden">
              {loadingMain ? (
                <ImSpinner2 className="animate-spin text-2xl text-gray-400" />
              ) : mainImage ? (
                <img
                  src={product.productImage}
                  alt="Main"
                  className="object-cover w-full h-full"
                />
              ) : (
                <span className="text-gray-400 text-sm">Main image</span>
              )}
              <input
                type="file"
                accept="image/*"
                onChange={handleMainImageChange}
                className="absolute inset-0 opacity-0 cursor-pointer"
              />
            </div>

            {/* Other images */}
            {product.productImages.map((img, idx) => (
              <div
                key={idx}
                className="relative aspect-square border rounded-lg overflow-hidden"
              >
                <img
                  src={img.imageLink}
                  alt={`Other ${idx}`}
                  className="object-cover w-full h-full"
                />
              </div>
            ))}

            {/* Upload more */}
            <div className="relative aspect-square border-dashed border-2 rounded-lg flex items-center justify-center cursor-pointer text-gray-400">
              {loadingOther ? (
                <ImSpinner2 className="animate-spin text-2xl" />
              ) : (
                <FaImage />
              )}
              <input
                type="file"
                accept="image/*"
                multiple
                onChange={handleOtherImagesChange}
                className="absolute inset-0 opacity-0 cursor-pointer"
              />
            </div>
          </div>
        </div>

        {/* Video upload */}
        <div>
          <label className="block font-semibold mb-2">Video</label>
          <p className="text-sm text-gray-400 mb-3">
            Aspect ratio between 9:16 and 16:9. Max file size: 100MB.
          </p>
          <div className="relative border-dashed border-2 rounded-lg p-6 flex items-center justify-center text-gray-400 cursor-pointer">
            {loadingVideo ? (
              <ImSpinner2 className="animate-spin text-2xl" />
            ) : (
              <FaVideo className="text-2xl" />
            )}
            <input
              type="file"
              accept="video/*"
              onChange={handleVideoChange}
              className="absolute inset-0 opacity-0 cursor-pointer"
            />
          </div>
        </div>
      </div>
 
      <div className="hidden md:block"></div>
    </div>
  );
}

function BaseInfo({ product, onchangeProperty, setProduct }) {
  const [categoryList, setCategoryList] = useState("");
  const [dataBase, setDataBase] = useState({
    brands: [],
    categories: [],
    properties:[ ]
  });
  const [reload,setReload]=useState(true)
  useEffect(() => {
    Promise.all([
      api.get("/sale/brand/getall"),
      api.get("/sale/categories/getall"),
    ]).then(([brandsRes, categoriesRes]) => {
      setDataBase((prev) => ({
        ...prev,
        brands: brandsRes.data.data,
        categories: categoriesRes.data.data,
      })); 
    });
  }, []);

  const getPropertiesByCategory=(cate)=>{  
    if(cate!=null){
      api.get("sale/properties/getall?categoryId="+cate.id).then(v=>v.data).then(v=>{ 
      const proDemo = v.data.map(item => ({
        property:item, 
        propertyValue:null
      }));
      product.properties=proDemo
      // setProduct({...product,properties:proDemo})  
      setProduct({...product,category:cate}) 
      setDataBase({...dataBase,properties:v.data}) 
    }).catch(error=>{
      console.log(error)
    })
    setProduct({...product,category:cate})
    }else{
      setProduct({...product,category:null})
    }
    
  }

  return (
    <div className="flex flex-col md:flex-row h-screen">
      {/* LEFT: Nội dung chính */}
      <div
        className="w-full md:w-2/3 p-6  overflow-y-auto"
        style={{ scrollbarWidth: "none", msOverflowStyle: "none" }}
      >
        <h3 className="font-semibold text-2xl mb-4">Basic information</h3>

        {/* Product Name */}
        <div>
          <label className="block font-semibold mb-1">
            Product name{" "}
            <TooltipCustome
              icon={"?"}
              title={"Please enter more than 25 character."}
            />
          </label>
          <p className="text-sm text-gray-400 font-light mb-3">
            Recommended length:{" "}
            <span className="font-semibold text-[#009DA6]">25 characters</span>{" "}
            or more. Will be identified according to product name.
          </p>
          <div className="relative w-full">
            <input
              type="text"
              value={product.productName}
              onChange={onchangeProperty.bind(null, "productName")}
              placeholder="Enter product name"
              maxLength={255}
              className="w-full hover:border-[#009DA6] border rounded px-3 py-1 focus:outline-none focus:ring-1 focus:ring-[#009DA6] pr-12"
            />
            <span className="absolute right-2 bottom-1 text-xs text-gray-400">
              {product.productName.length}/255
            </span>
          </div>
        </div>

        {/* Category */}
        <div>
          <label className="block font-semibold mb-1 mt-3">
            Category{" "}
            <TooltipCustome
              icon={"?"}
              title={"Please enter more than 25 character."}
            />
          </label>
          <p className="text-sm text-gray-400 font-light mb-3">
            Some categories are invite-only and can't be selected. To add these
            categories, click here to submit the:{" "}
            <span className="font-semibold text-[#009DA6]">
              invite only application
            </span>
            . Don't upload{" "}
            <span className="font-semibold text-[#009DA6]">prohibit</span> or{" "}
            <span className="font-semibold text-[#009DA6]">restricted</span>{" "}
            products.
          </p>
          <CustomModal
            trigger={
              <input
                type="text"
                placeholder={categoryList}
                className="hover:border-[#009DA6] border rounded px-3 py-1 focus:outline-none focus:ring-1 focus:ring-[#009DA6] w-full"
              />
            }
          >
            <CategorySelector
              setCategory={getPropertiesByCategory}
              setCategoryList={setCategoryList}
              data={dataBase.categories}
              setIfEnd={true}
            />
          </CustomModal>
        </div>

        {/* Brand */}
        <div>
          <label className="block font-semibold mb-1 mt-3">Brand</label>
          <p className="text-sm text-gray-400 font-light mb-3">
            Some categories are invite-only and can't be selected. To add these
            categories, click here to submit the. Don't upload{" "}
            <span className="font-semibold text-[#009DA6]">prohibit</span> or{" "}
            <span className="font-semibold text-[#009DA6]">restricted</span>{" "}
            products.
          </p>
          <select className="hover:border-[#009DA6] border rounded w-full p-1">
            {dataBase.brands.map((v, index) => {
              return <option value="">{v.brandName}</option>;
            })}
          </select>
        </div>

        {/* Product Attributes */}
        <div>
          <label className="block font-semibold mb-3 mt-3">
            Product Attributes
          </label>
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
            {dataBase.properties.map((v, index) => (
              <div key={index}>
                <p>{v.propertyName} {product.properties[index].propertyValue!=null?"":" (Chưa chọn)"}</p>
                <select onChange={(e)=>{
                   product.properties[index].propertyValue={id:e.target.value}
                   setReload(!reload)
                }} value={product.properties[index].propertyValue}
                  defaultValue=""
                  className="hover:border-[#009DA6] border rounded px-3 py-2 mt-2 focus:outline-none focus:ring-1 focus:ring-[#009DA6] w-full"
                >
                  <option value="" disabled hidden>
                    Select an value 
                  </option>
                  {v.propertyValues.map((vv) => (
                    <option   key={vv.id} value={vv.id}>
                      {vv.propertyValue}
                    </option>
                  ))}
                </select>


              </div>
            ))}
          </div>
        </div>

        <MediaSection product={product} setProduct={setProduct} />
        <ProductDetails setProduct={setProduct} product={product} onchangeProperty={onchangeProperty} />
        <ProductVariantForm product={product} setProduct={setProduct} />
      </div>

      {/* RIGHT: Preview */}
      <div className="w-full md:w-1/3 hidden md:block h-screen overflow-y-auto border-l">
        <ProductPreview product={product} />
      </div>
    </div>
  );
}

function ProductPreview({product}) {
  const [activeTab, setActiveTab] = useState("description");
  
  function calculateCompletion(obj) {
  let totalFields = 0;
  let completedFields = 0;
  

  function checkValue(value) {
    if (typeof value === "string") {
      totalFields++;
      if (value.trim() !== "") completedFields++;
    } else if (Array.isArray(value)) {
      totalFields++;
      if (value.length > 0) completedFields++;
    } else if (typeof value === "object" && value !== null) {
      totalFields++;
      completedFields++; // object khác null => hoàn thành
      // kiểm tra sâu hơn cho object con
      for (const key in value) {
        checkValue(value[key]);
      }
    } 
  }

  for (const key in obj) {
    checkValue(obj[key]);
  }

  return ((completedFields / totalFields) * 100).toFixed(2) + "%";
}

  const tabs = ["Overview", "Reviews", "Description"];

  return (
    <div className="border rounded-lg p-4 hidden md:block bg-white shadow-sm">
      <h3 className="font-semibold mb-4 text-lg">Preview</h3>

      <div className="text-sm text-gray-800">
        {/* Tabs */}
        <div className="flex border-b mb-4 text-gray-600 text-sm">
          {tabs.map((tab) => (
            <button
              key={tab}
              onClick={() => setActiveTab(tab.toLowerCase())}
              className={`px-4 py-2 -mb-px border-b-2 transition ${
                activeTab === tab.toLowerCase()
                  ? "border-black font-semibold text-black"
                  : "border-transparent hover:text-black"
              }`}
            >
              {tab}
            </button>
          ))}
        </div>

        {/* Tab content */}
        {activeTab === "overview" && (
          <div className="p-2 text-gray-600">
            <p>Overview content here...</p>
            <CircularProgress value={40} />
          </div>
        )}

        {activeTab === "reviews" && (
          <div className="p-2 text-gray-600">
            <p>Reviews content here...</p>
          </div>
        )}

        {activeTab === "description" && (
          <div>
            <div className="mb-4">
              <h4 className="font-medium mb-2">Details</h4>
              <ul className="text-gray-600 text-sm space-y-1">
                <li>
                  <strong>Brand:</strong> No brand
                </li>
                <li>- Attribute 1</li>
                <li>- Attribute 2</li>
                <li>- Attribute 3</li>
              </ul>
            </div>

            <div>
              <h4 className="font-medium mb-2">Description</h4>
              <p className="text-gray-600 text-sm">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec
                vel sapien at nunc fringilla tincidunt.
              </p>
            </div>
          </div>
        )}

        {activeTab === "recommend" && (
          <div className="p-2 text-gray-600">
            <p>Recommend content here...</p>
          </div>
        )}
      </div>

      {/* Buttons */}
      <div className="flex gap-2 mt-6">
        <button className="flex-1 flex items-center justify-center gap-2 border rounded-lg px-4 py-2 text-gray-600 cursor-not-allowed bg-gray-100">
          <FaShoppingCart />
          Add to cart
        </button>
        <button className="flex-1 flex items-center justify-center gap-2 bg-black text-white rounded-lg px-4 py-2 hover:bg-gray-800">
          <FaBolt />
          Buy now
        </button>
      </div>

      <p className="text-xs text-gray-400 mt-3">
        For reference only. This is not the final result on the user end.
      </p>
    </div>
  );
}

function ProductDetails({product,onchangeProperty,setProduct}) {  
  return (
    <div className=" ">
      {/* LEFT: chiếm 2/3 */}
      <div className="col-span-2 space-y-6">
        <h3 className="font-semibold text-2xl mb-3 mt-4">Product detail</h3>

        <div>
          <label className="block font-semibold mb-2">
            Product description{" "}
            <span className="text-red-500">
              <TooltipCustome
                icon={"?"}
                title={"Please enter more than 25 character."}
              />
            </span>
          </label>
          <p className="text-sm text-gray-400 mb-3">
            Recommended to provide a description of at least 200 characters long
            and add images to help customers make purchasing decisions.{" "}
            <span className="text-blue-500 cursor-pointer">Example</span>
          </p>
 
          <div className="border rounded-lg overflow-hidden shadow-sm">
            {/* Toolbar */}
            <div className="flex items-center gap-4 p-2 border-b bg-gray-50">
              <button className="text-gray-600 hover:text-black">
                <FaListUl />
              </button>
              <button className="text-gray-600 hover:text-black">
                <FaListOl />
              </button>
              <button className="text-gray-600 hover:text-black">
                <FaImage />
              </button>
            </div>

            {/* Textarea */}
           <CKEditor
            editor={ClassicEditor}
            data={product.discription || ""}  // giá trị ban đầu
            onChange={(event, editor) => {
              const data = editor.getData();
              setProduct({
  ...product,           // copy toàn bộ thuộc tính cũ
  discription: data     // ghi đè giá trị mới
});

               // update state cha
            }}
          />
          {/* <CKEditor
                  editor={ClassicEditor}
                  data="<p>Nhập nội dung tại đây...</p>"
                  // onChange={(event, editor) => {
                  //   const data = editor.getData();
                  //   setContent(data);
                  // }}
                /> */}

          </div>
        </div>
      </div>
    </div>
  );
}

function ProductVariantForm({ product, setProduct }) {
  const changeValue = (propName, value, index) => {
    product.productVariants[index][propName] = value;
    setProduct({ ...product, productVariants: product.productVariants });
  };

  const handleMainImageChange = async (e, propName, index) => { 
    uploadSingleImage(e).then(v => {
      product.productVariants[index][propName] = (v);  
      setProduct({ ...product, productImage: v });
    })
  };


  return (
    <div className="p-4 space-y-4 mt-3 rounded-md bg-white shadow">
      <div className="flex gap-10 item-center">
        <h3 className="font-semibold text-2xl mb-3 mt-4">Product variant</h3>
        <button
          className={`${greenMain}`}
          onClick={() => {
            product.productVariants.push({
              quantity: 0,
              price: 0,
              image: "",
              isActive: 0,
              variantName: "string",
              isDefault: 0,
              indexVariant: "string",
              height:0,
              weight:0
            });
            setProduct({
              ...product,
              productVariants: product.productVariants,
            });
          }}
        >
          + Add variant
        </button>
      </div>
      {/* variant */}
      <div>
        {product.productVariants.map((v, index) => {
          return (
            <>
              <div className="flex items-start space-x-3 border rounded-md bg-gray-50 p-3 mb-3">
                {v.image != "" ? (
                  <>
                    <div className="w-20 h-20 border rounded flex items-center justify-center text-gray-400 text-sm">
                      <img src={v.image}/>
                    </div>
                  </>
                ) : (
                  <>
                    <label className="w-24 h-24 border-2 border-dashed border-gray-300 rounded flex flex-col items-center justify-center cursor-pointer text-gray-400 text-sm">
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        className="h-6 w-6 mb-1"
                        fill="none"
                        viewBox="0 0 24 24"
                        stroke="currentColor"
                      >
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          strokeWidth={2}
                          d="M3 7v4a1 1 0 001 1h3m10-5h4m-4 0a2 2 0 00-2-2H9a2 2 0 00-2 2m12 0v12a2 2 0 01-2 2H7a2 2 0 01-2-2V9m12-2l-3.586 3.586a2 2 0 01-2.828 0L9 7"
                        />
                      </svg>
                      Upload image
                      <input onChange={(e)=>{
                        handleMainImageChange(e,"image",index)
                      }} type="file" className="hidden" />
                    </label>
                  </>
                )}
                <div className="flex-1 space-y-3">
                  <div className="flex space-x-3">
                    <input
                      type="text"
                      value={v.variantName}
                      onChange={(e) => {
                        changeValue("variantName", e.target.value, index);
                      }}
                      placeholder="Tên biến thể"
                      className="border rounded p-2 flex-1"
                    />
                    <input
                      type="number"
                      value={v.price}
                      onChange={(e) => {
                        changeValue("price", e.target.value, index);
                      }}
                      placeholder="price"
                      className="border rounded p-2 flex-1"
                    />
                  </div>
                  <div className="flex space-x-3">
                    <input
                      type="height"
                      value={v.height}
                      onChange={(e) => {
                        changeValue("height", e.target.value, index);
                      }}
                      placeholder="height"
                      className="border rounded p-2 flex-1"
                    />
                     <input
                      type="number"
                      value={v.weight}
                      onChange={(e) => {
                        changeValue("weight", e.target.value, index);
                      }}
                      placeholder="weight"
                      className="border rounded p-2 flex-1"
                    />
                    <input
                      type="number"
                      value={v.quantity}
                      onChange={(e) => {
                        changeValue("quantity", e.target.value, index);
                      }}
                      placeholder="Quantity"
                      className="border rounded p-2 flex-1"
                    />
                  </div>
                </div>
              </div>
            </>
          );
        })}
      </div>
    </div>
  );
}
