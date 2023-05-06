import React from 'react';
import Login from './views/login.js'
import "react-toastify/dist/ReactToastify.css";
import {Provider} from 'react-redux';
import store, {persistor} from './components/cartItemCount.js';


import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import Home from "./views/home.js";
import Logout from "./views/logout.js";
import Signup from "./views/signup.js";
import User from "./views/user.js";
import Product from "./views/product.js";
import { PersistGate } from 'redux-persist/es/integration/react';

function App() {
    return (
        <Provider store={store}>
            <PersistGate loading={null} persistor={persistor}>
                <Router>
                    <Routes>
                        <Route path={"/"} element={<Home/>}/>
                        <Route path={"/login"} element={<Login/>}/>
                        <Route path={"/logout"} element={<Logout/>}/>
                        <Route path={"/signup"} element={<Signup/>}/>
                        <Route path={"/user"} element={<User/>}/>
                        <Route path={"/product/:id"} element={<Product/>}/>
                    </Routes>
                </Router>
            </PersistGate>
        </Provider>
    );
};
export default App;

