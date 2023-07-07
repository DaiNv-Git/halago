import React from "react";
import ReactHtmlParser from "react-html-parser";

class HtmlComponent extends React.Component {
  render() {
    const { html } = this.props;

    if (!html) {
      return null;
    }
    
    return <>{ReactHtmlParser(this.props.html)}</>;
  }
}

export default HtmlComponent;
