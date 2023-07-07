import React, { useEffect, useState } from "react";
import { Upload } from "antd";
import { uploadFile } from "services/common";
import { UploadOutlined } from "@ant-design/icons";
import { messageError } from "utils/messageM";
import { createUUID, readFile } from "utils/helpers";

const MultiUploadM = ({ list = [], cba, cbr, ...rest }) => {
  const [fileList, setFileList] = useState([]);
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
    showUploadList: true,
    accept: "image/*",
    multiple: true,
    fileList,
    beforeUpload: onUpload,
    showUploadList: {
      showPreviewIcon: false,
    },
    onRemove: (file) => {
      const ri = fileList.findIndex((f) => f.uid == file.uid);
      if (ri > -1) return cbr(ri);
      return messageError("Error !!!");
    },
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
        cba(code, message, responseBase);
      } catch (error) {
        console.log(error);
        messageError(error.toString());
      }
    },
  };
  const genList = (array) => {
    if (array.length === 0) return [];
    let arr = [];
    for (let i = 0; i < array.length; i++) {
      const e = array[i];
      arr.push({
        uid: createUUID(),
        name: e,
        status: "done",
        url: e,
      });
    }
    return arr;
  };

  useEffect(() => {
    setFileList(genList(list));
  }, [list]);
  return (
    <Upload {...uploadProps} {...rest} className="w-100">
      {list.length >= 6 ? null : <UploadOutlined />}
    </Upload>
  );
};

export default MultiUploadM;
