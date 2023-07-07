import React, { useState, useEffect } from "react";
import Head from "next/head";
import { Card, Button, Tabs, Row, Col, Form, Input } from "antd";
import LayoutM from "layouts";
import { C_EMAIL, C_NAME, C_NOTE, C_PHONE } from "utils/fields";
import { baseRule } from "utils/validation";
import { sendRequestContact } from "utils/helpers";
import { messageError } from "utils/messageM";
import { CODE_API_SUCCESS } from "utils/constants";
import { getListBrandPortal } from "services/portal";
import { useTranslation } from "react-i18next";

const { Meta } = Card;

const { TabPane } = Tabs;
const layout = {
  labelCol: {
    span: 8,
  },
  wrapperCol: {
    span: 20,
  },
};
const meta = {
  title: "Halago - Seller",
  description:
    "Rất nhiều nhãn hàng đã tin tưởng, hợp cùng Halago. Hãy liên hệ với chúng ngay để cùng nhau thực hiện các kế hoạch",
  image: 'https://imgsonla.semob.info/img/logo-halago-meta.jpg', 
};

const Seller = () => {
  const { t } = useTranslation();
  const [form] = Form.useForm();
  const [tabMode, setTabMode] = useState("left");
  const [h, setH] = useState(null);
  const [b, setB] = useState(null);
  const [f, setF] = useState(null);

  const getList = async () => {
    try {
      const request = {};
      const { code, message, responseBase } = await getListBrandPortal(request);
      if (code === CODE_API_SUCCESS) {
        const { header } = responseBase.data;
        const { body } = responseBase.data;
        const { footer } = responseBase.data;
        setH(header);
        setB(body);
        setF(footer);
      } else {
        console.log(message);
        messageError(message);
      }
    } catch (error) {
      console.log(error);
      messageError(error.toString());
    }
  };
  const changeTabsMode = () => {
    let tabMode = window.innerWidth < 992 ? "top" : "left";
    setTabMode(tabMode);
  };

  const onFinish = (values) => {
    console.log("Success:", values);
  };

  const onFinishFailed = (errorInfo) => {
    console.log("Failed:", errorInfo);
  };
  const sendContact = async () => {
    await form.validateFields([C_NAME]);
    sendRequestContact(form.getFieldsValue());
  };

  useEffect(() => {
    changeTabsMode();
    getList();
    window.addEventListener("resize", () => {
      changeTabsMode();
    });
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
        <meta property="og:image" content={meta.image} />
      </Head>
      <div className="container-main page-content">
        <div className="container">
          <div className="seller">
            <div className="banner" style={{ backgroundImage: "url(" + (h && h[0].img) + ")" }}>
              <div className="banner-content">
                <div className="banner-content__title">{(h && h[0].title) || null}</div>
                <div className="banner-content__desc">{(h && h[0].description) || null}</div>
              </div>
            </div>
            <div className="network page-content">
              <div className="network-title text-center">{(h && h[1].description) || null}</div>
              <div className="network-image text-right">
                <img src={h && h[1].img} />
              </div>
            </div>
            <div className="highlight">
              <div className="highlight-title text-center">
                <h3>{t("brand.typicalCampaign")}</h3>
                <div className="custom-divider" />
              </div>
              <div className="highlight-content">
                <div className="content-tab">
                  {/* <Tabs tabPosition={tabMode} defaultActiveKey={7}>
                    {b &&
                      b.map((item) => {
                        return (
                          <TabPane tab={item.title} key={item.id}>
                            <div className="content-tab__detail">
                              <img src={item.img} alt="chiến dịch tiêu biếu" />
                              <div className="detail-desc">
                                <div className="detail-desc__left">
                                  <h3>{item.title}</h3>
                                  <p>
                                    {item.totalLike} likes | {item.totalComment} comment | {item.totalShare} share
                                  </p>
                                </div>
                                <div className="detail-desc__right">{item.description}</div>
                              </div>
                            </div>
                          </TabPane>
                        );
                      })}
                  </Tabs> */}
                  <Row>
                    {b && b.map((item) => {
                      return (
                        <Col xl={6} sm={12} xs={24} key={item.id}>
                            <div className="campaign-content__item">
                              <Card hoverable cover={<img alt="example" src={item.img} />}>
                                <Meta title={item.title} />
                              </Card>
                            </div>
                        </Col>
                      );
                    })}
                  </Row>
                </div>
              </div>
            </div>
            {/* <div className="customer">
              <Row>
                <Col xl={6} sm={12} xs={24}>
                  <div className="customer-title">
                    {t("brand.customer")} <br /> {t("brand.about")}
                  </div>
                </Col>
                {f &&
                  f.map((item) => {
                    return (
                      <Col xl={6} sm={12} xs={24} key={item.id} className="custom-col">
                        <div className="customer-item">
                          <div className="customer-item__desc">{item.description}</div>
                          <div className="customer-item__info">
                            <img src={item.img} alt="avatar" />
                            <div className="account">
                              <div className="name">{item.cusName}</div>
                              <div className="position">{item.position}</div>
                            </div>
                          </div>
                        </div>
                      </Col>
                    );
                  })}
              </Row>
            </div> */}
            <div className="seller-register page-content">
              <div className="highlight">
                <div className="highlight-title text-center">
                  <h3>{t("home.s4.title3")}</h3>
                  <div className="custom-divider" />
                </div>
              </div>
              <div className="seller-register__content">
                <Row>
                  <Col xl={12} sm={24} xs={24}>
                    <div className="content-image">
                      <img src="/images/seller/seller-register.png" alt="Đăng ký nhận tư vấn dịch vụ" />
                    </div>
                  </Col>
                  <Col xl={12} sm={24} xs={24}>
                    <div className="content-form">
                      <Form form={form} {...layout} name="basic" onFinish={onFinish} onFinishFailed={onFinishFailed}>
                        <Form.Item name={C_NAME} rules={[...baseRule]}>
                          <Input placeholder={t("home.s4.plhName")} />
                        </Form.Item>
                        <Form.Item name={C_PHONE}>
                          <Input placeholder={t("home.s4.plhPhone")} />
                        </Form.Item>
                        <Form.Item name={C_EMAIL}>
                          <Input placeholder={t("home.s4.plhEmail")} />
                        </Form.Item>
                        <Form.Item name={C_NOTE}>
                          <Input placeholder={t("home.s4.plhNote")} />
                        </Form.Item>
                        <Form.Item style={{ textAlign: "right" }}>
                          <Button type="primary" onClick={sendContact} className="custom-btn">
                            {t("home.s4.btnSend")}
                          </Button>
                        </Form.Item>
                      </Form>
                    </div>
                  </Col>
                </Row>
              </div>
            </div>
          </div>
        </div>
      </div>
    </LayoutM>
  );
};

export default Seller;
