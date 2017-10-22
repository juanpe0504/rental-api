Autor: Juan Pereira

#Rental Api

El siguiente es un proyecto para consumir servicios RESTfull en java utilizando Glassfish Server 4.1


Getting Started

El proyecto se puede obtener directamente de la carpeta /dist y copiarlo a un glassfish server. Tambien se puede clonar el repositorio, abrir en Netbeans 8, y pulsar el boton Play para mostrar una interfaz sencilla para consumir los servicios.

El proyecto se realizo creando un pequeño modelo de datos que consiste en 2 tablas. Una de ella de nombre Rentals y la segunda RentalsType. Este pequeño modelo relaciona ambas tablas a traves de un foreign key en la tabla Rentals que apunta al id de la tabla RentalsType, asi como tambien un foreign key a si mismo por si el objeto es de tipo Promocion Familiar. Esto ultimo es para cumplir con la condicion solicitada de que se debe manejar un descuento especial.
RentalsType cuenta con 4 propiedades que son:
	int id;               Identificador del objeto
  String description;   Descripcion del tipo de rentals
  double  tarifa;       Tarifa a cobrar 
  double  descuento;    Descuento asociado

Rentals cuenta con 6 propiedades que son:
  public int id;             Identificador del objeto
  public int id_parent;      ID para identificar Hijos y padres
  public String description; Descripcion del tipo de rentals
  public int id_type;        Tipo de Rentals
  public double total;       Valor total del Rentals
  public int timing;         La cantidad de horas, semanas, dias contabilizados

 Una ves armado el modelo se creo un proyecto en netbeans para permitir conexiones locales a un archivo json. Asi, permite la ejecucion y prueba rapida de estos servicios. A continuacion los servicios creados:

Servicios disponibles:

GET
	/getRentals
		Permite obtener todos los Rentals existentes.
	/getRentalsTypes
		Permite obtener todos los tipos de rentals registrados
POST
	/getRentalsTypesById
		Permite seleccionar los tipos de rentals por id
	/getRentalsByType
		Permite seleccionar los rentals segun su tipo de rentals 
	/getRentalsByParent
		Permite seleccionar los rentals por padre
	/getRentalsById
		Permite seleccionar los rentals por id
	/deleteRentalsById
		Permite eliminar un rentals segun su id, asi como tambien los hijos que lo tengan como padre
	/createRentals
		Permite crear un nuevo rental
	/updateRentals
		Permite actualizar los datos de un rental existente

Ademas como se menciona, estos serivcios pueden ser consumidos de forma manual (probados en Postman) o utilizando la vista web que se desarrollo. Los el api esta apuntando localmente a servidor localhost. Adicional se presenta la URL del api una ves levantado el servidor:

api = localDomain + "/rentals/webresources/Rental/" + servicioDeseado;

La pruebas se realizaron pueden ser ejecutadas en el frontEnd. Haciendo click en el boton Pruebas se mostraran unas pestañas de pruebas.
La primera de ellas es una prueba de insercion donde se llama al servicio de creacion. Se realiza unas 5 inserciones, donde se mostrara en la parte inferior el resultado de la prueba. Indica insertado si el registro se guardo exitosamente y ERROR en caso de algun error en servidor (por el tema de archivos, posible error al leer el archivo por varios llamados)
NOTA estas inserciones seran de tipos 1,2,3. La segunda prueba sera para ingresar de tipo 4.
La segunda prueba permite insertar registros de tipo Renta Familiar.
Por otro lado, la pruebas para eliminar registros, pueden realizarse directamente en el listado de rentals, ya que cada rental tiene su action para remover el registro de la tabla.

Notese que no se permite editar un registro de type Familiar (id_type 4) ya que esta compuesto por por otros y es el padre. Ademas si se elimina un registro de tipo padre se eliminaran todos sus hijos pertinentes. 
Tambien se permite visualizar los detalles de una renta familiar en su seccion de acciones en la tabla.