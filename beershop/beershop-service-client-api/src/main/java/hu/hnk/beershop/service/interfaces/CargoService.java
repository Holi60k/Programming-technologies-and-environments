package hu.hnk.beershop.service.interfaces;

import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.model.User;

/**
 * @author Nandi
 *
 */
public interface CargoService {

	/**
	 * @param cargo
	 * @return
	 */
	public Cargo saveNewCargo(Cargo cargo) throws Exception;

	/**
	 * Ellenőrzi hogy a paraméterként megadott felhasználónak rendelkezésére
	 * áll-e elegendő pénz a fizetésre.
	 * 
	 * @param user
	 *            az ellenőrizendő felhaszánló.
	 * @return igaz ha van elég pénze, hamis ha nem.
	 */
	public boolean isThereEnoughMoney(User user);
}
