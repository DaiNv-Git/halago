import React, { useState, useEffect } from "react";
import Head from "next/head";
import { useRouter } from "next/router";
import { Breadcrumb, Row, Col, Tabs } from "antd";
import LayoutM from "layouts";
import imgLogo from "assets/images/Influencer/influencer-detail-icon.png";
import imgBg from "assets/images/Influencer/influencer-detail-bg.png";

const { TabPane } = Tabs;
const arrTab = [
  {
    id: 1,
    title: "Giải thưởng",
  },
  {
    id: 2,
    title: "đăng ký tham gia",
  },
  {
    id: 3,
    title: "từ khóa",
  },
  {
    id: 4,
    title: "những việc cần làm",
  },
  {
    id: 5,
    title: "website",
  },
  {
    id: 6,
    title: "thông tin khác",
  },
  {
    id: 7,
    title: "định hướng nội dung",
  },
];

const InfluencerDetail = (props) => {
  const route = useRouter();
  const [tabMode, setTabMode] = useState("left");

  const { id } = route.query;

  const changeTabsMode = () => {
    let tabMode = window.innerWidth < 992 ? "top" : "left";
    setTabMode(tabMode);
  };

  useEffect(() => {
    console.log(props, id, "id nè");
    changeTabsMode();
    window.addEventListener("resize", () => {
      changeTabsMode();
    });
  }, []);
  return (
    <LayoutM>
      <Head>
        <title>Influencer Detail</title>
        <meta property="og:title" content="Influencer Detail" key="title" />
        <meta property="og:description" content="Influencer Detail" key="description" />
      </Head>
      <div className="wrapper">
        <div className="influencer-detail page-content container-main">
          <div className="influencer-detail__bread">
            <div className="container">
              <Breadcrumb separator="|">
                <Breadcrumb.Item>Trang chủ</Breadcrumb.Item>
                <Breadcrumb.Item>
                  <a href="">Chiến dịch Reviewer</a>
                </Breadcrumb.Item>
                <Breadcrumb.Item>Mỹ phẩm</Breadcrumb.Item>
              </Breadcrumb>
            </div>
          </div>
          <div className="influencer-detail__intro page-content">
            <Row>
              <Col xl={12} sm={24} xs={24}>
                <div className="left">
                  <div className="intro-logo">
                    <img src={imgLogo} alt="influencer detail icon" />
                  </div>
                  <div className="intro-title">
                    KEM BODY ZEELEE - REVIEW SẢN PHẨM NHẬN TIỀN MẶT 500K VÀ SẢN PHẨM 350K
                  </div>
                  <div className="intro-desc">
                    BODY DIỆP LỤC ĐANG HOT GÌ MÀ DÂN MẠNG XÃ HỘI LÀM RẦN RẦN LÊN NHỈ?? <br /> THOA LÊN LÀ TAN – THOA LÊN
                    LÀ THẤM – THOA LÊN LÀ TRẮNG – SÀI LÀ THÍCH CHỈ CÓ THỂ LÀ KEM BODY ZEELEE COLLAGEN DIỆP LỤC <br />{" "}
                    Kết hợp diệp lục cùng Collagen với kem lạnh mềm thấm sâu và nuôi dưỡng làn da giúp da đều màu , sáng
                    mịn, còn tăng cường chống nắng tốt VỚI ĐỘ TRẮNG MỊN MƯỚT CÓ HẠT DIỆP LỤC THOA ĐẾN ĐÂU TRẮNG MỊN ĐẾN
                    ĐÓ VÀ CAM KẾT KHÔNG: Không bết rít Không để lộ vân kem Không gây kích ứng da Không trôi nước.
                  </div>
                </div>
              </Col>
              <Col xl={12} sm={24} xs={24}>
                <div
                  className="right"
                  style={{
                    backgroundImage: `url(${imgBg})`,
                    width: "100%",
                    height: "100%",
                    backgroundPosition: "center",
                    backgroundSize: "cover",
                  }}
                ></div>
              </Col>
            </Row>
          </div>
          <div className="influencer-detail__content">
            <div className="content-tab">
              <Tabs tabPosition={tabMode} defaultActiveKey={7}>
                {arrTab.map((item) => {
                  return (
                    <TabPane tab={item.title} key={item.id}>
                      <div className="content-tab__detail">
                        <div className="title">Định hướng nội dung</div>
                        <p>
                          Thích da trắng thì về với ZEELEE COLLAGEN HONEY nha <br />
                          Phụ nữ lúc nào cũng muốn mình thật đẹp, thật xinh! <br />
                          Ra đường nhìn các hotgirl xinh đẹp da trắng là mê luôn <br /> Và bí kíp của các nàng là đây.
                          ZEELEE KEM BODY COLLAGEN MẬT ONG TRẮNG SÁNG HOÀN HẢO SAU 7 NGÀY Trong kem có chứa Collagen ,
                          giúp trẻ hóa da, cho làn da luôn trắng sáng.
                          <br /> Tinh chất mật ong trong kem cũng dưỡng trắng da, giữ ẩm, cho làn da luôn mềm mại, tươi
                          trẻ. Kem thẩm thấu nhanh, không bết rít, không dính áo quần Nuôi dưỡng da trắng từ bên trong,
                          không trắng ảo Da trắng lên tone tự nhiên Mùi thơm nhẹ, dễ chịu với mọi loại da Chống nắng bảo
                          vệ da Muốn da trắng chẳng hề khó với ZEELEE COLLAGEN MẬT ONG. <br /> Sử dụng để trải nghiệm
                          chất lượng kem tuyệt vời.
                          <br /> CHỈ VỚI 195K có được 1 Hộp Kem 200g tặng thêm 1 hộp Sữa tắm 525ml trị giá 99K BODY DIỆP
                          LỤC ĐANG HOT GÌ MÀ DÂN MẠNG XÃ HỘI LÀM RẦN RẦN LÊN NHỈ?? THOA LÊN LÀ TAN – THOA LÊN LÀ THẤM –
                          THOA LÊN LÀ TRẮNG – SÀI LÀ THÍCH CHỈ CÓ THỂ LÀ KEM BODY ZEELEE COLLAGEN DIỆP LỤC Kết hợp diệp
                          lục cùng Collagen với kem lạnh mềm thấm sâu và nuôi dưỡng làn da giúp da đều màu , sáng mịn,
                          còn tăng cường chống nắng tốt VỚI ĐỘ TRẮNG MỊN MƯỚT CÓ HẠT DIỆP LỤC THOA ĐẾN ĐÂU TRẮNG MỊN ĐẾN
                          ĐÓ VÀ CAM KẾT KHÔNG: Không bết rít Không để lộ vân kem Không gây kích ứng da Không trôi nước
                          HÃY mua ngay KEM BODY COLLAGEN DIỆP LỤC "cứu nguy" cho làn da body nhé!!! Hoàn toàn tự nhiên .{" "}
                          <br /> 1 Hộp Kem chỉ 195K tặng thêm 1 hộp Sữa tắm 525ml trị giá 99K{" "}
                        </p>
                      </div>
                    </TabPane>
                  );
                })}
              </Tabs>
            </div>
          </div>
        </div>
      </div>
    </LayoutM>
  );
};

InfluencerDetail.getInitialProps = async ({ query, req, store, isServer }) => {
  return {
    id: query.id,
  };
};

export default InfluencerDetail;
