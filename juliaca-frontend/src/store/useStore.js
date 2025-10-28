import { create } from "zustand";

export const useStore = create((set) => ({
  vehicles: [],
  alerts: [],
  setVehicles: (vehicles) => set({ vehicles }),
  setAlerts: (alerts) => set({ alerts }),
}));