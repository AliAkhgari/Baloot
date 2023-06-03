import axios from "./base.js";

export function getUserById(id) {
    return axios.get(`/users/${id}`);
}

export function addUserCredit(id, credit) {
    return axios.post(`/users/${id}/credit`, {credit: credit})
}
