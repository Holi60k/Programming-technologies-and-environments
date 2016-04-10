package hu.hnk.beershop.dao.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.openejb.junit.jee.transaction.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import hu.hnk.beershop.model.Rank;
import hu.hnk.beershop.model.Role;
import hu.hnk.beershop.model.User;
import hu.hnk.interfaces.UserDao;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDaoTest {

	private EntityManager em;
	private EntityTransaction tx;
	private static EJBContainer container;

	@BeforeClass
	public static void initEM() throws NamingException {
		// this.em =
		// Persistence.createEntityManagerFactory("BeerShopUnitTest").createEntityManager();
		// this.tx = this.em.getTransaction();
	}

	@Before
	public void setUp() throws NamingException {
		container = EJBContainer.createEJBContainer();
		container.getContext().bind("inject", this);
	}

	@EJB
	private UserDao userDao;

	@Test
	@Transaction(rollback = true)
	public void testFindByName() {

		User user = new User();
		user.setUsername("NameTest");
		user.setEmail("email@email.me");
		user.setPassword("ASD");
		user.setPoints((double) 150);
//		user.setRank(Rank.Amatuer);
		user.setDateOfBirth(new Date());
		userDao.save(user);

		User namedUser = userDao.findByUsername("NameTest");

		Assert.assertEquals("NameTest", namedUser.getUsername());
		userDao.remove(namedUser);

	}

	@Test
	@Transaction(rollback = true)
	public void testFindByEmail() {

		User user = new User();
		user.setUsername("EmailTest");
		user.setEmail("email@test.me");
		user.setPassword("ASD");
		user.setPoints((double) 150);
//		user.setRank(Rank.Amatuer);
		user.setDateOfBirth(new Date());
		userDao.save(user);

		User emailedUser = userDao.findByEmail("email@test.me");

		Assert.assertEquals("email@test.me", emailedUser.getEmail());
		userDao.remove(emailedUser);

	}

	@Test
	public void testFindByRole() {
		User user = new User();
		user.setUsername("RoleMe");
		user.setEmail("role@me.com");
		user.setPassword("ASD");
		user.setPoints((double) 150);
		user.setDateOfBirth(new Date());
		List<Role> roles = new ArrayList<>();
		Role userRole = new Role();
		userRole.setName("ROLE_USER");
		roles.add(userRole);
		user.setRoles(roles);
		userDao.save(user);
		
		User roledUser = userDao.findByUsername("RoleMe");
		System.out.println(roledUser);
		Assert.assertEquals("ROLE_USER", roledUser.getRoles().get(0).getName());
		userDao.remove(roledUser);
	}

	@After
	public void destroyContainer() {
		container.close();
	}

}