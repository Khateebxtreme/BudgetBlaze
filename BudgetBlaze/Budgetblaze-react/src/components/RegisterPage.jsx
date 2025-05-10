import Form_Stepper from "./Register_Login Steppers/Form_Stepper";
import React, { useEffect, useState } from "react";
import { set, useForm } from "react-hook-form";
import TextField from "./TextField";
import { Link, useNavigate } from "react-router-dom";
import toast from "react-hot-toast";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import api from "../Api/api";
import { button } from "framer-motion/client";

const RegisterPage = () => {
  const navigate = useNavigate();
  const [loader, setLoader] = useState(false); //usage for deployment time
  const [methodSelect, setMethodSelect] = useState("email");
  const [currentStep, setCurrentStep] = useState(0);
  const [termsChecked, setTermsChecked] = useState(false);
  const [userData, setUserData] = useState(null);
  let otp = null;

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm({
    defaultValues: {
      username: "",
      email: "",
      password: "",
    },
    mode: "onTouched",
  });

  const handleOTP = async () => {
    try {
      console.log(userData)
      otp =
        methodSelect == "email"
          ? await api.post("http://localhost:8080/UserService/requestOTP", {
              functionType: "Reg",
              email: userData.email,
            })
          : await api.post("http://localhost:8080/UserService/requestOTP", {
              functionType: "Reg",
              phone: userData.phone,
            });
      console.log(otp)
      otp = otp? otp.data.OTP:"No OTP exists";
      toast.success("OTP is now generated", { duration: 10000 });
    } catch (error) {
      console.error(error);
      toast.error("OTP couldn't be generated");
      reset();
    } finally {
    }
  };

  const handleChange = (event) => {
    console.log(event.target.value);
    setMethodSelect(event.target.value);
  };

  const handleCheckBox = (met) => {
    setTermsChecked(!termsChecked);
  };

  const handleRegister = async (data) => {
    if (currentStep == 0) {
      try {
        //api call to register user and toast
        setUserData(data);
        await api.post("http://localhost:8080/UserService/signUpUser", data);
        toast.success("Registration Successfull");
        setCurrentStep(1);
      } catch (error) {
        console.log(error);
        reset();
        toast.error("Registration Failed");
      } finally {
      }
    } else if (currentStep == 1 && termsChecked) {
      try {
        //api call to verify OTP of the user
        toast.success("User has been verified");
      } catch (error) {
        console.log(error);
        toast.error("User Verification Failed");
      } finally {
        setCurrentStep(2);
        reset();
      }
    } else if (currentStep == 2) {
      setCurrentStep((currentStep) => currentStep + 1);
      toast.success("User Account has been created", {
        duration: 6000,
        icon: "👏",
      });
      navigate("/login");
    }
  };

  return (
    <div className="mt-5 min-h-[calc(100vh-10vh)]">
      <div className="items-center justify-center flex my-5 p-5">
        <Form_Stepper step_count={currentStep} />
      </div>
      <div className="flex justify-center items-center mb-10">
        <form
          onSubmit={handleSubmit(handleRegister)}
          className="sm:w-[600px] w-[360px] 2xl:w-[800px]  shadow-custom pt-5 pb-4 sm:px-8 px-4 rounded-md"
        >
          {currentStep === 0 ? (
            <>
              <h2 className="text-center font-serif text-log_reg_headerColor font-medium lg:text-4xl text-3xl">
                Register User
              </h2>
              <hr className="mt-2 mb-3 border" />
              {/* Drop-Down List */}
              <div className="items-center justify-center flex mb-3">
                <p>Please choose the registration method : </p>
                <FormControl sx={{ m: 1, minWidth: 120 }} size="small">
                  <InputLabel id="demo-select-small-label"></InputLabel>
                  <Select
                    labelId="demo-select-small-label"
                    id="demo-select-small"
                    value={methodSelect}
                    onChange={handleChange}
                  >
                    <MenuItem value={"email"}>Email</MenuItem>
                    <MenuItem value={"phone"}>Phone</MenuItem>
                  </Select>
                </FormControl>
              </div>
              <div className="flex flex-col gap-3">
                <TextField
                  label={methodSelect == "email" ? "Email" : "Phone Number "}
                  required
                  id={methodSelect}
                  type={methodSelect}
                  message={
                    methodSelect == "email"
                      ? "*Email is Required"
                      : "*Phone Number is Required"
                  }
                  placeholder={
                    methodSelect == "email"
                      ? "Please Enter your Email"
                      : "Please Enter your Phone Number"
                  }
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
                {loader ? "Loading..." : "Register"}
              </button>
              <p className="text-center text-sm text-slate-700 mt-3">
                Already have an account?
                <span> </span>
                <Link
                  className="font-semibold underline hover:text-black"
                  to="/login"
                >
                  <span className=" hover:text-red-500">Sign In</span>
                </Link>
              </p>
            </>
          ) : (
            <>
              {currentStep === 1 && (
                <>
                  <h2 className="text-center font-serif font-medium lg:text-4xl text-3xl">
                    OTP Verification
                  </h2>
                  <hr className="mt-2 mb-3 border" />
                  <div className="flex flex-col gap-3">
                    <TextField
                      label="OTP"
                      required
                      id="otp"
                      type="otp"
                      message="Please Enter a Valid OTP"
                      placeholder="Please Enter your OTP"
                      register={register}
                      min={6}
                      max={6}
                      errors={errors}
                    />
                  </div>
                  {/* Checkbox */}
                  <div className="my-3 flex">
                    <div>
                      <input
                        type="checkbox"
                        id="terms"
                        name="terms"
                        value="terms"
                        checked={termsChecked}
                        onChange={() => handleCheckBox(event.target.id)}
                      />
                      <label className="pl-1 pb-2" for="phone">
                        I agree to the BudgetBlaze terms and conditions
                      </label>
                    </div>
                  </div>
                  <button
                    disabled={loader}
                    className="bg-customRed font-semibold text-[#363535]  bg-otp-gradient w-full mx-1 py-2 transition-colors duration-100 rounded-md my-3"
                    onClick={handleOTP}
                  >
                     Generate OTP
                  </button>
                  <button
                    disabled={loader}
                    type="submit"
                    className="bg-customRed font-semibold text-[#363535]  bg-core-theme-gradient w-full py-2 transition-colors duration-100 mx-1 rounded-md my-3"
                  >
                    {loader ? "Loading..." : "Verify OTP"}
                  </button>
                  <p className="text-center text-sm text-slate-700 mt-3">
                    Already have an account?
                    <span> </span>
                    <Link
                      className="font-semibold underline hover:text-black"
                      to="/login"
                    >
                      <span className="hover:text-red-500">Sign In</span>
                    </Link>
                  </p>
                </>
              )}
            </>
          )}
          {currentStep === 2 && (
            <div className="flex items-center justify-center flex-col">
              <h3 className="text-center font-serif text-log_reg_headerColor font-medium lg:text-2xl text-xl">
                User Account has been successfully created! Thanks for joining
                us. Start Blazing.
              </h3>
              <p className="mt-3"> Here is your username : Placeholder</p>
              <button
                disabled={loader}
                type="submit"
                className="bg-customRed font-semibold text-[#363535]  bg-core-theme-gradient w-1/2 my-7 py-2 transition-colors duration-100 rounded-md"
              >
                Go to Login Page
              </button>
            </div>
          )}
        </form>
      </div>
    </div>
  );
};

export default RegisterPage;
