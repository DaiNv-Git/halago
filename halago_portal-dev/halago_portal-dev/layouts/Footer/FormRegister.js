import React, { useState, useEffect, useContext } from "react";
import { Button, Col, Divider, Form, Input, Row } from "antd";
import styles from "./Footer.module.scss";
import { getListIntroduce } from "services/portal";
import { messageError } from "utils/messageM";
import { ACTION_R, CODE_API_SUCCESS, USER_B, USER_I } from "utils/constants";
import AuthStoreContext from "stores/AuthStore";
import { C_EMAIL, C_NAME, C_PHONE } from "utils/fields";
import { baseRule } from "utils/validation";
import { sendRequestContact } from "utils/helpers";
import { useTranslation } from "react-i18next";
import HtmlComponent from "components/Html/HtmlComponent";

const FormRegister = () => {
  const { t } = useTranslation();
  const authStore = useContext(AuthStoreContext);
  const [form] = Form.useForm();
  const { setVisibleModal, setUser, setAction, setActiveTab } = authStore;
  const [joinUsData, setJoinUsData] = useState(null);



  const getList = async () => {
    try {
      const request = {};
      const { code, message, responseBase } = await getListIntroduce(request);
      if (code === CODE_API_SUCCESS) {
        const { joinUs } = responseBase.data;
        setJoinUsData(joinUs);
      } else {
        messageError(message);
      }
    } catch (error) {
      messageError(error.toString);
    }
  };
  const openModal = (open, user, action, tab) => {
    setVisibleModal(open);
    setUser(user);
    setAction(action);
    setActiveTab(tab);
  };
  const sendContact = async () => {
    await form.validateFields([C_NAME]);
    sendRequestContact(form.getFieldsValue());
  };

  useEffect(() => {
    getList();
  }, []);


  return (
    <section className={styles.section_4}>
      <Row className="container-main">
        <Col xs={24} xl={12} className={styles.section_4_left}>
          <Form form={form} className={styles.section_4_form}>
            <div className={styles.section_4_form_title}>{t("home.s4.title")}</div>
            <Form.Item rules={[...baseRule]} name={C_NAME} className={styles.section_4_form_item}>
              <Input className={styles.section_4_form_item_input} placeholder={t("home.s4.plhName")} />
            </Form.Item>
            <Form.Item name={C_PHONE} className={styles.section_4_form_item}>
              <Input className={styles.section_4_form_item_input} placeholder={t("home.s4.plhPhone")} />
            </Form.Item>
            <Form.Item name={C_EMAIL} className={styles.section_4_form_item}>
              <Input className={styles.section_4_form_item_input} placeholder={t("home.s4.plhEmail")} />
            </Form.Item>
            <Form.Item className={styles.section_4_form_button}>
              <Button onClick={sendContact}>{t("home.s4.btnRegister")}</Button>
            </Form.Item>
          </Form>
        </Col>
        <Col xs={24} xl={12} className={styles.section_4_right}>
          <Divider className={styles.section_divider} style={{ width: 170 }} />
          <h3 className={styles.section_title} style={{ marginTop: 48 }}>
            {joinUsData?.title}
          </h3>

          <p>
            <HtmlComponent html={joinUsData?.content} />
          </p>

          <div className={styles.section_4_right_btn}>
            <Button
              onClick={() => openModal(true, USER_B, ACTION_R, USER_B + "-" + ACTION_R)}
              className={styles.section_4_right_button_1}
            >
              {t("home.s4.btnRegisterBrand")}
            </Button>
            <Button
              onClick={() => openModal(true, USER_I, ACTION_R, USER_I)}
              className={styles.section_4_right_button_2}
            >
              {t("home.s4.btnRegisterInfluencer")}
            </Button>
          </div>
        </Col>
      </Row>
    </section>
  );
};

export default FormRegister;
