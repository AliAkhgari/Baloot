import axios from "./base.js";
import AuthenticationService from "./authentication";

export function getCommodities() {
    return axios.get(`/commodities`,
        {headers: {Authorization: AuthenticationService.getAuthenticationHeader()}});
}

export function getCommodityById(id) {
    return axios.get(`commodities/${id}`,
        {headers: {Authorization: AuthenticationService.getAuthenticationHeader()}})
}

export function rateCommodity(id, rate, username) {
    return axios.post(`commodities/${id}/rate`, {rate: rate, username: username},
        {headers: {Authorization: AuthenticationService.getAuthenticationHeader()}})
}

export function searchCommodities(searchOption, searchValue) {
    return axios.post(`commodities/search`, {searchOption: searchOption, searchValue: searchValue},
        {headers: {Authorization: AuthenticationService.getAuthenticationHeader()}})
}

export function addComment(id, username, comment) {
    return axios.post(`commodities/${id}/comment`, {username: username, comment: comment},
        {headers: {Authorization: AuthenticationService.getAuthenticationHeader()}})
}

export function getComments(id) {
    return axios.get(`commodities/${id}/comment`,
        {headers: {Authorization: AuthenticationService.getAuthenticationHeader()}})
}

export function getSuggestedCommodities(id) {
    return axios.get(`/commodities/${id}/suggested`,
        {headers: {Authorization: AuthenticationService.getAuthenticationHeader()}})
}
