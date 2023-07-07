import React from "react";
import { CKEditor } from "@ckeditor/ckeditor5-react";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";

export default function MyEditor(props) {
  return <CKEditor editor={ClassicEditor} {...props} />;
}
