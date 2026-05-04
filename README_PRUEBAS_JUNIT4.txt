PRUEBAS UNITARIAS CON JUNIT 4

Configuración necesaria en NetBeans:

1. Clic derecho sobre el proyecto.
2. Properties.
3. Libraries.
4. Test Libraries.
5. Add Library.
6. Seleccionar JUnit 4.
7. Ejecutar Clean and Build.

Pruebas incluidas:

test/modelo
- DireccionTest
- LineaPresupuestoTest

test/service
- PresupuestoCalculoServiceTest
- ValidacionServiceTest
- StockServiceTest

test/mapper
- DireccionMapperTest
- LineaPresupuestoMapperTest
- ProductoMapperTest
- InstalacionMapperTest
- ClienteMapperTest
- PresupuestoMapperTest

test/utils
- CifradoDatosTest

Criterio de pruebas:

Las pruebas unitarias se centran en clases que pueden ejecutarse sin abrir JavaFX, sin conectar con MongoDB, sin llamar a Google Solar API, sin llamar a Supabase y sin generar documentos reales.

Se prueban principalmente:
- cálculos de presupuesto
- validaciones de datos
- lógica de stock
- cifrado y descifrado
- conversión Document ↔ modelo mediante mappers
- comportamiento básico de clases de modelo

No se prueban directamente con JUnit 4:
- controladores JavaFX
- ventanas FXML
- conexión real a MongoDB
- conexión real a Supabase
- conexión real a Google Solar API
- generación real de JasperReports o PDF

Esas partes corresponden a pruebas funcionales o de integración, no a pruebas unitarias puras.

Orden recomendado de ejecución:

1. Ejecutar primero cada test individual con Test File.
2. Cuando todos pasen, ejecutar Test sobre el proyecto completo.
3. Si NetBeans ejecuta alguna suite antigua generada automáticamente y falla, eliminar esa suite si no pertenece a las pruebas unitarias actuales.
