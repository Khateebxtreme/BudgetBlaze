import React, { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import TextField from "./TextField";
import moment from "moment";
import { Link, useNavigate } from "react-router-dom";
import Dropdowns from "./SubComponents/Dropdowns";
import { useStoreContext } from "../contextApi/ContextAPI";
import api from "../Api/api";

const Profile = () => {
  const navigate = useNavigate();
  const [loginSession, setLoginSession] = useState(null);
  const { token, setToken, user } = useStoreContext();
  const [formData, setFormData] = useState({
    username: user,
    fullname: "Pengu Gonzalez",
    email: "pengu@example.com",
    contactDetails: " XXX XXX XXXX",
    monthlyIncome: 0,
    currency: "INR",
    reminders: false,
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await api.get(
          `http://localhost:8080/UserService/profile/${user}`
        );

        const result = response.data.User;

        setFormData((current_user) => ({
          ...current_user,
          username: result.username,
          fullname: result.fullname,
          email: result.email,
          contactDetails: result.contactDetails,
          currency: result.currency,
          monthlyIncome: result.monthly_income,
          reminders: result.rec_rem,
        }));

        console.log(result);
      } catch (error) {
        console.log("An error occurred", error);
        reset();
        navigate("/error");
      } finally {
      }
    };

    fetchData();
  }, []);

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

  useEffect(() => {
    const lastLoginSession = moment
      .unix(Date.now() / 1000)
      .format("dddd, D MMMM YYYY, h:mm A");
    //set the loggin session from the token
    setLoginSession(lastLoginSession);
  }, [token]);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const handlePhone = () => {
    if (formData.contactDetails !== null) {
      return formData.contactDetails + "";
    } else {
      return "+91 XXX XXX XXXX";
    }
  };

  const handleSave = async (data) => {
    try {
      console.log(formData);
      const res = await api.post(
        `http://localhost:8080/UserService/updateProfile/${user}`,
        {
          email: data.email || formData.email,
          contactDetails: data.phone || formData.contactDetails,
          fullname: data.fullname || formData.fullname,
          currency: formData.currency,
          monthly_income: formData.monthlyIncome,
          rec_rem: formData.reminders,
        }
      );
      console.log(res.data);
    } catch (error) {
      console.log(error);
      navigate("/error");
    }
  };

  return (
    <div className="mt-5 min-h-[calc(100vh-10vh)] p-5 items-center justify-center flex overflow-hidden">
      <div className="sm:w-[50%] w-[90%] rounded-xl overflow-hidden shadow-xl bg-profile-component-gradient border border-gray-300 pt-4 pb-10 ">
        <div className="max-w-3xl mx-auto px-6">
          {/* Form Heading */}
          <h2 className="text-center flex items-center justify-center text-gray-700 font-semibold lg:text-4xl text-3xl font-roboto mt-2">
            Profile Settings
          </h2>
          {/* User Avatar and Details*/}
          <div className="flex items-center justify-center mt-4 mb-6">
            <img
              src="/images/Planning_Vid.gif"
              alt="Avatar"
              className="w-20 h-23 rounded-full mr-2 shadow-md"
            />
            <div>
              <h3 className="text-xl font-semibold font-lato">
                {formData.username}
              </h3>
              <p className="text-gray-600 font-lato">{formData.email}</p>
            </div>
          </div>
          {/* Displays Last Login Session of the user */}
          <div className="py-2 bg-profile-login-session-gradient shadow-md shadow-gray-300 border border-gray-300 mx-3 rounded-lg flex items-center justify-start">
            <h3 className="text-slate-800 px-2 text-md font-semibold flex items-center justify-start">
              Last Login Session :
            </h3>
            <p className="text-slate-700 text-md pl-1">
              <span>{loginSession}</span>
            </p>
          </div>
          <hr className="mt-2 mb-3 border" />
          {/* Form Inputs -> User Details */}
          <form
            className="space-y-3 flex flex-col px-4"
            onSubmit={handleSubmit(handleSave)}
          >
            <TextField
              label="Full Name"
              id="fullname"
              type="text"
              message="*FullName is required"
              placeholder={formData.fullname}
              register={register}
              errors={errors}
            />

            <TextField
              label="Email"
              id="email"
              type="email"
              message="*Email is Required"
              placeholder={formData.email}
              register={register}
              errors={errors}
            />

            <TextField
              label="Contact Details"
              id="phone"
              type="phone"
              message="*Phone Number is Required"
              placeholder={handlePhone()}
              register={register}
              errors={errors}
            />

            <div className="transition-all duration-1000 ease-in-out flex-col flex">
            <label className="block font-medium">Currency</label>
              <select id="currency" name="currency" className="px-2 py-2 mt-2 border border-slate-600 outline-none bg-transparent w-full text-slate-700 rounded-md"
              value={formData.currency} onChange={handleChange}>
                <option value="INR">INR - Indian Rupee</option>
                <option value="USD">USD - US Dollar</option>
                <option value="EUR">EUR - Euro</option>
                <option value="JPY">JPY - Japanese Yen</option>
                <option value="CAD">CAD - Canadian Dollar</option>
              </select>
            </div>

            <div>
              <label className="block font-medium">Monthly Income</label>
              <input
                type="number"
                name="monthlyIncome"
                value={formData.monthlyIncome || ""}
                onChange={handleChange}
                className="px-2 py-2 mt-2 border border-slate-600 outline-none bg-transparent w-full text-slate-700 rounded-md"
              />
            </div>

            <div className="flex items-center">
              <input
                type="checkbox"
                name="reminders"
                checked={formData.reminders}
                onChange={handleChange}
                className="mr-2"
              />
              <label>Enable Recurring Reminders</label>
            </div>

            <button
              type="submit"
              className="bg-profile-button-gradient text-white font-semibold px-4 py-2 rounded-lg"
            >
              Save Changes
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Profile;
