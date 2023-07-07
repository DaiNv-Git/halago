import React, { useEffect, useState } from "react";
import { observer } from "mobx-react-lite";
import Slider from "react-slick";
import Head from "next/head";
import "utils/i18n";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Masonry from "react-masonry-css";
import Footer from "layouts/Footer/Footer";
import Header from "layouts/Header/Header";
import styles from "./BookKol.module.scss";
import { Form, Button, Col, Divider, Input, Row, Tabs } from "antd";
import { RightCircleOutlined } from "@ant-design/icons";
import { sendRequestContact } from "utils/helpers";
import { C_EMAIL, C_NAME, C_PHONE } from "utils/fields";
import { baseRule } from "utils/validation";
import { getListKolPage } from "services/portal";
import { CODE_API_SUCCESS } from "utils/constants";
import { messageError } from "utils/messageM";
import { useTranslation } from "react-i18next";
import { LogoMeta } from "assets/images";

const settings = {
  dots: true,
  infinite: true,
  speed: 500,
  slidesToShow: 1,
  slidesToScroll: 1,
  // autoplay: true,
};
const sliderProps = {
  className: styles.section_0_slider_wrapper,
  dots: true,
  arrows: false,
  speed: 500,
  slidesToShow: 1,
  slidesToScroll: 1,
  centerMode: true,
  centerPadding: "300px",
  autoplay: true,
  autoplaySpeed: 5000,
  // responsive: [
  //   {
  //     breakpoint: 768,
  //     settings: {
  //       slidesToShow: 1,
  //       slidesToScroll: 1,
  //     },
  //   },
  // ],
};
const meta = {
  title: "Halago - Book Kols",
  description:
    "Với Halago, khách hàng luôn có thể hợp tác với rất nhiều Kols và sao hạng A trong mọi lĩnh vực phù hợp với mọi nhu cầu truyền thông",
};
const BookKol = observer(() => {
  const { t } = useTranslation();
  const [form] = Form.useForm();
  const [h, setH] = useState(null);
  const [s, setS] = useState(null);
  const [b, setB] = useState(null);
  const [f, setF] = useState(null);
  const [m, setM] = useState(null);

  const NextArrow = ({ onClick, className }) => (
    <Button
      className={`${className} ${styles.section_2_body_right_slider_next}`}
      type="text"
      icon={<RightCircleOutlined />}
      onClick={onClick}
    />
  );
  const getList = async () => {
    try {
      const request = {};
      const { code, message, responseBase } = await getListKolPage(request);
      if (code === CODE_API_SUCCESS) {
        const { header } = responseBase.data;
        const { statistical } = responseBase.data.header;
        const { body } = responseBase.data;
        const { footer } = responseBase.data;
        const { kolsMarketing } = responseBase.data;
        setH(header);
        setS(statistical);
        setB(body);
        setF(footer);
        setM(kolsMarketing);
      } else {
        console.log(message);
        messageError(message);
      }
    } catch (error) {
      console.log(error);
      messageError(error.toString());
    }
  };

  const sendContact = async () => {
    await form.validateFields([C_NAME]);
    sendRequestContact(form.getFieldsValue());
  };

  useEffect(() => {
    getList();
  }, []);
  return (
    <div className="app_wrapper">
      <Head>
        <link
          href="https://fonts.googleapis.com/css2?family=Inter&family=Prompt&family=Raleway&family=Roboto&family=Work+Sans&display=swap"
          rel="stylesheet"
        />
        <title>{meta.title}</title>
        <meta name="url" content="https://halago.vn" />
        <meta name="keywords" content="Halago, marketing, Influencer, Kol, sao hạng A" />
        <meta name="description" content={meta.description} />
        <meta name="og:title" content={meta.title} />
        <meta name="og:type" content="article" />
        <meta name="og:url" content="https://halago.vn" />
        <meta name="og:site_name" content={meta.title} />
        <meta name="og:description" key="description" content={meta.description} />
        <meta name="fb:page_id" content="157800657956361" />
        <meta name="og:country-name" content="VN" />
        <meta name="geo.region" content="VN" />
        <meta name="geo.placename" content="VN" />
        <meta property="og:type" content="article" />
        <meta property="og:title" content={meta.title} key="title" />
        <meta property="og:description" content={meta.description} key="description" />
      </Head>
      <div className="wrapper">
        <div className="page_wrapper">
          <section className={styles.section_0}>
            <Header />
            <div className={`app-d bk_section_0_slider ${styles.section_0_slider}`}>
              <Slider {...sliderProps}>
                {h &&
                  h.banner.map((item) => (
                    <div key={item} className={styles.section_0_slider_item}>
                      <div className={styles.section_0_slider_item_image}>
                        <img src={item.img} alt={item.title} />
                      </div>
                    </div>
                  ))}
              </Slider>
            </div>
            <div className="container-main app-m">
              <Slider {...settings}>
                {h &&
                  h.banner.map((item) => (
                    <div key={item}>
                      <div>
                        <img src={item.img} alt={item.title} />
                      </div>
                    </div>
                  ))}
              </Slider>
            </div>
            <div className={styles.section_0_info}>
              <h3 className={`${styles.section_title} text-center`}>
                {(h && h.title) || null}
                <br />
                <span className={styles.section_title_more}>{h && h.description}</span>
              </h3>
              <div className={styles.section_0_info_list}>
                <div className={styles.section_0_info_item}>
                  <div className={styles.section_0_info_item_1}>{s && s[0].title}</div>
                  <div className={styles.section_0_info_item_2}>{s && s[0].description}</div>
                </div>
                <Divider type="vertical" className={styles.section_0_info_divider} />
                <div className={styles.section_0_info_item}>
                  <div className={styles.section_0_info_item_1}>{s && s[1].title}</div>
                  <div className={styles.section_0_info_item_2}>{s && s[1].description}</div>
                </div>
                <Divider type="vertical" className={styles.section_0_info_divider} />
                <div className={styles.section_0_info_item}>
                  <div className={styles.section_0_info_item_1}>{s && s[2].title}</div>
                  <div className={styles.section_0_info_item_2}>{s && s[2].description}</div>
                </div>
                <Divider type="vertical" className={styles.section_0_info_divider} />
                <div className={styles.section_0_info_item}>
                  <div className={styles.section_0_info_item_1}>{s && s[3].title}</div>
                  <div className={styles.section_0_info_item_2}>{s && s[3].description}</div>
                </div>
                <Divider type="vertical" className={styles.section_0_info_divider} />
                <div className={styles.section_0_info_item}>
                  <div className={styles.section_0_info_item_1}>{s && s[4].title}</div>
                  <div className={styles.section_0_info_item_2}>{s && s[4].description}</div>
                </div>
              </div>
            </div>
          </section>
          <section className={styles.section_1}>
            <div className="container-main">
              <h3 className={styles.section_title}>{t("kol.s1.title")}</h3>
              <Divider className={styles.section_divider} />
              <Masonry
                breakpointCols={2}
                className={styles.section_1_masonry_grid}
                columnClassName={styles.section_1_masonry_grid_column}
              >
                <div className={styles.section_1_masonry_item_1}>
                  <img src={b && b[1].poster.img} alt={ b && b[1].poster.title} className={styles.section_1_masonry_item_1_image} />
                  <div
                    style={{
                      display: "flex",
                      width: "75%",
                      justifyContent: "space-between",
                      alignItems: "center",
                      margin: "40px 0",
                    }}
                    className={styles.section_1_masonry_item_1_content}
                  >
                    <div className={styles.section_1_masonry_item_1_name}>{b && b[1].title}</div>
                    <div className={styles.section_1_masonry_item_1_info}>
                      <div className={styles.section_1_masonry_item_1_info_item}>
                        <div className={styles.section_1_masonry_item_1_info_item_count}>{b && b[1].totalRegister}</div>
                        <div className={styles.section_1_masonry_item_1_info_item_type}>{t("kol.res")}</div>
                      </div>
                      <div className={styles.section_1_masonry_item_1_info_item}>
                        <div className={styles.section_1_masonry_item_1_info_item_count}>{b && b[1].totalView}</div>
                        <div className={styles.section_1_masonry_item_1_info_item_type}>{t("kol.sub")}</div>
                      </div>
                      <div className={styles.section_1_masonry_item_1_info_item}>
                        <div className={styles.section_1_masonry_item_1_info_item_count}>
                          {b && b[1].totalDurationView}
                        </div>
                        <div className={styles.section_1_masonry_item_1_info_item_type}>{t("kol.view")}</div>
                      </div>
                    </div>
                  </div>
                  <div className={styles.section_1_masonry_item_1_description}>{b && b[1].description}</div>
                </div>
                <div className={styles.section_1_masonry_item_2}>
                  <img src={b && b[0].poster.img} alt={b && b[0].poster.title} className={styles.section_1_masonry_item_2_image} />
                  <div className={styles.section_1_masonry_item_2_name}>{b && b[0].title}</div>
                  <div className={styles.section_1_masonry_item_2_info}>
                    <div className={styles.section_1_masonry_item_2_info_item}>
                      <div className={styles.section_1_masonry_item_2_info_item_count}>{b && b[0].totalRegister}</div>
                      <div className={styles.section_1_masonry_item_2_info_item_type}>{t("kol.res")}</div>
                    </div>
                    <div className={styles.section_1_masonry_item_2_info_item}>
                      <div className={styles.section_1_masonry_item_2_info_item_count}>{b && b[0].totalView}</div>
                      <div className={styles.section_1_masonry_item_2_info_item_type}>{t("kol.sub")}</div>
                    </div>
                    <div className={styles.section_1_masonry_item_2_info_item}>
                      <div className={styles.section_1_masonry_item_2_info_item_count}>
                        {b && b[0].totalDurationView}
                      </div>
                      <div className={styles.section_1_masonry_item_2_info_item_type}>{t("kol.view")}</div>
                    </div>
                  </div>
                  <div className={styles.section_1_masonry_item_2_description}>{b && b[0].description}</div>
                </div>
                <div className={styles.section_1_masonry_item_3}>
                  <img src={b && b[3].poster.img} alt={b && b[3].poster.title} className={styles.section_1_masonry_item_3_image} />
                  <div className={styles.section_1_masonry_item_3_name}>{b && b[3].title}</div>
                  <div className={styles.section_1_masonry_item_3_info}>
                    <div className={styles.section_1_masonry_item_3_info_item}>
                      <div className={styles.section_1_masonry_item_3_info_item_count}>{b && b[3].totalRegister}</div>
                      <div className={styles.section_1_masonry_item_3_info_item_type}>{t("kol.res")}</div>
                    </div>
                    <div className={styles.section_1_masonry_item_3_info_item}>
                      <div className={styles.section_1_masonry_item_3_info_item_count}>{b && b[3].totalView}</div>
                      <div className={styles.section_1_masonry_item_3_info_item_type}>{t("kol.sub")}</div>
                    </div>
                    <div className={styles.section_1_masonry_item_3_info_item}>
                      <div className={styles.section_1_masonry_item_3_info_item_count}>
                        {b && b[3].totalDurationView}
                      </div>
                      <div className={styles.section_1_masonry_item_3_info_item_type}>{t("kol.view")}</div>
                    </div>
                  </div>
                  <div className={styles.section_1_masonry_item_3_description}>{b && b[3].description}</div>
                </div>
                <div className={styles.section_1_masonry_item_4}>
                  <img src={b && b[2].poster.img} alt={b && b[2].poster.title} className={styles.section_1_masonry_item_4_image} />
                  <div className={styles.section_1_masonry_item_4_name}>{b && b[2].title}</div>
                  <div className={styles.section_1_masonry_item_4_info}>
                    <div className={styles.section_1_masonry_item_4_info_item}>
                      <div className={styles.section_1_masonry_item_4_info_item_count}>{b && b[2].totalRegister}</div>
                      <div className={styles.section_1_masonry_item_4_info_item_type}>{t("kol.res")}</div>
                    </div>
                    <div className={styles.section_1_masonry_item_4_info_item}>
                      <div className={styles.section_1_masonry_item_4_info_item_count}>{b && b[2].totalView}</div>
                      <div className={styles.section_1_masonry_item_4_info_item_type}>{t("kol.sub")}</div>
                    </div>
                    <div className={styles.section_1_masonry_item_4_info_item}>
                      <div className={styles.section_1_masonry_item_4_info_item_count}>
                        {b && b[2].totalDurationView}
                      </div>
                      <div className={styles.section_1_masonry_item_4_info_item_type}>{t("kol.view")}</div>
                    </div>
                  </div>
                  <div className={styles.section_1_masonry_item_4_description}>{b && b[2].description}</div>
                </div>
              </Masonry>
            </div>
          </section>
          <section className={styles.section_2}>
            <div className={styles.section_2_head}>
              <h3 className={styles.section_title}>{t("kol.s2.title")}</h3>
              <Divider className={styles.section_divider} />
              <img src="/images/&.png" alt="" />
            </div>
            <Row className={styles.section_2_body} align="middle">
              <Col xs={24} xl={12} className={styles.section_2_body_left}>
                <img src={f && f.poster.img} alt={f && f.poster.title} />
                <div className={styles.section_2_body_left_info}>
                  <div className={styles.section_2_body_left_info_item}>
                    <div className={styles.section_2_body_left_info_item_count}>{f && f.totalRegister}</div>
                    <div className={styles.section_2_body_left_info_item_type}>{t("kol.res")}</div>
                  </div>
                  <div className={styles.section_2_body_left_info_item}>
                    <div className={styles.section_2_body_left_info_item_count}>{f && f.totalView}</div>
                    <div className={styles.section_2_body_left_info_item_type}>{t("kol.sub")}</div>
                  </div>
                  <div className={styles.section_2_body_left_info_item}>
                    <div className={styles.section_2_body_left_info_item_count}>{f && f.totalDurationView}</div>
                    <div className={styles.section_2_body_left_info_item_type}>{t("kol.view")}</div>
                  </div>
                </div>
              </Col>
              <Col xs={24} xl={12} className={styles.section_2_body_right}>
                <Tabs>
                  <Tabs.TabPane tab={''}>
                    <div className={styles.section_2_body_right_name}>{f && f.title}</div>
                    <div className={styles.section_2_body_right_description}>{f && f.description}</div>
                    <div className={`bk_section_2_body_right_slider ${styles.section_2_body_right_slider}`}>
                      <Slider
                        dots={false}
                        arrows={true}
                        speed={500}
                        autoplay={true}
                        autoplaySpeed={3000}
                        slidesToShow={3}
                        slidesToScroll={1}
                        className={styles.section_2_body_right_slider_wrapper}
                        nextArrow={<NextArrow />}
                        responsive={[
                          {
                            breakpoint: 1600,
                            settings: {
                              slidesToShow: 2,
                            },
                          },
                        ]}
                      >
                        {f &&
                          f.img.map((item) => (
                            <div key={item} className={styles.section_2_body_right_slider_item}>
                              <img src={item.img} alt={item.title} />
                            </div>
                          ))}
                      </Slider>
                    </div>
                  </Tabs.TabPane>
                </Tabs>
              </Col>
            </Row>
          </section>
          <section className={styles.section_3}>
            <h3 className={`text-center ${styles.section_title}`}>{t("kol.s3.title")}</h3>
            <br />
            <br />
            <div className="container-main">
              <Row className={styles.section_3_content} align="top" justify="space-between">
                <Col xs={24} xl={7} className={styles.section_3_item}>
                  <div className={styles.section_3_item_no}>01</div>
                  <div className={styles.section_3_item_image}>
                    <img src="/images/bk_s3_1.png" alt="" />
                  </div>
                  <h3 style={{ fontSize: 22 }} className={styles.section_title}>
                    {m && m[0].title}
                  </h3>
                  <div dangerouslySetInnerHTML={{ __html: m && m[0].description }} />
                </Col>
                <Col xs={24} xl={7} className={styles.section_3_item}>
                  <div className={styles.section_3_item_no}>02</div>
                  <div className={styles.section_3_item_image}>
                    <img src="/images/bk_s3_2.png" alt="" />
                  </div>
                  <h3 style={{ fontSize: 22 }} className={styles.section_title}>
                    {m && m[1].title}
                  </h3>
                  <div dangerouslySetInnerHTML={{ __html: m && m[1].description }} />{" "}
                </Col>
                <Col xs={24} xl={7} className={styles.section_3_item}>
                  <div className={styles.section_3_item_no}>03</div>
                  <div className={styles.section_3_item_image}>
                    <img src="/images/bk_s3_3.png" alt="" />
                  </div>
                  <h3 style={{ fontSize: 22 }} className={styles.section_title}>
                    {m && m[2].title}
                  </h3>
                  <div dangerouslySetInnerHTML={{ __html: m && m[2].description }} />
                </Col>
              </Row>
              {/* <div className={styles.section_3_contact}>
                <img src="/images/bk_s3_4.png" alt="" />
                <div className={styles.section_3_contact_wrapper}>
                  <Form form={form} className={styles.section_3_contact_form}>
                    <span>{t("home.s4.title")}</span>
                    <Form.Item name={C_NAME} rules={[...baseRule]} className={styles.section_3_contact_form_item}>
                      <Input placeholder={t("home.s4.plhName")} className={styles.section_3_contact_form_item_input} />
                    </Form.Item>
                    <Form.Item name={C_PHONE} className={styles.section_3_contact_form_item}>
                      <Input placeholder={t("home.s4.plhPhone")} className={styles.section_3_contact_form_item_input} />
                    </Form.Item>
                    <Form.Item name={C_EMAIL} className={styles.section_3_contact_form_item}>
                      <Input placeholder={t("home.s4.plhEmail")} className={styles.section_3_contact_form_item_input} />
                    </Form.Item>
                    <Form.Item style={{ textAlign: "right", marginBottom: 0 }}>
                      <Button onClick={sendContact} className={styles.section_3_contact_form_item_button}>
                        {t("home.s4.btnRegister")}
                      </Button>
                    </Form.Item>
                  </Form>
                </div>
              </div> */}
            </div>
          </section>
        </div>
      </div>
      <Footer />
    </div>
  );
});

export default BookKol;
