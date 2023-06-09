package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.DeliveryException;
import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Client createClient(String name, String username, String password, String email, Date dateOfBirth) throws DeliveryException {
        Client newClient = new Client(name, username, password, email, dateOfBirth);
        try {
            return this.clientRepository.save(newClient);
        } catch (IllegalArgumentException e) {
            throw new DeliveryException("");
        } catch (OptimisticLockingFailureException e) {
            throw new DeliveryException("");
        }
    }

    @Override
    public DeliveryMan createDeliveryMan(String name, String username, String password, String email, Date dateOfBirth) throws DeliveryException {
        DeliveryMan newDeliveryMan = new DeliveryMan(name, username, password, email, dateOfBirth);
        try{
            return this.deliveryManRepository.save(newDeliveryMan);
        } catch (IllegalArgumentException e) {
            throw new DeliveryException("");
        } catch (OptimisticLockingFailureException e) {
            throw new DeliveryException("");
        }
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return this.userRepository.getUserByEmail(email);
    }

    @Override
    public Optional<DeliveryMan> getAFreeDeliveryMan() {
        return this.deliveryManRepository.getAFreeDeliveryMan(true);
    }

    @Override
    public DeliveryMan updateDeliveryMan(DeliveryMan deliveryMan1) throws DeliveryException {
        return null;
    }

    @Override
    public Address createAddress(String name, String address, String apartment, float coordX, float coordY, String description, Client client) throws DeliveryException {
        Address newAddress = new Address(name, address, apartment, coordX, coordY, description, client);
        try{
            Address newSavedAddress = this.addressRepository.save(newAddress);
            client.setNewAddress(newSavedAddress);
            return newSavedAddress;
        } catch (IllegalArgumentException e) {
            throw new DeliveryException("");
        } catch (OptimisticLockingFailureException e) {
            throw new DeliveryException("");
        }
    }

    @Override
    public Address createAddress(String name, String address, float coordX, float coordY, String description, Client client) throws DeliveryException {
        Address newAddress = new Address(name, address, coordX, coordY, description, client);
        try{
            Address newSavedAddress = this.addressRepository.save(newAddress);
            client.setNewAddress(newSavedAddress);
            return newSavedAddress;
        } catch (IllegalArgumentException e) {
            throw new DeliveryException("");
        } catch (OptimisticLockingFailureException e) {
            throw new DeliveryException("");
        }
    }

    @Override
    public Order createOrder(int number, Date dateOfOrder, String comments, Client client, Address address) throws DeliveryException {
        Order newOrder = new Order(number, dateOfOrder, comments, client, address);
        try{
            Order newSavedOrder = this.orderRepository.save(newOrder);
            client.setNewOrder(newSavedOrder);
            return newSavedOrder;
        } catch (IllegalArgumentException e) {
            throw new DeliveryException("");
        } catch (OptimisticLockingFailureException e) {
            throw new DeliveryException("");
        }
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return Optional.empty();
    }

    @Override
    public Supplier createSupplier(String name, String cuil, String address, float coordX, float coordY) throws DeliveryException {
        return null;
    }

    @Override
    public List<Supplier> getSupplierByName(String name) {
        return null;
    }

    @Override
    public ProductType createProductType(String name, String description) throws DeliveryException {
        return null;
    }

    @Override
    public Product createProduct(String name, float price, float weight, String description, Supplier supplier, List<ProductType> types) throws DeliveryException {
        return null;
    }

    @Override
    public Product createProduct(String name, float price, Date lastPriceUpdateDate, float weight, String description, Supplier supplier, List<ProductType> types) throws DeliveryException {
        return null;
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Product> getProductByName(String name) {
        return null;
    }

    @Override
    public List<Product> getProductsByType(String type) throws DeliveryException {
        return null;
    }

    @Override
    public Product updateProductPrice(Long id, float price) throws DeliveryException {
        return null;
    }

    @Override
    public boolean addDeliveryManToOrder(Long order, DeliveryMan deliveryMan) throws DeliveryException {
        return false;
    }

    @Override
    public boolean setOrderAsDelivered(Long order) throws DeliveryException {
        return false;
    }

    @Override
    public Qualification addQualificatioToOrder(Long order, String commentary) throws DeliveryException {
        return null;
    }

    @Override
    public Item addItemToOrder(Long order, Product product, int quantity, String description) throws DeliveryException {
        return null;
    }

    @Override
    public User updateUser(User user) throws DeliveryException {
        return null;
    }

    @Override
    public Qualification updateQualification(Qualification qualification) throws DeliveryException {
        return null;
    }

    @Override
    public List<User> getTopNUserWithMoreScore(int n) {
        return null;
    }

    @Override
    public List<DeliveryMan> getTop10DeliveryManWithMoreOrders() {
        return null;
    }

    @Override
    public List<Client> getUsersSpentMoreThan(float number) {
        return null;
    }

    @Override
    public List<Order> getAllOrdersFromUser(String username) {
        return null;
    }

    @Override
    public Long getNumberOfOrderNoDelivered() {
        return null;
    }

    @Override
    public Long getNumberOfOrderDeliveredAndBetweenDates(Date startDate, Date endDate) {
        return null;
    }

    @Override
    public Optional<Order> getOrderDeliveredMoreExpansiveInDate(Date date) {
        return Optional.empty();
    }

    @Override
    public List<Supplier> getSuppliersWithoutProducts() {
        return null;
    }

    @Override
    public List<Product> getProductsWithPriceDateOlderThan(int days) {
        return null;
    }

    @Override
    public List<Product> getTop5MoreExpansiveProducts() {
        return null;
    }

    @Override
    public Product getMostDemandedProduct() {
        return null;
    }

    @Override
    public List<Product> getProductsNoAddedToOrders() {
        return null;
    }

    @Override
    public List<ProductType> getTop3ProductTypesWithLessProducts() {
        return null;
    }

    @Override
    public Supplier getSupplierWithMoreProducts() {
        return null;
    }

    @Override
    public List<Supplier> getSupplierWith1StarCalifications() {
        return null;
    }
}
