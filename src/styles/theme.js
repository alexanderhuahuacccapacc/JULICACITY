import { createTheme } from "@mui/material/styles";

const theme = createTheme({
  palette: {
    mode: "dark",
    primary: { main: "#a3ff00" }, // verde neón
    secondary: { main: "#00bcd4" }, // celeste
    background: { default: "#1c1c1c", paper: "#2b2b2b" },
    text: { primary: "#fff", secondary: "#aaa" },
  },
  shape: { borderRadius: 10 },
});

export default theme;
