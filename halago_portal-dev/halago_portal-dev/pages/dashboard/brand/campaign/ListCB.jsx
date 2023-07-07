import React, { useContext, useEffect, useState } from "react";
import LayoutM from "layouts";
import { getListCampaignByBrand } from "services/campaign";
import { CODE_API_SUCCESS, DEFAULT_PAGE_SIZE } from "utils/constants";
import { authId } from "utils/storages";
import { messageError } from "utils/messageM";
import {
  FIELD_NAME_CREATE_CAMPAIGN_BRAND,
  FIELD_NAME_CREATE_CAMPAIGN_END,
  FIELD_NAME_CREATE_CAMPAIGN_IMAGE,
  FIELD_NAME_CREATE_CAMPAIGN_IMAGE_S,
  FIELD_NAME_CREATE_CAMPAIGN_INDUSTRY,
  FIELD_NAME_CREATE_CAMPAIGN_NAME,
  FIELD_NAME_CREATE_CAMPAIGN_START,
  FIELD_NAME_CREATE_CAMPAIGN_STATUS,
} from "utils/fields";
import { Button, Input, Select } from "antd";
import TableM from "components/TableM/TableM";
import { APP_DASHBOARD_B_C_C, APP_DASHBOARD_B_C_D } from "utils/paths";
import { useRouter } from "next/router";
import ButtonBack from "components/Button/ButtonBack";
import { PlusOutlined } from "@ant-design/icons";
import AuthStoreContext from "stores/AuthStore";
import Head from "next/head";
import { getConfig } from "utils/helpers";
import { listIndustry } from "services/common";
const meta = {
  title: "Halago - Seller Campaign",
  description: "Halago - Seller Campaign",
};

const ListCB = () => {
  const router = useRouter();
  const authStore = useContext(AuthStoreContext);
  const { id } = authStore;
  const pageSize = DEFAULT_PAGE_SIZE;
  const [pageNo, setPageNo] = useState(1);
  const [total, setTotal] = useState(0);
  const [list, setList] = useState([]);
  const [loading, setLoading] = useState(false);
  const [industry, setIndustry] = useState([]);
  const [status, setStatus] = useState("");

  const getList = async () => {
    try {
      setLoading(true);
      const request = {
        idBrand: authId() || id,
        status,
        pageNumber: pageNo,
        pageSize,
      };
      const { code, message, responseBase } = await getListCampaignByBrand(request);
      if (code === CODE_API_SUCCESS) {
        setList(responseBase.data);
        setTotal(responseBase.total);
      } else {
        setList([]);
        setTotal(0);
        messageError(message);
      }
    } catch (error) {
      console.log(error);
    } finally {
      setLoading(false);
    }
  };
  const onClickRow = (record) => ({
    onClick: () => {
      router.push({
        pathname: APP_DASHBOARD_B_C_D,
        query: { id: record.id },
      });
    },
  });
  const onClickCreate = () => {
    router.push(APP_DASHBOARD_B_C_C);
  };

  useEffect(() => {
    getConfig(listIndustry, setIndustry);
  }, []);
  useEffect(() => {
    getList();
  }, [pageNo, status]);

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
      </Head>
      <div className="wrapper">
        <div className="container-main p-4 mt-32">
          <div className="page--header">
            <ButtonBack />
            <Button type="primary" icon={<PlusOutlined />} onClick={onClickCreate}>
              Tạo mới Chiến dịch
            </Button>
          </div>
          <div className="page--title">
            <h2>Danh sách các chiến dịch</h2>
          </div>
          <div className="page--tool">
            {/* <div className="page--tool__search">
              <Input.Search className="tool__search" onSearch={(v) => setText(v)} placeholder="Tìm kiếm" />
            </div> */}
            <div className="page--tool__filter">
              <Select className="tool__filter" defaultValue="" onChange={(v) => setStatus(v)}>
                <Select.Option value="">Trạng thái</Select.Option>
                <Select.Option value="1">Active</Select.Option>
                <Select.Option value="0">Inactive</Select.Option>
              </Select>
            </div>
          </div>
          <TableM
            rowKey={(r) => r.id}
            loading={loading}
            className="page--table"
            rowClassName="page--table--row"
            onRow={onClickRow}
            pagination={{
              total,
              pageSize,
              current: pageNo,
              onChange: (page) => setPageNo(page),
            }}
            columns={[
              {
                title: "#",
                render: (_t, _r, i) => (pageNo - 1) * pageSize + i + 1,
              },
              {
                title: "Tên",
                dataIndex: FIELD_NAME_CREATE_CAMPAIGN_NAME,
              },
              {
                title: "Nhãn hàng",
                dataIndex: FIELD_NAME_CREATE_CAMPAIGN_BRAND,
              },
              {
                title: "Lĩnh vực",
                dataIndex: FIELD_NAME_CREATE_CAMPAIGN_INDUSTRY,
                render: (t) => industry.find((i) => i.id === t)?.industryName,
              },
              {
                title: "Ảnh chiến dịch",
                dataIndex: FIELD_NAME_CREATE_CAMPAIGN_IMAGE,
                render: (t) =>
                  t && (
                    <div className="t-center">
                      <img style={{ width: "200px", height: "80px", objectFit: 'contain' }} src={t || null} alt="Halago" />
                    </div>
                  ),
              },
              {
                title: "Ảnh sản phẩm",
                dataIndex: FIELD_NAME_CREATE_CAMPAIGN_IMAGE_S,
                render: (t) =>
                  t[0] && (
                    <div className="t-center">
                      <img style={{ width: "80px", height: "80px" }} src={t[0] || null} alt="Halago" />
                    </div>
                  ),
              },
              {
                title: "Ngày bắt đầu",
                dataIndex: FIELD_NAME_CREATE_CAMPAIGN_START,
              },
              {
                title: "Ngày kết thúc",
                dataIndex: FIELD_NAME_CREATE_CAMPAIGN_END,
              },
              {
                title: "Trạng thái",
                dataIndex: FIELD_NAME_CREATE_CAMPAIGN_STATUS,
                render: (t) => (t == 1 ? "Active" : t == 0 ? "Inactive" : null),
              },
            ]}
            dataSource={list}
          />
        </div>
      </div>
    </LayoutM>
  );
};

export default ListCB;
