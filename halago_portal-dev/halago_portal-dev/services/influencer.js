import client from "./client";
import { BASE_API_URL } from "utils/constants";

const URL_PREFIX = "/";
const URL_API = BASE_API_URL + URL_PREFIX;

const WS_CODE_INFLUENCER_UPDATE = "updateInfluencerPortal";
const WS_CODE_INFLUENCER_FIND = "findInfluencerById";
const WS_CODE_INFLUENCER_UPDATE_2 = "updateInfluencer";
const WS_CODE_INFLUENCER_LIST = "findInfluencerPortal";

export function updateInfluencerPortal(request) {
  return client.post(URL_API + WS_CODE_INFLUENCER_UPDATE, {
    wsCode: WS_CODE_INFLUENCER_UPDATE,
    wsRequest: request,
  });
}

export function findInfluencerById(request) {
  return client.post(URL_API + WS_CODE_INFLUENCER_FIND, {
    wsCode: WS_CODE_INFLUENCER_FIND,
    wsRequest: request,
  });
}

export function updateInfluencer2(request) {
  return client.post(URL_API + WS_CODE_INFLUENCER_UPDATE_2, {
    wsCode: WS_CODE_INFLUENCER_UPDATE_2,
    wsRequest: request,
  });
}

export function getListInfluencer(request) {
  return client.post(URL_API + WS_CODE_INFLUENCER_LIST, {
    wsCode: WS_CODE_INFLUENCER_LIST,
    wsRequest: request,
  });
}
