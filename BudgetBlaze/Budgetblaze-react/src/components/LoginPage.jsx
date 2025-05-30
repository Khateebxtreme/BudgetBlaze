import React, { useState } from "react";
import { useForm } from "react-hook-form";
import TextField from "./TextField";
import { Link, useNavigate } from "react-router-dom";
import toast from "react-hot-toast";
import { useStoreContext } from "../contextApi/ContextAPI";
import api from "../Api/api";

const LoginPage = () => {
  const navigate = useNavigate();
  const [loader, setLoader] = useState(false);
  const { setToken } = useStoreContext();
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm({
    defaultValues: {
      username: "",
      password: "",
    },
    mode: "onTouched",
  });

  const loginHandler = async (data) => {
    setLoader(true);
    try {
      //will make an API request to the backend and upon successful login, the user is assigned a token
      console.log(data);
      const { data: response } = await api.post(
        "http://localhost:8080/UserService/login",
        data
      );
      //storing the bearer token in local storage for the authenticated user
      console.log(response.token);
      setToken(response.token); // setting the token at context level
      localStorage.setItem("JWT_TOKEN", JSON.stringify(response.token)); //setting JWT token in local storage
      localStorage.setItem("USER", data.username); //making the username global, which may be required for some feature.
      toast.success("User has been successfully logged in");
      reset();
      navigate("/");
    } catch (error) {
      console.log(error);
      reset();
      toast.error("Failed to Login");
    } finally {
      setLoader(false);
    }
  };

  return (
    <div className=" mt-4 min-h-[calc(100vh-10vh)] flex justify-center items-center">
      <form
        onSubmit={handleSubmit(loginHandler)}
        className="sm:w-[600px] 2xl:w-[800px] w-[360px] shadow-custom pt-5 pb-4 sm:px-8 px-4 rounded-md"
      >
        <h2 className="text-center font-serif text-log_reg_headerColor font-medium lg:text-4xl text-3xl">
          Sign In to Continue
        </h2>
        <hr className="mt-2 mb-3 border" />
        <div className="flex flex-col gap-3">
          <TextField
            label="Unique ID"
            required
            id="username"
            type="text"
            message="*Unique ID is required"
            placeholder="Please Enter your Unique ID"
            register={register}
            errors={errors}
          />

          <TextField
            label="Password"
            required
            id="password"
            type="password"
            message="*Password is required"
            placeholder="Please Enter your password"
            register={register}
            min={6}
            errors={errors}
          />
        </div>
        <button
          disabled={loader}
          type="submit"
          className="bg-customRed font-semibold text-[#363535]  bg-core-theme-gradient w-full py-2 transition-colors duration-100 rounded-md my-3"
        >
          {loader ? "Loading..." : "Login"}
        </button>
        <p className="text-center text-sm text-slate-700 mt-6">
          Don't have an Account?
          <span> </span>
          <Link
            className="font-semibold underline hover:text-black"
            to="/register"
          >
            <span className="text-btnColor hover:text-red-500">Sign Up</span>
          </Link>
        </p>
      </form>
    </div>
  );
};

export default LoginPage;
