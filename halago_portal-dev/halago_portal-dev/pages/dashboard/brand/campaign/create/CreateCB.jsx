import LayoutM from "layouts";
import dynamic from "next/dynamic";
import React, { useState, useContext, useEffect } from "react";
import { Button, Col, DatePicker, Form, Input, Row, Select } from "antd";
import ButtonBack from "components/Button/ButtonBack";
import { baseRule } from "utils/validation";
import { useRouter } from "next/router";
import { CODE_API_SUCCESS, DEFAULT_FORMAT_DATE } from "utils/constants";
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
  FIELD_NAME_CREATE_CAMPAIGN_IMAGE_S,
  FIELD_NAME_CREATE_CAMPAIGN_INDUSTRY
} from "utils/fields";
import { formThreeQuarter } from "utils/formLayout";
import { getConfig } from "utils/helpers";
import { messageError, messageSuccess } from "utils/messageM";
import { insertCampaign } from "services/campaign";
import { authId, authName } from "utils/storages";
import AuthStoreContext from "stores/AuthStore";
import UploadM from "components/Upload/UploadM";
import MultiUploadM from "components/Upload/MultiUploadM";
import { listIndustry } from "services/common";

const MyEditor = dynamic(() => import("components/Editor/MyEditor"), {
  ssr: false,
});

let se = [];

const CreateCB = () => {
  const router = useRouter();
  const authStore = useContext(AuthStoreContext);
  const { id, name } = authStore;
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(false);
  const [img, setImg] = useState(null);
  const [listF, setListF] = useState([]);
  const [industry, setIndustry] = useState([]);

  const onFinish = async () => {
    await form.validateFields([...LIST_FIELD_CREATE_CAMPAIGN, "se"]);
    try {
      setLoading(true);
      const values = form.getFieldsValue();
      const { code, message } = await insertCampaign({
        [FIELD_NAME_CREATE_CAMPAIGN_BRAND_ID]: authId() || id,
        [FIELD_NAME_CREATE_CAMPAIGN_BRAND]: authName() || name,
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
        return router.back();
      } else {
        messageError(message);
      }
    } catch (error) {
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

  return (
    <LayoutM>
      <div className="wrapper">
        <div className="container-main p-4">
          <div className="page--header">
            <ButtonBack />
          </div>
          <div className="page--title t-center mb-32">
            <h2>Tạo mới chiến dịch</h2>
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
                  <Input.TextArea />
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
                  <Select>
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
                  <UploadM image={img} cb={(c, m, r) => cbm(c, m, r, setImg, FIELD_NAME_CREATE_CAMPAIGN_IMAGE)} />
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
                  <MultiUploadM list={listF} cba={(c, m, r) => cba(c, m, r, setListF)} cbr={(i) => cbr(i)} />
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
                <Form.Item rules={[...baseRule]} name="se">
                  <DatePicker.RangePicker
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
            <Row className="campaign--row">
              <Col xs={6} />
              <Col xs={18}>
                <Form.Item className="w-100">
                  <Button loading={loading} type="primary" onClick={onFinish} className="w-100">
                    Tạo mới
                  </Button>
                </Form.Item>
              </Col>
            </Row>
          </Form>
        </div>
      </div>
    </LayoutM>
  );
};

export default CreateCB;
