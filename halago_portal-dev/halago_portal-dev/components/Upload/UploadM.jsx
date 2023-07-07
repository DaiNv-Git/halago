import React from "react";
import { Upload } from "antd";
import { uploadFile } from "services/common";
import { UploadOutlined } from "@ant-design/icons";
import { messageError } from "utils/messageM";
import { readFile } from "utils/helpers";

const UploadM = ({ image, cb, name, ...rest }) => {
  const onUpload = (file) => {
    const isJpgOrPng = file.type === "image/jpeg" || file.type === "image/png";
    if (!isJpgOrPng) {
      messageError("Tệp tải lên không hợp lệ!");
    }
    const isLt2M = file.size / 1024 / 1024 < 10;
    if (!isLt2M) {
      messageError("Ảnh tải lên vượt quá dung lượng cho phép!");
    }
    return isJpgOrPng && isLt2M;
  };
  const uploadProps = {
    listType: "picture-card",
    className: "uploadM",
    showUploadList: false,
    accept: "image/*",
    multiple: false,
    beforeUpload: onUpload,
    async customRequest({ file }) {
      const isJpgOrPng = file.type === "image/jpeg" || file.type === "image/png";
      if (!isJpgOrPng) {
        return messageError("Tệp tải lên không hợp lệ!");
      }
      const isLt2M = file.size / 1024 / 1024 < 10;
      if (!isLt2M) {
        return messageError("Ảnh tải lên vượt quá dung lượng cho phép!");
      }
      const fileBase64 = await readFile(file);
      let request = {
        fileData: fileBase64.split(",")[1],
        fileName: file.name,
      };
      try {
        const { code, message, responseBase } = await uploadFile(request);
        cb(code, message, responseBase);
      } catch (error) {
        console.log(error);
        messageError(error.toString());
      }
    },
  };
  return (
    <Upload {...uploadProps} {...rest} className="w-100" name={name}>
      {image ? <img src={image} alt="Halago" className="w-100" /> : <UploadOutlined />}
    </Upload>
  );
};

export default UploadM;
