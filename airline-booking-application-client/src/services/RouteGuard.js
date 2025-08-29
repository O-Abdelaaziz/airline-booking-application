import { Navigate, useLocation } from "react-router-dom";
import ApiService from "./ApiService";

// export function RouteGuard({ children }) {
//     const location = useLocation();
//     return ApiService.isAuthenticated() ? (
//         children
//     ) : (
//         <Navigate to="/login" state={{ from: location }} replace />
//     );
// }

export const RouteGuard = ({ element: Component, allowedRoutes }) => {
  const location = useLocation();
  let hasRequiredRole = false;

  if (!allowedRoutes || allowedRoutes.length === 0) {
    hasRequiredRole = true;
  } else {
    hasRequiredRole = allowedRoutes.some((role) => {
      if (role === "ADMIN") {
        return ApiService.isAdmin();
      } else if (role === "PILOT") {
        return ApiService.isPilot();
      } else if (role === "CUSTOMER") {
        return ApiService.isCustomer();
      }
      return false;
    });
  }

  if (hasRequiredRole) {
    return Component;
  } else {
    return <Navigate to="/login" state={{ from: location }} replace />;
  }
};
