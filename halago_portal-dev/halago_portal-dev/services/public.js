import axios from "axios";
import { BASE_API_URL, REQUEST_TIME_OUT } from "utils/constants";

const server = axios.create({
  timeout: REQUEST_TIME_OUT,
  baseURL: BASE_API_URL,
});

// const getAuthToken = localStorage.getItem("__p_token__") ? localStorage.getItem("__p_token__") : '';
// const getLang = localStorage.getItem("__language__") ? localStorage.getItem("__language__") : '';

server.interceptors.request.use(
  (config) => {
    config.data = {
      token: "",
      session: "",
      wsCode: config.data.wsCode,
      wsRequest: {
        language: config.data.wsRequest.language == "vn" ? "vn": "en",
        ...config.data.wsRequest,
      },
    };
    return config;
  },
  (error) => Promise.reject(error),
);

server.interceptors.response.use(
  (response) => response.data,
  (error) => Promise.reject(error),
);

export default server;
