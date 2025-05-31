import { useStoreContext } from "./contextApi/ContextAPI";
import { Navigate } from "react-router-dom";

export default function PrivateRoute({children, publicPage}){
  const {token} = useStoreContext(); //We track if the user is in an authenticated session or not.

  if(publicPage){
    //if we are accessing pages like login or register when the user is already logged in, we lead the user to a dashboard screen (currently profile page) instead of these pages.
    return token ? <Navigate to="/profile"/> : children;
  }

  return !token ? <Navigate to="/register" /> : children; //if user is not logged in and if he tries to access an authenticated route, he is lead to register page.
}