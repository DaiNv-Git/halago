import React, { useState, useEffect, useContext } from "react";
import Head from "next/head";
import "utils/i18n";
import { observer } from "mobx-react-lite";
import {
  ImageHomeRT,
  ImageHomeSection1_04,
  ImageHomeSection1_05,
  ImageHomeSection2_01,
  ImageHomeSection2_02,
  ImageHomeSection2_03,
  ImageHomeSection2_04,
  ImageHomeTopDot,
  LogoMeta,
} from "assets/images";
import Header from "layouts/Header/Header";
import Carousel from "react-elastic-carousel";
import { Button, Col, Divider, Form, Input, Row } from "antd";
import { ArrowRightOutlined } from "@ant-design/icons";
import { IconDown, IconHand, IconUp } from "assets/icons";
import Footer from "layouts/Footer/Footer";
import styles from "./Home.module.scss";
import { getListIntroduce, getListNewsPortal } from "services/portal";
import { messageError } from "utils/messageM";
import { ACTION_R, CODE_API_SUCCESS, USER_B, USER_I } from "utils/constants";
import AuthStoreContext from "stores/AuthStore";
import { C_EMAIL, C_NAME, C_PHONE } from "utils/fields";
import { baseRule } from "utils/validation";
import { sendRequestContact, toSlug } from "utils/helpers";
import { useTranslation } from "react-i18next";
import { APP_NEWS_DETAIL } from "utils/paths";
import Link from "next/link";
import HtmlComponent from "components/Html/HtmlComponent";

const meta = {
  url: 'https://halago.vn',
  title: 'Halago - Nền Tảng Influencer KOLs Marketing Review Seller',
  description: 'Halago là nền tảng marketing có sự hợp tác với rất nhiều Influencer, Kol, sao hạng A nổi tiếng trên khắp cả trong và ngoài nước',
  image: 'https://imgsonla.semob.info/img/logo-halago-meta.jpg',
  keyword: 'Halago, marketing, Influencer, Kol, sao hạng A'
}
const Home = observer(() => {
  const { t } = useTranslation();
  const authStore = useContext(AuthStoreContext);
  const [form] = Form.useForm();
  const { setVisibleModal, setUser, setAction, setActiveTab } = authStore;
  const [h, setH] = useState(null);
  const [b, setB] = useState(null);
  const [r, setR] = useState(null);
  const [f, setF] = useState(null);
  const [br, setBr] = useState(null);
  const [joinUsData, setJoinUsData] = useState(null);
  const [data_getListNewsPortal, set_getListNewsPortal] = useState([]);
  const [loading, setLoading] = useState(false);

  const getList = async () => {
    try {
      setLoading(true);
      const request = {};
      const { code, message, responseBase } = await getListIntroduce(request);
      if (code === CODE_API_SUCCESS) {
        const { introduceHeader } = responseBase.data.header;
        const { introduceBodyList } = responseBase.data.body;
        const { introduceReasonList } = responseBase.data.reason;
        const { introduceFooter } = responseBase.data.footer;
        const { introduceBrandDeployment } = responseBase.data.brandDeployment;
        const { joinUs } = responseBase.data;

        setH(introduceHeader);
        setB(introduceBodyList);
        setR(introduceReasonList);
        setF(introduceFooter);
        setBr(introduceBrandDeployment);
        setJoinUsData(joinUs);
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

  useEffect(() => {
    handel_getListNewsPortal();
  }, []);

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

  return (
    !loading && (
      <div className="app_wrapper">
        <Head>
          <link
            href="https://fonts.googleapis.com/css2?family=Inter&family=Prompt&family=Raleway&family=Roboto&family=Work+Sans&display=swap"
            rel="stylesheet"
          />
          <title>{meta.title}</title>
          <meta name="url" content={meta.url} />
          <meta name="keywords" content={meta.keyword} />
          <meta
            name="description"
            content={meta.description}
          />
          <meta name="og:title" content={meta.title} />
          <meta name="og:type" content="article" />
          <meta name="og:url" content={meta.url} />
          <meta name="og:site_name" content={meta.title} />
          <meta
            name="og:description"
            key="description"
            content={meta.description}
          />
          <meta name="fb:page_id" content="157800657956361" />
          <meta name="og:country-name" content="VN" />
          <meta name="geo.region" content="VN" />
          <meta name="geo.placename" content="VN" />
          <meta property="og:type" content="article" />
          <meta property="og:title" content={meta.title} key="title" />
          <meta property="og:image" content={meta.image} itemProp="thumbnailUrl" />
          <meta
            property="og:description"
            content={meta.description}
            key="description"
          />
        </Head>
        <div className="wrapper">
          <div className="page_wrapper">
            <div className={styles.page_bg} />
            <section className={styles.section_0}>
              <Header />
              <img src={ImageHomeRT} alt="Halago" className={styles.image_rt} />
              <img src={ImageHomeTopDot} alt="" className={styles.image_dot} />
              <div className={`container-main`} style={{ paddingTop: 63 }}>
                <Row align="middle">
                  <Col xs={24} xl={9} className={styles.section_0_text}>
                    <h1>{h?.description}</h1>
                    <h3 style={{}}>{h?.content}</h3>
                    {/* <p>{h?.content}</p> */}
                    {/* <Button onClick={() => window.open("https://www.facebook.com/adshalago", "_blank")}>
                      {t("home.s0.btnMore")} <ArrowRightOutlined />
                    </Button> */}
                  </Col>
                  <Col xs={24} xl={15} className={styles.section_0_image}>
                    <img src={h?.img.img} alt="" className={styles.image_0} />
                    <img src={h?.imgSecond.img} alt="" className={styles.image_1} />
                    <img src={h?.imgFive.img} alt="" className={styles.image_2} />
                    <img src={h?.imgThird.img} alt="" className={styles.image_like} />
                    <img src={h?.imgFour.img} alt="" className={styles.image_love} />
                  </Col>
                </Row>
              </div>
            </section>
            <section className={styles.section_5}>
              <div className={`container-main`}>
                <h3 className={styles.section_title} style={{ textAlign: "center" }}>
                  {t("home.s5.title")}
                </h3>
                <Divider className={styles.section_divider} style={{ margin: "0 auto" }} />
                <div
                  className={styles.section_5_content}
                  dangerouslySetInnerHTML={{
                    __html: h?.contentSecond,
                  }}
                />
              </div>
            </section>
            <section className={styles.section_1}>
              <img src={ImageHomeSection1_04} alt="" className={styles.section_1_04} />
              <img src={ImageHomeSection1_05} alt="" className={styles.section_1_05} />
              <div className={`container-main`}>
                <h3 className={styles.section_title}>{t("home.s1.title")}</h3>
                <Divider className={styles.section_divider} />
                <Row align="middle" gutter={[16, 48]} className={styles.margin_0}>
                  {b?.map((b, i) =>
                    i % 2 === 0 ? (
                      <>
                        <Col xs={24} xl={10} className={`${styles.section_1_text} padding_l_r_0`}>
                          <h4>
                            <img src={IconHand} alt="" />
                            {b.title}
                          </h4>
                          <p>{b.description}</p>
                          <span>{b.content}</span>
                        </Col>
                        <Col xs={24} xl={14} className={styles.section_1_image}>
                          <img src={b.img.img} alt={b.img.title} />
                        </Col>
                      </>
                    ) : (
                      <>
                        <Col xs={24} xl={14} className={styles.section_1_image}>
                          <img src={b.img.img} alt={b.img.title} />
                        </Col>
                        <Col xs={24} xl={10} className={`${styles.section_1_text} padding_l_r_0`}>
                          <h4>
                            <img src={IconHand} alt="" />
                            {b.title}
                          </h4>
                          <p>{b.description}</p>
                          <span>{b.content}</span>
                        </Col>
                      </>
                    ),
                  )}
                </Row>
              </div>
            </section>
            <section className={styles.section_2}>
              <h3 className={styles.section_title}>{t("home.s2.title")}</h3>
              <Divider className={styles.section_divider} style={{ margin: "0 auto" }} />
              <div className={`container-main`}>
                <Row align="middle" gutter={[64, 64]} className={styles.margin_0}>
                  <Col xs={24} xl={12} className="text-center">
                    <img src={r?.[0]?.img.img || ImageHomeSection2_01} className={styles.section_2_image_banner} alt="" />
                  </Col>
                  <Col xs={24} xl={12} className={styles.padding_0}>
                    <div className={styles.section_2_item}>
                      <div className={styles.section_2_item_head}>
                        <div className={styles.section_2_item_no}>1</div>
                        <div className={styles.section_2_item_title}>
                          <h3>{r && r[0]?.title}</h3>
                          <p>{r && r[0]?.description}</p>
                        </div>
                      </div>
                      <div className={styles.section_2_item_body}>
                        <div className={styles.section_2_item_body_up}>
                          <img src={IconUp} alt="" />
                          <p>{r && r[0].contentFirst}</p>
                        </div>
                        <Divider type="vertical" className={styles.section_2_item_body_divider} />
                        <div className={styles.section_2_item_body_down}>
                          <img src={IconDown} alt="" />
                          <p>{r && r[0].contentSecond}</p>
                        </div>
                      </div>
                    </div>
                    <div className={styles.section_2_item}>
                      <div className={styles.section_2_item_head}>
                        <div className={styles.section_2_item_no}>2</div>
                        <div className={styles.section_2_item_title}>
                          <h3>{r && r[1]?.title}</h3>
                          <p>{r && r[1]?.description}</p>
                        </div>
                      </div>
                      <div className={styles.section_2_item_body}>
                        <div className={styles.section_2_item_body_up}>
                          <img src={IconUp} alt="" />
                          <p>{r && r[1]?.contentFirst}</p>
                        </div>
                        <Divider type="vertical" className={styles.section_2_item_body_divider} />
                        <div className={styles.section_2_item_body_down}>
                          <img src={IconDown} alt="" />
                          <p>{r && r[1]?.contentSecond}</p>
                        </div>
                      </div>
                    </div>
                  </Col>
                  <Col xs={24} xl={12} className={styles.padding_l_r_0}>
                    <div className={styles.section_2_item}>
                      <div className={styles.section_2_item_head}>
                        <div className={styles.section_2_item_no}>3</div>
                        <div className={styles.section_2_item_title}>
                          <h3>{r && r[2].title}</h3>
                          <p>{r && r[2]?.description}</p>
                        </div>
                      </div>
                      <div className={styles.section_2_item_body}>
                        <div className={styles.section_2_item_body_up}>
                          <img src={IconUp} alt="" />
                          <p>{r && r[2].contentFirst}</p>
                        </div>
                        <Divider type="vertical" className={styles.section_2_item_body_divider} />
                        <div className={styles.section_2_item_body_down}>
                          <img src={IconDown} alt="" />
                          <p>{r && r[2]?.contentSecond}</p>
                        </div>
                      </div>
                    </div>
                    <div className={styles.section_2_item}>
                      <div className={styles.section_2_item_head}>
                        <div className={styles.section_2_item_no}>4</div>
                        <div className={styles.section_2_item_title}>
                          <h3>{r && r[3].title}</h3>
                          <p>{r && r[3].description}</p>
                        </div>
                      </div>
                      <div className={styles.section_2_item_body}>
                        <div className={styles.section_2_item_body_up}>
                          <img src={IconUp} alt="" />
                          <p>{r && r[3].contentFirst}</p>
                        </div>
                        <Divider type="vertical" className={styles.section_2_item_body_divider} />
                        <div className={styles.section_2_item_body_down}>
                          <img src={IconDown} alt="" />
                          <p>{r && r[3].contentSecond}</p>
                        </div>
                      </div>
                    </div>
                  </Col>
                  <Col xs={24} xl={12} className="text-center">
                    <img src={r?.[1]?.img.img || ImageHomeSection2_02} className={styles.section_2_image_banner} alt="" />
                  </Col>
                </Row>
              </div>
              <div className={`container-main ${styles.section_2_bottom}`}>
                <div
                  style={{ backgroundImage: "src(" + ImageHomeSection2_04 + ")" }}
                  className={styles.section_2_bottom_image}
                >
                  <img src={f?.img.img} alt={f?.img.title} />
                </div>
                <div className={styles.section_2_bottom_text}>
                  <h3 className={styles.section_title}>{t("home.s2.title2")}</h3>
                  <Divider className={styles.section_divider} />
                  <p>{f?.content}</p>
                  <div className={styles.section_2_bottom_text_item}>
                    <span className={styles.section_2_bottom_text_item_count}>{f?.totalInfluencer}</span>
                    <br />
                    <span className={styles.section_2_bottom_text_item_type}>{t("home.s2.influencer")}</span>
                  </div>
                  <div className={styles.section_2_bottom_text_item}>
                    <span className={styles.section_2_bottom_text_item_count}>{f?.totalKols}</span>
                    <br />
                    <span className={styles.section_2_bottom_text_item_type}>{t("home.s2.kols")}</span>
                  </div>
                  <div className={styles.section_2_bottom_text_item}>
                    <span className={styles.section_2_bottom_text_item_count}>{f?.totalStar}</span>
                    <br />
                    <span className={styles.section_2_bottom_text_item_type}>{t("home.s2.aStar")}</span>
                  </div>
                </div>
              </div>
            </section>
            {/* <section className={styles.section_3}>
              <div className={`container-main`}>
                <Row align="bottom">
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
                        ].map(imgKey=><Col key={imgKey} xs={12} xl={6} className={styles.section_3_item_brand}>
                          {br?.[imgKey] && <div>
                            <img src={br?.[imgKey]} alt="" className={styles.img_brand} />
                          </div>}
                        </Col>)
                      }
                    </Row>
                  </Col>
                </Row>
              </div>
            </section> */}
            {/* <section className={styles.section_6}>
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
            </section> */}
            {/* <section className={styles.section_4}>
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
            </section> */}
          </div>
        </div>
        <Footer />
      </div>
    )
  );
});

export default Home;
