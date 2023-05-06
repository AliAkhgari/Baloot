import axios from "./base.js";

export function getProviderById(id) {
    return axios.get(`/providers/${id}`);
}

export function getAllProvidedCommodities(id) {
    return axios.get(`/providers/${id}/commodities`);
}