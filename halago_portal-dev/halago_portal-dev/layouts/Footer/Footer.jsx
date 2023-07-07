import React, { useEffect, useState } from "react";
import { ImageLogo, LogoZalo, LogoFacebook, LogoYoutube, LogoTiktok, LogoInstagram, IconCall } from "assets/images";
import styles from "./Footer.module.scss";
import { Col, Divider, Row } from "antd";
import Link from "next/link";
import { APP_CAMPAIGN, APP_HOME, APP_INFLUENCER, APP_KOL, APP_SEARCH, APP_SELLER, APP_NEWS_DETAIL } from "utils/paths";
import { useTranslation } from "react-i18next";
import { getListFooter, getListNewsFooter } from "services/portal";
import { messageError } from "utils/messageM";
import { CODE_API_SUCCESS } from "utils/constants";
import FormRegister from "./FormRegister";
import CustomerTalk from "./CustomerTalk";
import Magazine from "./Magazine";
import Brand from "./Brand";
import { toSlug } from "utils/helpers";

const Footer = () => {
  const { t } = useTranslation();
  const [i, setI] = useState(null);
  const [news, setNews] = useState([]);
  const getList = async () => {
    try {
      const request = {};
      const { code, message, responseBase } = await getListFooter(request);
      if (code === CODE_API_SUCCESS) {
        setI(responseBase.data[0]);
      } else {
        messageError(message);
      }
    } catch (error) {
      console.log(error);
      messageError(error.toString);
    }
  };
  const getListNews = async () => {
    try {
      const { code, message, responseBase } = await getListNewsFooter({});
      if (code === CODE_API_SUCCESS) {
        setNews(responseBase.data || [])
      } else {
        messageError(message);
      }
    } catch (error) {
      messageError(error.toString);
    }
  };


  useEffect(() => {
    getList();
    getListNews()
    try {
      window.fbAsyncInit = () => {
        FB.init({
          appId: '554203175818374',
          // appId: '407011427011887',
          cookie: true,  // enable cookies to allow the server to access
          xfbml: true,  // parse social plugins on this page
          version: 'v2.1' // use version 2.1
        });
      };
      (function (d, s, id) {
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) return;
        js = d.createElement(s); js.id = id;
        js.src = "//connect.facebook.net/en_US/sdk.js";
        fjs.parentNode.insertBefore(js, fjs);
      }(document, 'script', 'facebook-jssdk'));
    } catch (error) {

    }
  }, []);

  useEffect(() => {
    try {
      if (i?.facebook && FB) window.fbAsyncInit()
    } catch (error) {

    }
  }, [i]);

  const scrollTop = () => {
    window.scrollTo(0, 0)
  }
  return (
    <>
      <a className={styles.call_now} target="_top" href={`tel:${i?.hotline}`}>
        <div className={styles.call_now_img_call}>
          <img src={IconCall} />
        </div>
        <p>{i?.hotline}</p>
      </a>
      <Brand />
      <Magazine />
      <CustomerTalk />
      <FormRegister />
      <div className={styles.footer}>
        <div className="container-main">
          {/* <Divider className={styles.footer_divider} /> */}
          <div
            className={styles.logo}
            onClick={scrollTop}
          >
            <img src={ImageLogo} />
          </div>
          <Row>
            <Col xs={24} xl={6} className={styles.footer_info}>
              <h3 className={styles.footer_info_name}>{i?.company || "Sáng lập và vận hành bởi My Hồng Hà Media Group"}</h3>
              <p>
                <b>{t("footer.address")}: {i?.address}</b>
              </p>
              <p className={styles.yellow}>
                <b>Email: {i?.email} </b>
              </p>
              <p className={styles.yellow}>
                <b>Hotline: {i?.hotline}</b>
              </p>
              <p>
                <b>Mã số thuế: {i?.taxCode}</b>
              </p>
              {/* <p>
              <b>Fanpage: </b>
              <a href={i?.facebook} target="_blank">
                <a href={i?.facebook} target="_blank">
                  <img src={LogoFacebook} style={{ width: 20, height: 20 }} />
                </a>
              </a>
            </p>
            <p>
              <b>Zalo: </b>
              {i && i.zalo && (
                <a href={i?.zalo} target="_blank">
                  <img src={LogoZalo} style={{ width: 20, height: 20 }} />
                </a>
              )}
            </p> */}
            </Col>
            <Col xl={2} />
            <Col xs={12} xl={5} className={styles.footer_menu}>
              <ul>
                <li className={styles.footer_menu_item}>
                  <Link href={APP_HOME} passHref>
                    <a>{t("header.home")}</a>
                  </Link>
                </li>
                <li className={styles.footer_menu_item}>
                  <Link href={APP_CAMPAIGN} passHref>
                    <a>{t("header.campaign")}</a>
                  </Link>
                </li>
                <li className={styles.footer_menu_item}>
                  <Link href={APP_KOL} passHref>
                    <a>{t("header.kol")}</a>
                  </Link>
                </li>
                <li className={styles.footer_menu_item}>
                  <Link href={APP_SELLER} passHref>
                    <a>{t("header.brand")}</a>
                  </Link>
                </li>
              </ul>
            </Col>
            <Col xs={12} xl={5} className={styles.footer_menu}>
              <ul>
                <li className={styles.footer_menu_item}>
                  <Link href={APP_INFLUENCER} passHref>
                    <a>{t("header.influencer")}</a>
                  </Link>
                </li>
                <li className={styles.footer_menu_item}>
                  <Link href={APP_SEARCH} passHref>
                    <a>{t("header.search")}</a>
                  </Link>
                </li>

                <li className={styles.footer_menu_item}>
                  <Link href={'/about-us'} passHref>
                    <a>{t("header.aboutUs")}</a>
                  </Link>
                </li>

                <li className={styles.footer_menu_item}>
                  <Link href={'/vision'} passHref>
                    <a>{t("header.vision")}</a>
                  </Link>
                </li>
              </ul>
            </Col>
            <Col xs={24} xl={6} className={styles.footer_menu}>
              <ul>
                <li className={styles.footer_menu_item}>
                  <Link href="" passHref>
                    <a>{t("footer.contact_w_us")}</a>
                  </Link>
                </li>
              </ul>
              {/* <img src={require("./image.png")} width="100%" alt="" /> */}
              {!!i?.facebook && <div
                className="fb-page"
                data-href={i?.facebook}
                data-tabs=""
                data-width="300"
                data-height="70"
                data-small-header="false"
                data-adapt-container-width="false"
                data-hide-cover="false"
                data-show-facepile="false"
              >
                <blockquote
                  cite={i?.facebook}
                  className="fb-xfbml-parse-ignore"
                >
                  <a href={i?.facebook}>Facebook</a>
                </blockquote>
              </div>}
              <div className={styles.footer_groups_social}>
                <a href={i?.zalo} target="_blank">
                  <img src={LogoZalo} style={{ width: 55, height: 55 }} />
                </a>
                <a href={i?.youtube} target="_blank">
                  <img src={LogoYoutube} style={{ width: 55, height: 55 }} />
                </a>
                <a href={i?.tiktok} target="_blank">
                  <img src={LogoTiktok} style={{ width: 50, height: 50 }} />
                </a>
                <a href={i?.instagram} target="_blank">
                  <img src={LogoInstagram} style={{ width: 50, height: 50 }} />
                </a>
              </div>
            </Col>

            <Col xs={24}>
              <Divider className={styles.footer_divider} />
            </Col>
            <Col xs={12} xl={6} className={styles.footer_menu}>
              <p className={styles.footer_menu_item}>
                TRỢ GIÚP KHÁCH HÀNG
              </p>
              <p className={styles.bold}>
                + Hình thức thanh toán
              </p>
              <p className={styles.bold}>
                + Câu hỏi thường gặp
              </p>
              <p className={styles.bold}>
                + Tài liệu hỗ trợ
              </p>
              <p className={styles.bold}>
                Kỹ thuật: <span className={styles.yellow}>{i?.hotline}</span>
              </p>

            </Col>
            <Col xs={12} xl={6}>
            </Col>
            <Col xs={12} className={styles.footer_menu}>

              {news.map(item =>
                <Link href={{
                  pathname: APP_NEWS_DETAIL,
                  query: { id: item.idNews, slug: toSlug(item.title) },
                }}>
                  <p style={{
                    whiteSpace: 'nowrap',
                    overflow:'hidden',
                    textOverflow: 'ellipsis'
                  }}>
                    <a>+ {item.title}</a>
                  </p>
                </Link>
              )}



            </Col>
          </Row>
        </div>
        <div className={`${styles.footer_bottom} ft-bottom`}>
          <a
            href="https://adsmax.vn/"
            className="container-main"
            style={{ display: "flex", justifyContent: "center", alignItems: "center", height: "100%", fontWeight: 'bold', fontSize: 16 }}
          >
            Lập trình bới AdsMax.vn
        </a>
        </div>
      </div>
    </>
  );
};

export default Footer;
