ESTRUCTURA PROFESIONAL DEL PROYECTO SOLAR MANAGER

Proyecto JavaFX mantenido como NetBeans Ant y Java 8.

Estructura principal:

src/modelo
Contiene las clases de dominio de la aplicación: Cliente, Comercial, Producto, Presupuesto, Factura, InstalacionFotovoltaica, Direccion y clases relacionadas.

src/components
Contiene la capa visual JavaFX: ficheros FXML, controladores de pantalla, componentes visuales reutilizables y navegación. Se conservan las rutas originales para mantener la compatibilidad con los FXMLLoader y los fx:controller.

src/service
Contiene lógica de negocio reutilizable. Los servicios agrupan validaciones, cálculos, operaciones de presupuesto, stock, cliente, producto, comercial y factura.

src/dao
Contiene las clases de acceso a datos de MongoDB. Esta capa centraliza operaciones sobre colecciones para evitar acoplar la interfaz gráfica directamente a la base de datos.

src/mapper
Contiene transformadores entre documentos MongoDB org.bson.Document y objetos del modelo. Esta capa evita repetir conversiones dentro de los controladores y permite probar las conversiones con JUnit sin conectar con MongoDB.

src/db
Contiene la configuración y conexión principal a MongoDB.

src/integration
Contiene integraciones con servicios externos. Las integraciones se organizan por proveedor o tecnología:
- integration/google
- integration/supabase

src/report
Contiene clases auxiliares para informes, repositorio de datos de informes y compilación de JasperReports.

src/pdf
Contiene generación de documentos PDF.

src/utils
Contiene utilidades transversales de la aplicación, como alertas, cifrado y utilidades PDF.

test
Contiene pruebas unitarias con JUnit 4 organizadas por capa:
- test/modelo
- test/service
- test/mapper
- test/utils
