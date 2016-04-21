package hu.hnk.managedbeans;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.service.interfaces.CartService;
import hu.hnk.loginservices.SessionManager;
import hu.hnk.tool.FacesMessageTool;

/**
 * @author Nandi
 *
 */
@ManagedBean(name = "cartManagerBean")
@ViewScoped
public class CartManagerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = Logger.getLogger(CartManagerBean.class);

	@ManagedProperty(value = "#{sessionManagerBean}")
	private SessionManager sessionManager;

	FacesMessage msg;

	@EJB
	private CartService cartService;

	private List<CartItem> items;

	@PostConstruct
	public void init() {
		items = cartService.findByUser(sessionManager.getLoggedInUser()).getItems().stream().filter(e->e.getActive()).collect(Collectors.toList());
		logger.info(items);
	}

	public String getTotalPrice() {
		NumberFormat format = new DecimalFormat("#0.00");
		return format.format(cartService.countTotalCost(items));
	}

	public void deleteItemFromCart(CartItem item) {
		
		try {
			cartService.deletItemFromCart(item);
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Módosítások sikeresen mentve!",
					"Módosítások sikeresen mentve!");
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Módosításokat nem tudtuk menteni!",
					"Módosításokat nem tudtuk menteni!");
		}
		FacesMessageTool.publishMessage(msg);
	}
	
	public String countBonusPoints() {
		NumberFormat format = new DecimalFormat("#0.00");
		return format.format(cartService.countBonusPoints(items));
	}

	/**
	 * @return the sessionManager
	 */
	public SessionManager getSessionManager() {
		return sessionManager;
	}

	/**
	 * @param sessionManager
	 *            the sessionManager to set
	 */
	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	/**
	 * @return the cartService
	 */
	public CartService getCartService() {
		return cartService;
	}

	/**
	 * @param cartService
	 *            the cartService to set
	 */
	public void setCartService(CartService cartService) {
		this.cartService = cartService;
	}

	/**
	 * @return the items
	 */
	public List<CartItem> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<CartItem> items) {
		this.items = items;
	}

}
