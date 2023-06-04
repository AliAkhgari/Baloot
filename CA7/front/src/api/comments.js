import axios from "./base.js";
import AuthenticationService from "./authentication";

export function likeComment(id, username) {
    return axios.post(`/comment/${id}/like`, {username: username},
        {headers: {Authorization: AuthenticationService.getAuthenticationHeader()}});
}

export function dislikeComment(id, username) {
    return axios.post(`/comment/${id}/dislike`, {username: username},
        {headers: {Authorization: AuthenticationService.getAuthenticationHeader()}});
}