import React, {useState} from "react";
import {toast, ToastContainer} from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "../styles/login_signup.css";
import Authentication from "../api/authentication.js";
import AuthenticationService from "../api/authentication.js";
import logo from "../assets/images/logo.png";

const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const handleLogin = (e) => {
        e.preventDefault();
        Authentication.loginForm(username, password)
            .then((response) => {
                let userJWT = response.headers.token;
                let username = response.headers.username;
                AuthenticationService.setUser(userJWT, username)

                toast.success(response.data);
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

                <div className="github-oauth">
                    <label>login using github</label>
                    <br/>
                    <a href='https://github.com/login/oauth/authorize?client_id=192c5d086cf97b3a245b&scope=user'>
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" width="40" height="40">
                        </svg>
                    </a>
                </div>
            </form>
        </div>
    );
};

export default Login;
