import React from 'react';
import Login from './views/login.js'
import "react-toastify/dist/ReactToastify.css";


import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<Login/>}/>
            </Routes>
        </Router>
    );
};
export default App;

