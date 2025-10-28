const API_VEHICLES = import.meta.env.VITE_API_VEHICLES;
const API_ALERTS = import.meta.env.VITE_API_ALERTS;

export const fetchVehicles = async () => {
  const res = await fetch(API_VEHICLES);
  return res.json();
};

export const fetchAlerts = async () => {
  const res = await fetch(`${API_ALERTS}/active`);
  return res.json();
};