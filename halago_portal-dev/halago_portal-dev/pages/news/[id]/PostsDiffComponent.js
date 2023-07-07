import React, { useEffect, useState } from "react";
import { Row, Col } from "antd";
import styles from "../News.module.scss";
import { useTranslation } from "react-i18next";
import Link from "next/link";
import { APP_NEWS_DETAIL } from "utils/paths";
import { toSlug } from "utils/helpers";
import { getListNewsFooter } from "services/portal";
import { CODE_API_SUCCESS } from "utils/constants";
import { messageError } from "utils/messageM";


const PostsDiffComponent = () => {
  const { t } = useTranslation();

  const [data, setData] = useState([])
  const getListNews = async () => {
    try {
      const { code, message, responseBase } = await getListNewsFooter({});
      console.log("😡coh / file: PostsDiffComponent.js / line 20 / responseBase", responseBase)
      if (code === CODE_API_SUCCESS) {
        setData(responseBase.data.slice(0, 4) || [])
      } else {
        messageError(message);
      }
    } catch (error) {
      messageError(error.toString);
    }
  };
  useEffect(() => {
    getListNews()
  }, [])
  const renderListPost = () => {
    return data.map((item, index) => (
      <Link
        href={{
          pathname: APP_NEWS_DETAIL,
          query: { id: item.idNews, slug: toSlug(item.title) },
        }}
      >
        <Col xs={24} sm={12} md={12} lg={6} key={index} style={{ marginBottom: 20 }}>
          <div className={styles.post_row} style={{ height: '100%' }}>
            <img style={{ width: '100%', objectFit: 'cover' }} src={item.thumbnail} alt="arrow" />

            <h4>{item.title}</h4>
            <p>{item.des}</p>

            <a href="#!">
              <span>{t("common.readMore")}</span>
            </a>
          </div>
        </Col>
      </Link>
    ));
  };

  return <Row gutter={16} >{renderListPost()}</Row>;
};

export default PostsDiffComponent;
