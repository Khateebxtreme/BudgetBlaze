/** @type {import('tailwindcss').Config} */
 export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}", "./components/**/*.{js,ts,jsx,tsx}",],
  theme: {
    extend: {
      backgroundImage:{
        "core-theme-gradient" : "linear-gradient(to left, #a5c9fc, #7da7f8)",
        "footer-gradient" : "linear-gradient(to right, #4a4c4d, #6b6d6e)",
        "otp-button-gradient" : "linear-gradient(to right, #a8e6a1, #6fcf97)",
        "profile-button-gradient" : "linear-gradient(to left, #397acc, #1557cc)",
        "profile-component-gradient" : "linear-gradient(135deg, #fde6f3, #d9e9fb)",
        "profile-login-session-gradient" : "linear-gradient(135deg, #dbeafe, #ede9fe)",
        "profile-currency-dropdown-gradient" : "linear-gradient(135deg, #fff1f7, #e6f3ff)",
        "profile-Nav-dropdown-gradient" : "linear-gradient(135deg, #f7dfe9, #e3effa)",
        "logout-gradient" : "linear-gradient(to right, #fca5a5, #f87171, #ef4444)",
        "profile-component-gradient-dark":"linear-gradient(135deg, #e4cbd9, #bfd2e2)"
      },
      colors: {
        log_reg_headerColor: "#2E3A59",
      },
      fontFamily: {
        roboto: ["Roboto", "sans-serif"],
        bebas: ["Bebas Neue", "sans-serif"],
        lato:["Lato", "sans-serif"],
      },
    },
  },
  variants: {
    extend: {
      backgroundImage: ["responsive"],
    },
  },
  plugins: [
  ],
}
