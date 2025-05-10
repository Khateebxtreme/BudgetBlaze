import React from 'react'
import { FaGithub, FaLinkedin } from "react-icons/fa";
import { FaXTwitter } from "react-icons/fa6";

const Footer = () => {
  return (
    <footer className="bg-footer-gradient text-white py-10 mt-0 z-40 relative align-bottom w-full">
    <div className="mx-auto px-10 xl:px-14 flex flex-col lg:flex-row lg:justify-between items-center gap-5">
      <div className="text-center lg:text-left">
        <h2 className="text-3xl font-bold mb-2 2xl:text-4xl justify-center font-bebas">BudgetBlaze</h2>
      </div>
      <div className="md:flex-row text-white flex flex-col 2xl:text-xl px-2 2xl:gap-24 lg:gap-10 gap-5 mt-4 lg:mt-0">
        <a href="#" className="hover:text-cyan-100 lg:px-2 font-[500]">FAQ</a>
        <a href="#" className="hover:text-cyan-100 lg:px-2 font-[500]">Privacy Policy</a>
        <a href="#" className="hover:text-cyan-100 lg:px-2 font-[500]">Features</a>
        <a href="#" className="hover:text-cyan-100 lg:px-2 font-[500]">Contact Us</a>
      </div>
      <div className="flex space-x-5 mt-4 lg:mt-0 2xl:gap-5">
        <a href="#" className="hover:text-cyan-200">
          <FaLinkedin size={28} />
        </a>
        <a href="#" className="hover:text-cyan-200">
          <FaXTwitter size={28} />
        </a>
        <a href="https://github.com/Khateebxtreme/BudgetBlaze"
        target="_blank" className="hover:text-cyan-200">
          <FaGithub size={28} />
        </a>
      </div>
    </div>
  </footer>
  )
}

export default Footer
