import React, {useEffect} from "react";
import {toast, ToastContainer} from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const Logout = () => {
    useEffect(() => {
        sessionStorage.removeItem("username");
        toast.success("You have been logged out!");

        setTimeout(() => {
            window.location.replace("/");
        }, 2000);
    }, []);

    return <ToastContainer/>;
};

export default Logout;
