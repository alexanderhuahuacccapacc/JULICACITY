import { useState } from "react";
import {
  Box,
  Button,
  TextField,
  Typography,
  InputAdornment,
  Collapse,
  Divider,
} from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";

export default function RouteSelector({ onSelectDestination }) {

  const [selectedRoute, setSelectedRoute] = useState(null);
  const [direction, setDirection] = useState("IDA");
  const [selectedDestination, setSelectedDestination] = useState(null);


  // Lista de rutas
  const routes = ["18", "Naranja", "55", "22"];

  // Datos simulados
  const routeData = {
    distancia: "23 metros",
    paraderos: ["Parque Triciclista", "Cementerio Central", "Grifo Sillustani"],
  };

  const destinations = [
    {
      name: "Terminal Terrestre de Juliaca",
      lat: -15.5,
      lng: -70.15,
    },
    {
      name: "Aeropuerto Internacional Inca Manco Cápac",
      lat: -15.47,
      lng: -70.15,
    },
    {
      name: "Centro Comercial #2",
      lat: -15.49,
      lng: -70.13,
    },
  ];
  
  const handleDestinationClick = (dest) => {
    setSelectedDestination(dest.name);
    // Si el mapa padre pasó una función, la ejecutamos
    if (onSelectDestination) onSelectDestination(dest);
  };

  return (
    <Box
      sx={{
        bgcolor: "#1e1e1e",
        borderTopLeftRadius: 20,
        borderTopRightRadius: 20,
        p: 2,
        color: "white",
      }}
    >
      {/* Botones de rutas */}
      <Box sx={{ display: "flex", justifyContent: "space-around", mb: 2 }}>
        {routes.map((route, i) => (
          <Button
            key={i}
            variant="contained"
            onClick={() =>
              setSelectedRoute(selectedRoute === route ? null : route)
            }
            sx={{
              bgcolor: selectedRoute === route ? "#00aaff" : "#333",
              color: "white",
              textTransform: "none",
              fontWeight: "bold",
              px: 3,
              borderRadius: 5,
              transition: "0.3s",
              "&:hover": {
                bgcolor: selectedRoute === route ? "#00aaff" : "#444",
              },
            }}
          >
            {route}
          </Button>
        ))}
      </Box>

      {/* Si no hay ruta seleccionada → mostrar buscador y destinos */}
      {!selectedRoute && (
        <>
          <TextField
            variant="outlined"
            placeholder="Buscar destino o paradero..."
            fullWidth
            InputProps={{
              sx: {
                bgcolor: "#2b2b2b",
                borderRadius: 5,
                color: "white",
              },
              startAdornment: (
                <InputAdornment position="start">
                  <SearchIcon sx={{ color: "#aaa" }} />
                </InputAdornment>
              ),
            }}
          />

          {/* 🔹 Lista de destinos convertidos en botones */}
          <Box sx={{ mt: 2, display: "flex", flexDirection: "column", gap: 1 }}>
            {destinations.map((dest, i) => (
              <Button
                key={i}
                onClick={() => handleDestinationClick(dest)}
                variant={selectedDestination === dest.name ? "contained" : "outlined"}
                sx={{
                  justifyContent: "flex-start",
                  color: selectedDestination === dest.name ? "white" : "#ccc",
                  bgcolor: selectedDestination === dest.name ? "#00aaff" : "transparent",
                  borderColor: "#444",
                  textTransform: "none",
                  fontWeight: "bold",
                  "&:hover": { bgcolor: "#00aaff33" },
                }}
              >
                📍 {dest.name}
              </Button>
            ))}
          </Box>
        </>
      )}

      {/* Detalle de ruta seleccionada */}
      <Collapse in={!!selectedRoute}>
        {selectedRoute && (
          <Box sx={{ mt: 2, textAlign: "center" }}>
            {/* Botones IDA / VUELTA */}
            <Box
              sx={{
                display: "flex",
                justifyContent: "center",
                gap: 2,
                mb: 2,
              }}
            >
              <Button
                variant={direction === "IDA" ? "contained" : "outlined"}
                onClick={() => setDirection("IDA")}
                sx={{
                  bgcolor: direction === "IDA" ? "#d32f2f" : "transparent",
                  color: direction === "IDA" ? "white" : "#d32f2f",
                  borderColor: "#d32f2f",
                  fontWeight: "bold",
                  px: 3,
                  "&:hover": { bgcolor: "#d32f2f", color: "white" },
                }}
              >
                IDA
              </Button>
              <Button
                variant={direction === "VUELTA" ? "contained" : "outlined"}
                onClick={() => setDirection("VUELTA")}
                sx={{
                  bgcolor: direction === "VUELTA" ? "#00e676" : "transparent",
                  color: direction === "VUELTA" ? "white" : "#00e676",
                  borderColor: "#00e676",
                  fontWeight: "bold",
                  px: 3,
                  "&:hover": { bgcolor: "#00e676", color: "white" },
                }}
              >
                VUELTA
              </Button>
            </Box>

            {/* Distancia */}
            <Typography variant="body2" color="#ccc">
              La unidad más cerca se encuentra a
            </Typography>
            <Typography variant="h4" sx={{ fontWeight: "bold" }}>
              {routeData.distancia}
            </Typography>

            <Divider sx={{ my: 1, bgcolor: "#444" }} />

            {/* Paraderos */}
            <Typography variant="subtitle1" sx={{ textAlign: "left", mb: 1 }}>
              <strong>Ruta:</strong>
            </Typography>

            <Box sx={{ display: "flex", flexDirection: "column", pl: 2 }}>
              {routeData.paraderos.map((p, i) => (
                <Box key={i} sx={{ display: "flex", alignItems: "flex-start" }}>
                  {/* Ícono y línea punteada */}
                  <Box
                    sx={{
                      display: "flex",
                      flexDirection: "column",
                      alignItems: "center",
                      mr: 2,
                    }}
                  >
                    <Box
                      sx={{
                        width: 22,
                        height: 22,
                        borderRadius: "50%",
                        backgroundColor: "#00aaff",
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "center",
                      }}
                    >
                      <span role="img" aria-label="pin">
                        📍
                      </span>
                    </Box>
                    {i !== routeData.paraderos.length - 1 && (
                      <Box
                        sx={{
                          width: 2,
                          height: 30,
                          borderLeft: "2px dashed #555",
                          marginTop: "2px",
                          marginBottom: "2px",
                        }}
                      />
                    )}
                  </Box>

                  {/* Nombre del paradero */}
                  <Typography
                    variant="body1"
                    sx={{
                      color: "#fff",
                      fontWeight: "bold",
                      mb: 1.5,
                      mt: 0.3,
                    }}
                  >
                    {p}
                  </Typography>
                </Box>
              ))}
            </Box>
          </Box>
        )}
      </Collapse>
    </Box>
  );
}
