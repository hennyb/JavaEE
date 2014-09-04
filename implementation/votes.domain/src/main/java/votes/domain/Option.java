/*******************************************************************************
 * 2014, All rights reserved.
 *******************************************************************************/
package votes.domain;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Option.
 * 
 * @author Johannes
 */
public class Option {
	/**
	 * Description of the property description.
	 */
	public String description = "";

	/**
	 * Description of the property name.
	 */
	public String name = "";

	/**
	 * Description of the property id.
	 */
	public Integer id = Integer.valueOf(0);

	// Start of user code (user defined attributes for Option)

	// End of user code

	/**
	 * The constructor.
	 */
	public Option() {
		// Start of user code constructor for Option)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for Option)

	// End of user code
	/**
	 * Returns description.
	 * @return description 
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets a value to attribute description. 
	 * @param newDescription 
	 */
	public void setDescription(String newDescription) {
		this.description = newDescription;
	}

	/**
	 * Returns name.
	 * @return name 
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets a value to attribute name. 
	 * @param newName 
	 */
	public void setName(String newName) {
		this.name = newName;
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

}
