import { insertContactCustomer } from "services/common";
import { CODE_API_SUCCESS } from "./constants";
import { messageError, messageSuccess } from "./messageM";

export const checkData = (s) => {
  if (s && typeof s === "string" && s !== "" && s !== null) {
    return true;
  } else {
    return false;
  }
};

export const getConfig = async (api, _cb) => {
  const request = {};
  try {
    const { code, message, responseBase } = await api(request);
    if (code === CODE_API_SUCCESS) {
      _cb(responseBase.data);
    } else {
      console.log(message);
      return [];
    }
  } catch (error) {
    console.log(error);
    return [];
  }
};

export const toSlug = (str) => {
  let result = "";

  try {
    result = str.trim().toLowerCase();

    result = str.replace(/(à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ)/g, "a");
    result = str.replace(/(è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ)/g, "e");
    result = str.replace(/(ì|í|ị|ỉ|ĩ)/g, "i");
    result = str.replace(/(ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ)/g, "o");
    result = str.replace(/(ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ)/g, "u");
    result = str.replace(/(ỳ|ý|ỵ|ỷ|ỹ)/g, "y");
    result = str.replace(/(đ)/g, "d");

    result = str.replace(/([^0-9a-z-\s])/g, "");

    result = str.replace(/(\s+)/g, "-");

    result = str.replace(/^-+/g, "");

    result = str.replace(/-+$/g, "");
  } catch (error) {}

  return result;
};

export function readFile(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.addEventListener("load", () => resolve(reader.result), false);
    reader.readAsDataURL(file);
    reader.onerror = (error) => reject(error);
  });
}

export function createUUID() {
  var dt = new Date().getTime();
  var uuid = "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g, function (c) {
    var r = (dt + Math.random() * 16) % 16 | 0;
    dt = Math.floor(dt / 16);
    return (c == "x" ? r : (r & 0x3) | 0x8).toString(16);
  });
  return uuid;
}

export async function sendRequestContact(request) {
  try {
    const { c_email, c_phone, c_name } = request;
    if (!checkData(c_email) && !checkData(c_phone)) {
      return messageError("Thiếu thông tin liên lạc (email hoặc số điện thoại)");
    }
    const { code, message } = await insertContactCustomer({
      name: c_name,
      phone: c_phone,
      email: c_email,
      note: request.c_note || null,
    });
    if (code === CODE_API_SUCCESS) {
      messageSuccess(message);
    } else {
      messageError(message);
    }
  } catch (error) {
    console.log(error);
    messageError(error.toString());
  }
}
