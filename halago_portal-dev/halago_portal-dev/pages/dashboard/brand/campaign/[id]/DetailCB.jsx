import LayoutM from "layouts";
import dynamic from "next/dynamic";
import React, { useState, useEffect, useContext } from "react";
import moment from "moment";
import { Button, Col, DatePicker, Form, Input, Popconfirm, Row, Select, Upload } from "antd";
import ButtonBack from "components/Button/ButtonBack";
import { baseRule } from "utils/validation";
import { useRouter } from "next/router";
import {
  ACCEPT,
  ACTIVE,
  CODE_API_SUCCESS,
  DEFAULT_FORMAT_DATE,
  DEFAULT_PAGE_SIZE,
  PENDING,
  REJECT,
} from "utils/constants";
import {
  LIST_FIELD_CREATE_CAMPAIGN,
  FIELD_NAME_CREATE_CAMPAIGN_BRAND,
  FIELD_NAME_CREATE_CAMPAIGN_CONTENT,
  FIELD_NAME_CREATE_CAMPAIGN_DESCRIPTION,
  FIELD_NAME_CREATE_CAMPAIGN_END,
  FIELD_NAME_CREATE_CAMPAIGN_NAME,
  FIELD_NAME_CREATE_CAMPAIGN_REWARDS,
  FIELD_NAME_CREATE_CAMPAIGN_START,
  FIELD_NAME_CREATE_CAMPAIGN_STATUS,
  FIELD_NAME_CREATE_CAMPAIGN_BRAND_ID,
  FIELD_NAME_CREATE_CAMPAIGN_IMAGE,
  FIELD_NAME_CREATE_CAMPAIGN_INDUSTRY,
  FIELD_NAME_CREATE_CAMPAIGN_IMAGE_S,
} from "utils/fields";
import { formThreeQuarter } from "utils/formLayout";
import { getConfig } from "utils/helpers";
import { messageError, messageSuccess } from "utils/messageM";
import { listIndustry } from "services/common";
import { CheckOutlined, CloseCircleOutlined, CloseOutlined, EditOutlined, UploadOutlined } from "@ant-design/icons";
import { deleteCampaign, findCampaignById, updateCampaign } from "services/campaign";
import { authId, authName } from "utils/storages";
import { APP_DASHBOARD_B_C } from "utils/paths";
import TableM from "components/TableM/TableM";
import { approveInfluencer, listApprove } from "services/approve";
import AuthStoreContext from "stores/AuthStore";
import UploadM from "components/Upload/UploadM";
import MultiUploadM from "components/Upload/MultiUploadM";

const MyEditor = dynamic(() => import("components/Editor/MyEditor"), {
  ssr: false,
});

let se = [];

const DetailCB = ({ id }) => {
  const router = useRouter();
  const authStore = useContext(AuthStoreContext);
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(false);
  const [img, setImg] = useState(null);
  const [camp, setCamp] = useState(null);
  const [update, setUpdate] = useState(false);
  const pageSize = DEFAULT_PAGE_SIZE;
  const [list, setList] = useState([]);
  const [total, setTotal] = useState(0);
  const [pageNo, setPageNo] = useState(1);
  const [status, setStatus] = useState("");
  const [listF, setListF] = useState([]);
  const [industry, setIndustry] = useState([]);

  const onGet = async () => {
    try {
      const { code, message, responseBase } = await findCampaignById({ id });
      if (code === CODE_API_SUCCESS) {
        setCamp(responseBase.data);
        setImg(responseBase.data?.img);
        setListF(responseBase.data.imgProduct);
        form.setFieldsValue(responseBase.data);
        form.setFieldsValue({
          se: [
            moment(responseBase.data?.dateStart || null, DEFAULT_FORMAT_DATE),
            moment(responseBase.data?.dateEnd || null, DEFAULT_FORMAT_DATE),
          ],
        });
        se = [responseBase.data?.dateStart || null, responseBase.data?.dateEnd || null];
      } else {
        messageError(message);
      }
    } catch (error) {
      console.log(error);
    }
  };
  const getList = async () => {
    try {
      const request = {
        idCampaign: id,
        status,
        pageSize,
        pageNumber: pageNo,
      };
      const { code, message, responseBase } = await listApprove(request);
      if (code === CODE_API_SUCCESS) {
        setList(responseBase.data);
        setTotal(responseBase.total);
      } else {
        setList([]);
        setTotal(0);
        console.log(message);
      }
    } catch (error) {
      console.log(error);
    }
  };
  const approve = async (id, status) => {
    try {
      setLoading(true);
      const request = { id, status };
      const { code, message } = await approveInfluencer(request);
      if (code === CODE_API_SUCCESS) {
        messageSuccess(message);
        getList();
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
  const onClickUpdate = () => {
    setUpdate(true);
  };
  const onFinish = async () => {
    await form.validateFields([...LIST_FIELD_CREATE_CAMPAIGN, "se"]);
    try {
      setLoading(true);
      const values = form.getFieldsValue();
      const { code, message } = await updateCampaign({
        id,
        [FIELD_NAME_CREATE_CAMPAIGN_BRAND_ID]: authId() || authStore.id,
        [FIELD_NAME_CREATE_CAMPAIGN_BRAND]: authName() || authStore.name,
        [FIELD_NAME_CREATE_CAMPAIGN_NAME]: values[FIELD_NAME_CREATE_CAMPAIGN_NAME],
        [FIELD_NAME_CREATE_CAMPAIGN_INDUSTRY]: values[FIELD_NAME_CREATE_CAMPAIGN_INDUSTRY],
        [FIELD_NAME_CREATE_CAMPAIGN_DESCRIPTION]: values[FIELD_NAME_CREATE_CAMPAIGN_DESCRIPTION],
        [FIELD_NAME_CREATE_CAMPAIGN_REWARDS]: values[FIELD_NAME_CREATE_CAMPAIGN_REWARDS],
        [FIELD_NAME_CREATE_CAMPAIGN_CONTENT]: values[FIELD_NAME_CREATE_CAMPAIGN_CONTENT],
        [FIELD_NAME_CREATE_CAMPAIGN_START]: se[0],
        [FIELD_NAME_CREATE_CAMPAIGN_END]: se[1],
        [FIELD_NAME_CREATE_CAMPAIGN_STATUS]: "1",
        [FIELD_NAME_CREATE_CAMPAIGN_IMAGE]: values[FIELD_NAME_CREATE_CAMPAIGN_IMAGE],
        [FIELD_NAME_CREATE_CAMPAIGN_IMAGE_S]: listF,
      });
      if (code === CODE_API_SUCCESS) {
        messageSuccess(message);
        return router.push(APP_DASHBOARD_B_C);
      } else {
        messageError(message);
      }
    } catch (error) {
      messageError(error.toString());
    } finally {
      setLoading(false);
    }
  };
  const onDelete = async () => {
    try {
      setLoading(true);
      const { code, message } = await deleteCampaign({ id });
      if (code === CODE_API_SUCCESS) {
        messageSuccess(message);
        return router.replace(APP_DASHBOARD_B_C);
      } else {
        return messageError(message);
      }
    } catch (error) {
      console.log(error);
      return messageError(error.toString());
    } finally {
      setLoading(false);
    }
  };
  const onApproveAll = async () => {
    try {
      setLoading(true);
      const request = { idCampaign: id, status: 1 };
      const { code, message } = await approveAll(request);
      if (code === CODE_API_SUCCESS) {
        messageSuccess(message);
        getList();
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
  const cbm = (c, m, r, s, n) => {
    if (c === CODE_API_SUCCESS) {
      s(r.data);
      form.setFieldsValue({ [n]: r.data });
    } else {
      messageError(m);
    }
  };
  const cba = (c, m, r, s) => {
    if (c === CODE_API_SUCCESS) {
      let arr = [...listF, r.data];
      s(arr);
    } else {
      messageError(m);
    }
  };
  const cbr = (i) => {
    let arr = [...listF];
    arr.splice(i, 1);
    setListF(arr);
  };

  useEffect(() => {
    getConfig(listIndustry, setIndustry);
  }, []);
  useEffect(() => {
    onGet();
  }, [id]);
  useEffect(() => {
    getList();
  }, [status, pageNo]);

  return (
    camp && (
      <LayoutM>
        <div className="wrapper">
          <div className="container-main p-4">
            <div className="page--header">
              <ButtonBack />
              {update ? (
                <div>
                  <Popconfirm
                    title="Chắc chắn xoá chiến dịch này?"
                    okText="Có"
                    cancelText="Không"
                    onConfirm={onDelete}
                    placement="bottomRight"
                  >
                    <Button loading={loading} danger icon={<CloseCircleOutlined />} type="primary" className="mr-8">
                      Xoá
                    </Button>
                  </Popconfirm>
                  <Button type="default" onClick={() => setUpdate(false)}>
                    Huỷ
                  </Button>
                </div>
              ) : (
                camp?.status === ACTIVE && (
                  <Button type="primary" icon={<EditOutlined />} onClick={onClickUpdate}>
                    Cập nhật thông tin
                  </Button>
                )
              )}
            </div>
            <div className="page--title t-center mb-32">
              <h2>Chi tiết chiến dịch</h2>
            </div>
            <Form form={form} className="page--form" layout="vertical" {...formThreeQuarter}>
              <Row className="campaign--row">
                <Col xs={6} className="campaign--row--title">
                  <span>
                    Tên chiến dịch<small> *</small>
                  </span>
                </Col>
                <Col xs={18}>
                  <Form.Item rules={[...baseRule]} name={FIELD_NAME_CREATE_CAMPAIGN_NAME}>
                    <Input.TextArea disabled={!update} />
                  </Form.Item>
                </Col>
              </Row>
              <Row className="campaign--row">
                <Col xs={6} className="campaign--row--title">
                  <span>
                    Lĩnh vực<small> *</small>
                  </span>
                </Col>
                <Col xs={18}>
                  <Form.Item rules={[...baseRule]} name={FIELD_NAME_CREATE_CAMPAIGN_INDUSTRY}>
                    <Select disabled={!update}>
                      {industry?.map((i) => (
                        <Select.Option key={i.id} value={i.id}>
                          {i.industryName}
                        </Select.Option>
                      ))}
                    </Select>
                  </Form.Item>
                </Col>
              </Row>

              <Row className="campaign--row">
                <Col xs={6} className="campaign--row--title">
                  <span>
                    Ảnh chiến dịch<small> *</small>
                  </span>
                </Col>
                <Col xs={18}>
                  <Form.Item name={FIELD_NAME_CREATE_CAMPAIGN_IMAGE} rules={[...baseRule]}>
                    <UploadM
                      disabled={!update}
                      image={img}
                      cb={(c, m, r) => cbm(c, m, r, setImg, FIELD_NAME_CREATE_CAMPAIGN_IMAGE)}
                    />
                  </Form.Item>
                </Col>
              </Row>
              <Row className="campaign--row">
                <Col xs={6} className="campaign--row--title">
                  <span>
                    Ảnh sản phẩm<small> *</small>
                  </span>
                </Col>
                <Col xs={18}>
                  <Form.Item>
                    <MultiUploadM
                      disabled={!update}
                      list={listF}
                      cba={(c, m, r) => cba(c, m, r, setListF)}
                      cbr={(i) => cbr(i)}
                    />
                  </Form.Item>
                </Col>
              </Row>
              <Row className="campaign--row">
                <Col xs={6} className="campaign--row--title">
                  <span>
                    Thời gian thực hiện<small> *</small>
                  </span>
                </Col>
                <Col xs={18}>
                  <Form.Item
                    rules={[...baseRule]}
                    name="se"
                    initialValue={
                      camp?.dateStart
                        ? [
                            moment(camp?.dateStart || null, DEFAULT_FORMAT_DATE),
                            moment(camp?.dateEnd || null, DEFAULT_FORMAT_DATE),
                          ]
                        : null
                    }
                  >
                    <DatePicker.RangePicker
                      disabled={!update}
                      format={DEFAULT_FORMAT_DATE}
                      onChange={(_d, ds) => {
                        se = ds;
                      }}
                    />
                  </Form.Item>
                </Col>
              </Row>
              <Row className="campaign--row">
                <Col xs={6} className="campaign--row--title">
                  <span>
                    Mô tả chiến dịch<small> *</small>
                  </span>
                </Col>
                <Col xs={18}>
                  <Form.Item rules={[...baseRule]} name={FIELD_NAME_CREATE_CAMPAIGN_DESCRIPTION}>
                    <MyEditor
                      disabled={!update}
                      onReady={(editor) => editor.setData(camp[FIELD_NAME_CREATE_CAMPAIGN_DESCRIPTION])}
                      onBlur={(_e, editor) =>
                        form.setFieldsValue({ [FIELD_NAME_CREATE_CAMPAIGN_DESCRIPTION]: editor.getData() })
                      }
                    />
                    <small>
                      <i>Các thông tin mô tả chi tiết của chiến dịch: mục tiêu, khách hàng,...</i>
                    </small>
                  </Form.Item>
                </Col>
              </Row>
              <Row className="campaign--row">
                <Col xs={6} className="campaign--row--title">
                  <span>
                    Nội dung ứng viên thực hiện<small> *</small>
                  </span>
                </Col>
                <Col xs={18}>
                  <Form.Item rules={[...baseRule]} name={FIELD_NAME_CREATE_CAMPAIGN_CONTENT}>
                    <MyEditor
                      disabled={!update}
                      onReady={(editor) => editor.setData(camp[FIELD_NAME_CREATE_CAMPAIGN_CONTENT])}
                      onBlur={(_e, editor) =>
                        form.setFieldsValue({ [FIELD_NAME_CREATE_CAMPAIGN_CONTENT]: editor.getData() })
                      }
                    />
                    <small>
                      <i>Nội dung công việc mà ứng viên cần thực hiện</i>
                    </small>
                  </Form.Item>
                </Col>
              </Row>
              <Row className="campaign--row">
                <Col xs={6} className="campaign--row--title">
                  <span>
                    Phần thưởng<small> *</small>
                  </span>
                </Col>
                <Col xs={18}>
                  <Form.Item rules={[...baseRule]} name={FIELD_NAME_CREATE_CAMPAIGN_REWARDS}>
                    <MyEditor
                      disabled={!update}
                      onReady={(editor) => editor.setData(camp[FIELD_NAME_CREATE_CAMPAIGN_REWARDS])}
                      onBlur={(_e, editor) =>
                        form.setFieldsValue({ [FIELD_NAME_CREATE_CAMPAIGN_REWARDS]: editor.getData() })
                      }
                    />
                    <small>
                      <i>Phần thưởng ứng viên nhận được từ nhãn hàng khi hoàn thành công việc</i>
                    </small>
                  </Form.Item>
                </Col>
              </Row>
              {update ? (
                <Row className="campaign--row">
                  <Col xs={6} />
                  <Col xs={18}>
                    <Form.Item className="w-100">
                      <Button loading={loading} type="primary" onClick={onFinish} className="w-100">
                        Cập nhật
                      </Button>
                    </Form.Item>
                  </Col>
                </Row>
              ) : null}
            </Form>
            {!update ? (
              <Row className="campaign--row">
                <Col xs={6} className="campaign--row--title">
                  <span>Danh sách Influencer</span>
                </Col>
                <Col xs={18}>
                  <div className="page--tool">
                    {/* <div className="page--tool__search">
              <Input.Search className="tool__search" onSearch={(v) => setText(v)} placeholder="Tìm kiếm" />
            </div> */}
                    <div className="page--tool__filter">
                      <Select
                        className="tool__filter"
                        defaultValue=""
                        onChange={(v) => {
                          setPageNo(1);
                          setStatus(v);
                        }}
                      >
                        <Select.Option value="">Trạng thái</Select.Option>
                        <Select.Option value={PENDING}>Chờ phê duyệt</Select.Option>
                        <Select.Option value={ACCEPT}>Phê duyệt</Select.Option>
                        <Select.Option value={REJECT}>Từ chối</Select.Option>
                      </Select>
                    </div>
                    <div>
                      <Button type="primary" onClick={onApproveAll}>
                        Phê duyệt tất cả
                      </Button>
                    </div>
                  </div>
                  <TableM
                    rowKey={(r) => r.id}
                    loading={loading}
                    className="page--table"
                    rowClassName="page--table--row"
                    dataSource={list}
                    columns={[
                      {
                        title: "#",
                        render: (_t, _r, i) => (pageNo - 1) * pageSize + i + 1,
                      },
                      {
                        title: "Tên",
                        dataIndex: "influencerName",
                      },
                      {
                        title: "Trạng thái",
                        dataIndex: "statusName",
                      },
                      {
                        title: "Phê duyệt",
                        // eslint-disable-next-line react/display-name
                        render: (_t, r) =>
                          camp?.status === ACTIVE && r.status === PENDING ? (
                            <div>
                              <Button
                                size="small"
                                shape="round"
                                type="primary"
                                icon={<CheckOutlined />}
                                className="mr-8"
                                onClick={() => approve(r.id, ACCEPT)}
                              >
                                Phê duyệt
                              </Button>
                              <Button
                                size="small"
                                shape="round"
                                type="primary"
                                danger
                                icon={<CloseOutlined />}
                                onClick={() => approve(r.id, REJECT)}
                              >
                                Từ chối
                              </Button>
                            </div>
                          ) : null,
                      },
                    ]}
                    pagination={{
                      total,
                      pageSize,
                      current: pageNo,
                      onChange: (page) => setPageNo(page),
                    }}
                  />
                </Col>
              </Row>
            ) : null}
          </div>
        </div>
      </LayoutM>
    )
  );
};

DetailCB.getInitialProps = async ({ query }) => {
  return {
    id: query.id,
  };
};

export default DetailCB;
