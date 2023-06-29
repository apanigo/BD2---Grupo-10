package ar.edu.unlp.info.bd2.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlp.info.bd2.DeliveryException;
import ar.edu.unlp.info.bd2.model.Address;
import ar.edu.unlp.info.bd2.model.Client;
import ar.edu.unlp.info.bd2.model.DeliveryMan;
import ar.edu.unlp.info.bd2.model.Item;
import ar.edu.unlp.info.bd2.model.Order;
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.ProductType;
import ar.edu.unlp.info.bd2.model.Qualification;
import ar.edu.unlp.info.bd2.model.Supplier;
import ar.edu.unlp.info.bd2.model.User;
import ar.edu.unlp.info.bd2.repositories.AddressRepository;
import ar.edu.unlp.info.bd2.repositories.ClientRepository;
import ar.edu.unlp.info.bd2.repositories.DeliveryManRepository;
import ar.edu.unlp.info.bd2.repositories.ItemRepository;
import ar.edu.unlp.info.bd2.repositories.OrderRepository;
import ar.edu.unlp.info.bd2.repositories.ProductRepository;
import ar.edu.unlp.info.bd2.repositories.ProductTypeRepository;
import ar.edu.unlp.info.bd2.repositories.QualificationRepository;
import ar.edu.unlp.info.bd2.repositories.SupplierRepository;
import ar.edu.unlp.info.bd2.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class SpringDataDeliveryServiceImpl implements DeliveryService, DeliveryStatisticsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private DeliveryManRepository deliveryManRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductTypeRepository productTypeRepository;
    @Autowired
    private QualificationRepository qualificationRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    //---------------------------------------------------TP1--------------------------------------------------------------

    @Override
    @Transactional
    public Client createClient(String name, String username, String password, String email, Date dateOfBirth) throws DeliveryException {
        Client newClient = new Client(name, username, password, email, dateOfBirth);
        try {
            return this.clientRepository.save(newClient);
        } catch (ConstraintViolationException | DataIntegrityViolationException e) {
            throw new DeliveryException("Constraint Violation");
        } catch (OptimisticLockingFailureException e) {
            throw new DeliveryException("Optimistic Locking Failure");
        } catch (IllegalArgumentException e) {
            throw new DeliveryException("Illegal Argument");
        } catch (Exception e) {
            throw new DeliveryException("Hubo un error");
        }
    }

    @Override
    @Transactional
    public DeliveryMan createDeliveryMan(String name, String username, String password, String email, Date dateOfBirth) throws DeliveryException {
        DeliveryMan newDeliveryMan = new DeliveryMan(name, username, password, email, dateOfBirth);
        try{
            return this.deliveryManRepository.save(newDeliveryMan);
        } catch (ConstraintViolationException | DataIntegrityViolationException e) {
            throw new DeliveryException("Constraint Violation");
        } catch (OptimisticLockingFailureException e) {
            throw new DeliveryException("Optimistic Locking Failure");
        } catch (IllegalArgumentException e) {
            throw new DeliveryException("Illegal Argument");
        } catch (Exception e) {
            throw new DeliveryException("Hubo un error");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DeliveryMan> getAFreeDeliveryMan() {
        return this.deliveryManRepository.findByFree(true);
    }

    @Override
    @Transactional
    public DeliveryMan updateDeliveryMan(DeliveryMan deliveryMan1) throws DeliveryException {
        try {
            return this.deliveryManRepository.save(deliveryMan1);
        } catch (OptimisticLockingFailureException e) {
            throw new DeliveryException("Optimistic Locking Failure");
        } catch (IllegalArgumentException e) {
            throw new DeliveryException("Illegal Argument");
        } catch (Exception e) {
            throw new DeliveryException("Hubo un error");
        }
    }

    @Override
    @Transactional
    public Address createAddress(String name, String address, String apartment, float coordX, float coordY, String description, Client client) throws DeliveryException {
        Address newAddress = new Address(name, address, apartment, coordX, coordY, description, client);
        try{
            Address newSavedAddress = this.addressRepository.save(newAddress);
            client.setNewAddress(newSavedAddress);
            return newSavedAddress;
        } catch (ConstraintViolationException | DataIntegrityViolationException e) {
            throw new DeliveryException("Constraint Violation");
        } catch (OptimisticLockingFailureException e) {
            throw new DeliveryException("Optimistic Locking Failure");
        } catch (IllegalArgumentException e) {
            throw new DeliveryException("Illegal Argument");
        } catch (Exception e) {
            throw new DeliveryException("Hubo un error");
        }
    }

    @Override
    @Transactional
    public Address createAddress(String name, String address, float coordX, float coordY, String description, Client client) throws DeliveryException {
        Address newAddress = new Address(name, address, coordX, coordY, description, client);
        try{
            Address newSavedAddress = this.addressRepository.save(newAddress);
            client.setNewAddress(newSavedAddress);
            return newSavedAddress;
        } catch (ConstraintViolationException | DataIntegrityViolationException e) {
            throw new DeliveryException("Constraint Violation");
        } catch (OptimisticLockingFailureException e) {
            throw new DeliveryException("Optimistic Locking Failure");
        } catch (IllegalArgumentException e) {
            throw new DeliveryException("Illegal Argument");
        } catch (Exception e) {
            throw new DeliveryException("Hubo un error");
        }
    }

    @Override
    @Transactional
    public Order createOrder(int number, Date dateOfOrder, String comments, Client client, Address address) throws DeliveryException {
        Order newOrder = new Order(number, dateOfOrder, comments, client, address);
        try{
            Order newSavedOrder = this.orderRepository.save(newOrder);
            client.setNewOrder(newSavedOrder);
            return newSavedOrder;
        } catch (ConstraintViolationException | DataIntegrityViolationException e) {
            throw new DeliveryException("Constraint Violation");
        } catch (OptimisticLockingFailureException e) {
            throw new DeliveryException("Optimistic Locking Failure");
        } catch (IllegalArgumentException e) {
            throw new DeliveryException("Illegal Argument");
        } catch (Exception e) {
            throw new DeliveryException("Hubo un error");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> getOrderById(Long id) {
        return this.orderRepository.findById(id);
    }

    @Override
    @Transactional
    public Supplier createSupplier(String name, String cuil, String address, float coordX, float coordY) throws DeliveryException {
        Supplier newSupplier = new Supplier(name, cuil, address, coordX, coordY);
        try{
            return this.supplierRepository.save(newSupplier);
        } catch (ConstraintViolationException | DataIntegrityViolationException e) {
            throw new DeliveryException("Constraint Violation");
        } catch (OptimisticLockingFailureException e) {
            throw new DeliveryException("Optimistic Locking Failure");
        } catch (IllegalArgumentException e) {
            throw new DeliveryException("Illegal Argument");
        } catch (Exception e) {
            throw new DeliveryException("Hubo un error");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Supplier> getSupplierByName(String name) {
        return this.supplierRepository.findByNameContaining(name);
    }

    @Override
    @Transactional
    public ProductType createProductType(String name, String description) throws DeliveryException {
        ProductType newProductType = new ProductType(name, description);
        try{
            return this.productTypeRepository.save(newProductType);
        } catch (ConstraintViolationException | DataIntegrityViolationException e) {
            throw new DeliveryException("Constraint Violation");
        } catch (OptimisticLockingFailureException e) {
            throw new DeliveryException("Optimistic Locking Failure");
        } catch (IllegalArgumentException e) {
            throw new DeliveryException("Illegal Argument");
        } catch (Exception e) {
            throw new DeliveryException("Hubo un error");
        }
    }

    @Override
    @Transactional
    public Product createProduct(String name, float price, float weight, String description, Supplier supplier, List<ProductType> types) throws DeliveryException {
        Calendar calendar = Calendar.getInstance();
        Product newProduct = new Product(name, price, calendar.getTime(), weight, description, supplier, types);
        try {
            Product newSavedProduct = this.productRepository.save(newProduct);
            for (ProductType type : types) {
                type.addProducts(newSavedProduct);
            }
            return newSavedProduct;
        } catch (ConstraintViolationException | DataIntegrityViolationException e) {
            throw new DeliveryException("Constraint Violation");
        } catch (OptimisticLockingFailureException e) {
            throw new DeliveryException("Optimistic Locking Failure");
        } catch (IllegalArgumentException e) {
            throw new DeliveryException("Illegal Argument");
        } catch (Exception e) {
            throw new DeliveryException("Hubo un error");
        }
    }

    @Override
    @Transactional
    public Product createProduct(String name, float price, Date lastPriceUpdateDate, float weight, String description, Supplier supplier, List<ProductType> types) throws DeliveryException {
        Product newProduct = new Product(name, price, lastPriceUpdateDate, weight, description, supplier, types);
        try {
            Product newSavedProduct = this.productRepository.save(newProduct);
            for (ProductType type : types) {
                type.addProducts(newSavedProduct);
            }
            return newSavedProduct;
        } catch (ConstraintViolationException | DataIntegrityViolationException e) {
            throw new DeliveryException("Constraint Violation");
        } catch (OptimisticLockingFailureException e) {
            throw new DeliveryException("Optimistic Locking Failure");
        } catch (IllegalArgumentException e) {
            throw new DeliveryException("Illegal Argument");
        } catch (Exception e) {
            throw new DeliveryException("Hubo un error");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long id) {
        return this.productRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductByName(String name) {
        return this.productRepository.findByNameContaining(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByType(String type) throws DeliveryException {
        List<Product> typeList = this.productRepository.findByTypesName(type);
        if (typeList.size() != 0) {
            return typeList;
        } else {
            throw new DeliveryException("No existe el tipo de producto");
        }
    }

    @Override
    @Transactional
    public Product updateProductPrice(Long id, float price) throws DeliveryException {
        Optional<Product> optionalProduct = this.productRepository.findById(id);
        Product aProduct = optionalProduct.orElseThrow(() -> new DeliveryException("No existe el producto a actualizar"));

        aProduct.setPrice(price);
        aProduct.setLastPriceUpdateDate(new Date());

        return this.productRepository.save(aProduct);
    }

    @Override
    @Transactional
    public boolean addDeliveryManToOrder(Long order, DeliveryMan deliveryMan) throws DeliveryException {
        Optional<Order> optionalOrder = this.orderRepository.findById(order);
        Order anOrder = optionalOrder.orElseThrow(() -> new DeliveryException("No existe la orden"));
        if (deliveryMan.isFree() && !anOrder.isDelivered() && !anOrder.getItems().isEmpty()) {
            anOrder.setDeliveryMan(deliveryMan);
            this.orderRepository.save(anOrder);

            deliveryMan.setFree(false);
            this.deliveryManRepository.save(deliveryMan);

            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean setOrderAsDelivered(Long order) throws DeliveryException {
        Optional<Order> optionalOrder = this.orderRepository.findById(order);
        Order anOrder = optionalOrder.orElseThrow(() -> new DeliveryException("No existe la orden"));
        if(anOrder.getDeliveryMan() != null){
            anOrder.setDelivered(true);
            this.orderRepository.save(anOrder);

            DeliveryMan aDeliveryMan = anOrder.getDeliveryMan();
            aDeliveryMan.incrementNumberOfSuccessOrders();
            aDeliveryMan.incrementScore();
            aDeliveryMan.setFree(true);
            this.deliveryManRepository.save(aDeliveryMan);

            Client aClient = anOrder.getClient();
            aClient.incrementScore();
            this.clientRepository.save(aClient);

            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Qualification addQualificatioToOrder(Long order, String commentary) throws DeliveryException {
        Optional<Order> optionalOrder = this.orderRepository.findById(order);
        Order anOrder = optionalOrder.orElseThrow(() -> new DeliveryException("No existe la orden"));

        Qualification newQualification = new Qualification(commentary, anOrder);
        Qualification newSavedQualification = this.qualificationRepository.save(newQualification);

        anOrder.setQualification(newSavedQualification);
        this.orderRepository.save(anOrder);

        return newSavedQualification;
    }

    @Override
    @Transactional
    public Item addItemToOrder(Long order, Product product, int quantity, String description) throws DeliveryException {
        Optional<Order> optionalOrder = this.orderRepository.findById(order);
        Order anOrder = optionalOrder.orElseThrow(() -> new DeliveryException("No existe la orden"));
        Item newItem = new Item(quantity, description, anOrder, product);
        Item newSavedItem = this.itemRepository.save(newItem);

        anOrder.setTotalPrice(anOrder.getTotalPrice() + (product.getPrice() * quantity));
        this.orderRepository.save(anOrder);

        return newSavedItem;
    }

    @Override
    @Transactional
    public User updateUser(User user) throws DeliveryException {
        try {
            return this.userRepository.save(user);
        } catch (OptimisticLockingFailureException e) {
            throw new DeliveryException("Optimistic Locking Failure");
        } catch (IllegalArgumentException e) {
            throw new DeliveryException("Illegal Argument");
        } catch (Exception e) {
            throw new DeliveryException("Hubo un error");
        }
    }

    @Override
    @Transactional
    public Qualification updateQualification(Qualification qualification) throws DeliveryException {
        try {
            return this.qualificationRepository.save(qualification);
        } catch (OptimisticLockingFailureException e) {
            throw new DeliveryException("Optimistic Locking Failure");
        } catch (IllegalArgumentException e) {
            throw new DeliveryException("Illegal Argument");
        } catch (Exception e) {
            throw new DeliveryException("Hubo un error");
        }
    }

    //---------------------------------------------------TP2--------------------------------------------------------------

    @Override
    @Transactional(readOnly = true)
    public List<User> getTopNUserWithMoreScore(int n) {
    	PageRequest pageReq = PageRequest.of(0, n); 
    	return this.userRepository.findByOrderByScoreDesc(pageReq);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeliveryMan> getTop10DeliveryManWithMoreOrders() {
        PageRequest pageReq = PageRequest.of(0, 10);
        return this.deliveryManRepository.findByOrderByNumberOfSuccessOrdersDesc(pageReq);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> getUsersSpentMoreThan(float number) {
    	return this.clientRepository.findDistinctByOrdersTotalPriceGreaterThanEqual(number);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrdersFromUser(String username) {
        Optional<Client> optionalUser = this.clientRepository.findByUsername(username);
    	try {
    		Client client = optionalUser.get();
    		return client.getOrders();
    	}
    	catch (NoSuchElementException e) {
    		return null;
    	}
    }

    @Override
    @Transactional(readOnly = true)
    public Long getNumberOfOrderNoDelivered() {
        return this.orderRepository.countByDeliveredFalse();
    }

    @Override
    @Transactional(readOnly = true)
    public Long getNumberOfOrderDeliveredAndBetweenDates(Date startDate, Date endDate) {
        return this.orderRepository.countByDeliveredTrueAndDateOfOrderBetween(startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> getOrderDeliveredMoreExpansiveInDate(Date date) {
        return this.orderRepository.findFirstByDeliveredTrueAndDateOfOrderOrderByTotalPriceDesc(date);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Supplier> getSuppliersWithoutProducts() {
    	return this.supplierRepository.findAllByProductsEmpty();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsWithPriceDateOlderThan(int days) {
        Date queryDate = java.sql.Date.valueOf(LocalDate.now().minusDays(days));

        return this.productRepository.findByLastPriceUpdateDateLessThanEqual(queryDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getTop5MoreExpansiveProducts() {
        return this.productRepository.findTop5ByOrderByPriceDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public Product getMostDemandedProduct() {
    	Pageable page = PageRequest.of(0,1);
    	Page<Product> productPage = this.productRepository.findByMostDemanded(page);

        if (productPage != null && productPage.hasContent()) {
            return productPage.getContent().get(0);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsNoAddedToOrders() {
        return this.productRepository.getProductsNoAddedToOrders();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductType> getTop3ProductTypesWithLessProducts() {
        return this.productTypeRepository.getTop3ProductTypesWithLessProducts(PageRequest.of(0, 3));
    }

    @Override
    @Transactional(readOnly = true)
    public Supplier getSupplierWithMoreProducts() {
        Pageable page = PageRequest.of(0,1);
        Page<Supplier> supplierPage = this.supplierRepository.getSupplierWithMoreProducts(page);

        if (supplierPage != null && supplierPage.hasContent()) {
            return supplierPage.getContent().get(0);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Supplier> getSupplierWith1StarCalifications() {
        return this.supplierRepository.getSupplierWith1StarCalifications();
    }
}
