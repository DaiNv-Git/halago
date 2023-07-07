import React, { useState, useEffect, useContext } from "react";
import Head from "next/head";
import { observer } from "mobx-react-lite";
import AuthStoreContext from "stores/AuthStore";
import LayoutM from "layouts";
import { Button } from "antd";
import { getListInfluencerPage } from "services/portal";
import { messageError } from "utils/messageM";
import { ACTION_R, CODE_API_SUCCESS, USER_I } from "utils/constants";
import { useTranslation } from "react-i18next";

const meta = {
  title: "Halago - Influencer, KOLs",
  description:
    "Rất nhiều Influencer và KOLs nổi tiếng trong các lĩnh vực đã hợp tác cùng phát triển với Halago. Hãy liên hệ ngay với chúng tôi",
  image: 'https://imgsonla.semob.info/img/logo-halago-meta.jpg',  
};

const Influencer = observer(() => {
  const { t } = useTranslation();
  const authStore = useContext(AuthStoreContext);
  const { setVisibleModal, setUser, setAction, setActiveTab } = authStore;
  const [h, setH] = useState(null);
  const [b, setB] = useState(null);
  const [f, setF] = useState(null);
  const [loading, setLoading] = useState(false);

  const getList = async () => {
    try {
      setLoading(true);
      const request = {};
      const { code, message, responseBase } = await getListInfluencerPage(request);
      if (code === CODE_API_SUCCESS) {
        const { influencerPortalheader } = responseBase.data.header;
        const { bodyList } = responseBase.data.body;
        const { footerList } = responseBase.data.footer;
        setH(influencerPortalheader);
        setB(bodyList);
        setF(footerList);
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
  useEffect(() => {
    getList();
  }, []);

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
          <div className="container-main">
            <div className="influencer page-content">
              <div className="banner">
                <img className="w-100" src={h?.img} alt="banner influencer" />
                <div className="banner-desc">
                  <div className="halago-title banner-desc__title">{h?.title}</div>
                  <div className="halago-title banner-desc__small">{h?.content}</div>
                </div>
              </div>
              <div className="chance">
                <div className="chance-title halago-title text-center">{t("influencer.s1.title")}</div>
                <div className="custom-divider"></div>
                <div className="chance-content">
                  <div className="chance-content__item">
                    <div className="item-image-1">
                      <img className="w-100" src="/images/influencer/item-image-1.png" alt="loa loa" />
                    </div>
                    <div className="item-desc">{b && b[0]?.content}</div>
                    <div className="item-image-2">
                      <img className="w-100" src={b && b[0]?.img} alt="loa loa" />
                    </div>
                  </div>
                  <div className="chance-content__item">
                    <div className="item-image-1">
                      <img className="w-100" src="/images/influencer/item-image-3.png" alt="loa loa" />
                    </div>
                    <div className="item-desc">{b && b[1]?.content}</div>
                    <div className="item-image-2">
                      <img className="w-100" src={b && b[1]?.img} alt="loa loa" />
                    </div>
                  </div>
                  <div className="chance-content__item">
                    <div className="item-image-1">
                      <img className="w-100" src="/images/influencer/item-image-2.png" alt="loa loa" />
                    </div>
                    <div className="item-desc">{b && b[2]?.content}</div>
                    <div className="item-image-2">
                      <img className="w-100" src={b && b[2]?.img} alt="loa loa" />
                    </div>
                  </div>
                </div>
              </div>
              <div className="work">
                <div className="work-item">
                  <img className="w-100" src={f && f[0].img} alt="avatar ads" />
                  <div className="work-item__content">
                    <div className="halago-title desc">{f && f[0].title}</div>
                    <div className="content-option">
                      <img src="/images/influencer/checked-icon.svg" alt="checked icon" />
                      <p>{f && f[0].content}</p>
                    </div>
                    <div className="content-option">
                      <img src="/images/influencer/checked-icon.svg" alt="checked icon" />
                      <p>{f && f[0].contentSecond}</p>
                    </div>
                    <div className="content-option">
                      <img src="/images/influencer/checked-icon.svg" alt="checked icon" />
                      <p>{f && f[0].contentThird}</p>
                    </div>
                  </div>
                </div>
                <div className="work-item" style={{ justifyContent: "space-between" }}>
                  <div className="work-item__content">
                    <div className="halago-title desc">{f && f[1].title}</div>
                    <div className="content-option">
                      <img src="/images/influencer/checked-icon.svg" alt="checked icon" />
                      <p>{f && f[1].content}</p>
                    </div>
                    <div className="content-option">
                      <img src="/images/influencer/checked-icon.svg" alt="checked icon" />
                      <p>{f && f[1].contentSecond}</p>
                    </div>
                    <div className="content-option">
                      <img src="/images/influencer/checked-icon.svg" alt="checked icon" />
                      <p>{f && f[1].contentThird}</p>
                    </div>
                  </div>
                  <img className="w-100" src={f && f[1].img} alt="avatar ads" />
                </div>
                <div className="work-item">
                  <img className="w-100" src={f && f[2].img} alt="avatar ads" />
                  <div className="work-item__content">
                    <div className="halago-title desc">{f && f[2].title}</div>
                    <div className="content-option">
                      <img src="/images/influencer/checked-icon.svg" alt="checked icon" />
                      <p>{f && f[2].content}</p>
                    </div>
                    <div className="content-option">
                      <img src="/images/influencer/checked-icon.svg" alt="checked icon" />
                      <p>{f && f[2].contentSecond}</p>
                    </div>
                    <div className="content-option">
                      <img src="/images/influencer/checked-icon.svg" alt="checked icon" />
                      <p>{f && f[2].contentThird}</p>
                    </div>
                  </div>
                </div>
              </div>
              <div
                className="become-influencer"
                style={{ backgroundImage: "url(/images/influencer/become-influencer-bg.png)" }}
              >
                <div className="become-influencer__title-1">{t("influencer.s3.title")}</div>
                <div className="become-influencer__title-2">{t("influencer.s3.title2")}</div>
                <Button onClick={() => openModal(true, USER_I, ACTION_R, USER_I)} className="become-influencer__btn">
                  {t("home.s4.btnRegisterInfluencer")}
                </Button>
              </div>
            </div>
          </div>
        </div>
      </LayoutM>
    )
  );
});

export default Influencer;
