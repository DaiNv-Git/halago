import React, { useState, useEffect } from "react";
import Head from "next/head";
import Link from "next/link";
import LayoutM from "layouts";
import { ArrowRightOutlined } from "@ant-design/icons";
import { messageError } from "utils/messageM";
import { toSlug } from "utils/helpers";
import styles from "./News.module.scss";
import { useTranslation } from "react-i18next";
import { getListNewsPortal } from "services/portal";
import { CATEGORIES_NEWS, CODE_API_SUCCESS } from "utils/constants";
import { APP_NEWS_DETAIL } from "utils/paths";
import HtmlComponent from "components/Html/HtmlComponent";

const meta = {
  title: "Halago - News",
  description: "Halago - News",
  thumbnail: "",
  image: "https://imgsonla.semob.info/img/logo-halago-meta.jpg",
};

const data = [1, 2, 3];

const News = () => {
  const { t } = useTranslation();
  const [data_getListNewsPortal, set_getListNewsPortal] = useState({});
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    handel_getListNewsPortal();
  }, []);

  const handel_getListNewsPortal = async () => {
    try {
      setLoading(true);
      const request = {};
      const { code, message, responseBase } = await getListNewsPortal(request);
      if (code === CODE_API_SUCCESS) {
        set_getListNewsPortal(responseBase.data);
      } else {
        messageError(message);
      }
    } catch (error) {
      console.log(error);
      messageError(error.toString);
    } finally {
      setLoading(false);
    }
  };

  const renderPosts = (result) => {
    return result.map((item, index) => {
      // console.log(item);
      let a = item.description.slice(item.description.indexOf("=") + 2, item.description.indexOf("html") + 4);
      let b = a.replace('"', " ");
      return (
        <Link
          href={
            item.type == 3
              ? b
              : {
                  pathname: APP_NEWS_DETAIL,
                  query: { id: item.idNews, slug: toSlug(item.title) },
                }
          }
        >
          <div style={{ cursor: "pointer" }} className={styles.section_6_row} key={index}>
            <div className={styles.section_6_row_thumbnail}>
              <img alt="Halago" src={item.thumbnail} />
            </div>
            <div className={styles.section_6_row_content}>
              <small className={styles.section_6_row_thumbnail_date}>
                <i>{item.created}</i>
              </small>
              <h2 className={styles.section_6_row_content_title}>{item.title}</h2>
              <div className={styles.section_6_row_content_description}>
                {<HtmlComponent html={item.description} />}
              </div>
              <div className={styles.section_6_row_content_more}>
                <small>
                  <i>
                    <a>
                      {t("common.readMore")} <ArrowRightOutlined />
                    </a>
                  </i>
                </small>
              </div>
            </div>
          </div>
        </Link>
      );
    });
  };

  const renderTitle = (keyTitle) => {
    if (keyTitle == CATEGORIES_NEWS.internalNews) {
      return <p className={styles.section_title}>{t("news.internal")}</p>;
    } else if (keyTitle == CATEGORIES_NEWS.foreignNews) {
      return <p className={styles.section_title}>{t("news.foreign")}</p>;
    } else if (keyTitle == CATEGORIES_NEWS.newsPapers) {
      return <p className={styles.section_title}>{t("news.papers")}</p>;
    }

    return null;
  };

  const renderContent = () => {
    return Object.keys(data_getListNewsPortal).map((key, index) => {
      return (
        <div className={styles.div_row} key={index}>
          <div className={styles.div_title}>{renderTitle(key)}</div>

          {renderPosts(data_getListNewsPortal[key])}
        </div>
      );
    });
  };

  return (
    !loading && (
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
        <div className="wrapper">
          <div className="container-main news">{renderContent()}</div>
        </div>
      </LayoutM>
    )
  );
};

export default News;
