import axios from "./base.js";

export function signupForm(address, birthDate, email, username, password) {
    if (!address)
        throw new Error("Address is required.");

    if (!birthDate)
        throw new Error("Birth date is required.");

    if (!email || !isValidEmail(email))
        throw new Error("Invalid email address.");

    if (!username)
        throw new Error("Username is required.");

    if (!password )
        throw new Error("Password is required.");

    return axios.post(`/signup`, {
        address: address,
        birthDate: birthDate,
        email: email,
        username: username,
        password: password
    });
}

function isValidEmail(email) {
    const emailRegex = /^\S+@\S+\.\S+$/;
    return emailRegex.test(email);
}