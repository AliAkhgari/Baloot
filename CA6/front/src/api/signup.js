import axios from "./base.js";

export function signupForm(address, birthDate, email, username, password) {
    return axios.post(`/signup`, {
        address: address,
        birthDate: birthDate,
        email: email,
        username: username,
        password: password
    });
}