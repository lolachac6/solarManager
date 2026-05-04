CAMBIOS REALIZADOS

1. Se mantiene el proyecto como NetBeans Ant y Java 8.

2. Se conserva la estructura visual principal en src/components para no romper rutas FXML, controladores ni navegación JavaFX.

3. Se eliminó código residual no referenciado del paquete antiguo src/components/erp, que duplicaba una pantalla de alta de presupuesto y tenía un paquete Java inconsistente.

4. Se reorganizaron las integraciones externas en src/integration:
- APIGoogleSolar pasa a integration/google/legacy.
- Integration/google pasa a integration/google.
- ConexionSupabase pasa a integration/supabase.

5. Se actualizaron los imports de Google y Supabase en los controladores y clases afectadas.

6. Se creó src/report para separar clases técnicas de informes de la capa visual.

7. Se sustituyó el acceso antiguo database.getCollection por report.ReportRepository.getCollection en la pantalla de informes.

8. Se revisaron rutas FXML usadas con getResource y se corrigieron rutas con mayúsculas/minúsculas que no coincidían con los nombres reales de los archivos.

9. Se corrigió el fx:controller del FXML consumoAnual.fxml para apuntar a la clase existente.

10. Se añadieron pruebas JUnit 4 adicionales para la capa mapper, service, modelo y utils.

11. Se validó que todos los ficheros Java tienen paquetes coherentes con su carpeta física.

12. Se validó que los fx:controller declarados en FXML apuntan a clases existentes.

13. Se validó que las rutas absolutas getResource detectables apuntan a recursos existentes dentro de src.

14. Se compiló la capa no visual principal compatible con el entorno disponible: modelo, dao, db, mapper, integration, service parcial, report y CifradoDatos.

15. Se ejecutó una validación local de las pruebas unitarias incluidas mediante un runner auxiliar temporal, obteniendo 35 pruebas superadas.
