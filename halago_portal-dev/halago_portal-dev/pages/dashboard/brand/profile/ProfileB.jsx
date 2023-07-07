import React, { useState, useEffect } from "react";
import { EditOutlined } from "@ant-design/icons";
import { Button, Col, Form, Input, Modal, Row } from "antd";
import ButtonBack from "components/Button/ButtonBack";
import LayoutM from "layouts";
import { baseRule } from "utils/validation";
import {
  FIELD_NAME_CREATE_BRAND_EMAIL,
  FIELD_NAME_CREATE_BRAND_NAME,
  FIELD_NAME_CREATE_BRAND_PASSWORD,
  FIELD_NAME_CREATE_BRAND_PHONE,
  FIELD_NAME_CREATE_BRAND_REGISTER,
  FIELD_NAME_CREATE_BRAND_LINK,
  FIELD_NAME_CREATE_BRAND_DESCRIPTION,
  FIELD_NAME_CREATE_BRAND_LOGO,
  LIST_FIELD_CREATE_BRAND,
} from "layouts/Header/const";
import { findBrandById, updateBrand } from "services/brand";
import { authId } from "utils/storages";
import { messageError, messageSuccess } from "utils/messageM";
import { CODE_API_SUCCESS } from "utils/constants";

import dynamic from "next/dynamic";
import { forgotPassword } from "services/auth";
import Head from "next/head";
import UploadM from "components/Upload/UploadM";
const MyEditor = dynamic(() => import("components/Editor/MyEditor"), {
  ssr: false,
});

const formLayout = {
  labelCol: { offset: 4, span: 8 },
  wrapperCol: { offset: 4, span: 16 },
};
const meta = {
  title: "Halago - Seller Profile",
  description: "Halago - Seller Profile",
};

const ProfileB = () => {
  const [form] = Form.useForm();
  const [update, setUpdate] = useState(false);
  const [brand, setBrand] = useState(null);
  const [description, setDescription] = useState("");
  const [loading, setLoading] = useState(false);
  const [visible, setVisible] = useState(false);
  const [logo, setLogo] = useState("");

  const findBrand = async () => {
    try {
      const { code, message, responseBase } = await findBrandById({ id: authId() });
      if (code === CODE_API_SUCCESS) {
        setBrand(responseBase.data);
        setDescription(responseBase.data.description || "");
        setLogo(responseBase.data.logo || null);
        form.setFieldsValue(responseBase.data);
      } else {
        messageError(message);
      }
    } catch (error) {
      console.log(error);
      messageError(error.toString());
    }
  };
  const onClickUpdate = () => {
    setUpdate(true);
  };
  const onUpdate = async () => {
    await form.validateFields([
      FIELD_NAME_CREATE_BRAND_REGISTER,
      FIELD_NAME_CREATE_BRAND_EMAIL,
      FIELD_NAME_CREATE_BRAND_PHONE,
      FIELD_NAME_CREATE_BRAND_NAME,
    ]);
    try {
      setLoading(true);
      const values = form.getFieldsValue([
        FIELD_NAME_CREATE_BRAND_REGISTER,
        FIELD_NAME_CREATE_BRAND_EMAIL,
        FIELD_NAME_CREATE_BRAND_PHONE,
        FIELD_NAME_CREATE_BRAND_NAME,
        FIELD_NAME_CREATE_BRAND_DESCRIPTION,
        FIELD_NAME_CREATE_BRAND_LINK,
        FIELD_NAME_CREATE_BRAND_LOGO,
      ]);
      const { code, message } = await updateBrand({
        ...values,
        description,
        website: form.getFieldValue(FIELD_NAME_CREATE_BRAND_LINK) || "",
        id: authId(),
      });
      if (code === CODE_API_SUCCESS) {
        messageSuccess(message);
        findBrand();
        setUpdate(false);
      } else {
        messageError(message);
      }
    } catch (error) {
      console.log(error);
      messageError(error.toString());
    } finally {
      setLoading(false);
    }
  };
  const onChangePassword = async () => {
    await form.validateFields(["newPassword"]);
    try {
      const request = {
        newPassword: form.getFieldValue("newPassword"),
        email: form.getFieldValue("brandEmail"),
      };
      const { code, message } = await forgotPassword(request);
      if (code === CODE_API_SUCCESS) {
        messageSuccess(message);
        setVisible(false);
      } else {
        messageError(message);
      }
    } catch (error) {
      console.log(error);
      messageError(error.toString());
    }
  };
  const cbm = (c, m, r, s, n) => {
    if (c === CODE_API_SUCCESS) {
      s(r.data);
      form.setFieldsValue({ [n]: r.data });
    } else {
      messageError(m);
    }
  };

  useEffect(() => {
    findBrand();
  }, []);

  return (
    <LayoutM>
      <Head>
        <title>{meta.title}</title>
        <meta name="description" content={meta.description} />
        <meta name="og:title" content={meta.title} />
        <meta name="og:site_name" content={meta.title} />
        <meta name="og:description" key="description" content={meta.description} />
        <meta property="og:title" content={meta.title} key="title" />
        <meta property="og:description" content={meta.description} key="description" />
      </Head>
      <div className="wrapper">
        <div className="container-main p-4 mt-32">
          <div className="page--header">
            <ButtonBack />
            <Button type="primary" icon={<EditOutlined />} onClick={onClickUpdate}>
              Cập nhật thông tin
            </Button>
          </div>
          <div className="page--title t-center">
            <h2>Thông tin nhãn hàng</h2>
          </div>
          <Form form={form} className="page--form" {...formLayout} layout="vertical">
            <Row>
              <Col xs={24} xl={12}>
                <Form.Item label="Tên người đăng ký" rules={[...baseRule]} name={FIELD_NAME_CREATE_BRAND_REGISTER}>
                  <Input disabled={!update} />
                </Form.Item>
              </Col>
              <Col xs={24} xl={12}>
                <Form.Item label="Tên nhãn hàng" rules={[...baseRule]} name={FIELD_NAME_CREATE_BRAND_NAME}>
                  <Input disabled={!update} />
                </Form.Item>
              </Col>
              <Col xs={24} xl={12}>
                <Form.Item label="Email" rules={[...baseRule]} name={FIELD_NAME_CREATE_BRAND_EMAIL}>
                  <Input disabled={!update} />
                </Form.Item>
              </Col>
              <Col xs={24} xl={12}>
                <Form.Item label="Website/Fanpage" name={FIELD_NAME_CREATE_BRAND_LINK} initialValue="">
                  <Input disabled={!update} />
                </Form.Item>
              </Col>
              <Col xs={24} xl={12}>
                <Form.Item label="Số điện thoại" rules={[...baseRule]} name={FIELD_NAME_CREATE_BRAND_PHONE}>
                  <Input disabled={!update} />
                </Form.Item>
              </Col>
              <Col xs={24} xl={12}>
                <Form.Item label="Logo" nam={FIELD_NAME_CREATE_BRAND_LOGO}>
                  <UploadM
                    disabled={!update}
                    name={FIELD_NAME_CREATE_BRAND_LOGO}
                    image={logo}
                    cb={(c, m, r) => cbm(c, m, r, setLogo, FIELD_NAME_CREATE_BRAND_LOGO)}
                  />
                </Form.Item>
              </Col>
              <Col xs={24}>
                <Form.Item
                  label="Giới thiệu"
                  name={FIELD_NAME_CREATE_BRAND_DESCRIPTION}
                  labelCol={{ offset: 2 }}
                  wrapperCol={{ offset: 2, span: 20 }}
                >
                  <MyEditor
                    disabled={!update}
                    data={description}
                    onReady={(editor) => editor.setData(description)}
                    onChange={(_e, editor) => {
                      const data = editor.getData();
                      setDescription(data);
                    }}
                  />
                </Form.Item>
              </Col>
            </Row>
            {update ? (
              <Form.Item wrapperCol={{ offset: 2, span: 20 }}>
                <Button onClick={onUpdate} loading={loading} type="primary" className="w-100">
                  Cập nhật
                </Button>
              </Form.Item>
            ) : (
              <Form.Item wrapperCol={{ offset: 2, span: 20 }}>
                <Button type="link" onClick={() => setVisible(true)} loading={loading}>
                  Đổi mật khẩu
                </Button>
              </Form.Item>
            )}
          </Form>
          <Modal visible={visible} title="Đổi mật khẩu" footer={null} onCancel={() => setVisible(false)}>
            <Form form={form} layout="vertical">
              <Form.Item label="Nhập mật khẩu mới" name="newPassword" rules={[...baseRule]}>
                <Input.Password />
              </Form.Item>
              <Form.Item>
                <Button className="w-100" type="primary" onClick={onChangePassword}>
                  Xác nhận
                </Button>
              </Form.Item>
            </Form>
          </Modal>
        </div>
      </div>
    </LayoutM>
  );
};

export default ProfileB;
