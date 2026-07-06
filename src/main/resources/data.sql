-- ============================================
-- ROLES (según ejemplo del documento, punto 1.2)
-- ============================================
INSERT IGNORE INTO rol (nombre_rol, estado) VALUES ('Superusuario', true);
INSERT IGNORE INTO rol (nombre_rol, estado) VALUES ('Vendedor', true);
INSERT IGNORE INTO rol (nombre_rol, estado) VALUES ('Almacen', true);
INSERT IGNORE INTO rol (nombre_rol, estado) VALUES ('Contabilidad', true);

-- ============================================
-- TIPOS DE OPERACION DE KARDEX (punto 4 del documento)
-- ============================================
INSERT IGNORE INTO tipo_operacion (descripcion) VALUES ('Ingreso');
INSERT IGNORE INTO tipo_operacion (descripcion) VALUES ('Venta');
INSERT IGNORE INTO tipo_operacion (descripcion) VALUES ('Extorno');
INSERT IGNORE INTO tipo_operacion (descripcion) VALUES ('Ajuste');

-- ============================================
-- CORRELATIVOS DE VENTA (punto 3.1 del documento)
-- ============================================
INSERT IGNORE INTO correlativo (serie, numero_actual) VALUES ('F001', 0);
INSERT IGNORE INTO correlativo (serie, numero_actual) VALUES ('B001', 0);

-- ============================================
-- FUNCIONALIDADES BASE (menu del sistema, punto 1.3)
-- ============================================
INSERT IGNORE INTO funcionalidad (nombre, icono, padre) VALUES ('Roles', 'shield', NULL);
INSERT IGNORE INTO funcionalidad (nombre, icono, padre) VALUES ('Funcionalidades', 'grid', NULL);
INSERT IGNORE INTO funcionalidad (nombre, icono, padre) VALUES ('Permisos', 'lock', NULL);
INSERT IGNORE INTO funcionalidad (nombre, icono, padre) VALUES ('Usuarios', 'user', NULL);
INSERT IGNORE INTO funcionalidad (nombre, icono, padre) VALUES ('Categorias', 'tag', NULL);
INSERT IGNORE INTO funcionalidad (nombre, icono, padre) VALUES ('Clientes', 'users', NULL);
INSERT IGNORE INTO funcionalidad (nombre, icono, padre) VALUES ('Productos', 'box', NULL);
INSERT IGNORE INTO funcionalidad (nombre, icono, padre) VALUES ('Tipos de Documento', 'file-text', NULL);
INSERT IGNORE INTO funcionalidad (nombre, icono, padre) VALUES ('Ingreso de Productos', 'inbox', NULL);
INSERT IGNORE INTO funcionalidad (nombre, icono, padre) VALUES ('Ajuste de Productos', 'sliders', NULL);
INSERT IGNORE INTO funcionalidad (nombre, icono, padre) VALUES ('Kardex', 'list', NULL);
INSERT IGNORE INTO funcionalidad (nombre, icono, padre) VALUES ('Registrar Venta', 'shopping-cart', NULL);
INSERT IGNORE INTO funcionalidad (nombre, icono, padre) VALUES ('Anular Venta', 'x-circle', NULL);
INSERT IGNORE INTO funcionalidad (nombre, icono, padre) VALUES ('Ver Ventas', 'eye', NULL);