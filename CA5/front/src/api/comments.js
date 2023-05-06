import axios from "./base.js";

export function likeComment(id, username) {
    return axios.post(`/comment/${id}/like`, {username: username});
}

export function dislikeComment(id, username) {
    return axios.post(`/comment/${id}/dislike`, {username: username});
}