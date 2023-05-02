import React from 'react';
import Login from './views/login.js'
import "react-toastify/dist/ReactToastify.css";

import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import Header from "./views/header.js";
import Home from "./views/home.js";
import Logout from "./views/logout.js";
import Signup from "./views/signup.js";
import User from "./views/user.js";

function App() {
    return (
        <Router>
            <Routes>
                <Route path={"/"} element={<Home/>}/>
                <Route path={"/login"} element={<Login/>}/>
                <Route path={"/logout"} element={<Logout/>}/>
                <Route path={"/signup"} element={<Signup/>}/>
                <Route path={"/user"} element={<User/>}/>
            </Routes>
        </Router>
    );
};
export default App;

