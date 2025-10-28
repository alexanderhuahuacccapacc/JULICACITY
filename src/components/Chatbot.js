import { useState } from "react";
import { Box, IconButton, TextField, Typography, Button } from "@mui/material";
import ChatIcon from "@mui/icons-material/Chat";
import CloseIcon from "@mui/icons-material/Close";

export default function Chatbot() {
  const [open, setOpen] = useState(false);
  const [messages, setMessages] = useState([
    { from: "bot", text: "¡Hola! 😊 ¿En qué puedo ayudarte hoy?" },
  ]);
  const [input, setInput] = useState("");

  const sendMessage = () => {
    if (!input.trim()) return;
    setMessages([...messages, { from: "user", text: input }]);
    setInput("");
  };

  return (
    <>
      {/* Botón flotante */}
      <IconButton
        onClick={() => setOpen(!open)}
        sx={{
          position: "fixed",
          bottom: 20,
          right: 20,
          bgcolor: "#00aaff",
          color: "white",
          "&:hover": { bgcolor: "#0090e0" },
        }}
      >
        {open ? <CloseIcon /> : <ChatIcon />}
      </IconButton>

      {/* Ventana del chatbot */}
      {open && (
        <Box
          sx={{
            position: "fixed",
            bottom: 80,
            right: 20,
            width: 300,
            height: 400,
            bgcolor: "#1e1e1e",
            borderRadius: 3,
            boxShadow: 4,
            p: 2,
            display: "flex",
            flexDirection: "column",
          }}
        >
          <Typography variant="h6" sx={{ color: "#00aaff", mb: 1 }}>
            Soporte
          </Typography>
          <Box
            sx={{
              flex: 1,
              overflowY: "auto",
              mb: 1,
              "&::-webkit-scrollbar": { display: "none" },
            }}
          >
            {messages.map((msg, i) => (
              <Box
                key={i}
                sx={{
                  textAlign: msg.from === "user" ? "right" : "left",
                  mb: 1,
                }}
              >
                <Typography
                  sx={{
                    display: "inline-block",
                    bgcolor: msg.from === "user" ? "#00aaff" : "#333",
                    color: "white",
                    px: 2,
                    py: 1,
                    borderRadius: 2,
                    fontSize: "0.9rem",
                  }}
                >
                  {msg.text}
                </Typography>
              </Box>
            ))}
          </Box>

          <Box sx={{ display: "flex", gap: 1 }}>
            <TextField
              value={input}
              onChange={(e) => setInput(e.target.value)}
              placeholder="Escribe un mensaje..."
              fullWidth
              size="small"
              sx={{
                bgcolor: "#2b2b2b",
                borderRadius: 2,
                input: { color: "white" },
              }}
            />
            <Button
              variant="contained"
              color="primary"
              onClick={sendMessage}
              sx={{ textTransform: "none" }}
            >
              Enviar
            </Button>
          </Box>
        </Box>
      )}
    </>
  );
}
