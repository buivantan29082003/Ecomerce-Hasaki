const handleError=(error)=>{
    if (error.response) { 
      const { message, data } = error.response.data; 
      return { "message":message, "data":data };  
    } else { 
      console.error("Lỗi mạng hoặc axios:", error.message);
      return { message: "Lỗi kết nối", data: null, success: false };
    }
}

export default handleError