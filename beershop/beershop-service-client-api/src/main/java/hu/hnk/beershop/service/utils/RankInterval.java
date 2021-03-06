package hu.hnk.beershop.service.utils;

import java.util.ArrayList;
import java.util.List;

import hu.hnk.beershop.model.Rank;

/**
 * A rangok tapasztalatponthoz kötött információit leíró osztály.
 * 
 * @author Nandi
 *
 */
public class RankInterval {

	/**
	 * A rang amit a minimum illetve maximum tapasztalatpont határoz meg.
	 */
	private Rank rank;
	/**
	 * A minimális tapasztalatpont.
	 */
	private Integer minimumXP;
	/**
	 * A maximális tapasztalatpont.
	 */
	private Integer maximumXP;

	/**
	 * A rangokat tartalmazó statikus lista.
	 */
	private static List<RankInterval> rankIntverals;

	/**
	 * Az osztály konstuktora.
	 * 
	 * @param rank
	 *            a szabályozandó rang.
	 * @param minimumXP
	 *            a minimum tapasztalatpont.
	 * @param maximumXP
	 *            a maximum tapasztalatpont.
	 */
	public RankInterval(Rank rank, Integer minimumXP, Integer maximumXP) {
		super();
		this.rank = rank;
		this.minimumXP = minimumXP;
		this.maximumXP = maximumXP;
	}

	static {
		rankIntverals = new ArrayList<>();
		rankIntverals.add(new RankInterval(Rank.AMATUER, -1, 2500));
		rankIntverals.add(new RankInterval(Rank.SORFELELOS, 2500, 5000));
		rankIntverals.add(new RankInterval(Rank.IVOBAJNOK, 5000, 7500));
		rankIntverals.add(new RankInterval(Rank.SORMESTER, 7500, 10000));
		rankIntverals.add(new RankInterval(Rank.SORDOKTOR, 10000, 12500));
		rankIntverals.add(new RankInterval(Rank.LEGENDA, 12500, 15000));
	}

	/**
	 * Visszadja a rangot.
	 * 
	 * @return a rang.
	 */
	public Rank getRank() {
		return rank;
	}

	/**
	 * Visszaadja a minimális tapasztalatpontot.
	 * 
	 * @return a minimális tapasztalatpont.
	 */
	public Integer getMinimumXP() {
		return minimumXP;
	}

	/**
	 * Visszaadja a maximális tapasztalatpontot.
	 * 
	 * @return a maximális tapasztalatpont.
	 */
	public Integer getMaximumXP() {
		return maximumXP;
	}

	/**
	 * Visszaadja a már meghatározott rangok listáját.
	 * 
	 * @return a meghatározott rangok listája.
	 */
	public static List<RankInterval> getRankIntverals() {
		return rankIntverals;
	}

}
