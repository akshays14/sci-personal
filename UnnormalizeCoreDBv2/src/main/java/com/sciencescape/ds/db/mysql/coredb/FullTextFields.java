package com.sciencescape.ds.db.mysql.coredb;

/**
 * Class to contain fields related to full-text.
 *
 * @author akshay
 * @version 0.1
 */
public class FullTextFields {

	/**!< abstract of the paper */
	private String abstractText;
	/**!< introduction of the paper */
	private String introduction;

	/**
	 * Creates an {@code FullTextFields} object with given parameters.
	 *
	 * @param pAbstractText abstract of the paper
	 * @param pIntroduction introduction of the paper
	 */
	public FullTextFields(final String pAbstractText,
			final String pIntroduction) {
		this.abstractText = pAbstractText;
		this.introduction = pIntroduction;
	}

	/**
	 * @return the abstractText
	 */
	public final String getAbstractText() {
		return abstractText;
	}

	/**
	 * @param pAbstractText the abstractText to set
	 */
	public final void setAbstractText(final String pAbstractText) {
		this.abstractText = pAbstractText;
	}

	/**
	 * @return the introduction
	 */
	public final String getIntroduction() {
		return introduction;
	}

	/**
	 * @param pIntroduction the introduction to set
	 */
	public final void setIntroduction(final String pIntroduction) {
		this.introduction = pIntroduction;
	}
}
