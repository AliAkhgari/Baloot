import React from 'react';
import {Navigate} from "react-router-dom";

const withAuth = (Component) => {
    return (props) => {
        const username = localStorage.getItem("username");
        const isAuthenticated = !!username;

        if (isAuthenticated) {
            return <Component {...props} />;
        }
        return <Navigate replace to="/login" />;

    };
};

export default withAuth;