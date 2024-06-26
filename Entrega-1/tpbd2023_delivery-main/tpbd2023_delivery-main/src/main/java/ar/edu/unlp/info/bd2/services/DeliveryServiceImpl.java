package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.DeliveryException;
import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.DeliveryRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryServiceImpl implements DeliveryService, DeliveryStatisticsService {


	private DeliveryRepository delivery_repo;
    public DeliveryServiceImpl(DeliveryRepository repo){
        this.delivery_repo=repo;
    }

	// TP1

	/**
	 * Crea y retorna un usuario de tipo Cliente
	 * @param name nombre y apellido del cliente
	 * @param username nombre de usuario del cliente
	 * @param password clave del cliente
	 * @param email email del cliente
	 * @param dateOfBirth fecha de nacimiento del cliente
	 * @return el cliente creado
	 */
	@Override
	@Transactional
	public Client createClient(String name, String username, String password, String email, Date dateOfBirth) throws DeliveryException{
		Client newClient =  new Client(name, username, password, email, dateOfBirth);
		delivery_repo.saveClass(newClient);
		return newClient;
	}

	/**
	 * Crea y retorna un usuario de tipo Repartidor
	 * @param name nombre y apellido del repartidor
	 * @param username nombre de usuario del repartidor
	 * @param password clave del repartidor
	 * @param email email del repartidor
	 * @param dateOfBirth fecha de nacimiento del repartidor
	 * @return el cliente creado
	 */
	@Override
	@Transactional
	public DeliveryMan createDeliveryMan(String name, String username, String password, String email, Date dateOfBirth) throws DeliveryException{
		DeliveryMan newDeliveryMan = new DeliveryMan(name, username, password, email, dateOfBirth);
		delivery_repo.saveClass(newDeliveryMan);
		return newDeliveryMan;
	}

	/**
	 * Obtiene el usuario (de cualqueir tipo) por id
	 * @param id
	 * @return el usuario con el id provisto
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<User> getUserById(Long id){
		return delivery_repo.getOptionalById("id_user", id, User.class);
	}

	/**
	 * Obtiene el usuario (de cualquier tipo) por el email
	 * @param email
	 * @return el usuario con el email provisto
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<User> getUserByEmail(String email){
		return delivery_repo.getOptionalByProperty("email", email, User.class);
	}

	/**
	 * Obtiene un repartidor libre de manera aleatoria
	 * @return el repartidor obtenido
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<DeliveryMan> getAFreeDeliveryMan(){
		Boolean free = true;
		return delivery_repo.getOptionalByProperty("free", free, DeliveryMan.class);
	}

	/**
	 * Actualiza los datos de un repartido
	 * @param newDeliveryMan el repartidor a actualizar
	 * @return el repartidor actualizo
	 */
	@Override
	@Transactional
	public DeliveryMan updateDeliveryMan(DeliveryMan newDeliveryMan) throws DeliveryException {
		delivery_repo.updateClass(newDeliveryMan);
		return delivery_repo.getOptionalById("id_user", newDeliveryMan.getId(), DeliveryMan.class).orElse(null);
	}

	/**
	 * Crea y retorna una direccion de entrega de un cliente especifico
	 * @param name titulo de la direccion
	 * @param address direccion indicada
	 * @param apartment numero de departamento
	 * @param coordX latitud de la dirección
	 * @param coordY longuitud de la dirección
	 * @param description detalle que acompaña la direccion
	 * @param client cliente dueño de la dirección
	 * @return la nueva dirección de entrega
	 */
	@Override
	@Transactional
	public Address createAddress(String name, String address, String apartment, float coordX, float coordY, String description, Client client) throws DeliveryException{
		Address newAddress = new Address(name, address, apartment, coordX, coordY, description, client);
		delivery_repo.saveClass(newAddress);
		client.setNewAddress(newAddress);
		return newAddress;
	}

	/**
	 * Crea y retorna una direccion de entrega de un cliente especifico (sin numero de departamento)
	 * @param name titulo de la direccion
	 * @param address direccion indicada
	 * @param coordX latitud de la dirección
	 * @param coordY longuitud de la dirección
	 * @param description detalle que acompaña la direccion
	 * @param client cliente dueño de la dirección
	 * @return la nueva dirección de entrega**/
	@Override
	@Transactional
	public Address createAddress(String name, String address, float coordX, float coordY, String description, Client client) throws DeliveryException {
		Address newAddress = new Address(name, address, coordX, coordY, description, client);
		delivery_repo.saveClass(newAddress);
		client.setNewAddress(newAddress);
		return newAddress;
	}

	/**
	 * Crea y retorna un nuevo Proveedor
	 * @param name nombre del Proveedor
	 * @param cuil cuil del Proveedor
	 * @param address dirección del Proveedor
	 * @param coordX  coordenada X de la dirección del Proveedor
	 * @param coordY coordeada Y de la dirección del Proveedor
	 * @return el proveedor creado
	 **/
	@Override
	@Transactional
	public Supplier createSupplier(String name, String cuil, String address, float coordX, float coordY) throws DeliveryException {
		Supplier newSupplier = new Supplier(name, cuil, address, coordX, coordY);
		delivery_repo.saveClass(newSupplier);
		return newSupplier;
	}

	/**
	 * Obtener y retornar los Suppliers con un nombre
	 * @param name nombre a buscar
	 * @return listado de Suppliers
	 **/
	@Override
	@Transactional(readOnly = true)
	public List<Supplier> getSupplierByName(String name) {
		return delivery_repo.getClassListByProperty("name", name, Supplier.class);
	}

	/**
	 * Crea y retorna un nuevo pedido
	 * @param number numero de orden
	 * @param dateOfOrder timestamp de la fecha en que fue realizado el pedido
	 * @param comments comentarios del cliente sobre la orden
	 * @param client cliente que realizó el pedido
	 * @param address dirección en la cual se debe entregar el pedido
	 * @return el nuevo pedido
	 */
	@Override
	@Transactional
	public Order createOrder(int number, Date dateOfOrder, String comments, Client client,  Address address) throws DeliveryException {
		Order newOrder = new Order(number, dateOfOrder, comments, client, address);
		delivery_repo.saveClass(newOrder);
		client.setNewOrder(newOrder);
		return newOrder;
	}

	/**
	 * Obtiene el pedido por id
	 * @param id
	 * @return el pedido con el id provisto
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Order> getOrderById(Long id) {
		return delivery_repo.getOptionalById("id_order", id, Order.class);
	}

	/**
	 * Crea y retorna un nuevo tipo de producto
	 * @param name nombre del tipo de producto
	 * @param description descripcion del tipo de producto
	 * @return el nuevo tipo de producto
	 **/
	@Override
	@Transactional
	public ProductType createProductType(String name, String description) throws DeliveryException{
		ProductType newProductType = new ProductType(name, description);
		delivery_repo.saveClass(newProductType);
		return newProductType;
	}

	/**
	 *  Crea y devuelve un nuevo Producto.
	 * @param name nombre del producto a ser creado
	 * @param price precio actual del producto
	 * @param weight peso actual del producto
	 * @param description descripción del producto
	 * @param supplier el productor del producto
	 * @param types listado de los tipos del producto
	 * @return el producto creado
	 **/
	@Override
	@Transactional
	public Product createProduct(String name, float price, float weight, String description, Supplier supplier, List<ProductType> types) throws DeliveryException {
		Calendar calendar = Calendar.getInstance();
		Product newProduct = new Product(name, price, calendar.getTime(), weight, description, supplier, types);
		for (ProductType type : types) {
			type.addProducts(newProduct);
		}
		delivery_repo.saveClass(newProduct);
		return newProduct;
	}

	/**
	 *  Crea y devuelve un nuevo Producto.
	 * @param name nombre del producto a ser creado
	 * @param price precio actual del producto
	 * @param lastPriceUpdateDate ultima fecha donde se actualizó el precio del producto
	 * @param weight peso actual del producto
	 * @param description descripción del producto
	 * @param supplier el productor del producto
	 * @param types listado de los tipos del producto
	 * @return el producto creado
	 **/
	@Override
	@Transactional
	public Product createProduct(String name, float price, Date lastPriceUpdateDate, float weight, String description, Supplier supplier, List<ProductType> types) throws DeliveryException{
		Product newProduct = new Product(name, price, lastPriceUpdateDate, weight, description, supplier, types);
		for (ProductType type : types) {
			type.addProducts(newProduct);
		}
		delivery_repo.saveClass(newProduct);
		return newProduct;
	}

	/**
	 * Obtiene el producto por id
	 * @param id
	 * @return el producto con el id provisto
	 **/
	@Override
	@Transactional(readOnly = true)
	public Optional<Product> getProductById(Long id) {
		return delivery_repo.getOptionalById("id_product", id, Product.class);
	}

	/**
	 * Obtiene el listado de productos que su nombre contega el string dado
	 * @param name string a buscar
	 * @return Lista de productos
	 **/
	@Override
	@Transactional(readOnly = true)
	public List<Product> getProductByName(String name) {
		return delivery_repo.getClassListByProperty("name", name, Product.class);
	}

	/**
	 * Obtiene el listado de productos que el nombre de alguno de sus tipo coincide con el string dado
	 * @param type nombre del tipo
	 * @return Lista de productos
	 **/
	@Override
	@Transactional(readOnly = true)
	public List<Product> getProductsByType(String type) throws DeliveryException {
		List<Product> typeList = delivery_repo.getProductsByType(type);
		if (typeList.size() != 0) {
			return typeList;
		} else {
			throw new DeliveryException("No existe el tipo de producto");
		}

	}

	/**
	 * Actualiza el precio del producto guardando la fecha de la actualización.
	 * @param id id del producto
	 * @param price nuevo precio del producto
	 * @return el producto modificado
	 * @throws DeliveryException en caso de que no exista el producto para el id dado
	 *
	 */
	@Override
	@Transactional
	public Product updateProductPrice(Long id, float price) throws DeliveryException{
		Product aProduct = delivery_repo.getClassByProperty("id_product", id, Product.class);
		if (aProduct != null){
			aProduct.setPrice(price);
			aProduct.setLastPriceUpdateDate(new Date());
			delivery_repo.updateClass(aProduct);

			return aProduct;
		} else {
			throw new DeliveryException("No existe el producto a actualizar");
		}
	}

	/**
	 * Asigna un repartidor a una orden
	 * Se debe verificar si el repartidor esta libre y si la orden no fue entregada
	 * @param order id de orden a ser asignada
	 * @param deliveryMan repartidor a asignar
	 * @return retorna si se pudo hacer la asignación
	 * @throws DeliveryException en caso de no existir el numero de orden
	 *
	 */
	@Override
	@Transactional
	public boolean addDeliveryManToOrder(Long order, DeliveryMan deliveryMan) throws DeliveryException{
		Order anOrder = delivery_repo.getClassByProperty("id_order", order, Order.class);
		if (anOrder != null){
			if (deliveryMan.isFree() && !anOrder.isDelivered() && !anOrder.getItems().isEmpty()) {
				anOrder.setDeliveryMan(deliveryMan);
				delivery_repo.updateClass(anOrder);

				deliveryMan.setFree(false);
				delivery_repo.updateClass(deliveryMan);

				return true;
			}
			return false;
		} else {
			throw new DeliveryException("No existe la orden");
		}
	}

	/**
	 * Registra que la orden fue entregada y libera al repartidor
	 * @param order id de la orden a actualizar
	 * @return retorno si se pudo actualizar la orden
	 * @throws DeliveryException en caso de no existir el numero de orden
	 */
	@Override
	@Transactional
	public boolean setOrderAsDelivered(Long order) throws DeliveryException {
		Order anOrder = delivery_repo.getClassByProperty("id_order", order, Order.class);
		if (anOrder != null){
			if(anOrder.getDeliveryMan() != null){
				anOrder.setDelivered(true);
				delivery_repo.updateClass(anOrder);

				DeliveryMan aDeliveryMan = anOrder.getDeliveryMan();
				aDeliveryMan.incrementNumberOfSuccessOrders();
				aDeliveryMan.incrementScore();
				aDeliveryMan.setFree(true);
				delivery_repo.updateClass(aDeliveryMan);

				Client aClient = anOrder.getClient();
				aClient.incrementScore();
				delivery_repo.updateClass(aClient);

				return true;
			}
			return false;
		} else {
			throw new DeliveryException("No existe la orden");
		}
	}

	/**
	 * Agrega una reseña a una orden
	 * @param order id de orden sobre la que hace la reseña
	 * @param commentary comentario de la reseña
	 * @return la nueva reseña
	 * @throws DeliveryException en caso de no existir el numero de orden
	 **/
	@Override
	@Transactional
	public Qualification addQualificatioToOrder(Long order, String commentary) throws DeliveryException {
		Order anOrder = delivery_repo.getClassByProperty("id_order", order, Order.class);
		if (anOrder != null){
			Qualification newQualification = new Qualification(commentary, anOrder);

			anOrder.setQualification(newQualification);

			delivery_repo.saveClass(newQualification);
			delivery_repo.updateClass(anOrder);

			return newQualification;
		} else {
			throw new DeliveryException("No existe la orden");
		}

	}

	/**
	 * agrega un item al pedido, es decir, una cantidad de un producto
	 * @param order pedido al cual se le agrega el producto
	 * @param quantity cantidad de producto a agregar
	 * @param product producto a agregar
	 * @return el pedido con el nuevo producto
	 * @throws DeliveryException en caso de no existir el pedido
	 */
	@Override
	@Transactional
	public Item addItemToOrder( Long order, Product product,  int quantity, String description ) throws DeliveryException{
		Order anOrder = delivery_repo.getClassByProperty("id_order", order, Order.class);
		if (anOrder != null){
			Item newItem = new Item(quantity, description, anOrder, product);
			delivery_repo.saveClass(newItem);

			anOrder.setTotalPrice(anOrder.getTotalPrice() + (product.getPrice() * quantity));
			delivery_repo.updateClass(anOrder);

			return newItem;
		} else {
			throw new DeliveryException("No existe la orden");
		}
	}

	//--------------------------------------------------------TP2----------------------------------------------------------------
	@Override
	@Transactional
	public User updateUser(User newUser) throws DeliveryException {
		delivery_repo.updateClass(newUser);
		return newUser;
	}

	@Override
	@Transactional
	public Qualification updateQualification(Qualification newQualification) throws DeliveryException {
		delivery_repo.updateClass(newQualification);
		return newQualification;
	}

	// TP2

	/**
	 * Obtiene los N usuarios que poseen mayor puntaje
	 * @param n cantidad de usuarios
	 * @return una lista con los usuarios
	 */
	@Override
	@Transactional(readOnly = true)
	public List<User> getTopNUserWithMoreScore(int n) {
		return delivery_repo.getTopNUserWithMoreScore(n);
	}

	/**
	 * Obtiene los 10 usuarios de tipo Delivery Man que mas ordenes completaron
	 * @return el listado de Delivery Man
	 */
	@Override
	@Transactional(readOnly = true)
	public List<DeliveryMan> getTop10DeliveryManWithMoreOrders() {
		List<DeliveryMan> dm = delivery_repo.getTop10DeliveryManWithMoreOrders();
		return dm;
	}

	/**
	 * Obtiene una lista de usuarios de tipo Cliente que hicieron al menos una orden con un monto total igual o superior a un valor
	 * @param number monto de las ordenes
	 * @return el listado de clientes
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Client> getUsersSpentMoreThan(float number) {
		List<Client> clients = delivery_repo.getUsersSpentMoreThan(number);
		return clients;
	}

	/**
	 * Obtiene el listado de ordenes realizadas por un cliente
	 * @param username nombre de usuario del cliente
	 * @return la lista de ordenes
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Order> getAllOrdersFromUser(String username) {
		List<Order> orders = delivery_repo.getAllOrdersFromUser(username);
		return orders;
	}

	/**
	 * Obtiene el numero de ordenes que todavia no fueron completadas, es decir que no fueron entregadas por un Delivery Man
	 * @return el numero de ordenes
	 */
	@Override
	@Transactional(readOnly = true)
	public Long getNumberOfOrderNoDelivered() {
		return delivery_repo.getNumberOfOrderNoDelivered();
	}

	/**
	 * Obtiene el numero de ordenes completadas, es decir entregadas, en un rango de fechas dadas
	 * @param startDate fecha de incio del rango
	 * @param endDate fecha final del rango
	 * @return el numero de ordenes
	 */
	
	@Override
	@Transactional(readOnly = true)
	public Long getNumberOfOrderDeliveredAndBetweenDates(Date startDate, Date endDate) {
		return delivery_repo.getNumberOfOrderDeliveredAndBetweenDates(startDate, endDate); 
	};

	/**
	 * Obtiene la orden completada, es decir entregada por el repartidor, de mayor valor total en un dia dado
	 * @param date el dia
	 * @return la orden obtenida
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Order> getOrderDeliveredMoreExpansiveInDate(Date date){
		return delivery_repo.getOrderDeliveredMoreExpansiveInDate(date);
	};

	/**
	 * Obtiene la lista de suppliers que no tienen productos agregados a su catalogo
	 * @return la lista de suppliers
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Supplier> getSuppliersWithoutProducts(){
		return delivery_repo.getSuppliersWithoutProducts();
	};

	/**
	 * Obtiene los productos que no han actualizado su precio en los ultimos N dias
	 * @param days cantidad de dias
	 * @return el listado de productos
	 */
	
	@Override
	@Transactional(readOnly = true)
	public List<Product> getProductsWithPriceDateOlderThan(int days){
		return delivery_repo.getProductsWithPriceDateOlderThan(days);
	};

	/**
	 * Obtiene los 5 productos de mayor valor
	 * @return el listado de productos
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Product> getTop5MoreExpansiveProducts(){
		return delivery_repo.getTop5MoreExpansiveProducts();
	}

	/**
	 * Obtiene el producto más demandado, es decir, aquel que esta se incluyo más veces en ordenes (tener en cuenta la cantidad)
	 * @return el producto obtenido
	 */
	@Override
	@Transactional(readOnly = true)
	public Product getMostDemandedProduct() {
		return delivery_repo.getMostDemandedProduct();
	}

	/**
	 * Obtiene aquellos productos existentes que no fueron incluidos en ninguna orden
	 * @return el listado de productos
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Product> getProductsNoAddedToOrders(){
		return delivery_repo.getProductsNoAddedToOrders();
	}

	/**
	 * Obtiene los 3 tipos de productos que menos productos tienen asociados
	 * @return el listado de tipos de producto
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ProductType> getTop3ProductTypesWithLessProducts(){
		return delivery_repo.getTop3ProductTypesWithLessProducts();
	}

	/**
	 * Obtiene el supplier que más productos tiene asociado
	 * @return el supplier resultante
	 */
	@Override
	@Transactional(readOnly = true)
	public Supplier getSupplierWithMoreProducts(){
		return delivery_repo.getSupplierWithMoreProducts();
	}

	/**
	 * Obtiene aquellos suppliers que tienen al menos una calificación de una estrella entre sus ordenes completadas
	 * @return el listado de suppliers
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Supplier> getSupplierWith1StarCalifications(){
		return delivery_repo.getSupplierWith1StarCalifications();
	}


}