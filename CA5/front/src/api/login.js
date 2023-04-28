import axios from "./base.js";

export function loginForm(username, password) {
    return axios.post(`/login`, {"username": username, "password": password});
}