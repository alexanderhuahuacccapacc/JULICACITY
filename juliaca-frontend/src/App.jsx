import { useEffect } from "react";
import MapView from "./components/MapView";
import AlertsPanel from "./components/AlertsPanel";
import { fetchAlerts, fetchVehicles } from "./api/api";
import { useStore } from "./store/useStore";
import useStomp from "./hooks/useStomp";

export default function App() {
  const { setVehicles, setAlerts } = useStore();
  useStomp();

  useEffect(() => {
    const loadData = async () => {
      const v = await fetchVehicles();
      const a = await fetchAlerts();
      setVehicles(v);
      setAlerts(a);
    };
    loadData();
  }, [setVehicles, setAlerts]);

  return (
    <div className="h-screen w-full bg-gray-100 flex flex-col">
      <MapView />
      <AlertsPanel />
    </div>
  );
}