import { Box, Button, Typography, Drawer, List, ListItem, Divider } from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import NotificationsIcon from "@mui/icons-material/Notifications";
import { useState } from "react";
import RouteSelector from "../components/RouteSelector";
import MapView from "../components/MapView";

export default function PassengerHome() {
  const [menuOpen, setMenuOpen] = useState(false);
  const [marker, setMarker] = useState(null);
  const [menuSection, setMenuSection] = useState("Ciudad");
  const [history, setHistory] = useState([]);

  // 👉 Cuando el usuario selecciona un destino desde RouteSelector
  const handleSelectDestination = (dest) => {
    setMarker(dest);
    setHistory((prev) => [...prev, dest]);
  };

  // 👉 Renderiza el contenido del Drawer según la sección
  const renderMenuContent = () => {
    switch (menuSection) {
      case "Ciudad":
        return (
          <Typography sx={{ mt: 2, textAlign: "center" }}>
            🏙️ Estás visualizando: <b>Juliaca</b>
          </Typography>
        );
      case "Historial de Destinos":
        return (
          <Box sx={{ mt: 2 }}>
            {history.length === 0 ? (
              <Typography color="#aaa" textAlign="center">
                No hay destinos guardados aún.
              </Typography>
            ) : (
              history.map((dest, i) => (
                <Button
                  key={i}
                  variant="outlined"
                  fullWidth
                  sx={{
                    mt: 1,
                    color: "white",
                    borderColor: "#555",
                    textTransform: "none",
                    "&:hover": { bgcolor: "rgba(255,255,255,0.1)" },
                  }}
                  onClick={() => {
                    setMarker(dest);
                    setMenuOpen(false);
                  }}
                >
                  {dest.name || dest.label || `Destino ${i + 1}`}
                </Button>
              ))
            )}
          </Box>
        );
      case "Ayuda":
        return (
          <Box sx={{ mt: 2, px: 2 }}>
            <Typography sx={{ color: "#ccc" }}>
              💡 <b>Instrucciones de uso:</b>
            </Typography>
            <Typography sx={{ color: "#aaa", mt: 1 }}>
              - Selecciona tu línea y sentido de ruta. <br />
              - Visualiza las unidades disponibles. <br />
              - Puedes marcar un destino y recibir notificaciones de llegada. <br />
              - Usa el modo conductor si eres chofer.
            </Typography>
          </Box>
        );
      case "Soporte":
        return (
          <Box sx={{ mt: 2, px: 2 }}>
            <Typography sx={{ color: "#ccc" }}>
              🧰 <b>Centro de soporte:</b>
            </Typography>
            <Typography sx={{ color: "#aaa", mt: 1 }}>
              Para asistencia técnica o reportes, contacta a:<br />
              📞 955-999-111<br />
              ✉️ soporte@transportetracker.pe
            </Typography>
          </Box>
        );
      default:
        return null;
    }
  };

  return (
    <Box sx={{ position: "relative", height: "100vh", overflow: "hidden" }}>
      {/* 🌍 MAPA */}
      <Box sx={{ position: "absolute", inset: 0, zIndex: 1 }}>
        <MapView marker={marker} />
      </Box>

      {/* 🧭 PANEL DE RUTAS */}
      <Box sx={{ position: "absolute", bottom: 0, left: 0, width: "100%", zIndex: 3 }}>
        <RouteSelector onSelectDestination={handleSelectDestination} />
      </Box>

      {/* ☰ BOTÓN MENÚ */}
      <Button
        onClick={() => setMenuOpen(true)}
        sx={{
          position: "absolute",
          top: 20,
          left: 20,
          color: "white",
          zIndex: 4,
          bgcolor: "rgba(0,0,0,0.4)",
          "&:hover": { bgcolor: "rgba(0,0,0,0.6)" },
        }}
      >
        <MenuIcon />
      </Button>

      {/* 🔔 NOTIFICACIONES */}
      <Button
        href="/notifications"
        sx={{
          position: "absolute",
          top: 20,
          right: 20,
          color: "white",
          zIndex: 4,
          bgcolor: "rgba(0,0,0,0.4)",
          "&:hover": { bgcolor: "rgba(0,0,0,0.6)" },
        }}
      >
        <NotificationsIcon />
      </Button>

      {/* 🏙️ NOMBRE DE CIUDAD */}
      <Typography
        variant="h6"
        sx={{
          position: "absolute",
          top: 20,
          left: "50%",
          transform: "translateX(-50%)",
          color: "white",
          fontWeight: "bold",
          zIndex: 4,
          textShadow: "0px 0px 8px rgba(0,0,0,0.8)",
        }}
      >
        Juliaca
      </Typography>

      {/* 🧾 MENÚ LATERAL */}
      <Drawer
        anchor="left"
        open={menuOpen}
        onClose={() => setMenuOpen(false)}
        PaperProps={{
          sx: {
            bgcolor: "#2b2b2b",
            color: "white",
            width: 280,
            p: 2,
          },
        }}
      >
        {/* Lista principal */}
        <List>
          {["Ciudad", "Historial de Destinos", "Ayuda", "Soporte"].map((item) => (
            <ListItem
              key={item}
              button
              onClick={() => setMenuSection(item)}
              sx={{
                borderRadius: 2,
                "&:hover": { bgcolor: "#444" },
                color: menuSection === item ? "#00e676" : "white",
              }}
            >
              {item}
            </ListItem>
          ))}
        </List>

        <Divider sx={{ my: 2, borderColor: "#555" }} />

        {/* Contenido dinámico */}
        {renderMenuContent()}

        <Box sx={{ mt: "auto", p: 2 }}>
          <Button
            variant="contained"
            fullWidth
            href="/driver"
            sx={{
              bgcolor: "#ccff00",
              color: "black",
              fontWeight: "bold",
              borderRadius: 3,
              textTransform: "none",
              "&:hover": { bgcolor: "#aaff00" },
            }}
          >
            Modo Conductor
          </Button>
        </Box>
      </Drawer>
    </Box>
  );
}
