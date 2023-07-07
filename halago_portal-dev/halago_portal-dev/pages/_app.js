import React, { useContext, useEffect } from "react";
import "antd/dist/antd.css";
import "styles/index.scss";
import "styles/Influencer/influencer.scss";
import "styles/seller/seller.scss";
import "styles/search/search.scss";
import "styles/campaign/campaign.scss";
import "styles/campaign/campaignDetail.scss";
import "./App.css";
import { useRouter } from "next/router";

const MyApp = ({ Component, pageProps }) => {
  const router = useRouter()
  useEffect(() => {
    if (window) window.scrollTo(0, 0)
  }, [router])
  return <Component {...pageProps} />;
};

export default MyApp;
