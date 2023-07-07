import React from 'react';
import Head from 'next/head'

const MeTaTag = (props) => (
    <Head>
        {/* <!-- HTML Meta Tags --> */}
        <title>{props.title}</title>
        <meta name="description" content={props.description} />

        {/* <!-- Facebook Meta Tags --> */}
        <meta property="og:title" itemProp="name" content={props.title} />
        <meta property="og:url" itemProp="url" content="" />
        <meta property="og:description" content={props.description} />
        <meta content={props.image} property="og:image" itemProp="thumbnailUrl" />

        {/* <!-- Twitter Meta Tags --> */}
        <meta name="twitter:card" content="summary_large_image" />
        <meta property="twitter:domain" content="halago.vn" />
        <meta property="twitter:url" content="" />
        <meta name="twitter:title" content={props.title} />
        <meta name="twitter:description" content={props.description} />
        <meta name="twitter:image" content={props.image} />
    </Head>
);

export default MeTaTag;