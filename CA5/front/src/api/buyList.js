import axios from "./base.js";

export function getBuyList(username) {
    return axios.post(`/buy-list`, {username: username});
}

export function addToBuyList(username, id) {
    return axios.post(`/buy-list/add`, {username: username, id: id});
}

export function removeFromBuyList(username, id) {
    return axios.post(`/buy-list/remove`, {username: username, id: id});
}

export function getPurchasedList(username) {
    return axios.post(`/purchased-list`, {username: username});
}

export function purchaseBuyList(username) {
    return axios.post(`/buy-list/purchase`, {username: username});
}

// export function getDiscount(id) {
//     return axios.get(`/buy-list/discount/${id}`);
// }

export function applyDiscount(id, username) {
    return axios.post(`/buy-list/discount/${id}`, {username: username});
}
