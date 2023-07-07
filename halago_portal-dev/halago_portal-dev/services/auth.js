import client from "./client";
import { BASE_API_URL } from "utils/constants";

const URL_PREFIX = "/";
const URL_API = BASE_API_URL + URL_PREFIX;

export function login(request) {
  return client.post(URL_API + "login", { wsRequest: request });
}

export function loginPortal(request) {
  return client.post(URL_API + "loginPortal", { wsRequest: request });
}

export function loginFbInfluencer(request) {
  return client.post(URL_API + "loginFbInfluencer", { wsCode: "loginFbInfluencer", wsRequest: request });
}

export function forgotPassword(request) {
  return client.post(URL_API + "forgotPassword", {
    wsCode: "forgotPassword",
    wsRequest: request,
  });
}
