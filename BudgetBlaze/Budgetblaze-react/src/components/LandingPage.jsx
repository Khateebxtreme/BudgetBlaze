import React from "react";
import FeatureCard from "./FeatureCard";
import { motion } from "framer-motion";

const LandingPage = () => {
  return (
    <div className="mt-5 min-h-[calc(100vh-10vh)] w-full overflow-x-hidden">
      {/* HomePage Logo and Banner */}
      <section className="mt-3 lg:px-14 sm:px-8 px-4 w-full md:mx-0 mx-auto">
        <div className="md:flex flex md:flex-row flex-col items-center justify-center">
          <img
            className="sm:w-[53%] w-[500px] object-cover rounded-md"
            src="/images/Landing_Logo.jpg"
            alt="Homepage Logo"
          />
          <div className="md:flex block flex-col justify-center items-center">
            <motion.div
              initial={{ opacity: 0, x: 100 }}
              whileInView={{ opacity: 1, x: 0 }}
              transition={{ duration: 0.7 }}
            >
              <h1 className="font-[800] font-roboto text-slate-800 2xl:text-8xl md:text-5xl sm:text-4xl text-3xl  md:leading-[55px] sm:leading-[45px] leading-10 w-full md:w-full lg:w-full">
                Track smarter. Budget better. Live freer.
              </h1>
              <p className="block text-slate-700 text-lg 2xl:mt-12 mt-3 mb-5 font-[400] font-lato 2xl:text-3xl">
                Stay in control of your finances with BudgetBlaze — the
                intuitive, AI-powered expense tracker and budgeting tool that
                helps you save more, spend smarter, and reach your financial
                goals with confidence.
              </p>
            </motion.div>
          </div>
        </div>
      </section>
      {/* Super-Feature Section */}
      <section className="mt-7 lg:px-8 sm:px-8 px-4 w-full mx-0 flex-row">
        <div className="flex-col flex items-center justify-center md:flex-row">
          <div className="md:flex block flex-col justify-center items-center">
            <motion.div
              initial={{ opacity: 0, x: -100 }}
              whileInView={{ opacity: 1, x: 0 }}
              transition={{ duration: 0.7 }}
            >
              <h1 className="font-semibold font-roboto text-slate-800 2xl:text-6xl md:text-4xl sm:text-2xl text-xl  md:leading-[45px] sm:leading-[35px] leading-10 w-full md:w-full lg:w-full">
                Plan Ahead with Recurring Bills & Goals
              </h1>
              <p className="block text-slate-500 2xl:text-3xl text-lg 2xl:mt-10 mt-2 mb-3 font-[400] font-lato">
                Take the stress out of managing money. With our smart budget
                tracker, you can easily set up recurring bills, create savings
                goals, and let the app do the rest. It learns your spending
                habits and gives you tips to use your money smarter. You'll get
                helpful insights, automatic tracking, and a clear view of where
                your money's going—so you can plan ahead and stay on top of your
                finances without the hassle.
              </p>
              <ul className="block text-slate-500 2xl:text-3xl text-lg 2xl:mt-10 mt-2 mb-3 font-[400] font-lato list-disc ml-12 space-y-2">
                <li>📅 Auto-schedule your regular bills and expenses</li>
                <li>🎯 Set and track custom savings goals</li>
                <li> 💡 Smart suggestions based
                on your spending habits</li>  <li>🔔 Stay
                prepared with helpful reminders</li>
                <li>📈 Real-time progress tracking</li>
              </ul>
            </motion.div>
          </div>
          <img
            className="sm:w-[40%] w-[400px] object-cover rounded-lg"
            src="/images/planning.jpg"
            alt="Super feature Logo"
          />
        </div>
      </section>
      {/* Features Section */}
      <section className=" px-4 my-2 bg-[#ffffff] w-full">
        <h1 className="font-semibold font-roboto text-slate-600 justify-center items-center flex pt-10 md:leading-[45px] sm:leading-[25px] leading-10 text-2xl md:text-3xl lg:text-4xl 2xl:text-6xl 2xl:pb-10">
          Smart tools. Smarter money decisions.
        </h1>
        <div className="pt-4 pb-7 sm:pl-3 grid gap-7 2xl:grid-cols-4 lg:grid-cols-3 sm:grid-cols-2 grid-cols-1 mt-4 place-items-center 2xl:flex 2xl:items-center 2xl:justify-center 2xl:flex-wrap">
          <FeatureCard
            title={"Effortless Expense Tracking"}
            desc={
              "Quickly log every transaction or let the app do it for you. Categorize spending, set recurring expenses, and keep tabs on where your money goes—no guesswork needed."
            }
            imageUrl={"/images/expense_tracking.jpg"}
          />
          <FeatureCard
            title={"Smart Budget Management"}
            desc={
              "Build custom budgets for what matters—like groceries, rent, or weekend fun. Get real-time feedback and alerts to help you stay on track and make confident money moves."
            }
            imageUrl={"/images/budget_management.jpg"}
          />
          <FeatureCard
            title={"Insightful Spending Analytics"}
            desc={
              "Instantly visualize your cash flow, track category trends, and discover spending patterns with clean, interactive dashboards making budgeting feel effortless."
            }
            imageUrl={"/images/analytics.jpg"}
          />
          <FeatureCard
            title={"Safe, Secure & Always in Sync"}
            desc={
              "Your data is protected with bank-level encryption. Biometric logins, cloud backup, and seamless multi-device access mean your finances are secure and always within reach."
            }
            imageUrl={"/images/security.jpg"}
          />
          <FeatureCard
            title={"Multi-Account Management"}
            desc={
              "Connect and manage all your accounts in one place—cash, credit cards, bank accounts, and more. See your full financial picture instantly. Stay on top of your spending with real-time insights and seamless account syncing."
            }
            imageUrl={"/images/multi_account.jpg"}
          />
          <FeatureCard
            title={"Export & Download Reports"}
            desc={
              "Download your financial data in PDF, Excel, or CSV formats—perfect for backups, sharing with accountants, or detailed analysis. Customize the date range or filter by category to get exactly the report you need."
            }
            imageUrl={"/images/export.jpg"}
          />
        </div>
      </section>
    </div>
  );
};

export default LandingPage;
