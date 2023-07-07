import React, { useState, useEffect, useContext } from "react";
import "utils/i18n";
import Carousel from "react-elastic-carousel";
import { Button, Col, Divider, Form, Input, Row } from "antd";
import { ArrowRightOutlined } from "@ant-design/icons";
import styles from "./Number.module.scss";
import { getListIntroduce, getListNewsPortal } from "services/portal";
import { messageError } from "utils/messageM";
import { ACTION_R, CODE_API_SUCCESS, USER_B, USER_I } from "utils/constants";
import { toSlug } from "utils/helpers";
import { useTranslation } from "react-i18next";
import { APP_NEWS_DETAIL } from "utils/paths";
import Link from "next/link";
import HtmlComponent from "components/Html/HtmlComponent";

const Magazine = () => {
  const { t } = useTranslation();
  const [data_getListNewsPortal, set_getListNewsPortal] = useState([]);

  const handel_getListNewsPortal = async () => {
    try {
      const request = {};
      const { code, message, responseBase } = await getListNewsPortal(request);
      if (code === CODE_API_SUCCESS) {
        if (responseBase.data && responseBase.data.newsPapers) {
          set_getListNewsPortal(responseBase.data.newsPapers);
        }
      } else {
        messageError(message);
      }
    } catch (error) {
      console.log(error);
      messageError(error.toString);
    }
  };

  useEffect(() => {
    handel_getListNewsPortal();
  }, []);

  const renderNews = () => {
    return data_getListNewsPortal.map((item, index) => {
      let a = item.description.slice(item.description.indexOf("=") + 2, item.description.indexOf("html") + 4);
      let b = a.replace('"', " ");
      return (
        <Col span={24} key={index} style={{ outline: "none", cursor: "pointer" }}>
          <div className={styles.postRow}>
            <div className={styles.postRowHead}>
              <Link href={b}>
                <img src={item.thumbnail} alt="Halago" className={styles.homePostImage} />
              </Link>
            </div>
            <h4 className={styles.postRowH4}>{item.title}</h4>
            <p className={styles.postRowP}>{<HtmlComponent html={item.description} />}</p>

            <Link
              href={
                b
              }
            >
              <a>
                {t("common.readMore")} <ArrowRightOutlined />
              </a>
            </Link>
          </div>
        </Col>
      );
    });
  };

  return (
    <section className={styles.section_6}>
      <div className={`container-main`}>
        <h3 className={styles.section_title}>{t("home.s6.title")}</h3>
        <Divider className={styles.section_divider} />
        {data_getListNewsPortal && !!data_getListNewsPortal.length && (
          <Carousel
            renderPagination={() => {
              return <div></div>;
            }}
            enableAutoPlay
            focusOnSelect={false}
            breakPoints={[
              { width: 1, itemsToShow: 1 },
              { width: 550, itemsToShow: 2 },
              { width: 768, itemsToShow: 3 },
              { width: 1200, itemsToShow: 4 },
            ]}
          >
            {renderNews()}
          </Carousel>
        )}
      </div>
    </section>
  );
};

export default Magazine;
