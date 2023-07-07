import React from "react";
import Head from "next/head";
import { BackTop } from "antd";
import "utils/i18n";
import Header from "./Header";
import Footer from "./Footer/Footer";
import { LogoMeta } from "assets/images";

const LayoutM = (props) => {
  return (
    <div className="app_wrapper">
      <Header />
      <div className="page_wrapper">{props.children}</div>
      <Footer />
      <BackTop />
    </div>
  );
};

export default LayoutM;
