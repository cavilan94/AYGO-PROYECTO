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

En la pantalla de resultados se imprimen las salidas de las bases de datos configuradas, lo anterior solo si fue posible establecer conexión con ambas bases de datos.

![image](https://github.com/user-attachments/assets/4b5e3aec-928c-45eb-9e4b-4da9e05c9c78)

Botón para verificar el nodo Maestro: Este botón tiene como propósito que al ser oprimido haga una validación sobre cuál de los nodos está ejecutando la función o el role de nodo Maestro, lo anterior lo hace por medio de un proceso de validación con respecto a la conexión del nodo de producción.
![image](https://github.com/user-attachments/assets/729a8537-3009-495b-8988-3f8d5485cb10)

Acción del botón verificar Nodo Maestro: De acuerdo con la validación realizada sobre el nodo de producción se toma una decisión y se indica si el nodo maestro es el nodo de Producción (conexión con base de producción exitosa) o si es el nodo Maestro es el nodo de contingencia (en caso de no poder establecer conexión con el nodo de producción)

![image](https://github.com/user-attachments/assets/b7c77446-39c0-4ab7-8f5b-564e8685ff8e)

Botón para sincronizar contenido de las tablas: El propósito de este botón es realizar un proceso de sincronización entre las tablas de las bases de datos de producción y contingencia, para que de esa forma el contenido de ambas de bases de datos sea el mismo, esto es una medida de contingencia posterior a desastres.

![image](https://github.com/user-attachments/assets/499eda6d-c3e4-4ce8-ad45-ca27b53c518a)

Acción del botón sincronizar tablas: Lo que hace esta función es un proceso de validación con respecto a la opción escogida en el selector, de acuerdo con esta opción se toma como referencia una de las bases de datos como referencia para igualar las tablas.

![image](https://github.com/user-attachments/assets/500fb7b2-2a6f-4513-a920-51ca760b5458)

El proceso para igualar las tablas consiste en 3 acciones, la primera es realizar una consulta sobre la base de datos de referencia para conocer el contenido que se debe igualar, la segunda acción es borrar el contenido de la tabla en la base de datos que se debe actualizar y la tercera acción es insertar los datos que se tomaron como referencia en la primera acción.

![image](https://github.com/user-attachments/assets/75ac993c-ee4b-4e37-99a3-6e3b3c61bb11)

Si el proceso de sincronización se completó de forma exitosa, aparecerá en pantalla un mensaje indicándolo, de igual forma si se registra un error por parte de la base de datos, se mostrará un mensaje informando el intento fallido.
