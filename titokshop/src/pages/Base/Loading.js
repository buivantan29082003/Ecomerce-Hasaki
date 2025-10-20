import Logo from "../../assets/image/logo/image.png";

const Loading = () => {
  return (
    <div className="w-screen h-screen bg-black flex items-center justify-center">
      <img src={Logo} alt="logo" className="w-70 h-70 object-contain" />
    </div>
  );
};

export default Loading;
