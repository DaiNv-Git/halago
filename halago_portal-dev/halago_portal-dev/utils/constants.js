export const BASE_API_URL = "https://api.halago.vn/";
export const DEFAULT_PAGE_SIZE = 10;
export const REQUEST_TIME_OUT = 1000000;
export const CODE_API_SUCCESS = 200;
export const DEFAULT_FORMAT_DATE = "DD-MM-YYYY";

export const G_MALE = 1;
export const G_FEMALE = 0;
export const G_OTHER = 2;
export const G_LIST = [
  {
    value: G_MALE,
    label: "Nam",
  },
  {
    value: G_FEMALE,
    label: "Nữ",
  },
  {
    value: G_OTHER,
    label: "Khác",
  },
];

export const MARRIED = 2;
export const N_MARRIED = 1;
export const O_MARRIED = 0;
export const M_LIST = [
  {
    value: MARRIED,
    label: "Đã kết hôn",
  },
  {
    value: N_MARRIED,
    label: "Độc thân",
  },
  {
    value: O_MARRIED,
    label: "Khác",
  },
];

export const ACTIVE = 1;
export const INACTIVE = 0;

export const ACCEPT = 1;
export const REJECT = 2;
export const PENDING = 0;

export const ROLE_I = "INFLUENCER";
export const ROLE_B = "BRAND";

export const USER_B = "brand";
export const USER_I = "influencer";
export const ACTION_R = "register";
export const ACTION_L = "login";
export const ACTION_F = "forgot";

export const CATEGORIES_NEWS = {
  internalNews: 'internalNews',
  foreignNews: 'foreignNews',
  newsPapers: 'newsPapers',
};