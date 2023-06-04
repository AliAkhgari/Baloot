import axios from "./base.js";
import AuthenticationService from "./authentication";

export function getBuyList(username) {
    return axios.post(`/buy-list`, {username: username},
        {headers: {Authorization: AuthenticationService.getAuthenticationHeader()}});
}

export function addToBuyList(username, id) {
    return axios.post(`/buy-list/add`, {username: username, id: id},
        {headers: {Authorization: AuthenticationService.getAuthenticationHeader()}});
}

export function removeFromBuyList(username, id) {
    return axios.post(`/buy-list/remove`, {username: username, id: id},
        {headers: {Authorization: AuthenticationService.getAuthenticationHeader()}});
}

export function getPurchasedList(username) {
    return axios.post(`/purchased-list`, {username: username},
        {headers: {Authorization: AuthenticationService.getAuthenticationHeader()}});
}

export function purchaseBuyList(username) {
    return axios.post(`/buy-list/purchase`, {username: username},
        {headers: {Authorization: AuthenticationService.getAuthenticationHeader()}});
}

export function applyDiscount(id, username) {
    return axios.post(`/buy-list/discount/${id}`, {username: username},
        {headers: {Authorization: AuthenticationService.getAuthenticationHeader()}});
}
