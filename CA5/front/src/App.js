import React from 'react';
import Login from './views/login.js'
import "react-toastify/dist/ReactToastify.css";

import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import Header from "./components/header/header.js";
import Home from "./views/home.js";
import Logout from "./views/logout.js";

function App() {
    return (
        <Router>
            <Routes>
                <Route path={"/"} element={<div><Header/><Home/></div>}/>
                <Route path={"/login"} element={<Login/>}/>
                <Route path={"/logout"} element={<Logout/>}/>
            </Routes>
        </Router>
    );
};
export default App;

