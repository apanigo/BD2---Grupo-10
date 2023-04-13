package ar.edu.unlp.info.bd2.services;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import ar.edu.unlp.info.bd2.DeliveryException;
import ar.edu.unlp.info.bd2.config.AppConfig;
import ar.edu.unlp.info.bd2.config.HibernateConfiguration;
import ar.edu.unlp.info.bd2.model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class, HibernateConfiguration.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
@Rollback(true)
public class DeliveryServiceTests {

	@Autowired
	DeliveryService service;

	private Date dob1;
	private Date dob2;
	private Date dpri;

	@BeforeEach
	public void setUp(){
		Calendar cal1 = Calendar.getInstance();
		cal1.set(1980, Calendar.APRIL, 5);
		this.dob1 = cal1.getTime();
		cal1.set(1992, Calendar.SEPTEMBER, 16);
		this.dob2 = cal1.getTime();
		cal1.set(2022, Calendar.SEPTEMBER, 21);
		this.dpri = cal1.getTime();
	}

	@Test
	void initialTest() {
		// Probar correcta conexión con la BD y creación de entidades
	}
	
	@Test
	void testCreationAndGetUsers() throws DeliveryException {
		/**
		 * Creacion de Usuario tipo Cliente
		 */
		Client client = this.service.createClient("Juan Perez", "jperez", "1234", "jperez@gmail.com", dob1);
		assertNotNull(client.getId());
		assertEquals(0, client.getScore());
		assertEquals("jperez@gmail.com", client.getEmail());

		/**
		 * Creción de Usuario tipo DeliveryMan
		 */
		DeliveryMan deliveryMan = this.service.createDeliveryMan("Ramiro Benítez", "rbenitez", "1234", "rbenitez@gmail.com", dob2);
		assertNotNull(deliveryMan.getId());
		assertEquals(0, deliveryMan.getScore());
		assertEquals("rbenitez", deliveryMan.getUsername());

		/**
		 * Obtención de Usuario por ID
		 */
		Long idClient = client.getId();
		Optional<User> optionalUser1 = this.service.getUserById(idClient);
		assertTrue(optionalUser1.isPresent());
		User user1 = optionalUser1.get();
		assertEquals(Client.class, user1.getClass());
		Client client1 = (Client) user1;
		assertEquals(idClient, client1.getId());
		assertEquals("jperez", client1.getUsername());
		assertEquals("jperez@gmail.com", client1.getEmail());

		/**
		 * Obtención de Usuario por Email
		 */
		Long idDeliveryMan = deliveryMan.getId();
		Optional<User> optionalUser2 = this.service.getUserById(idDeliveryMan);
		assertTrue(optionalUser2.isPresent());
		User user2 = optionalUser2.get();
		assertEquals(user2.getClass(), DeliveryMan.class);
		DeliveryMan deliveryMan1 = (DeliveryMan) user2;
		assertEquals(idDeliveryMan, deliveryMan1.getId());
		assertEquals("rbenitez", deliveryMan1.getUsername());
		assertEquals("rbenitez@gmail.com", deliveryMan1.getEmail());

		assertFalse(this.service.getUserByEmail("otromail@gmail.com").isPresent());
	}
}
