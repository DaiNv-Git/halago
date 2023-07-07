import React, { useState, useEffect, useContext } from "react";
import Head from "next/head";
import Link from "next/link";
import { Card, Row, Col, Input, Select, Pagination, Button } from "antd";
import { SearchOutlined } from "@ant-design/icons";
import LayoutM from "layouts";
import { APP_CAMPAIGN, APP_CAMPAIGN_DETAIL } from "utils/paths";
import { ACTIVE, CODE_API_SUCCESS, ACTION_R, USER_B, USER_I, ROLE_I, ROLE_B } from "utils/constants";
import { getListCampaign } from "services/campaign";
import { messageError } from "utils/messageM";
import { toSlug } from "utils/helpers";
import { authId, authRole } from "utils/storages";
import { insertApprove } from "services/approve";

import AuthStoreContext from "stores/AuthStore";
import { useRouter } from "next/router";


const { Meta } = Card;
const { Option } = Select;
const meta = {
  title: "Halago - Marketing Campaign",
  description:
    "Cùng với sự tin tưởng và phối hợp của khách hàng, Halago cùng với các đối tác đã triển khai thành công nhiều chiến dịch marketing trong các lĩnh vực khác nhau",
  image: 'https://imgsonla.semob.info/img/logo-halago-meta.jpg',
};

const Campaign = () => {
  const pageSize = 12;
  const { setVisibleModal, setUser, setAction, setActiveTab } = useContext(AuthStoreContext);

  const [listCampaign, setListCampaign] = useState([]);
  const [total, setTotal] = useState(0);
  const [pageNo, setPageNo] = useState(1);
  const [text, setText] = useState("");
  const [status, setStatus] = useState("1");
  const [loading, setLoading] = useState(false);
  const route = useRouter()

  const handleSelectSearch = (val) => {
    console.log(val);
  };
  const handleClickIcon = () => {
    onGetList();
  };
  const onGetList = async () => {
    try {
      setLoading(true);
      const request = {
        name: text,
        status: status,
        pageNumber: pageNo,
        pageSize: pageSize,
      };
      const { code, message, responseBase } = await getListCampaign(request);
      if (code === CODE_API_SUCCESS) {
        setListCampaign(responseBase.data);
        setTotal(responseBase.total);
      } else {
        messageError(message);
      }
    } catch (error) {
      console.log(error);
      messageError(error.toString());
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    onGetList();
  }, [pageNo]);

  const onApprove = (id, href) => async (e) => {
    e.preventDefault();

    if (!authId()) {
      setVisibleModal(true);
      setUser(USER_I);
      setAction(ACTION_R);
      setActiveTab(USER_I);
      return false
    }
    if (authRole() === ROLE_B) {
      return route.push(href)
    }
    if (authRole() !== ROLE_I) return false;
    try {
      const request = {
        idCampaign: id,
        idInfluencer: authId(),
      };
      const { code, message } = await insertApprove(request);
      if (code === CODE_API_SUCCESS) {
        messageSuccess(message);
        route.push(href)
      } else {
        messageError(message);
      }
    } catch (error) {
      console.log(error);
    }

  }

  return (
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
        <div className="campaign container-main">
          <div className="campaign-search">
            <Row>
              <Col xl={18} sm={24} xs={24}>
                <div className="campaign-search__box">
                  <Input.Group style={{ display: "flex" }} compact>
                    {/* <Select
                      className="campaign-search__select"
                      // value={defaultSelectValue}
                      onChange={handleSelectSearch}
                      size="large"
                      name="searchKey"
                      placeholder="Tất cả ngành (80)"
                    >
                      <Option value="Option1">Ngành 1</Option>
                      <Option value="Option2">Ngành 2</Option>
                    </Select> */}
                    <Input
                      onPressEnter={handleClickIcon}
                      value={text}
                      className="input-custom"
                      placeholder="Tìm kiếm..."
                      addonAfter={<SearchOutlined onClick={handleClickIcon} />}
                      name="searchWord"
                      autoComplete="off"
                      onChange={(e) => setText(e.target.value)}
                    />
                  </Input.Group>
                </div>
              </Col>
            </Row>
          </div>
          <div className="campaign-content">
            <Row>
              {listCampaign.map((item) => {
                return (
                  <Col xl={6} sm={12} xs={24} key={item.id}>
                    <Link
                      href={{
                        pathname: APP_CAMPAIGN_DETAIL,
                        query: { id: item.id, slug: toSlug(item.campaignName) },
                      }}
                    >
                      <div className="campaign-content__item">
                        <Card hoverable cover={<img alt="example" src={item.img} />}>
                          {/* <Meta title={item.campaignName} /> */}
                          <p style={{ fontSize: 16 }} className="title">
                            {item.campaignName}
                          </p>
                          <Button
                            disabled = {item.status === 3 ? true : false}
                            type="primary"
                            shape="round"
                            onClick={onApprove(item.id, {
                              pathname: APP_CAMPAIGN_DETAIL,
                              query: { id: item.id, slug: toSlug(item.campaignName) },
                            })}
                            style={{
                              height: 40,
                              fontWeight: 'bold',
                            }}
                          >
                            {/* {authRole() === ROLE_B ? 'Xem chiến dịch' : "Ứng tuyển"}  */}
                            {authRole() === ROLE_B ? 'Xem chiến dịch' : item.status === 3 ? "Đã ứng tuyển" : "Ứng tuyển"} 
                          </Button>
                        </Card>
                      </div>
                    </Link>
                  </Col>
                );
              })}
            </Row>
            <Row justify="end">
              <Pagination total={total} pageSize={pageSize} current={pageNo} onChange={(page) => setPageNo(page)} />
            </Row>
          </div>
        </div>
      </div>
    </LayoutM>
  );
};

export default Campaign;
