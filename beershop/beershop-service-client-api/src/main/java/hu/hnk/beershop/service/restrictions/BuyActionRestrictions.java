package hu.hnk.beershop.service.restrictions;

import java.util.ArrayList;
import java.util.List;

import hu.hnk.beershop.model.Rank;
import hu.hnk.beershop.service.interfaces.RestrictionCheckerService;

/**
 * A vásárlási korlátozásokat leíró osztály.
 * 
 * Minden egyes felhasználó az aktuális rangjához képest bizonyos
 * korlátozásoknak kell eleget tennie.
 * 
 * Minden ranghoz tartozik egy korlátozott mennyiség amivel a napi vásárlásainak
 * a számát korlátozni tudjuk.
 * 
 * Ezeket a korlátozásokat a {@link RestrictionCheckerService} interfész
 * implementációja valósítja meg.
 * 
 * @author Nandi
 *
 */
public class BuyActionRestrictions {

	/**
	 * A korlátozás rangja.
	 */
	private Rank rank;
	/**
	 * A korlátozott napi vásárlási mennyiség.
	 */
	private Integer restrictedValue;
	/**
	 * A korlátozásokat tartalmazó lista.
	 */
	private static List<BuyActionRestrictions> restirctedValues;

	/**
	 * A szállítási költség.
	 */
	private static Double shippingCost = 590.0;

	static {
		restirctedValues = new ArrayList<>();
		restirctedValues.add(new BuyActionRestrictions(Rank.AMATUER, 3));
		restirctedValues.add(new BuyActionRestrictions(Rank.SORFELELOS, 5));
		restirctedValues.add(new BuyActionRestrictions(Rank.IVOBAJNOK, 7));
		restirctedValues.add(new BuyActionRestrictions(Rank.SORMESTER, 10));
		restirctedValues.add(new BuyActionRestrictions(Rank.SORDOKTOR, 15));
		restirctedValues.add(new BuyActionRestrictions(Rank.LEGENDA, 30));
	}

	/**
	 * A vásárlási korlátozásokat létrehozó osztály kontruktora.
	 * 
	 * @param rank
	 *            a korlátozáshoz kötött rang.
	 * @param restrictedValue
	 *            a korlátozáshoz kötött érté.
	 */
	public BuyActionRestrictions(Rank rank, Integer restrictedValue) {
		super();
		this.rank = rank;
		this.restrictedValue = restrictedValue;
	}

	/**
	 * Visszaadja a korlátozás rangját.
	 * 
	 * @return a korlátozás rangja.
	 */
	public Rank getRank() {
		return rank;
	}

	/**
	 * Visszaadja a korlátozás értékét.
	 * 
	 * @return a korlátozás értéke.
	 */
	public Integer getRestrictedValue() {
		return restrictedValue;
	}

	/**
	 * Visszaadja a korlátozásokat tartlamazó listát.
	 * 
	 * @return a korlátozásokat tartalmazó lista.
	 */
	public static List<BuyActionRestrictions> getRestirctedValues() {
		return restirctedValues;
	}

	/**
	 * Visszaadja a szállítási költséget.
	 * 
	 * @return a szállítási költség.
	 */
	public static Double getShippingCost() {
		return shippingCost;
	}

}
