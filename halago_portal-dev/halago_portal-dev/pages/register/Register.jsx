import React, { useState, useEffect, useContext } from "react";
import Head from "next/head";
import { useRouter } from "next/router";
import { Button, Checkbox, Col, DatePicker, Form, Input, Radio, Row, Select } from "antd";
import { CODE_API_SUCCESS, DEFAULT_FORMAT_DATE, G_LIST, M_LIST, ROLE_I } from "utils/constants";
import { getConfig } from "utils/helpers";
import { listCareer, listChannel, listCity, listIndustry, listTypesInteraction } from "services/common";
import { messageError, messageSuccess } from "utils/messageM";
import { baseRule } from "utils/validation";
import * as fields from "utils/fields2";
import { APP_DASHBOARD_I_P, APP_HOME } from "utils/paths";
import { updateInfluencerPortal } from "services/influencer";
import LayoutM from "layouts";
import AuthStoreContext from "stores/AuthStore";
import dynamic from "next/dynamic";
import { loginFbInfluencer } from "services/auth";
const MyEditor = dynamic(() => import("components/Editor/MyEditor"), {
  ssr: false,
});
const meta = {
  title: "Halago - Register",
  description:
    "Rất nhiều Influencer đã đăng ký trên nền tảng của Halago. Còn chờ đợi gì nữa, hãy thử ngay",
};

function Register() {
  const authStore = useContext(AuthStoreContext);
  const router = useRouter();
  const id = router?.query?.id;
  const fbId = router?.query?.fbId;
  const token = router?.query?.accessToken;
  const avatar = router?.query?.avatar || null;
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(false);
  const [city, setCity] = useState([]);
  const [career, setCareer] = useState([]);
  const [channel, setChannel] = useState([]);
  const [type, setType] = useState([]);
  const [industry, setIndustry] = useState([]);

  const update = true;

  const onFinish = async () => {
    await form.validateFields(fields.LIST_FIELD_VALIDATE);
    try {
      setLoading(true);
      const values = form.getFieldsValue();
      const request = {
        ...values,
        id: parseInt(id),
        avatar,
        fbId: fbId,
        [fields.FIELD_INFLUENCER_DOB]: values[fields.FIELD_INFLUENCER_DOB].format(DEFAULT_FORMAT_DATE),
      };
      const { code, message, responseBase } = await updateInfluencerPortal(request);
      if (code === CODE_API_SUCCESS) {
        messageSuccess(message);
        onLogin();
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
  const onLogin = async () => {
    try {
      const request = {
        token,
        fbId
      };
      const { code, message, responseBase } = await loginFbInfluencer(request);
      if (code === CODE_API_SUCCESS) {
        console.log(responseBase);
        messageSuccess(message);
        authStore.login(responseBase.data, ROLE_I);
        return router.push(APP_DASHBOARD_I_P);
      } else {
        messageError(message);
      }
    } catch (error) {
      console.log(error);
    }
  };
  useEffect(() => {
    getConfig(listCity, setCity);
    getConfig(listCareer, setCareer);
    getConfig(listChannel, setChannel);
    getConfig(listTypesInteraction, setType);
    getConfig(listIndustry, setIndustry);
  }, [id]);
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
          <h2>Đăng ký Influencer</h2>
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
                <Col xs={24} md={12}>
                  <Form.Item label="Ngày sinh" rules={[...baseRule]} name={fields.FIELD_INFLUENCER_DOB}>
                    <DatePicker disabled={!update} format={DEFAULT_FORMAT_DATE} />
                  </Form.Item>
                </Col>
                <Col xs={24} md={12}>
                  <Form.Item label="Địa chỉ" rules={[...baseRule]} name={fields.FIELD_INFLUENCER_ADDRESS}>
                    <Input disabled={!update} />
                  </Form.Item>
                </Col>
                <Col xs={24} md={12}>
                  <Form.Item label="Tỉnh/Thành phố" rules={[...baseRule]} name={fields.FIELD_INFLUENCER_PROVINCE}>
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
                  <Form.Item label="Nghề nghiệp" rules={[...baseRule]} name={fields.FIELD_INFLUENCER_WORK}>
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
                  <Form.Item label="Ngành quan tâm" rules={[...baseRule]} name={fields.FIELD_INFLUENCER_WORKS}>
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
                  <Form.Item label="Giới tính" rules={[...baseRule]} name={fields.FIELD_INFLUENCER_GENDER}>
                    <Radio.Group disabled={!update} options={G_LIST} />
                  </Form.Item>
                </Col>
                <Col xs={24} md={12}>
                  <Form.Item label="Tình trạng hôn nhân" rules={[...baseRule]} name={fields.FIELD_INFLUENCER_MARRIED}>
                    <Radio.Group disabled={!update} options={M_LIST} />
                  </Form.Item>
                </Col>
                <Col xs={24} xl={12}>
                  <Form.Item label="Tên ngân hàng" rules={[...baseRule]} name={fields.FIELD_INFLUENCER_BANK_N}>
                    <Input disabled={!update} />
                  </Form.Item>
                </Col>
                <Col xs={24} xl={12}>
                  <Form.Item label="Số tài khoản" rules={[...baseRule]} name={fields.FIELD_INFLUENCER_BANK_A}>
                    <Input disabled={!update} />
                  </Form.Item>
                </Col>
              </Row>
              <Row gutter={32} className="influencer--row">
                <Col xs={24} xl={12}>
                  <Form.Item label="Giới thiệu bản thân" rules={[...baseRule]} name={fields.FIELD_INFLUENCER_INTRO}>
                    <MyEditor
                      disabled={!update}
                      onBlur={(_e, editor) =>
                        form.setFieldsValue({ [fields.FIELD_INFLUENCER_INTRO]: editor.getData() })
                      }
                    />
                  </Form.Item>
                </Col>
                <Col xs={24} xl={12}>
                  <Form.Item label="Kinh nghiệm bán hàng, review" rules={[...baseRule]} name={fields.FIELD_INFLUENCER_EXP}>
                    <MyEditor
                      disabled={!update}
                      onBlur={(_e, editor) => form.setFieldsValue({ [fields.FIELD_INFLUENCER_EXP]: editor.getData() })}
                    />
                  </Form.Item>
                </Col>
                <Col xs={24} xl={12}>
                  <Form.Item label="Link profile FB" name={fields.FIELD_INFLUENCER_FB}>
                    <Input disabled={!update} />
                  </Form.Item>
                </Col>
                <Col xs={24} xl={12}>
                  <Form.Item label="Số tương tác trung bình 1 bài đăng" rules={[...baseRule]} name={fields.FIELD_INFLUENCER_TT}>
                    <Input disabled={!update} />
                  </Form.Item>
                </Col>
                <Col xs={24}>
                  <Form.Item label="Các kênh tương tác có kinh nghiệm" rules={[...baseRule]} name={fields.FIELD_INFLUENCER_CHANEL}>
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
                  <Form.Item label="Các loại tương tác có kinh nghiệm" rules={[...baseRule]} name={fields.FIELD_INFLUENCER_TYPE}>
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
                    Đăng ký
                  </Button>
                </Form.Item>
              </Row>
            </Col>
          </Row>
        </Form>
      </div>
    </LayoutM>
  );
}

export default Register;
