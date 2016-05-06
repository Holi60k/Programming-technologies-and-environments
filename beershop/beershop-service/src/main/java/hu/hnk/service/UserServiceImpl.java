package hu.hnk.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import hu.hnk.beershop.exception.DailyMoneyTransferLimitExceeded;
import hu.hnk.beershop.exception.EmailNotFound;
import hu.hnk.beershop.exception.InvalidPinCode;
import hu.hnk.beershop.exception.UsernameNotFound;
import hu.hnk.beershop.model.Rank;
import hu.hnk.beershop.model.Role;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.EventLogService;
import hu.hnk.beershop.service.interfaces.RestrictionCheckerService;
import hu.hnk.beershop.service.interfaces.UserService;
import hu.hnk.beershop.service.logfactory.EventLogType;
import hu.hnk.interfaces.RoleDao;
import hu.hnk.interfaces.UserDao;
import hu.hnk.service.factory.EventLogFactory;
import hu.hnk.service.tools.RankInterval;

/**
 * A felhasználói szolgálatásokkal foglalkozó osztály. Enterprise Java Bean.
 * 
 * @author Nandi
 * 
 */
@Stateless(name = "userService")
@Local(UserService.class)
public class UserServiceImpl implements UserService {

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = Logger.getLogger(UserServiceImpl.class);

	/**
	 * A felhasználókat kezelő adathozzáférési objektum.
	 */
	@EJB
	private UserDao userDao;

	/**
	 * A jogköröket kezelő adathozzáférési objektum.
	 */
	@EJB
	private RoleDao roleDao;

	/**
	 * Az eseményeket kezelő szolgáltatás.
	 */
	@EJB
	private EventLogService eventLogService;

	@EJB
	private RestrictionCheckerService restrictionCheckerService;

	private List<RankInterval> rankIntverals = RankInterval.getRankIntverals();

	/**
	 * A felhasználó mentése.
	 * 
	 * @param user
	 *            A mentendő felhasználó.
	 */
	public void save(User user) {
		Role role = roleDao.findByName("ROLE_USER");

		if (role == null) {
			role = new Role();
			role.setName("ROLE_USER");
			try {
				role = roleDao.save(role);
			} catch (Exception e) {
				logger.warn(e);
			}
		}

		User userData = null;
		try {
			userData = userDao.save(user);
		} catch (Exception e) {
			logger.warn(e);
		}
		List<Role> userRoles = userData.getRoles();

		if (userRoles == null || userRoles.isEmpty()) {
			userRoles = new ArrayList<>();
		}

		userRoles.add(role);
		userData.setRoles(userRoles);
		userData.getCart()
				.setUser(userData);
		try {
			userDao.update(userData);
		} catch (Exception e) {
			logger.warn(e);
		}
		eventLogService.save(EventLogFactory.createEventLog(EventLogType.Registration, userData));
	}

	/**
	 * Ellenőrzi hogy a megadott dátum már "idősebb" mint 18 év.
	 * 
	 * @param dateOfBirth
	 *            a vizsgálandó dátum.
	 * @return igaz ha idősebb, hamis ha még nem.
	 */
	public boolean isOlderThanEighteen(Date dateOfBirth) {
		LocalDate now = LocalDate.now();
		Instant instant = Instant.ofEpochMilli(dateOfBirth.getTime());
		LocalDate birth = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
				.toLocalDate();
		return birth.until(now)
				.getYears() > 17;
	}

	/**
	 * Felhasználó keresése a felhasználóneve alapján.
	 * 
	 * @param username
	 *            a keresendő felhasználónév
	 * @return a megtalált felhasználó, ha nincs ilyen akkor null.
	 */
	@Override
	public User findByUsername(String username) {
		User user = null;
		try {
			user = userDao.findByUsername(username);
		} catch (UsernameNotFound e) {
			logger.warn("Username:" + username + " is not found in our database.");
		}
		return user;
	}

	/**
	 * Felhasználónév ellenörzés, a kapott felhasználónevet ellenőrzi hogy
	 * válaszható-e még a regisztráció során.
	 * 
	 * @param username
	 *            az ellenőrizendő felhasználónév.
	 * @return hamis ha szabad a felhasználónév, igaz ha már nem.
	 */
	@Override
	public boolean isUsernameAlreadyTaken(String username) {
		try {
			userDao.findUsername(username);
			return true;
		} catch (UsernameNotFound e) {
			return false;
		}
	}

	/**
	 * E-mail cím ellenörzés, a kapott e-mail címet ellenőrzi hogy válaszható-e
	 * még a regisztráció során.
	 * 
	 * @param email
	 *            az ellenőrizendő e-mail cím.
	 * @return hamis ha szabad a email cím, igaz ha már nem.
	 */
	@Override
	public boolean isEmailAlreadyTaken(String email) {
		try {
			userDao.findEmail(email);
			return true;
		} catch (EmailNotFound e) {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rank countRankFromXp(User user) {
		// if (user.getExperiencePoints() > -1 && user.getExperiencePoints() <=
		// 2500) {
		// userRank = Rank.Amatuer;
		// } else if (user.getExperiencePoints() > 2500 &&
		// user.getExperiencePoints() <= 5000) {
		// userRank = Rank.Sorfelelos;
		// } else if (user.getExperiencePoints() > 5000 &&
		// user.getExperiencePoints() <= 7500) {
		// userRank = Rank.Ivobajnok;
		// } else if (user.getExperiencePoints() > 7500 &&
		// user.getExperiencePoints() <= 10000) {
		// userRank = Rank.Sormester;
		// } else if (user.getExperiencePoints() > 10000 &&
		// user.getExperiencePoints() <= 12500) {
		// userRank = Rank.Sordoktor;
		// } else if (user.getExperiencePoints() > 12500) {
		// userRank = Rank.Legenda;
		// }

		return rankIntverals.stream()
				.filter(p -> user.getExperiencePoints() > p.getMinimumXP()
						&& user.getExperiencePoints() <= p.getMaximumXP())
				.findFirst()
				.get()
				.getRank();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer countExperiencePointsInPercentage(Double experiencePoints) {

		Integer result = 0;

		if (experiencePoints > -1 && experiencePoints <= 2500) {
			result = (int) ((experiencePoints / 2500) * 100);
		} else if (experiencePoints > 2500 && experiencePoints <= 5000) {
			experiencePoints -= 2500;
			result = (int) ((experiencePoints / 2500) * 100);
		} else if (experiencePoints > 5000) {
			result = 100;
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void transferMoney(String userPin, String expectedPin, Integer money, User loggedInUser)
			throws InvalidPinCode, DailyMoneyTransferLimitExceeded {
		if (restrictionCheckerService.checkIfUserCanTransferMoney(loggedInUser)) {
			if (!userPin.equals(expectedPin)) {
				logger.info("User entered invalid PIN code.");
				throw new InvalidPinCode("PINs are not the same.");
			}
		} else {
			logger.info("User reached the maximum money tranfer limit today.");
			throw new DailyMoneyTransferLimitExceeded("Maximum limit exceeded.");
		}
		loggedInUser.setMoney(loggedInUser.getMoney() + money);
		try {
			userDao.update(loggedInUser);
		} catch (Exception e) {
			logger.warn(e);
		}
		eventLogService.save(EventLogFactory.createEventLog(EventLogType.MoneyTransfer, loggedInUser));
	}

	/**
	 * @param userDao
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * @param eventLogService
	 */
	public void setEventLogService(EventLogService eventLogService) {
		this.eventLogService = eventLogService;
	}

	/**
	 * @param restrictionCheckerService
	 */
	public void setRestrictionCheckerService(RestrictionCheckerService restrictionCheckerService) {
		this.restrictionCheckerService = restrictionCheckerService;
	}

}
