import dynamic from "next/dynamic";

const DynamicEditor = dynamic(() => import("./MyEditor"), {
  ssr: false,
});

export default DynamicEditor;
