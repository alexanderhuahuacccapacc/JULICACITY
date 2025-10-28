import { Box, Typography, List, ListItem, ListItemText } from "@mui/material";

const mensajes = [
  {
    fecha: "Martes, 16 septiembre",
    texto: "En la app encontrarás soporte disponible 24/7 para cualquier situación",
  },
  {
    fecha: "Hoy, 28 octubre",
    texto: "Antecedentes, fotos y vehículo de cada conductor verificados.",
  },
];

export default function Notifications() {
  return (
    <Box sx={{ bgcolor: "#1c1c1c", color: "white", height: "100vh", p: 2 }}>
      <Typography variant="h6" sx={{ mb: 2 }}>
        Notificaciones
      </Typography>
      <List>
        {mensajes.map((msg, i) => (
          <ListItem key={i} sx={{ bgcolor: "#2b2b2b", mb: 1, borderRadius: 2 }}>
            <ListItemText
              primary={msg.texto}
              secondary={msg.fecha}
              secondaryTypographyProps={{ color: "#aaa" }}
            />
          </ListItem>
        ))}
      </List>
    </Box>
  );
}
