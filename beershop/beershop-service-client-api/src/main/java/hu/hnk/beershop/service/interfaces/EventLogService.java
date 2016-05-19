package hu.hnk.beershop.service.interfaces;

import java.util.List;

import hu.hnk.beershop.model.EventLog;
import hu.hnk.beershop.model.User;

/**
 * Az eseményeket kezelő szolgáltatás.
 * 
 * Az események listázásért illetve mentéséért felelős szolgáltatás. Az
 * eseményeknek különböző fajtáik vannak.
 * 
 * {@link EventLogType#REGISTRATION} - regisztrációs esemény amely egy új
 * felhasználó regisztrációja során mentődik el automatikusan.
 * 
 * {@link EventLogType#MONEYTRANSFER} - egy pénzfeltöltési esemény során
 * létrejövő felhasználó esemény.
 * 
 * {@link EventLogType#BUY} - egy vásárlás során létrejövő felhasználó esemény.
 * 
 * Az eseményeket szolgáltató <code>Factory</code> osztály az
 * <p>
 * EventLogFactory
 * </p>
 * osztály amely a
 * <p>
 * service
 * </p>
 * modulban található.
 * 
 * @author Nandi
 *
 */
public interface EventLogService {

	/**
	 * Egy adott felhasználóhoz kapcsolódó mindig az aznapi eseményeket
	 * visszaadó metódus.
	 * 
	 * @param user
	 *            a felhasználó akinek az eseményeit vissza szeretnénk adni.
	 * @return az eseményeket tartalmazó lista.
	 * @throws Exception
	 */
	public List<EventLog> findByUser(User user) throws Exception;

	/**
	 * Egy esemény mentése.
	 * 
	 * @param event
	 *            az mentendő esemény.
	 * @return a mentett esemény.
	 * @throws Exception
	 */
	public EventLog save(EventLog event) throws Exception;

}
