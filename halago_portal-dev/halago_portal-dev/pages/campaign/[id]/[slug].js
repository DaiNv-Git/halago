import React, { useState, useEffect, useContext } from "react";
import Head from "next/head";
import { Breadcrumb, Row, Col, Tabs, Button } from "antd";
import LayoutM from "layouts";
import { APP_CAMPAIGN } from "utils/paths";
import { findCampaignById } from "services/campaign";
import { messageError, messageSuccess } from "utils/messageM";
import { CODE_API_SUCCESS, ROLE_I } from "utils/constants";
import { authId, authRole } from "utils/storages";
import { insertApprove } from "services/approve";
import AuthStoreContext from "stores/AuthStore";

const { TabPane } = Tabs;

const CampaignDetail = ({ id }) => {
  const authStore = useContext(AuthStoreContext);
  let approve = authStore.role == ROLE_I;
  const [tabMode, setTabMode] = useState("left");
  const [campaign, setCampaign] = useState(null);
  const [loading, setLoading] = useState(false);

  const find = async () => {
    try {
      const { code, message, responseBase } = await findCampaignById({ id });
      if (code === CODE_API_SUCCESS) {
        setCampaign(responseBase.data);
      } else {
        messageError(message);
      }
    } catch (error) {
      console.log(error);
      messageError(error.toString());
    }
  };
  const changeTabsMode = () => {
    let tabMode = window.innerWidth < 992 ? "top" : "left";
    setTabMode(tabMode);
  };
  const onApprove = async () => {
    if (authRole() !== ROLE_I) return false;
    try {
      setLoading(true);
      const request = {
        idCampaign: id,
        idInfluencer: authId(),
      };
      const { code, message } = await insertApprove(request);
      if (code === CODE_API_SUCCESS) {
        messageSuccess(message);
        approve = false;
      } else {
        messageError(message);
      }
    } catch (error) {
      console.log(error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    find();
  }, [id]);
  useEffect(() => {
    changeTabsMode();
    window.addEventListener("resize", () => {
      changeTabsMode();
    });
  }, []);
  return (
    campaign && (
      <LayoutM>
        <Head>
          <title>{campaign.campaignName}</title>
          <meta property="og:title" content={campaign.campaignName} key="title" />
          <meta property="og:description" content={campaign.campaignName} key="description" />
        </Head>
        <div className="wrapper">
          <div className="campaign-detail page-content container-main">
            {/* <div className="campaign-detail__bread">
            <div className="container">
              <Breadcrumb separator="|">
                <Breadcrumb.Item>Trang chủ</Breadcrumb.Item>
                <Breadcrumb.Item>
                  <a href="">Chiến dịch Reviewer</a>
                </Breadcrumb.Item>
                <Breadcrumb.Item>Mỹ phẩm</Breadcrumb.Item>
              </Breadcrumb>
            </div>
          </div> */}
            <div className="campaign-detail__intro page-content">
              <Row>
                <Col xl={12} sm={24} xs={24}>
                  <div className="left">
                    <div className="intro-logo">{campaign.brandName}</div>
                    <div className="intro-title">{campaign?.campaignName}</div>
                    <div className="intro-desc">
                      ({campaign.dateStart} - {campaign.dateEnd})
                      <br />
                      <br />
                      <Button type="primary" shape="round" disabled={!approve} onClick={onApprove}>
                        Ứng tuyển
                      </Button>
                    </div>
                    <p style={{marginTop: 20}} dangerouslySetInnerHTML={{ __html: campaign?.description }} />
                  </div>
                </Col>
                <Col xl={12} sm={24} xs={24}>
                  <img className="img-banner" alt="" src={campaign.img} />
                </Col>
              </Row>
            </div>
            <div className="campaign-detail__content">
              <div className="content-tab">
                <Tabs tabPosition={tabMode} defaultActiveKey={0}>
                  <TabPane tab="Mô tả chiến dịch" key={1}>
                    <div className="content-tab__detail">
                      <p dangerouslySetInnerHTML={{ __html: campaign?.description }} />
                    </div>
                  </TabPane>
                  <TabPane tab="Nội dung thực hiện" key={2}>
                    <div className="content-tab__detail">
                      <p dangerouslySetInnerHTML={{ __html: campaign?.content }} />
                    </div>
                  </TabPane>
                  <TabPane tab="Phần thưởng" key={3}>
                    <div className="content-tab__detail">
                      <p dangerouslySetInnerHTML={{ __html: campaign?.rewards }} />
                    </div>
                  </TabPane>
                  <TabPane tab="Ảnh sản phẩm" key={4}>
                    <div className="content-tab__detail">
                      {campaign?.imgProduct.map((img) => (
                        <img src={img} className="w-100 mb-16" />
                      ))}
                    </div>
                  </TabPane>
                </Tabs>
              </div>
            </div>
          </div>
        </div>
      </LayoutM>
    )
  );
};

CampaignDetail.getInitialProps = async ({ query }) => {
  return {
    id: query.id,
  };
};

export default CampaignDetail;
