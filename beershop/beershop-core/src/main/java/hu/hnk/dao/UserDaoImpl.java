package hu.hnk.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import hu.hnk.beershop.exception.EmailNotFound;
import hu.hnk.beershop.exception.UsernameNotFound;
import hu.hnk.beershop.model.User;
import hu.hnk.interfaces.UserDao;

/**
 * A felhasználókat kezelő adathozzáférési osztály implementációja. Enterprise
 * Java Bean
 * 
 * @author Nandi
 *
 */
@Stateless
@Local(UserDao.class)
public class UserDaoImpl implements UserDao {

	/**
	 * Az osztály Logger-e.
	 */
	public static final Logger logger = Logger.getLogger(UserDaoImpl.class);

	/**
	 * Az osztály entitás menedzsere.
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Új felhasználó mentése.
	 * 
	 * @param user
	 *            az új felhasználó
	 * @return a mentett felhasználó
	 */
	public User save(User user) {
		logger.info("Felhasználó mentése.");
		return em.merge(user);
	}

	/**
	 * Felhasználó keresése felhasználónév alapján.
	 * 
	 * @param username
	 *            a keresendő felhasználó felhasználóneve.
	 * @return a megtalált felhasználó
	 * @throws UsernameNotFound
	 *             ha nem létezik ilyen felhasználónévvel felhasználó.
	 */
	@Override
	public User findByUsername(String username) throws UsernameNotFound {
		TypedQuery<User> query = em.createNamedQuery("User.findByUsername", User.class);
		query.setParameter("name", username);
		User user;
		try {
			user = query.getSingleResult();
		} catch (Exception e) {
			throw new UsernameNotFound("There is no user with this username.");
		}
		return user;
	}

	/**
	 * Felhasználó keresése e-mail cím alapján.
	 * 
	 * @param email
	 *            a keresendő felhasználó e-mail címe.
	 * @return a megtalált felhasználó
	 * @throws EmailNotFound
	 *             ha nem létezik ilyen e-mail címmel felhasználó.
	 */
	@Override
	public User findByEmail(String email) throws EmailNotFound {
		TypedQuery<User> query = em.createNamedQuery("User.findByEmail", User.class);
		query.setParameter("email", email);
		User user;
		try {
			user = query.getSingleResult();
		} catch (Exception e) {
			throw new EmailNotFound("There is no user with this e-mail.");
		}
		return user;
	}

	/**
	 * Felhasználó törlése.
	 */
	@Override
	public void remove(User user) {
		em.remove(em.contains(user) ? user : em.merge(user));
	}

//	@Override
//	public User findByRole(List<Role> roleName) {
//		CriteriaBuilder cb = em.getCriteriaBuilder();
//		CriteriaQuery cq = cb.createQuery(User.class);
//		Root<User> user = cq.from(User.class);
//		cq.select(user).where(user.get("roles")).from(User.class).in(roleName);
//		return (User) em.createQuery(cq).getResultList().get(0);
//	}

	/**
	 * Felhasználó keresése felhasználónév alapján.
	 * 
	 * @param username
	 *            a keresendő felhasználónév.
	 * @return a kapott felhasználónév.
	 * @throws UsernameNotFound
	 *             ha a keresett felhasználónévvel nem létezik felhasználó.
	 */
	@Override
	public String findUsername(String username) throws UsernameNotFound {
		TypedQuery<String> query = em.createNamedQuery("User.findUsername", String.class);
		query.setParameter("name", username);
		String user;
		try {
			 user = query.getSingleResult();
			 return user;
		} catch (Exception e) {
			throw new UsernameNotFound("There is no user with this username.");
		}
	}

	/**
	 * Felhasználó keresése e-mail cím alapján.
	 * 
	 * @param email
	 *            a keresendő e-mail cím.
	 * @return a kapott e-mail cím.
	 * @throws EmailNotFound
	 *             ha a keresett e-mail címmel nem létezik felhasználó.
	 */
	@Override
	public String findEmail(String email) throws EmailNotFound {
		TypedQuery<String> query = em.createNamedQuery("User.findEmail", String.class);
		query.setParameter("email", email);
		String user;
		try {
			 user = query.getSingleResult();
			 return user;
		} catch (Exception e) {
			throw new EmailNotFound("There is no user with this email.");
		}
	}

}
