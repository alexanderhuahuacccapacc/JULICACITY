import { Box, Button, Typography } from "@mui/material";
import { useState, useEffect } from "react";
import MapView from "../components/MapView";
import L from "leaflet";

// Ícono del bus
const busIcon = new L.Icon({
  iconUrl: "https://cdn-icons-png.flaticon.com/512/743/743922.png",
  iconSize: [45, 45],
  iconAnchor: [22, 45],
});

export default function DriverMode() {
  const [sharing, setSharing] = useState(true);
  const [busPosition, setBusPosition] = useState({
    lat: -15.4997,
    lng: -70.1296,
  });

  // Simular movimiento del bus cada 3 segundos
  useEffect(() => {
    if (!sharing) return;

    const interval = setInterval(() => {
      setBusPosition((prev) => ({
        lat: prev.lat + (Math.random() - 0.5) * 0.0003,
        lng: prev.lng + (Math.random() - 0.5) * 0.0003,
      }));
    }, 3000);

    return () => clearInterval(interval);
  }, [sharing]);

  return (
    <Box sx={{ position: "relative", height: "100vh" }}>
      {/* Mapa */}
      <MapView
        marker={{
          lat: busPosition.lat,
          lng: busPosition.lng,
          name: "Mi ubicación (bus)",
          icon: busIcon,
        }}
      />

      {/* Panel inferior */}
      <Box
        sx={{
          position: "absolute",
          bottom: 0,
          left: 0,
          width: "100%",
          bgcolor: "#2b2b2b",
          color: "white",
          borderTopLeftRadius: 20,
          borderTopRightRadius: 20,
          p: 2,
          textAlign: "center",
          zIndex: 3,
        }}
      >
        {/* Línea superior */}
        <Box
          sx={{
            width: 40,
            height: 4,
            bgcolor: "#777",
            borderRadius: 2,
            mx: "auto",
            mb: 1,
          }}
        />

        <Typography variant="h6" sx={{ mb: 1, fontWeight: "bold" }}>
          Compartiendo ubicación
        </Typography>

        {/* Botones IDA / VUELTA */}
        <Box sx={{ display: "flex", justifyContent: "center", gap: 2, mb: 1 }}>
          <Button
            variant="outlined"
            sx={{
              color: "#d32f2f",
              borderColor: "#d32f2f",
              fontWeight: "bold",
              px: 3,
              "&:hover": { bgcolor: "#d32f2f", color: "white" },
            }}
          >
            IDA
          </Button>
          <Button
            variant="outlined"
            sx={{
              color: "#00e676",
              borderColor: "#00e676",
              fontWeight: "bold",
              px: 3,
              "&:hover": { bgcolor: "#00e676", color: "white" },
            }}
          >
            VUELTA
          </Button>
        </Box>

        <hr style={{ borderColor: "#555", margin: "10px 0" }} />

        {/* Ruta simulada */}
        <Typography
          variant="subtitle1"
          sx={{ textAlign: "left", fontWeight: "bold", mb: 1 }}
        >
          Ruta:
        </Typography>

        <Box sx={{ display: "flex", flexDirection: "column", pl: 3 }}>
          {["Parque Triciclista", "Cementerio Central", "Grifo Sillustani"].map(
            (p, i) => (
              <Box
                key={i}
                sx={{ display: "flex", alignItems: "flex-start", mb: 1 }}
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
                    mr: 2,
                    fontSize: 14,
                  }}
                >
                  📍
                </Box>
                <Typography sx={{ color: "white", fontWeight: "bold" }}>
                  {p}
                </Typography>
              </Box>
            )
          )}
        </Box>

        {/* Botones de acción */}
        <Box sx={{ mt: 2 }}>
          <Button
            variant="outlined"
            fullWidth
            sx={{
              color: "white",
              borderColor: "white",
              borderRadius: 5,
              mb: 1,
              "&:hover": { bgcolor: "rgba(255,255,255,0.1)" },
            }}
            onClick={() => setSharing(false)}
          >
            Detener ubicación
          </Button>

          <Button
            variant="outlined"
            fullWidth
            sx={{
              color: "white",
              borderColor: "white",
              borderRadius: 5,
              "&:hover": { bgcolor: "rgba(255,255,255,0.1)" },
            }}
          >
            En trasbordo
          </Button>
        </Box>
      </Box>
    </Box>
  );
}
