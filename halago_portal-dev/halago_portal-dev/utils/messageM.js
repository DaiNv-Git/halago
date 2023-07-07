import { message } from "antd";

const DEFAULT_DURATION = 3;

export function messageError(msg = "Error", key = "error", dr = DEFAULT_DURATION, options = {}) {
  return message.error({
    content: msg,
    key: key,
    duration: dr,
    ...options,
  });
}

export function messageSuccess(msg = "Success", key = "success", dr = DEFAULT_DURATION, options = {}) {
  return message.success({
    content: msg,
    key: key,
    duration: dr,
    ...options,
  });
}
