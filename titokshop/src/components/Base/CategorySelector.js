import { useEffect, useState } from "react"; 
import api from "../../config/AxiosConfig";

const CategorySelector = ({setIfEnd,setCategoryList, setCategory }) => {
  const [selectedPath, setSelectedPath] = useState([]);  
  const getChildren = (node) => node?.children || []; 
  const buildPathString = (nodes) => {
    return nodes.map((x) => x.categoryName).join(" > ");
  }; 
  const [data,setData]=useState([])
  useEffect(()=>{
    api.get("/sale/categories/getall").then(v=>{
      setData(v.data.data)
    })
  },[])
  const renderLevel = (items, level) => {
    if (!items || items.length === 0) return null;

    return (
      <div className="w-1/3 border-l pl-2">
        {items.map((item) => (
          <div
            key={item.id}
            className={`cursor-pointer px-2 py-1 rounded hover:bg-gray-100 ${
              selectedPath[level]?.id === item.id ? "text-red-500 font-semibold" : ""
            }`}
            onClick={() => { 
              const newPath = [...selectedPath.slice(0, level), item];
              setSelectedPath(newPath);
              if (!item.children || item.children.length === 0&&setIfEnd) {
                setCategoryList(buildPathString(newPath));
                setCategory(item); 
                console.log(item) 
              }else  {
                setCategoryList("");
                setCategory(item);  
                console.log(item)
              }
            }}
          >
            {item.categoryName}
          </div>
        ))}
      </div>
    );
  };

  return (
    <div className="flex rounded p-4 gap-4 w-full max-w-4xl overflow-x-auto"> 
      {Array.from({ length: selectedPath.length + 1 }).map((_, level) => {
        const parent = selectedPath[level - 1] || null;
        const items = level === 0 ? data : getChildren(parent);
        return renderLevel(items, level);
      })}
    </div>
  );
};

export default CategorySelector;
