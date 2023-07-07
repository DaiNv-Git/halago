import React, { useState, useEffect, useContext } from "react";
import Head from "next/head";
import LayoutM from "layouts";
import PostsDiffComponent from "./PostsDiffComponent";
import styles from "../News.module.scss";
import { useTranslation } from "react-i18next";
import { getNewsPublic } from "services/portal";
import { messageError } from "utils/messageM";
import { CODE_API_SUCCESS } from "utils/constants";
import HtmlComponent from "components/Html/HtmlComponent";
import MeTaTag from "layouts/MetaTag/MetaTag";


const NewsDetail = ({ data }) => {
  const { t } = useTranslation();

  return (
    <LayoutM>
      <MeTaTag
        title={data.title}
        description={data.description.replace(/&lt;/g,'<').replace(/&gt;/g,'>').replace(/(<(.*?)>|&\w+;)/g,'')}
        image={data.thumbnail} />

      <div className="wrapper">
        <div className="container-main news">
          <div style={{ paddingTop: 80 }}>
            <h1 className={styles.detail_post_title}>{data.title}</h1>

            <div className={styles.detail_post_content}>
              <div
                dangerouslySetInnerHTML={{ __html: data.content }}
              />
            </div>

            <div className={styles.home_login_footer}>
              <h1 className={styles.home_login_footer_title}>{t("news.postsDiff")}</h1>

              <PostsDiffComponent />
            </div>
          </div>
        </div>
      </div>
    </LayoutM>
  );
}

NewsDetail.getInitialProps = async ({ query }) => {
  try {
    const res = await getNewsPublic({
      idNews: query.id
    });
    const result = res.responseBase.data;
    return {
      data: result
    }
  } catch (error) {
    console.log(error);
  }

}

export default NewsDetail