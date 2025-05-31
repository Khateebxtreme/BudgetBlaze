import React, { useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { IoIosMenu } from "react-icons/io";
import { RxCross2 } from "react-icons/rx";
import { FaArrowRightLong } from "react-icons/fa6";
import { motion } from "framer-motion";
import { useStoreContext } from "../contextApi/ContextAPI";
import Profile_Dropdown from "./SubComponents/Profile_Dropdown";

const Navbar = () => {
  const navigate = useNavigate();
  const path = useLocation().pathname;
  const [navbarOpen, setNavbarOpen] = useState(false);
  const { token, setToken, user } = useStoreContext();

  const handleRegister = () => {
    navigate("/register");
  };
  const handleLogin = () => {
    navigate("/login");
  };

  const onLogOutHandler = () => {
    setToken(null);
    localStorage.removeItem("JWT_TOKEN");
    navigate("/register"); //logs out the user and redirects him to register page
  };

  return (
    <div className="h-[80px] 2xl:h-[150px] bg-[#ffffff] z-50 flex items-center top-0  w-full py-3 justify-between px-2">
      <div className="lg:px-4 sm:px-2 px-3 flex w-4/6">
        <Link to="/">
          <motion.h1
            initial={{ opacity: 0, scale: 0.8 }}
            whileInView={{ opacity: 1, scale: 1 }}
            transition={{ duration: 0.8 }}
            className="font-bold font-bebas text-4xl 2xl:text-5xl text-[#212121] py-1 md:mt-0 mt-2 mr-8"
          >
            BudgetBlaze
          </motion.h1>
        </Link>
        <ul
          className={`sm:hidden md:flex lg:flex flex sm:gap-4 gap-3 md:items-center sm:mt-2 sm:pt-0 pt-3  text-slate-800 md:static absolute left-0 top-[56px] sm:shadow-none ${
            navbarOpen
              ? "h-fit sm:pb-0 pb-5 w-full top-[55px] z-10"
              : "h-0 overflow-hidden"
          }  transition-all duration-100 md:h-fit md:bg-none  bg-[#ffffff] md:w-fit w-full sm:flex-row flex-col px-3 sm:px-0`}
        >
          <li className="hover:bg-gray-100 font-[500] py-1 2xl:px-5 lg:px-3 sm:px-2 px-2  transition-all duration-150 rounded-lg 2xl:text-2xl">
            Expenses
          </li>
          <li className="hover:bg-gray-100 font-[500] py-1 2xl:px-5  lg:px-3 sm:px-2 px-2  transition-all duration-150 rounded-lg 2xl:text-2xl">
            Budgets
          </li>
          <li className="hover:bg-gray-100 font-[500] py-1 2xl:px-5 lg:px-3 sm:px-2 px-2  transition-all duration-150 rounded-lg 2xl:text-2xl">
            Transactions
          </li>
          {navbarOpen && !token && (
            <li>
              <button
                className="bg-[#6E6E6E] text-[#fdf8f8] rounded-full border-grey-lightest px-3 py-1 shadow-md font-mono text-md font-semibold sm:hidden flex"
                onClick={handleLogin}
              >
                Sign In
              </button>
            </li>
          )}

          {navbarOpen && !token && (
            <li>
              <button
                className="bg-core-theme-gradient rounded-full border-grey-lightest px-3 py-1 shadow-md font-mono text-md font-semibold text-[#363535] flex sm:hidden"
                onClick={handleRegister}
              >
                <span>
                  Get Started
                  <FaArrowRightLong className="display: inline-flex text-base font-normal p-0 ml-2" />
                </span>
              </button>
            </li>
          )}
          <li>
            {navbarOpen && token && (
              <button
                className="bg-profile-component-gradient-dark rounded-md border-grey-lightest px-4 py-1 shadow-md font-mono text-md mx-2 font-semibold text-[#363535] flex sm:hidden"
                onClick={()=>navigate("/profile")}
              >
                Profile
              </button>
            )}
          </li>
          <li>
            {navbarOpen && token && (
              <button
                className="bg-logout-gradient rounded-md border-grey-lightest px-5 mx-2 py-1 shadow-md font-mono text-md font-semibold text-[#363535] flex sm:hidden"
                onClick={onLogOutHandler}
              >
                Logout
              </button>
            )}
          </li>
        </ul>
      </div>
      <div className="flex flex-row">
        {!token && (
          <motion.button
            whileHover={{ scale: 1.05 }}
            whileTap={{ scale: 0.95 }}
            className="bg-[#6E6E6E] text-[#fdf8f8] rounded-full border-grey-lightest md:my-1 mx-2 md:px-3 md:py-1 px-1 shadow-md font-mono text-md 2xl:py-2 2xl:text-xl font-semibold hidden md:flex 2xl:px-4"
            onClick={handleLogin}
          >
            Sign In
          </motion.button>
        )}
        {!token && (
          <motion.button
            whileHover={{ scale: 1.05 }}
            whileTap={{ scale: 0.95 }}
            className="bg-core-theme-gradient rounded-full border-grey-lightest md:my-1 mx-2 md:px-4 md:py-1 px-1 shadow-md font-mono text-md font-semibold text-[#363535] hidden md:flex 2xl:text-xl 2xl:py-2 2xl:px-4"
            onClick={handleRegister}
          >
            Get Started
          </motion.button>
        )}
        {token && <Profile_Dropdown user={user} />}
        <button
          onClick={() => setNavbarOpen(!navbarOpen)}
          className="md:hidden flex items-center sm:mt-0 mt-2"
        >
          {navbarOpen ? (
            <RxCross2 className="text-black text-3xl" />
          ) : (
            <IoIosMenu className="text-black text-3xl" />
          )}
        </button>
      </div>
    </div>
  );
};

export default Navbar;
