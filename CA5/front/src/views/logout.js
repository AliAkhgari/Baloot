import React, {useEffect} from "react";
import {toast, ToastContainer} from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import {useDispatch} from "react-redux";

const Logout = () => {
    const dispatch = useDispatch();
    const resetCart = () => ({
        type: 'cart/reset',
    });

    useEffect(() => {
        sessionStorage.clear();
        dispatch(resetCart());

        toast.success("You have been logged out!");

        setTimeout(() => {
            window.location.replace("/login");
        }, 2000);
    }, []);

    return <ToastContainer/>;
};

export default Logout;
