import LayoutM from "layouts";
import React, { useState, useEffect, useContext } from "react";
import Head from "next/head";
import moment from "moment";
import dynamic from "next/dynamic";
const MyEditor = dynamic(() => import("components/Editor/MyEditor"), {
  ssr: false,
});
import { Button, Checkbox, Col, DatePicker, Form, Input, Radio, Row, Select } from "antd";
import { CODE_API_SUCCESS, DEFAULT_FORMAT_DATE, G_LIST, M_LIST } from "utils/constants";
import { getConfig } from "utils/helpers";
import { listCareer, listChannel, listCity, listIndustry, listTypesInteraction } from "services/common";
import { messageError, messageSuccess } from "utils/messageM";
import { baseRule } from "utils/validation";
import * as fields from "utils/fields2";
import { findInfluencerById, updateInfluencer2, updateInfluencerPortal } from "services/influencer";
import { authId } from "utils/storages";
import AuthStoreContext from "stores/AuthStore";
import UploadM from "components/Upload/UploadM";
const meta = {
  title: "Halago - Influencer Profile",
  description: "Halago - Influencer Profile",
};

const ProfileI = () => {
  const authStore = useContext(AuthStoreContext);
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(false);
  const [city, setCity] = useState([]);
  const [career, setCareer] = useState([]);
  const [channel, setChannel] = useState([]);
  const [type, setType] = useState([]);
  const [industry, setIndustry] = useState([]);
  const [influencer, setInfluencer] = useState(null);
  const [logo, setLogo] = useState("");
  const update = true;

  const getInfo = async () => {
    try {
      const { code, message, responseBase } = await findInfluencerById({ id: authId() || authStore.id });
      if (code === CODE_API_SUCCESS) {
        setInfluencer(responseBase.data);
        setLogo(responseBase.data.avatar || "");
        form.setFieldsValue({
          ...responseBase.data,
          [fields.FIELD_INFLUENCER_DOB]:
            responseBase.data[fields.FIELD_INFLUENCER_DOB] &&
            moment(responseBase.data[fields.FIELD_INFLUENCER_DOB], DEFAULT_FORMAT_DATE),
        });
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
  const onFinish = async () => {
    if (!influencer || !influencer?.id) return;
    await form.validateFields(fields.LIST_FIELD_VALIDATE);
    try {
      setLoading(true);
      const values = form.getFieldsValue();
      const request = {
        ...values,
        id: influencer.id,
        status: influencer.status,
        [fields.FIELD_INFLUENCER_DOB]: values[fields.FIELD_INFLUENCER_DOB].format(DEFAULT_FORMAT_DATE),
      };
      const { code, message } = await updateInfluencer2(request);
      if (code === CODE_API_SUCCESS) {
        messageSuccess(message);
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

  useEffect(() => {
    getConfig(listCity, setCity);
    getConfig(listCareer, setCareer);
    getConfig(listChannel, setChannel);
    getConfig(listTypesInteraction, setType);
    getConfig(listIndustry, setIndustry);
    getInfo();
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
      <div className="page--wrapper">
        <div className="page--title t-center mb-32 mt-32">
          <h2>Thông tin Influencer</h2>
        </div>
        <Form form={form} className="page--form" layout="vertical">
          <Row>
            <Col xs={{ span: 20, offset: 2 }}>
              <Row gutter={32} className="influencer--row">
                <Col xs={24} md={12}>
                  <Form.Item label="Họ tên" rules={[...baseRule]} name={fields.FIELD_INFLUENCER_NAME}>
                    <Input disabled={!update} />
                  </Form.Item>
                </Col>
                <Col xs={24} md={12}>
                  <Form.Item label="Email" rules={[...baseRule]} name={fields.FIELD_INFLUENCER_EMAIL}>
                    <Input disabled={!update} />
                  </Form.Item>
                </Col>
                <Col xs={24} md={12}>
                  <Form.Item label="Số điện thoại" rules={[...baseRule]} name={fields.FIELD_INFLUENCER_PHONE}>
                    <Input disabled={!update} />
                  </Form.Item>
                </Col>
                <Col xs={24} md={6}>
                  <Form.Item label="Ngày sinh" {...baseRule} name={fields.FIELD_INFLUENCER_DOB}>
                    <DatePicker disabled={!update} format={DEFAULT_FORMAT_DATE} />
                  </Form.Item>
                </Col>
                <Col xs={24} md={6}>
                  <Form.Item label="Ảnh đại diện" {...baseRule} name={fields.FIELD_INFLUENCER_AVATAR}>
                    <UploadM
                      disabled={!update}
                      name={fields.FIELD_INFLUENCER_AVATAR}
                      image={logo}
                      cb={(c, m, r) => cbm(c, m, r, setLogo, fields.FIELD_INFLUENCER_AVATAR)}
                    />
                  </Form.Item>
                </Col>
                <Col xs={24} md={12}>
                  <Form.Item label="Địa chỉ" {...baseRule} name={fields.FIELD_INFLUENCER_ADDRESS}>
                    <Input disabled={!update} />
                  </Form.Item>
                </Col>
                <Col xs={24} md={12}>
                  <Form.Item label="Tỉnh/Thành phố" {...baseRule} name={fields.FIELD_INFLUENCER_PROVINCE}>
                    <Select disabled={!update}>
                      {city?.map((i) => (
                        <Select.Option key={i.city_id} value={i.city_id}>
                          {i.cityName}
                        </Select.Option>
                      ))}
                    </Select>
                  </Form.Item>
                </Col>
                <Col xs={24} md={12}>
                  <Form.Item label="Nghề nghiệp" {...baseRule} name={fields.FIELD_INFLUENCER_WORK}>
                    <Select disabled={!update}>
                      {career?.map((i) => (
                        <Select.Option key={i.id} value={i.id}>
                          {i.careerName}
                        </Select.Option>
                      ))}
                    </Select>
                  </Form.Item>
                </Col>
                <Col xs={24} md={12}>
                  <Form.Item label="Ngành quan tâm" {...baseRule} name={fields.FIELD_INFLUENCER_WORKS}>
                    <Select disabled={!update} mode="multiple">
                      {industry?.map((i) => (
                        <Select.Option key={i.id} value={i.id}>
                          {i.industryName}
                        </Select.Option>
                      ))}
                    </Select>
                  </Form.Item>
                </Col>
                <Col xs={24} md={12}>
                  <Form.Item label="Giới tính" {...baseRule} name={fields.FIELD_INFLUENCER_GENDER}>
                    <Radio.Group disabled={!update} options={G_LIST} />
                  </Form.Item>
                </Col>
                <Col xs={24} md={12}>
                  <Form.Item label="Tình trạng hôn nhân" {...baseRule} name={fields.FIELD_INFLUENCER_MARRIED}>
                    <Radio.Group disabled={!update} options={M_LIST} />
                  </Form.Item>
                </Col>
                <Col xs={24} xl={12}>
                  <Form.Item label="Tên ngân hàng" name={fields.FIELD_INFLUENCER_BANK_N}>
                    <Input disabled={!update} />
                  </Form.Item>
                </Col>
                <Col xs={24} xl={12}>
                  <Form.Item label="Số tài khoản" name={fields.FIELD_INFLUENCER_BANK_A}>
                    <Input disabled={!update} />
                  </Form.Item>
                </Col>
              </Row>
              <Row gutter={32} className="influencer--row">
                <Col xs={24} xl={12}>
                  <Form.Item label="Giới thiệu bản thân" name={fields.FIELD_INFLUENCER_INTRO}>
                    <MyEditor
                      disabled={!update}
                      data={(influencer && influencer[fields.FIELD_INFLUENCER_INTRO]) || null}
                      onBlur={(_e, editor) =>
                        form.setFieldsValue({ [fields.FIELD_INFLUENCER_INTRO]: editor.getData() })
                      }
                    />
                  </Form.Item>
                </Col>
                <Col xs={24} xl={12}>
                  <Form.Item label="Kinh nghiệm bán hàng, review" name={fields.FIELD_INFLUENCER_EXP}>
                    <MyEditor
                      disabled={!update}
                      data={(influencer && influencer[fields.FIELD_INFLUENCER_EXP]) || null}
                      onBlur={(_e, editor) => form.setFieldsValue({ [fields.FIELD_INFLUENCER_EXP]: editor.getData() })}
                    />
                  </Form.Item>
                </Col>
                <Col xs={24} xl={12}>
                  <Form.Item label="Link profile FB" name={fields.FIELD_INFLUENCER_FB}>
                    <Input disabled={!update}/>
                  </Form.Item>
                </Col>
                <Col xs={24} xl={12}>
                  <Form.Item label="Số tương tác trung bình 1 bài đăng" {...baseRule} name={fields.FIELD_INFLUENCER_TT}>
                    <Input disabled={!update} />
                  </Form.Item>
                </Col>
                <Col xs={24}>
                  <Form.Item label="Các kênh tương tác có kinh nghiệm" name={fields.FIELD_INFLUENCER_CHANEL}>
                    <Checkbox.Group disabled={!update}>
                      <Row>
                        {channel?.map((i) => (
                          <Col key={i.id} xs={12} md={8} lg={6} xl={4}>
                            <Checkbox value={i.id}>{i.nameInteraction}</Checkbox>
                          </Col>
                        ))}
                      </Row>
                    </Checkbox.Group>
                  </Form.Item>
                </Col>
                <Col xs={24}>
                  <Form.Item label="Các loại tương tác có kinh nghiệm" name={fields.FIELD_INFLUENCER_TYPE}>
                    <Checkbox.Group disabled={!update} className="w-100">
                      <Row>
                        {type?.map((i) => (
                          <Col xs={12} xl={6} key={i.id}>
                            <Checkbox value={i.id}>{i.nameInteraction}</Checkbox>
                          </Col>
                        ))}
                      </Row>
                    </Checkbox.Group>
                  </Form.Item>
                </Col>
              </Row>
              <Row className="influencer--row">
                <Form.Item className="w-100">
                  <Button type="primary" onClick={onFinish} loading={loading} className="w-100">
                    Cập nhật
                  </Button>
                </Form.Item>
              </Row>
            </Col>
          </Row>
        </Form>
      </div>
    </LayoutM>
  );
};

export default ProfileI;
