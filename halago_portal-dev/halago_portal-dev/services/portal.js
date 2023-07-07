import client from "./client";
import server from "./public";
import { BASE_API_URL } from "utils/constants";

const URL_PREFIX = "/";
const URL_API = BASE_API_URL + URL_PREFIX;

const WS_CODE_FOOTER = "getListFooter";
const WS_CODE_FOOTER_U = "updateFooter";
const WS_CODE_INTRODUCE = "getListIntroduce";
const WS_CODE_INFLUENCER = "getListInfluencerPgae";
const WS_CODE_KOL = "getListBookKols";
const WS_CODE_BRAND = "getListBrandPortal";
const WS_CODE_LIST_NEWS_PORTAL = "getListNewsPortal";
const WS_CODE_NEW_PORTAL = "getNews";
const WS_CODE_ABOUT_US = "getAboutUs";
const WS_CODE_VISION = "getVision";
const WS_CODE_NEWS_FOOTER = "getListNewsFooter";

export function getListNewsFooter(request) {
  return client.post(URL_API + WS_CODE_NEWS_FOOTER, {
    wsCode: WS_CODE_NEWS_FOOTER,
    wsRequest: request,
  });
}

export function getListFooter(request) {
  return client.post(URL_API + WS_CODE_FOOTER, {
    wsCode: WS_CODE_FOOTER,
    wsRequest: request,
  });
}

export function updateFooter(request) {
  return client.post(URL_API + WS_CODE_FOOTER_U, {
    wsCode: WS_CODE_FOOTER_U,
    wsRequest: request,
  });
}

export function getListIntroduce(request) {
  return client.post(URL_API + WS_CODE_INTRODUCE, {
    wsCode: WS_CODE_INTRODUCE,
    wsRequest: request,
  });
}

export function getListInfluencerPage(request) {
  return client.post(URL_API + WS_CODE_INFLUENCER, {
    wsCode: WS_CODE_INFLUENCER,
    wsRequest: request,
  });
}

export function getListKolPage(request) {
  return client.post(URL_API + WS_CODE_KOL, {
    wsCode: WS_CODE_KOL,
    wsRequest: request,
  });
}

export function getListBrandPortal(request) {
  return client.post(URL_API + WS_CODE_BRAND, {
    wsCode: WS_CODE_BRAND,
    wsRequest: request,
  });
}

export function getListNewsPortal(request) {
  return client.post(URL_API + WS_CODE_LIST_NEWS_PORTAL, {
    wsCode: WS_CODE_LIST_NEWS_PORTAL,
    wsRequest: request,
  });
}

export function getNews(request) {
  return client.post(URL_API + WS_CODE_NEW_PORTAL, {
    wsCode: WS_CODE_NEW_PORTAL,
    wsRequest: request,
  });
}

export function getAboutUs(request) {
  return client.post(URL_API + WS_CODE_ABOUT_US, {
    wsCode: WS_CODE_ABOUT_US,
    wsRequest: request,
  });
}

export function getVision(request) {
  return client.post(URL_API + WS_CODE_VISION, {
    wsCode: WS_CODE_VISION,
    wsRequest: request,
  });
}

export function getNewsPublic(request) {
  return server.post(URL_API + WS_CODE_NEW_PORTAL, {
    wsCode: WS_CODE_NEW_PORTAL,
    wsRequest: request,
  });
}




