import { Navigate, useLocation } from "react-router-dom";

export default function RequireAuth({ children }) {
  const location = useLocation();
  const isAuthenticated = true; // Giả lập xác thực, bạn có thể thay đổi theo logic thực tế

  if (!isAuthenticated) {
    return <Navigate to="/user/home" state={{ from: location }} replace />;
  }

  return children;
}