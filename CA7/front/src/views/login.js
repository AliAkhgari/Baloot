import React, {useEffect, useState} from "react";
import {toast, ToastContainer} from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "../styles/login_signup.css";
import Authentication from "../api/authentication.js";
import AuthenticationService from "../api/authentication.js";
import logo from "../assets/images/logo.png";

const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    useEffect(() => {
        if (AuthenticationService.getUserJWT() != null) {
            window.location.replace("/");
        }
    }, []);

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
                    <a href='https://github.com/login/oauth/authorize?client_id=4e1a1049c6dea3c2480a&scope=user'>
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" width="40" height="40">
                            <path fillRule="evenodd"
                                  d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82.64-.18 1.32-.27 2-.27.68 0 1.36.09 2 .27 1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.013 8.013 0 0016 8c0-4.42-3.58-8-8-8z"></path>
                        </svg>
                    </a>
                </div>
            </form>
        </div>
    );
};

export default Login;
