import React, { useEffect, useState } from "react";
import LayoutM from "layouts";
import { getListCampaignApply } from "services/campaign";
import { CODE_API_SUCCESS, DEFAULT_PAGE_SIZE } from "utils/constants";
import { authId } from "utils/storages";
import { messageError } from "utils/messageM";
import {
  FIELD_NAME_CREATE_CAMPAIGN_BRAND,
  FIELD_NAME_CREATE_CAMPAIGN_NAME,
  FIELD_NAME_CREATE_CAMPAIGN_STATUS,
} from "utils/fields";
import { Input, Select } from "antd";
import TableM from "components/TableM/TableM";
import { APP_CAMPAIGN_DETAIL } from "utils/paths";
import { useRouter } from "next/router";
import { toSlug } from "utils/helpers";
import Head from "next/head";

const meta = {
  title: "Halago - Influencer Campaign",
  description: "Halago - Influencer Campaign",
};

const ListCI = () => {
  const router = useRouter();
  const pageSize = DEFAULT_PAGE_SIZE;
  const [pageNo, setPageNo] = useState(1);
  const [total, setTotal] = useState(0);
  const [list, setList] = useState([]);
  const [loading, setLoading] = useState(false);
  const [status, setStatus] = useState("");

  const getList = async () => {
    try {
      setLoading(true);
      const request = {
        idInfluencer: authId(),
        pageNumber: pageNo,
        pageSize,
      };
      const { code, message, responseBase } = await getListCampaignApply(request);
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
        pathname: APP_CAMPAIGN_DETAIL,
        query: { id: record.id, slug: toSlug(record.campaignName) },
      });
    },
  });

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
                title: "Thời gian",
                render: (_t, r) => (r?.dateStart || "??") + " đến " + (r?.dateEnd || "??"),
              },
              {
                title: "Trạng thái",
                dataIndex: "statusApproveName",
              },
            ]}
            dataSource={list}
          />
        </div>
      </div>
    </LayoutM>
  );
};

export default ListCI;
