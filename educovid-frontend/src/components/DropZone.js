import React, { useMemo } from "react";
import { useDropzone } from "react-dropzone";

const baseStyle = {
  display: "flex",
  justifyContent: "space-around",
  alignItems: "center",
  flexDirection: "column",
  flexWrap: "nowrap",
  width: "80vw",
  padding: "20px",
  borderWidth: 4,
  // borderRadius: 8,
  borderColor: "#eeeeee",
  borderStyle: "dashed",
  color: "#EBFDF8",
  outline: "none",
  transition: "border .24s ease-in-out"
};

const activeStyle = {
  borderColor: "#10BD9E"
};

const acceptStyle = {
  borderColor: "#10BD9E"
};

const rejectStyle = {
  borderColor: "#ffac8a"
};

function DropZone({ onDrop, setFiles, files }) {
  const {
    getRootProps,
    getInputProps,
    isDragActive,
    isDragAccept,
    isDragReject
  } = useDropzone({ onDrop, accept: "text/csv" });

  const style = useMemo(
    () => ({
      ...baseStyle,
      ...(isDragActive ? activeStyle : {}),
      ...(isDragAccept ? acceptStyle : {}),
      ...(isDragReject ? rejectStyle : {})
    }),
    [isDragActive, isDragReject, isDragAccept]
  );

  return (
    <div className="dropzone-container">
      <div {...getRootProps({ style })}>
        <input {...getInputProps()} />
        <img src="/upload_icon.png" alt="CSV document icon" />
        <p>Arrastra tus archivos CSV aquí o haz click.</p>
      </div>
    </div>
  );
}

export default DropZone;
