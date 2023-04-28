import React, {useState} from "react";
import {toast, ToastContainer} from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "../styles/login_signup.css";
import {loginForm} from "../api/login.js";
import logo from "../assets/images/logo.png";

const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const handleLogin = (e) => {
        e.preventDefault();
        loginForm(username, password)
            .then((data) => {
                sessionStorage.setItem('username', username);
                toast.success(data.data);
                setTimeout(() => {
                    window.location.replace("/");
                }, 2000);
            })
            .catch((error) => {
                toast.error(error.response.data);
            });
    };

    return (
        <div className={"login-container"}>
            <ToastContainer/>
            <div className={"logo-container"}>
                <img src={logo} alt={"logo"}/>
                <h1>Baloot Store</h1>
            </div>
            <form className={"login-form"} onSubmit={handleLogin}>
                <label htmlFor={"username"}>Username</label>
                <input
                    type={"text"}
                    id={"username"}
                    name={"username"}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                />
                <label htmlFor={"password"}>Password</label>
                <input
                    type={"password"}
                    id={"password"}
                    name={"password"}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <button type={"submit"}>Login</button>
            </form>
        </div>
    );
};

export default Login;
