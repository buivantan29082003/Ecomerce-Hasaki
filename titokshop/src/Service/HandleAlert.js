const autoAlertError=(titile,content)=>{
    var errorAlert=document.getElementById("alertError");  
    var opaAlert=document.getElementById("opa");
    var titiles=document.getElementById("aw-title");
    var contents=document.getElementById("aw-desc")
    if(errorAlert!=null){  
        titiles.textContent=titile;
        contents.textContent=content;
        errorAlert.style.display="block";
        opaAlert.style.display="block"
        setTimeout(()=>{
            opaAlert.style.display="none"
            errorAlert.style.display="none";
        },3000)
    } 
}



module.exports={
    autoAlertError:autoAlertError
}