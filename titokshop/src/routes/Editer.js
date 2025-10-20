import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import { CKEditor } from "@ckeditor/ckeditor5-react";
import { useState } from "react";

const MyEditor = () => {
  const [content, setContent] = useState("");

  const handlePrint = () => {
    console.log("Nội dung CKEditor:", content);
    alert(content); 
  };

  return (
    <div className="p-4">
      <h2 className="text-xl font-bold mb-2">Demo CKEditor</h2>
      <CKEditor
        editor={ClassicEditor}
        data="<p>Nhập nội dung tại đây...</p>"
        onChange={(event, editor) => {
          const data = editor.getData();
          setContent(data);
        }}
      />
      <button
        onClick={handlePrint}
        className="mt-4 px-4 py-2 bg-blue-500 text-white rounded-lg"
      >
        In nội dung
      </button>
    </div>
  );
}

export default  MyEditor