import client from "./client";
import { BASE_API_URL } from "utils/constants";

const URL_PREFIX = "/";
const URL_API = BASE_API_URL + URL_PREFIX;

const WS_CODE_CAMPAIGN_INSERT = "insertCampaign";
const WS_CODE_CAMPAIGN_LIST = "getListCampaign";
const WS_CODE_CAMPAIGN_FIND = "findCampaignById";
const WS_CODE_CAMPAIGN_UPDATE = "updateCampaign";
const WS_CODE_CAMPAIGN_DELETE = "deleteCampaign";
const WS_CODE_CAMPAIGN_BRAND = "getListCampaignByBrand";
const WS_CODE_CAMPAIGN_APPLY = "getListCampaignApply";

export function insertCampaign(request) {
  return client.post(URL_API + WS_CODE_CAMPAIGN_INSERT, {
    wsCode: WS_CODE_CAMPAIGN_INSERT,
    wsRequest: request,
  });
}

export function getListCampaign(request) {
  return client.post(URL_API + WS_CODE_CAMPAIGN_LIST, {
    wsCode: WS_CODE_CAMPAIGN_LIST,
    wsRequest: request,
  });
}

export function findCampaignById(request) {
  return client.post(URL_API + WS_CODE_CAMPAIGN_FIND, {
    wsCode: WS_CODE_CAMPAIGN_FIND,
    wsRequest: request,
  });
}

export function updateCampaign(request) {
  return client.post(URL_API + WS_CODE_CAMPAIGN_UPDATE, {
    wsCode: WS_CODE_CAMPAIGN_UPDATE,
    wsRequest: request,
  });
}

export function deleteCampaign(request) {
  return client.post(URL_API + WS_CODE_CAMPAIGN_DELETE, {
    wsCode: WS_CODE_CAMPAIGN_DELETE,
    wsRequest: request,
  });
}

export function getListCampaignByBrand(request) {
  return client.post(URL_API + WS_CODE_CAMPAIGN_BRAND, {
    wsCode: WS_CODE_CAMPAIGN_BRAND,
    wsRequest: request,
  });
}

export function getListCampaignApply(request) {
  return client.post(URL_API + WS_CODE_CAMPAIGN_APPLY, {
    wsCode: WS_CODE_CAMPAIGN_APPLY,
    wsRequest: request,
  });
}
