import client from "./client";
import { BASE_API_URL } from "utils/constants";

const URL_PREFIX = "/";
const URL_API = BASE_API_URL + URL_PREFIX;

const WS_CODE_CITY = "listCity";
const WS_CODE_CAREER = "listCareer";
const WS_CODE_CHANNEL = "listChannel";
const WS_CODE_TYPES = "listTypesInteraction";
const WS_CODE_INDUSTRY = "listIndustry";
const WS_CODE_UPLOAD = "uploadFile";
const WS_CODE_CONTACT = "insertContactCustomer";

export function listCity(request) {
  return client.post(URL_API + WS_CODE_CITY, { wsCode: WS_CODE_CITY, wsRequest: request });
}

export function listCareer(request) {
  return client.post(URL_API + WS_CODE_CAREER, { wsCode: WS_CODE_CAREER, wsRequest: request });
}

export function listChannel(request) {
  return client.post(URL_API + WS_CODE_CHANNEL, { wsCode: WS_CODE_CHANNEL, wsRequest: request });
}

export function listTypesInteraction(request) {
  return client.post(URL_API + WS_CODE_TYPES, { wsCode: WS_CODE_TYPES, wsRequest: request });
}

export function listIndustry(request) {
  return client.post(URL_API + WS_CODE_INDUSTRY, { wsCode: WS_CODE_INDUSTRY, wsRequest: request });
}

export function uploadFile(request) {
  return client.post(URL_API + WS_CODE_UPLOAD, {
    wsCode: WS_CODE_UPLOAD,
    wsRequest: request,
  });
}

export function insertContactCustomer(request) {
  return client.post(URL_API + WS_CODE_CONTACT, {
    wsCode: WS_CODE_CONTACT,
    wsRequest: request,
  });
}
