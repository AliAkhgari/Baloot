import React, {useState} from "react";
import "../styles/login_signup.css"
import logo from "../assets/images/logo.png";
import {toast, ToastContainer} from "react-toastify";
import {signupForm} from "../api/signup.js";

const Signup = () => {
    const [address, setAddress] = useState("");
    const [birth, setBirth] = useState("");
    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const handleSignup = (e) => {
        e.preventDefault();
        try {
            signupForm(address, birth, email, username, password)
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
        } catch (error) {
            toast.error(error.message);
        }
    };


    return (
        <div className="login-container">
            <ToastContainer/>
            <div className="logo-container">
                <img src={logo} alt="Logo"/>
                <h1>Baloot Store</h1>
            </div>
            <form className="login-form" onSubmit={handleSignup}>
                <label htmlFor="address">Address</label>
                <input type="text" id="address" name="address"
                       onChange={(e) => setAddress(e.target.value)} required/>
                <label htmlFor="birth">Birth Date</label>
                <input type="text" id="birth" name="birth"
                       onChange={(e) => setBirth(e.target.value)} required/>
                <label htmlFor="email">Email</label>
                <input type="text" id="email" name="email"
                       onChange={(e) => setEmail(e.target.value)} required/>
                <label htmlFor="username">Username</label>
                <input type="text" id="username" name="username"
                       onChange={(e) => setUsername(e.target.value)} required/>
                <label htmlFor="password">Password</label>
                <input type="password" id="password" name="password"
                       onChange={(e) => setPassword(e.target.value)} required/>
                <button type="submit">Register</button>
            </form>
        </div>
    )
}

export default Signup;