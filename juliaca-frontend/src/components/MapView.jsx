import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import L from "leaflet";
import { useStore } from "../store/useStore";

const vehicleIcon = new L.Icon({
  iconUrl: "https://cdn-icons-png.flaticon.com/512/61/61231.png",
  iconSize: [35, 35],
});

export default function MapView() {
  const { vehicles } = useStore();

  return (
    <div className="h-[70vh] w-full">
      <MapContainer
        center={[-15.5, -70.15]}
        zoom={13}
        className="h-full w-full rounded-2xl shadow-md"
      >
        <TileLayer
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          attribution="© OpenStreetMap"
        />
        {vehicles.map((v) => (
          <Marker
            key={v.id}
            position={[v.current.lat, v.current.lng]}
            icon={vehicleIcon}
          >
            <Popup>
              <div>
                <strong>{v.plate}</strong>
                <br />
                {v.line}
                <br />
                Estado: {v.status}
              </div>
            </Popup>
          </Marker>
        ))}
      </MapContainer>
    </div>
  );
}