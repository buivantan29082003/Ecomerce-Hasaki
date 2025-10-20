 const uploadSingleImage = async (e) => {
    const file = e.target.files[0];
    if (!file) return;  
    const formData = new FormData();
    formData.append("files", file); 
    try {
      const res = await fetch("http://localhost:8080/upload-single-files", {
        method: "POST",
        body: formData,
      });
      const result = await res.json();
      return result.data
    } catch (err) {
      return null
    }  
}

const uploadMultiImage = async (e) => {
    const files = Array.from(e.target.files);
    if (files.length === 0) return; 

    const formData = new FormData();
    files.forEach((file) => {
      formData.append("files", file);
    });

    try {
      const response = await fetch(
        "http://localhost:8080/upload-multiple-files",
        {
          method: "POST",
          body: formData,
        }
      ); 
      const result = await response.json(); 
      return result.data
    } catch (err) {
      return null;
    }
};

const uploadVideo = async (e) => {
    const file = e.target.files[0];
    if (!file) return; 
    const formData = new FormData();
    formData.append("files", file);

    try {
      const res = await fetch(
        "http://localhost:8080/upload-single-files-video",
        {
          method: "POST",
          body: formData,
        }
      );
      const result = await res.json();
      return result.data
    } catch (err) {
      return null
    }  
};

module.exports={
    uploadSingleImage:uploadSingleImage,
    uploadMultiImage:uploadMultiImage,
    uploadVideo:uploadVideo
}