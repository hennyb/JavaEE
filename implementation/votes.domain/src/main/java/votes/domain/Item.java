/*******************************************************************************
 * 2014, All rights reserved.
 *******************************************************************************/
package votes.domain;

// Start of user code (user defined imports)

import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// End of user code
/**
 * Description of Item.
 * 
 * @author Johannes
 */
@Entity
public class Item {
	/**
	 * Description of the property options.
	 */
	public HashSet<Option> options = new HashSet<Option>();

	/**
	 * Description of the property titel.
	 */
	public String titel = "";

	/**
	 * Description of the property id.
	 */
	public Integer id = Integer.valueOf(0);

	/**
	 * Description of the property m.
	 */
	public Integer m = Integer.valueOf(0);

	// Start of user code (user defined attributes for Item)

	// End of user code

	/**
	 * The constructor.
	 */
	public Item() {
		// Start of user code constructor for Item)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for Item)

	// End of user code
	/**
	 * Returns options.
	 * @return options 
	 */
	public HashSet<Option> getOptions() {
		return this.options;
	}

	/**
	 * Sets a value to attribute options. 
	 * @param newOptions 
	 */
	public void setOptions(HashSet<Option> newOptions) {
		this.options = newOptions;
	}

	/**
	 * Returns titel.
	 * @return titel 
	 */
	public String getTitel() {
		return this.titel;
	}

	/**
	 * Sets a value to attribute titel. 
	 * @param newTitel 
	 */
	public void setTitel(String newTitel) {
		this.titel = newTitel;
	}

	/**
	 * Returns id.
	 * @return id 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * Sets a value to attribute id. 
	 * @param newId 
	 */
	public void setId(Integer newId) {
		this.id = newId;
	}

	/**
	 * Returns m.
	 * @return m 
	 */
	public Integer getM() {
		return this.m;
	}

	/**
	 * Sets a value to attribute m. 
	 * @param newM 
	 */
	public void setM(Integer newM) {
		this.m = newM;
	}

}
