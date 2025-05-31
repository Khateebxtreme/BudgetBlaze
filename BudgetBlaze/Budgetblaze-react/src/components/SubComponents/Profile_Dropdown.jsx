import { Menu, MenuButton, MenuItem, MenuItems } from "@headlessui/react";
import { ChevronDownIcon } from "@heroicons/react/16/solid";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useStoreContext } from "../../contextApi/ContextAPI";
import { motion } from "framer-motion";

export default function Profile_Dropdown({ user }) {
  const navigate = useNavigate();
  const { token, setToken } = useStoreContext();
  const path = useLocation().pathname;

  const onLogOutHandler = () => {
    setToken(null);
    localStorage.removeItem("JWT_TOKEN");
    navigate("/register"); //logs out the user and redirects him to register page
  };

  return (
    <div className="text-right mr-6">
      <div className="relative">
        <Menu as="div" className="inline-block">
          {/* Options button on top right corner -> Holds link to user profile and log out button */}

          <MenuButton className="md:inline-flex items-center justify-center gap-2 rounded-lg bg-profile-component-gradient-dark px-4 py-1 text-md font-bold hidden text-gray-600 shadow-md 2xl:text-xl 2xl:px-6 2xl:py-2 border-0 outline-none focus:outline-none">
            {user}
            <ChevronDownIcon className="size-4" />
          </MenuButton>
          {/* Menu items as mentioned above. */}
          <MenuItems
            transition
            className="absolute flex w-full flex-col rounded-lg bg-profile-Nav-dropdown-gradient px-4 py-2 gap-2 text-md text-gray-600 transition duration-100 ease-out mt-1 items-center justify-center border-0 outline-none focus:outline-none"
          >
            <MenuItem>
              <Link to="/profile">
                <button className="group flex font-semibold items-center justify-center w-full rounded-lg px-3.5 py-1 2xl:text-lg 2xl:px-6 2xl:py-2">
                  Profile
                </button>
              </Link>
            </MenuItem>
            <MenuItem>
              <button
                onClick={onLogOutHandler}
                className="group flex w-full items-center justify-center rounded-lg px-3.5 py-1 bg-[#fd4e4e] text-white font-semibold 2xl:text-lg 2xl:px-6 2xl:py-1"
              >
                Logout
              </button>
            </MenuItem>
          </MenuItems>
        </Menu>
      </div>
    </div>
  );
}
