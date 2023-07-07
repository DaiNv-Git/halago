import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import Head from "next/head";
import LayoutM from "layouts";
import { getVision } from "services/portal";
import { messageError } from "utils/messageM";
import { CODE_API_SUCCESS } from "utils/constants";
import HtmlComponent from "components/Html/HtmlComponent";

const meta = {
  title: "Halago - Seller",
  description:
    "Rất nhiều nhãn hàng đã tin tưởng, hợp cùng Halago. Hãy liên hệ với chúng ngay để cùng nhau thực hiện các kế hoạch",
  image: 'https://imgsonla.semob.info/img/logo-halago-meta.jpg',
};

const Vision = () => {
  const { t } = useTranslation();
  const [loading, setLoading] = useState(false);
  const [data_getVision, setDataAboutUs] = useState({});

  const { content } = data_getVision;

  useEffect(() => {
    handle_getVision();
  }, []);

  const handle_getVision = async () => {
    try {
      setLoading(true);
      const request = {};
      const { code, message, responseBase } = await getVision(request);
      if (code === CODE_API_SUCCESS) {
        setDataAboutUs(responseBase.data);
      } else {
        messageError(message);
      }
    } catch (error) {
      messageError(error.toString);
    } finally {
      setLoading(false);
    }
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
        <div style={{ padding: "0 20px" }} className="container-main page-content">
          <h1>{t("common.vision")}</h1>
          <div className="vision_about_us">
            <HtmlComponent html={content} />
          </div>
        </div>
      </LayoutM>
    )
  );
};

export default Vision;
