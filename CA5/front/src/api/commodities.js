import axios from "./base.js";

export function getCommodities() {
    return axios.get(`/commodities`);
}

export function getCommodityById(id) {
    return axios.get(`commodities/${id}`)
}

export function rateCommodity(id, rate, username) {
    return axios.post(`commodities/${id}/rate`, {rate: rate, username: username})
}

export function searchCommodities(searchOption, searchValue) {
    return axios.post(`commodities/search`, {searchOption: searchOption, searchValue: searchValue})
}
