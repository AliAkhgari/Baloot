import axios from "./base.js";

export function getCommodities() {
    return axios.get(`/commodities`);
}

export function getCommodityById(id) {
    return axios.get(`commodities/${id}`)
}

export function rateCommodity(id, rate) {
    return axios.post(`commodities/${id}/rate`, {rate: rate})
}
