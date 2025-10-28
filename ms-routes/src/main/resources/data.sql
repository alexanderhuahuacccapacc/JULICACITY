INSERT INTO line (id, name, color) VALUES (1, 'Línea 18', '#FF5733');
INSERT INTO line (id, name, color) VALUES (2, 'Línea 22', '#33FF57');

-- NOTA: Para rutas y paraderos puedes insertar luego con PostGIS:
-- INSERT INTO route (name, path, line_id) VALUES ('Ruta 18 Ida', ST_GeomFromText('LINESTRING(-70.12 -15.5, -70.13 -15.51, -70.14 -15.52)', 4326), 1);
-- INSERT INTO stop (name, location, route_id) VALUES ('Paradero Central', ST_GeomFromText('POINT(-70.13 -15.51)', 4326), 1);