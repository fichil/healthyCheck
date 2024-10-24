import Cookies from "js-cookie";
const TokenKey = "enter-token";//作为cookie的名字

export function getToken() {
    return Cookies.get(TokenKey);
}

export function setToken(token){
    Cookies.set(TokenKey,token);
}

export function removeToken(){
    Cookies.remove(TokenKey);
}
