import { useStore } from "../store/useStore";

export default function AlertsPanel() {
  const { alerts } = useStore();

  return (
    <div className="fixed bottom-0 left-0 w-full bg-white rounded-t-2xl shadow-lg max-h-[30vh] overflow-y-auto p-4">
      <h2 className="text-lg font-semibold mb-2 text-gray-800">
        🚨 Alertas Activas
      </h2>
      {alerts.length === 0 ? (
        <p className="text-gray-500 text-sm">No hay alertas activas</p>
      ) : (
        alerts.map((a) => (
          <div
            key={a.id}
            className={`border-l-4 p-3 mb-2 rounded shadow-sm ${
              a.severity === "CRITICAL"
                ? "border-red-500 bg-red-50"
                : a.severity === "WARN"
                ? "border-yellow-400 bg-yellow-50"
                : "border-blue-400 bg-blue-50"
            }`}
          >
            <p className="text-sm font-semibold">{a.type}</p>
            <p className="text-xs text-gray-700">{a.message}</p>
          </div>
        ))
      )}
    </div>
  );
}