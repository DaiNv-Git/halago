import React from "react";
import Document, { Html, Head, Main, NextScript } from "next/document";

// GA Tracking Id
const GA_TRACKING_ID = 'G-631JL3HEB3'

class MyDocument extends Document {
  render() {
    return (
      <Html lang="en">
        <Head>
          <meta charSet="utf-8" />
          <meta content="IE=edge" />
          <meta property="og:type" content="website" />
          <meta property="og:site_name" content="/" />
          <meta name="twitter:card" content="summary" />
          <meta name="twitter:site" content="/" />
          <meta name="twitter:creator" content={"/"} />
          {/* <meta name="viewport" content="width=device-width, initial-scale=1" /> */}
          <link rel="icon" type="image/png" href="/favicon.ico" />
          <script
            async
            src={`https://www.googletagmanager.com/gtag/js?id=${GA_TRACKING_ID}`}
          />
          <script
            dangerouslySetInnerHTML={{
              __html: `
              window.dataLayer = window.dataLayer || [];
              function gtag(){dataLayer.push(arguments);}
              gtag('js', new Date());
            
              gtag('config', '${GA_TRACKING_ID}');
          `
            }}
          />
        </Head>
        <body style={{ overflowX: 'hidden' }}>
          <Main />
          <NextScript />
        </body>
      </Html>
    );
  }
}

export default MyDocument;
