import { useEffect } from "react";
import { over } from "stompjs";
import SockJS from "sockjs-client";
import { useStore } from "../store/useStore";

export default function useStomp() {
  const { setVehicles } = useStore();

  useEffect(() => {
    const wsUrl = import.meta.env.VITE_WS_VEHICLES;
    const socket = new SockJS(wsUrl);
    const stompClient = over(socket);

    stompClient.connect({}, () => {
      stompClient.subscribe("/topic/vehicles", (message) => {
        const data = JSON.parse(message.body);
        setVehicles((prev) => {
          const others = prev.filter((v) => v.id !== data.id);
          return [...others, data];
        });
      });
    });

    return () => {
      stompClient.disconnect();
    };
  }, [setVehicles]);
}