import { Box, TextField, Button, Typography } from "@mui/material";

export default function DriverLogin() {
  return (
    <Box
      sx={{
        backgroundColor: "#1c1c1c",
        height: "100vh",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        p: 3,
      }}
    >
      <Typography variant="h5" sx={{ mb: 2 }}>
        Modo Conductor
      </Typography>
      <TextField
        label="Placa"
        variant="filled"
        sx={{ mb: 2, bgcolor: "#333", input: { color: "white" } }}
      />
      <TextField
        label="Contraseña"
        variant="filled"
        type="password"
        sx={{ mb: 3, bgcolor: "#333", input: { color: "white" } }}
      />
      <Button variant="contained" color="primary" fullWidth href="/driver">
        Ingresar
      </Button>
      <Button href="/" sx={{ mt: 2, color: "#aaa" }}>
        Ir a modo pasajero
      </Button>
    </Box>
  );
}
