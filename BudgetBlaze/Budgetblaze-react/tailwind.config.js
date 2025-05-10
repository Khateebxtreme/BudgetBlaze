/** @type {import('tailwindcss').Config} */
 export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}", "./components/**/*.{js,ts,jsx,tsx}",],
  theme: {
    extend: {
      backgroundImage:{
        "core-theme-gradient" : "linear-gradient(to left, #a5c9fc, #7da7f8)",
        "footer-gradient" : "linear-gradient(to right, #4a4c4d, #6b6d6e)",
        "otp-gradient" : "linear-gradient(to right, #a8e6a1, #6fcf97)",
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
  plugins: [],
}
