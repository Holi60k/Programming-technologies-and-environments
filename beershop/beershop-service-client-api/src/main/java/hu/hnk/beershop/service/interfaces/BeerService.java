package hu.hnk.beershop.service.interfaces;

import java.util.List;

import hu.hnk.beershop.model.Beer;

/**
 * A sör entitáshoz kapcsolódó szolgáltatásokat tartalmazó interfész.
 * 
 * @author Nandi
 *
 */
public interface BeerService {
	/**
	 * Az összes sör lekérdezése.
	 * 
	 * @return a sörök listája
	 * @throws Exception
	 *             adatbázis illetve más nem várt kivétel esetén
	 */
	public List<Beer> findAll() throws Exception;

}
