const SESSION_STORAGE_AUTH_TOKEN = "__p_token__";
const SESSION_STORAGE_AUTH_ROLE = "__p_role__";
const SESSION_STORAGE_AUTH_ID = "__p_id__";
const SESSION_STORAGE_AUTH_NAME = "__p_name__";
const SESSION_STORAGE_AUTH_LOGO = "__p_logo__";
const STORAGE_LANGUAGE = "__language__";

export function authToken(token) {
  if (token) return localStorage.setItem(SESSION_STORAGE_AUTH_TOKEN, token);
  const getToken = localStorage.getItem(SESSION_STORAGE_AUTH_TOKEN);
  if (getToken && getToken !== "") return String(getToken);
  return false;
}

export function authRole(role) {
  if (role) return localStorage.setItem(SESSION_STORAGE_AUTH_ROLE, role);
  const getRole = localStorage.getItem(SESSION_STORAGE_AUTH_ROLE);
  if (getRole && getRole !== "") return String(getRole);
  return false;
}

export function authId(id) {
  if (id) return localStorage.setItem(SESSION_STORAGE_AUTH_ID, id);
  const getId = localStorage.getItem(SESSION_STORAGE_AUTH_ID);
  if (getId && getId !== "") return Number(getId);
  return false;
}

export function authName(name) {
  if (name) return localStorage.setItem(SESSION_STORAGE_AUTH_NAME, name);
  const getName = localStorage.getItem(SESSION_STORAGE_AUTH_NAME);
  if (getName && getName !== "") return String(getName);
  return false;
}

export function language(lan) {
  if (lan) return localStorage.setItem(STORAGE_LANGUAGE, lan);
  const getLan = localStorage.getItem(STORAGE_LANGUAGE);
  if (getLan && getLan !== "") return String(getLan);
  return false;
}

export function authLogo(logo) {
  if (logo) return localStorage.setItem(SESSION_STORAGE_AUTH_LOGO, logo);
  const getLogo = localStorage.getItem(SESSION_STORAGE_AUTH_LOGO);
  if (getLogo !== "") return String(getLogo);
  return false;
}