En el presente documento se detallara la arquitectura y diseño  del Proyecto para la materia de AYGO.
El titulo del proyecto es: "Entendimiento de sistema de replicación de clusters, para escenarios de contingencia"

Para este proyecto se desarollo una aplicación en lenguaje JAVA,llamada RESOFA (Replication software for all) la cual ofrece por medio de una GUI diferentes opciones para validar que la información se este replicando de manera correcta a cada uno de los nodos presentes en la arquitectura de cluster, validar cual es el nodo que esta asumiendo el rol de maestro y realizar una sincronización de datos con base en una referencia seleccionable, la aplicación esta pensada como una solución que se ofrezca de forma gratuita para empresas que quieran implementar procesos de replciación en clusters, pero no cuenten con el musculo financiero para adquirir soluciones pagas.

Arquitectura de la Aplicación RESOFA
![image](https://github.com/user-attachments/assets/c12cb55c-ef7d-4e39-8b9a-7874b15d04b1)

La apicación realiza conexóon a cada una de las bases de datos configuradas para validar su conexión, realizar consulta de información y el proceso de sincronización enc aso de ser requerido.

GUI de RESOFA
![image](https://github.com/user-attachments/assets/72def4da-d071-4fea-b86e-5ac1da973c14)

La GUI ofrece 3 componentes claves los cuales se detallaran a continuación:

Campo para ingresar consultas: Aquí se cuenta con un espacio donde se puede escribir el query que se desea ejecutar y al oprimir el botón “ejecutar consulta” este quiery procederá a ser ejecutado en ambas bases de datos, para que la información este actualizada y sincronizada.

![image](https://github.com/user-attachments/assets/300b8a57-8ad0-461f-b124-442d278cb5f1)

Acción del botón ejecutar consulta: Al ser oprimido hace un llamado a la función: “ejecutar consulta” la cual verifica que haya conexión hacia las bases de datos configuradas: Producción y contingencia.

![image](https://github.com/user-attachments/assets/4c665f31-c40f-4aee-bc41-c4fa9fc3cad7)

![image](https://github.com/user-attachments/assets/a787943f-9d96-495f-9082-80029e472e36)

En caso de que exista conexión hacia estas bases de datos, la función procede a hacer un llamado a otra función llamada “ejecutarConsultaEnBaseDeDatos” la cual se encargará de ejecutar la consulta ingresada, en la base de datos donde ya se validó la conexión.

![image](https://github.com/user-attachments/assets/608aa417-2087-4ebe-a8f1-930c75548c08)



