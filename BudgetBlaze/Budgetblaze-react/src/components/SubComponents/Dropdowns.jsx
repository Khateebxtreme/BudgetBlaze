import {
  Listbox,
  ListboxButton,
  ListboxOption,
  ListboxOptions,
} from "@headlessui/react";
import clsx from "clsx";
import { useState } from "react";

//Currency Dropdown component in Profile section
const currencies = [
  { id: "INR", name: "INR - Indian Rupee" },
  { id: "USD", name: "USD - US Dollar" },
  { id: "EUR", name: "EUR - Euro" },
  { id: "JPY", name: "JPY - Japanese Yen" },
  { id: "CAD", name: "CAD - Canadian Dollar" },
];

const Dropdowns = ({selected, setSelected}) => {
  return (
    <div className="mx-auto w-full">
      <Listbox value={selected} onChange={setSelected}>
        <div className="relative">
          <Listbox.Label className="block font-medium text-black mb-1">
            Currency
          </Listbox.Label>
          <ListboxButton
            className={clsx(
              "w-full relative rounded-lg border border-slate-700 bg-transparent py-2 px-2 text-left font-normal text-black",
              "focus:not-data-focus data-focus:outline-2"
            )}
          >
            {selected.name}
          </ListboxButton>
          <ListboxOptions
            transition
            className={clsx(
              "w-full absolute rounded-xl border border-gray-500 bg-[#F8F8FF] focus:outline-none z-10",
              "transition duration-100 ease-in data-leave:data-closed:opacity-0"
            )}
          >
            {currencies.map((currency) => (
              <ListboxOption
                key={currency.name}
                value={currency}
                className="group flex cursor-pointer gap-2 px-3 py-1 select-none hover:bg-profile-currency-dropdown-gradient hover:rounded-xl"
              >
                <div className="text-md text-black">{currency.name}</div>
              </ListboxOption>
            ))}
          </ListboxOptions>
        </div>
      </Listbox>
    </div>
  );
};

export default Dropdowns;
