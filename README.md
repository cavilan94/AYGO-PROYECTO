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

Pruebas realizadas para verificar el funcionamiento de la aplicación:

Pruebas de escritura: Se realizaron pruebas ejecutando diferentes tipos de queries hacia las bases de datos configuradas, para validar si el resultado obtenido era el mismo para todas o si por el contrario, se presentaban inconsistencias o problemas para poder visualizar el resultado.
Al ejecutar una consulta sobre alguna de las tablas presentes en la base de datos, se visualizó la misma respuesta o salida para cada uno de los nodos, como se puede ver a continuación:

![image](https://github.com/user-attachments/assets/2af72795-9450-4ea5-9aa5-6f0dadf424db)

De igual forma al realizar alguna modificación sobre los registros de la tabla, como insertar algún valor, actualizarlo o borrarlo, se observó que el resultado obtenido era que el cambio había sido aplicado en todas las bases de datos, como se puede ver en el siguiente ejemplo donde se realizan cambios sobre los registros de la tabla y al consultarlos nuevamente vemos que han sido modificados para todas las bases de datos.

![image](https://github.com/user-attachments/assets/f373ec22-d4d0-45e8-a261-16881e5caa5b)

Pruebas al haber indisponibilidad de las bases de datos: en esta prueba se realizaron modificación sobre las credenciales del usuario configurado para acceder a la base de datos del nodo principal, para de esta forma simular un problema de conexión, que se pudo haber generado por que el nodo se encuentra caído, no se tiene alcance dentro de la red, se modificó algún puerto, entre otras posibles razones.

![image](https://github.com/user-attachments/assets/40554d26-3161-4bd5-900d-05f1000a3b5b)

En este escenario al momento de ejecutar algún tipo de querie desde la interfaz de RESOFA, el resultado fue que en la pantalla solo se mostró la salida de la base de datos que aún mantenía conexión con RESOFA, ya que con la otra base de datos no fue posible establecer una conexión y por ende la ejecución de comandos no llegó.

![image](https://github.com/user-attachments/assets/ffcbeb29-c6a5-4c43-9182-502d519daf68)

En este escenario al momento de ejecutar algún tipo de querie desde la interfaz de RESOFA, el resultado fue que en la pantalla solo se mostró la salida de la base de datos que aún mantenía conexión con RESOFA, ya que con la otra base de datos no fue posible establecer una conexión y por ende la ejecución de comandos no llegó.

![image](https://github.com/user-attachments/assets/0fe6e468-7704-4914-9819-fdbadbf56d60)

![image](https://github.com/user-attachments/assets/adbd97c8-adb6-45e0-a8db-ea51eaa7e85c)

Luego se probó el botón para validar cual es el nodo maestro que se está encargando de procesar las solicitudes en ese momento, ya que el nodo al cual no se puede conectar era el nodo principal, el resultado de la validación fue que el nodo maestro era el de contingencia.

![image](https://github.com/user-attachments/assets/fb7829fc-1217-4fd3-8a4e-d5f1bd8d3627)

Una vez se restablecieron los valores correctos de las credenciales utilizadas y se pudo establecer nuevamente conexión con la base de datos, se procedió a validar por medio de una consulta que valores contenían las tablas de cada base de datos, encontrándose inconsistencias en la información mostrada, como se puede ver a continuación:

![image](https://github.com/user-attachments/assets/1133f898-3ba7-40b6-ba9a-728ca28afaa6)

![image](https://github.com/user-attachments/assets/11e7ead0-0382-4c7f-a7e0-c63b1d5df103)

Lo anterior debido a que mientras uno de los nodos se encontraba indisponible, se realizaron modificaciones sobre el otro nodo, los cuales no pudieron ser replicados en tiempo real, por lo tanto, el nodo inactivo se encuentra en un estado desactualizado, para corregir esta inconsistencia, se realizó la tercera prueba que se explicara a continuación.

Pruebas de sincronización post-desastres: En esta prueba lo que se realizó fue verificar el contenido de las tablas antes y después de realizar las tareas de sincronización para poder igualar la información presente en cada base de datos, antes de realizar el proceso de sincronización, como se mencionó en la anterior, se evidenciaron algunas prueba inconsistencias que debían ser corregidas. Frente a este escenario se procedió a probar el botón diseñado para poder sincronizar las bases de datos, lo que hace este botón es hacer un llamado a una función que realiza unas tareas de iteración validando el contenido entre las tablas de cada base de datos, con base en una indicación previa que se da para saber contra que se debe iterar, es decir cuál es la base de datos que se debe tomar como referencia para igualar el contenido de ambas bases de datos, lo anterior se hace pensando en que el usuario tenga libertad de escoger la manera en que se debe procesar la información. Una vez se completó el proceso de sincronización de los datos se volvió a consultar el contenido de las tablas encontrando que los registros eran iguales para ambas bases de datos, comprobando que el proceso de sincronización fue realizado de manera correcta.

![image](https://github.com/user-attachments/assets/46090f89-cc5a-485f-9f01-090455ab0949)

![image](https://github.com/user-attachments/assets/b3674571-b365-4930-9d33-ef17c2073654)

![image](https://github.com/user-attachments/assets/d1381873-81d2-4696-9254-c2ffce326e43)


