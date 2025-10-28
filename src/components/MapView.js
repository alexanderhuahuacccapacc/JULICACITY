import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import L from "leaflet";

// Ícono personalizado para el marcador (puedes usar el tuyo)
const markerIcon = new L.Icon({
  iconUrl:
    "https://cdn-icons-png.flaticon.com/512/252/252025.png", // marcador azul
  iconSize: [35, 35],
  iconAnchor: [17, 35],
  popupAnchor: [0, -30],
});

export default function MapView({ marker }) {
  // Si hay marcador, centramos ahí; si no, centramos en Juliaca
  const center = marker ? [marker.lat, marker.lng] : [-15.4997, -70.1296];

  return (
    <MapContainer
      center={center}
      zoom={14}
      scrollWheelZoom={true}
      style={{
        width: "100%",
        height: "100vh",
        zIndex: 1,
      }}
    >
      {/* 🌙 Capa de mapa oscuro de Carto (basado en OpenStreetMap) */}
      <TileLayer
        attribution='&copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> & Carto'
        url="https://{s}.basemaps.cartocdn.com/dark_all/{z}/{x}/{y}{r}.png"
      />

      {/* 📍 Marcador */}
      {marker && (
        <Marker position={[marker.lat, marker.lng]} icon={markerIcon}>
          <Popup>{marker.name}</Popup>
        </Marker>
      )}
    </MapContainer>
  );
}
