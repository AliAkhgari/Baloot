import axios from "./base.js";

export function getProviderById(id) {
    return axios.get(`/providers/${id}`);
}