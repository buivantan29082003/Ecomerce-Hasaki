const validatePhoneNumber=(phoneNumber)=>{
    return /^((\+84)|0)(3|5|7|8|9)[0-9]{8}$/.test(phoneNumber)
}

module.exports={
    validatePhoneNumber:validatePhoneNumber
}