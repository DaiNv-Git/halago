import React from "react";
import { Button } from "antd";
import { ArrowLeftOutlined } from "@ant-design/icons";
import { useRouter } from "next/router";

const ButtonBack = () => {
  const { back } = useRouter();
  return <Button type="text" icon={<ArrowLeftOutlined />} onClick={() => back()} />;
};

export default ButtonBack;
