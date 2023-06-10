import axios from "./base";

function isValidEmail(email) {
    const emailRegex = /^\S+@\S+\.\S+$/;
    return emailRegex.test(email);
}

export default class Authentication {
    static getUserJWT() {
        return localStorage.getItem("userJWT");
    }

    static getUsername() {
        return localStorage.getItem("username");
    }

    static setUser(userJWT, username) {
        localStorage.setItem("userJWT", userJWT);
        localStorage.setItem("username", username);
    }

    static getAuthenticationHeader() {
        return this.getUserJWT();
    }

    static signupForm(address, birthDate, email, username, password) {
        if (!email || !isValidEmail(email))
            throw new Error("Invalid email address.");

        return axios.post(`/signup`, {
            address: address,
            birthDate: birthDate,
            email: email,
            username: username,
            password: password
        });
    }

    static loginForm(username, password) {
        return axios.post(`/login`, {"username": username, "password": password});
    }

    static logout() {
        localStorage.removeItem("userJWT");
        localStorage.removeItem("username");
    }

    static callback(code) {
        return axios.get("/callback", {params: code});
    }
}