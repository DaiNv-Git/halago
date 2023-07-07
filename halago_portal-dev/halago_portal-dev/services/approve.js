import client from "./client";
import { BASE_API_URL } from "utils/constants";

const URL_PREFIX = "/";
const URL_API = BASE_API_URL + URL_PREFIX;

const WS_CODE_APPROVE_LIST = "getListAppprove";
const WS_CODE_APPROVE_INSERT = "insertApprove";
const WS_CODE_APPROVE = "approveInfluencer";
const WS_CODE_APPROVE_ALL = "approveAll";

export function listApprove(request) {
  return client.post(URL_API + WS_CODE_APPROVE_LIST, {
    wsCode: WS_CODE_APPROVE_LIST,
    wsRequest: request,
  });
}

export function insertApprove(request) {
  return client.post(URL_API + WS_CODE_APPROVE_INSERT, {
    wsCode: WS_CODE_APPROVE_INSERT,
    wsRequest: request,
  });
}

export function approveInfluencer(request) {
  return client.post(URL_API + WS_CODE_APPROVE, {
    wsCode: WS_CODE_APPROVE,
    wsRequest: request,
  });
}

export function approveAll(request) {
  return client.post(URL_API + WS_CODE_APPROVE_ALL, {
    wsCode: WS_CODE_APPROVE_ALL,
    wsRequest: request,
  });
}
