import axios from "./base.js";
import AuthenticationService from "./authentication";

export function getUserById(id) {
    return axios.get(`/users/${id}`,
        {headers: {Authorization: AuthenticationService.getAuthenticationHeader()}});
}

export function addUserCredit(id, credit) {
    return axios.post(`/users/${id}/credit`, {credit: credit},
        {headers: {Authorization: AuthenticationService.getAuthenticationHeader()}})
}
