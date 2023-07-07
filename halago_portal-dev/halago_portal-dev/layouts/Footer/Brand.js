import React, { useState, useEffect, useContext } from "react";


import { Button, Col, Divider, Form, Input, Row } from "antd";

import styles from "./Number.module.scss";
import { getListIntroduce, getListNewsPortal } from "services/portal";
import { messageError } from "utils/messageM";
import { ACTION_R, CODE_API_SUCCESS, USER_B, USER_I } from "utils/constants";
import bg from './bg.jpg'

const Brand = () => {

  const [br, setBr] = useState(null);
  const getList = async () => {
    try {

      const request = {};
      const { code, message, responseBase } = await getListIntroduce(request);
      if (code === CODE_API_SUCCESS) {
        const { introduceBrandDeployment } = responseBase.data.brandDeployment;
        setBr(introduceBrandDeployment);
      } else {
        messageError(message);
      }
    } catch (error) {
      console.log(error);
      messageError(error.toString);
    }
  };

  useEffect(() => {
    getList();
  }, []);

  return (
    <section
      className={styles.section_3}
      style={{ backgroundImage: `url(${bg})` }}
    >
      <div className={`container-main`}>
        <Row align="middle">
          <Col xs={24} xxl={6}>
            <h3 className={styles.section_title}>{br?.title}</h3>
            <p>{br?.description}</p>
          </Col>
          <Col xs={0} xxl={2} />
          <Col xs={24} xxl={16}>
            <Row gutter={[64, 0]} className={styles.margin_0}>
              {
                [
                  'img',
                  'imgSecond',
                  'imgThird',
                  'imgFour',
                  'imgFive',
                  'imgSix',
                  'imgSeven',
                  'imgEight',
                  'imgNine',
                  'imgTen',
                  'imgEleven',
                  'imgTwelve',
                ].map(imgKey => <Col key={imgKey} xs={24} sm={12} md={12} lg={8} xl={6} className={styles.section_3_item_brand}>
                  {br?.[imgKey] && <div>
                    <img src={br?.[imgKey].img} alt="" className={styles.img_brand} />
                  </div>}
                </Col>)
              }
            </Row>
          </Col>
        </Row>
      </div>
    </section>
  )
}

export default Brand;
