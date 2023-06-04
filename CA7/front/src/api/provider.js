import axios from "./base.js";
import AuthenticationService from "./authentication";

export function getProviderById(id) {
    return axios.get(`/providers/${id}`,
        {headers: {Authorization: AuthenticationService.getAuthenticationHeader()}});
}

export function getAllProvidedCommodities(id) {
    return axios.get(`/providers/${id}/commodities`,
        {headers: {Authorization: AuthenticationService.getAuthenticationHeader()}});
}