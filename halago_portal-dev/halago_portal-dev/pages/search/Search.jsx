import React, { useState, useEffect } from "react";
import Head from "next/head";
import { Form, Input, Button, Row, Col, Select } from "antd";
import { SearchOutlined } from "@ant-design/icons";
import LayoutM from "layouts";
import { messageError, messageSuccess } from "utils/messageM";
import { getListInfluencer } from "services/influencer";
import { CODE_API_SUCCESS, DEFAULT_PAGE_SIZE, G_LIST } from "utils/constants";
import { getConfig } from "utils/helpers";
import { listCity, listIndustry } from "services/common";
import TableM from "components/TableM/TableM";

const arrAge = [
  {
    key: -1,
    value: "0-100",
    title: "Tất cả",
  },
  {
    key: 0,
    value: "0-18",
    title: "0-18",
  },
  {
    key: 1,
    value: "18-25",
    title: "18-25",
  },
  {
    key: 2,
    value: "25-40",
    title: "25-40",
  },
  {
    key: 3,
    value: "40-100",
    title: "40+",
  },
];

const conver = (t) =>{
  if( t != null && t.indexOf("dlinfluencer") === -1){
    t= "http://graph.facebook.com/" + t.slice(t.indexOf("id=")+3,t.indexOf("&height")) + "/picture?type=large&redirect=true&width=500&height=500"
  }
return t;
};
const meta = {
  title: "Halago - Search Influencer",
  description:
    "Halago luôn có rất nhiều Influencer hợp tác, hãy tìm kiếm ngay người phù hợp với bạn",
  image: 'https://imgsonla.semob.info/img/logo-halago-meta.jpg',
};

const Search = () => {
  const [city, setCity] = useState([]);
  const [industry, setIndustry] = useState([]);
  const [as, setAs] = useState(arrAge[0].value);
  const [is, setIs] = useState(-1);
  const [ss, setSs] = useState(-1);
  const [cs, setCs] = useState(-1);
  const [total, setTotal] = useState(0);
  const [pageNo, setPageNo] = useState(1);
  const [list, setList] = useState([]);
  const [loading, setLoading] = useState(false);
  const pageSize = DEFAULT_PAGE_SIZE;

  const getList = async () => {
    try {
      setLoading(true);
      const aas = as.split("-");
      const request = {
        industryId: is,
        fromAge: parseInt(aas[0]),
        toAge: parseInt(aas[1]),
        sex: ss,
        cityId: cs,
        pageNumber: pageNo,
        pageSize,
      };
      const { code, message, responseBase } = await getListInfluencer(request);
      if (code === CODE_API_SUCCESS) {
        const { data, total } = responseBase;
        setList(data);
        setTotal(total);
      } else {
        messageError(message);
        setList([]);
        setTotal(0);
      }
    } catch (error) {
      console.log(error);
      messageError(error.toString());
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    getConfig(listIndustry, setIndustry);
    getConfig(listCity, setCity);
  }, []);
  useEffect(() => {
    getList();
  }, [pageNo]);

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
        <div className="container-main page-content">
          <div className="search">
            <div className="banner">
              <img src="/images/search/search_bg.png" alt="search banner" />
              <div className="banner-content">
                <div className="banner-content__title text-center">Tìm kiếm Influencer</div>
                <div className="banner-content__desc text-center">Phù Hợp Nhất Cho Nhãn Hàng Của Bạn</div>
              </div>
              <div className="search-basic">
                <div className="basic-content">
                  <div className="basic-content__item">
                    <div className="item-label">Ngành</div>
                    <div className="">
                      <Select defaultValue={-1} onChange={(v) => setIs(v)}>
                        <Select.Option value={-1}>Tất cả</Select.Option>
                        {industry?.map((i) => (
                          <Select.Option key={i.id} value={i.id}>
                            {i.industryName}
                          </Select.Option>
                        ))}
                      </Select>
                    </div>
                  </div>
                  <div className="basic-content__item">
                    <div className="item-label">Độ tuổi</div>
                    <div className="">
                      <Select defaultValue={arrAge[0].value} onChange={(v) => setAs(v)}>
                        {arrAge.map((i) => (
                          <Select.Option key={i.key} value={i.value}>
                            {i.title}
                          </Select.Option>
                        ))}
                      </Select>
                    </div>
                  </div>
                  <div className="basic-content__item">
                    <div className="item-label">Giới tính</div>
                    <div className="">
                      <Select defaultValue={-1} onChange={(v) => setSs(v)}>
                        <Select.Option value={-1}>Tất cả</Select.Option>
                        <Select.Option value={1}>Nam</Select.Option>
                        <Select.Option value={0}>Nữ</Select.Option>
                      </Select>
                    </div>
                  </div>
                  <div className="basic-content__item">
                    <div className="item-label">Tỉnh thành</div>
                    <div className="">
                      <Select defaultValue={-1} onChange={(v) => setCs(v)}>
                        <Select.Option value={-1}>Tất cả</Select.Option>
                        {city?.map((i) => (
                          <Select.Option key={i.city_id} value={i.city_id}>
                            {i.cityName}
                          </Select.Option>
                        ))}
                      </Select>
                    </div>
                  </div>
                  <Button className="basic-content__item-btn" onClick={getList}>
                    <SearchOutlined />
                    <div className="" style={{ textTransform: "uppercase" }}>
                      Search
                    </div>
                  </Button>
                </div>
              </div>
            </div>
          </div>
          <div style={{padding:'0 20px'}}>
            <TableM
              rowKey={(r) => r.id}
              loading={loading}
              className="page--table"
              rowClassName="page--table--row"
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
                  title: "Ảnh đại diện",
                  dataIndex: "avatar",
                  render: (t) =>
                    t && (
                      <div className="t-center">
                        <img style={{ width: "80px", height: "80px" }} src={ conver(t)  || null} alt="Halago" />
                      </div>
                    ),
                },
                {
                  title: "Tên",
                  dataIndex: "name",
                },
                {
                  title: "Ngày sinh",
                  dataIndex: "birthday",
                },
                {
                  title: "Giới tính",
                  render: (_t, r) => r.sex && G_LIST.find((i) => i.value === r.sex).label,
                },
                {
                  title: "Tương tác trung bình",
                  dataIndex: "averageInteract",
                  render: (t) => t?.toLocaleString("en-US"),
                },
              ]}
              dataSource={list}
            />
          </div>
        </div>
      </div>
    </LayoutM>
  );
};

export default Search;
