import Form_Stepper from "./SubComponents/Form_Stepper";
import React, { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import TextField from "./TextField";
import { Link, useNavigate } from "react-router-dom";
import toast from "react-hot-toast";
import api from "../Api/api";

const RegisterPage = () => {
  const navigate = useNavigate();
  const [loader, setLoader] = useState(false); //usage for deployment time
  const methodSelect = "email";
  const [currentStep, setCurrentStep] = useState(0);
  const [termsChecked, setTermsChecked] = useState(false);
  const [userData, setUserData] = useState(null);
  const [isDisabled, setIsDisabled] = useState(false);
  const [timer, setTimer] = useState(0);
  const [username, setUsername] = useState(null);
  const [otp, setOtp] = useState(null);

  const TIMER_DURATION = 180; // 3 minutes
  const STORAGE_KEY = "otp_expiry_time";

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

  const formatTime = (seconds) => {
    //handles format of time (displayed on Generate OTP button once the request is fired.)
    const mins = String(Math.floor(seconds / 60)).padStart(2, "0");
    const secs = String(seconds % 60).padStart(2, "0");
    return `${mins}:${secs}`;
  };

  const handleOTP = async () => {
    const expiryTime = Date.now() + TIMER_DURATION * 1000;
    localStorage.setItem(STORAGE_KEY, expiryTime.toString()); //setting up the key-value pair in local storage to keep track of when generate OTP button is disabled and enabled.

    setIsDisabled(true); //disabling the generate OTP button
    setTimer(TIMER_DURATION); //to keep track of counter that is shown on the button
    try {
      console.log(userData);
      const response = await api.post("http://localhost:8080/UserService/requestOTP", {
        functionType: "Reg",
        email: userData.email,
      })
      setOtp(response.data.OTP || null)
      console.log(response);
      toast.success("OTP is now generated", { duration: 3000 });
    } catch (error) {
      console.error(error);
      toast.error("OTP couldn't be generated");
      reset();
    } finally {
    }
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
        toast.error("Registration Failed");
      } finally {
        reset();
      }
    } else if (currentStep == 1 && termsChecked) {
      try {
        //api call to verify OTP of the user
        console.log(otp)
        const response = await api.post("http://localhost:8080/UserService/validateOTP", {
        functionType: "Reg",
        email: userData.email,
        otp: otp,
      });
      setUsername(response.data.UniqueId)
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

  useEffect(() => {
    //basically used at time of page refresh to limit the number of API calls.
    const savedExpiry = localStorage.getItem(STORAGE_KEY); //get the time set for the expiry of generate OTP button
    if (savedExpiry) {
      //if token exists -> to keep track if the Generate OTP button is clicked or not.
      const remaining = Math.floor((Number(savedExpiry) - Date.now()) / 1000);
      if (remaining > 0) {
        //if there is any positive time diff - Exp_time - current time then update the timer and button remains disabled
        setIsDisabled(true);
        setTimer(remaining);
      } else {
        localStorage.removeItem(STORAGE_KEY); // Once the expiry time is < Date.now(), we are cleaning up the key-value pair stored in local storage.
      }
    }
  }, []);

    useEffect(() => {
      //setting up interval to update the timer on the Generate OTP button.
    let interval;
    if (isDisabled && timer > 0) {
      interval = setInterval(() => {
        setTimer((prev) => {
          const newTime = prev - 1;
          if (newTime <= 0) {
            setIsDisabled(false);
            localStorage.removeItem(STORAGE_KEY);
            clearInterval(interval);
          }
          return newTime;
        });
      }, 1000);
    }
    return () => clearInterval(interval);
  }, [isDisabled, timer]);

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
          {/* Step 1 -> Registering a User based on their email and set password. */}
          {currentStep === 0 ? (
            <>
              <h2 className="text-center font-serif text-log_reg_headerColor font-medium lg:text-4xl text-3xl">
                Register User
              </h2>
              <hr className="mt-2 mb-3 border" />
              <div className="flex flex-col gap-3">
                <TextField
                  label="Email"
                  required
                  id="email"
                  type="email"
                  message="*Email is Required"
                  placeholder="Please Enter your Email"
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
              {/* Step 2 -> Once the user is logged in, We can generate and verify OTP at this step. */}
              {currentStep === 1 && (
                <>
                  <h2 className="text-center font-serif font-medium lg:text-4xl text-3xl">
                    OTP Verification
                  </h2>
                  <hr className="mt-2 mb-3 border" />
                  <p className="text-red-600 font-semibold flex items-center justify-center py-3">Warning : Please do not refresh the page to avoid any issue in the User registration process. Usernames to login are provided at the end of the verification process.</p>
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
                      <label className="pl-1 pb-2">
                        I agree to the BudgetBlaze terms and conditions
                      </label>
                    </div>
                  </div>
                  {/* Generate and Verify OTP buttons */}
                  <button
                    disabled={loader || isDisabled}
                    className="bg-customRed font-semibold text-[#363535]  bg-otp-button-gradient w-full mx-1 py-2 transition-colors duration-100 rounded-md my-3"
                    onClick={handleOTP}
                  >
                    {isDisabled ? `Wait - ${formatTime(timer)}` : "Generate OTP"}
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
          {/* Step 3 -> Once the OTP is verified, we provide the user with his username for login */}
          {currentStep === 2 && (
            <div className="flex items-center justify-center flex-col">
              <h3 className="text-center font-serif text-log_reg_headerColor font-medium lg:text-2xl text-xl">
                User Account has been successfully created! Thanks for joining
                us. Start Blazing.
              </h3>
              <p className="mt-3 font-semibold"> Here is your username : {username}</p>
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
