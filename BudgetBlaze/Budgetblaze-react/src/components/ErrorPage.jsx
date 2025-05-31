import React from 'react'
import { useNavigate } from 'react-router-dom'
import { MdOutlineError } from "react-icons/md";

const ErrorPage = ({message}) => {
  //General page to navigate to if an error, it lets the user know that an error has occurred and suggests him or her to navigate to root page.
  const navigate = useNavigate();
  return (
    <div className="flex flex-col items-center justify-center min-h-[calc(100vh-64px)] bg-[##fdf6f0] p-6 w-full">
    <MdOutlineError className='text-7xl 2xl:text-9xl text-red-500 mb-4' />
    <h1 className='sm:text-4xl 2xl:text-7xl font-bold mb-5 text-gray-800'>
        Oops! Something went wrong.
    </h1>
    <button onClick={() => {
        navigate("/");
    }}
    className='px-4 py-2 bg-sky-400 text-white font-medium rounded-md transition hover:bg-sky-600 2xl:font-semibold 2xl:text-lg 2xl:px-6 2xl:py-4'
    >
        Go back to home
    </button>
</div>
  )
}

export default ErrorPage