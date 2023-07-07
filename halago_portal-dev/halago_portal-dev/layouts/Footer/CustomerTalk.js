import React, { useState, useEffect } from "react";
import { Card, Button, Tabs, Row, Col, Form, Input } from "antd";
import { messageError } from "utils/messageM";
import { CODE_API_SUCCESS } from "utils/constants";
import { getListBrandPortal } from "services/portal";
import { useTranslation } from "react-i18next";
import styles from "./Footer.module.scss";


const CustomerTalk = () => {
  const { t } = useTranslation();
  const [f, setF] = useState(null);

  const getList = async () => {
    try {
      const request = {};
      const { code, message, responseBase } = await getListBrandPortal(request);
      if (code === CODE_API_SUCCESS) {
        const { footer } = responseBase.data;
        setF(footer);
      } else {
        messageError(message);
      }
    } catch (error) {
      messageError(error.toString());
    }
  };





  useEffect(() => {

    getList();

  }, []);

  return (
    <div className={styles.customer}>
      <div className={styles.customer_title}>
        {t("brand.customer")} {t("brand.about")}
      </div>
      <Row>
        {f &&
          f.map((item, index) => {
            return (
              <Col xl={index == 0 ? 24 : 6} sm={index == 0 ? 24 : 12} xs={24} key={item.id}>
                <div
                  className={styles.customer_item}
                  style={index == 0 ? {
                    flexDirection: 'row',
                    gap: 20,
                    flexWrap: 'wrap'
                  } : {}}
                >
                  <div className={styles.customer_item__info}>
                    <img src={item.img} alt="avatar"
                      style={index == 0 ? {
                        width: 150,
                        height: 150
                      } : {}}
                    />

                    <div className={styles.customer_item__desc}>
                      <div className={styles.customer_item__info_name}>{item.cusName}</div>
                      <p style={{ marginBottom: 5 }}>"{item.description}"</p>
                      <i className={styles.customer_item__info_position}>-----{item.position}</i>
                    </div>
                  </div>
                </div>
              </Col>
            );
          })}
      </Row>
    </div>
  );
};

export default CustomerTalk;
