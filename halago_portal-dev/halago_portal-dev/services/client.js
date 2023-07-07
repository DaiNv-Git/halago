import axios from "axios";
import { BASE_API_URL, REQUEST_TIME_OUT } from "utils/constants";
import { authToken, language } from "utils/storages";

const client = axios.create({
  timeout: REQUEST_TIME_OUT,
  baseURL: BASE_API_URL,
});

client.interceptors.request.use(
  (config) => {
    config.data = {
      token: authToken() || "",
      session: "",
      wsCode: config.data.wsCode,
      wsRequest: {
        language: language() === "eng" ? "en" : "vn",
        ...config.data.wsRequest,
      },
    };
    return config;
  },
  (error) => Promise.reject(error),
);

client.interceptors.response.use(
  (response) => response.data,
  (error) => Promise.reject(error),
);

export default client;
