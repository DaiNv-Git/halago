import client from "./client";
import { BASE_API_URL } from "utils/constants";

const URL_PREFIX = "/";
const URL_API = BASE_API_URL + URL_PREFIX;

const WS_CODE_BRAND_INSERT = "insertBrand";
const WS_CODE_BRAND_INSERT_P = "insertBrandPortal";
const WS_CODE_BRAND_LIST = "getListBrand";
const WS_CODE_BRAND_FIND = "findBrandById";
const WS_CODE_BRAND_UPDATE = "updateBrand";
const WS_CODE_BRAND_DELETE = "deleteBrand";

export function insertBrand(request) {
  return client.post(URL_API + WS_CODE_BRAND_INSERT, {
    wsCode: WS_CODE_BRAND_INSERT,
    wsRequest: request,
  });
}

export function insertBrandPortal(request) {
  return client.post(URL_API + WS_CODE_BRAND_INSERT_P, {
    wsCode: WS_CODE_BRAND_INSERT_P,
    wsRequest: request,
  });
}

export function getListBrand(request) {
  return client.post(URL_API + WS_CODE_BRAND_LIST, {
    wsCode: WS_CODE_BRAND_LIST,
    wsRequest: request,
  });
}

export function findBrandById(request) {
  return client.post(URL_API + WS_CODE_BRAND_FIND, {
    wsCode: WS_CODE_BRAND_FIND,
    wsRequest: request,
  });
}

export function updateBrand(request) {
  return client.post(URL_API + WS_CODE_BRAND_UPDATE, {
    wsCode: WS_CODE_BRAND_UPDATE,
    wsRequest: request,
  });
}

export function deleteBrand(request) {
  return client.post(URL_API + WS_CODE_BRAND_DELETE, {
    wsCode: WS_CODE_BRAND_DELETE,
    wsRequest: request,
  });
}
