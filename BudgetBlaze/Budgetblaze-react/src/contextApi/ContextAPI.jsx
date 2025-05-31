import React, { createContext, useContext, useState } from 'react'

const ContextAPI = createContext();

export const ContextProvider = ({children})=>{
  const getToken = localStorage.getItem("JWT_TOKEN") ? JSON.parse(localStorage.getItem("JWT_TOKEN")) : null; //retrieving JWT from local storage that is set up login of an authenticated user.

  const getUser = localStorage.getItem("USER")!==null ? localStorage.getItem("USER") : null;

  const [token, setToken] = useState(getToken);
  const [user, setUser] = useState(getUser);

  //token data we will be sharing across the application as it is used for requests that are available for authenticated users
  const sendData = {
    token, setToken, user, setUser
  }

  return <ContextAPI.Provider value={sendData}>{children}</ContextAPI.Provider>
}

export const useStoreContext = () => {
  const context = useContext(ContextAPI);
  return context;
}