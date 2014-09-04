/*******************************************************************************
 * 2014, All rights reserved.
 *******************************************************************************/
package votes.domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import java.util.Date;
// Start of user code (user defined imports)
import java.util.HashSet;

// End of user code

/**
 * Description of Poll.
 * 
 * @author Johannes
 */
@Entity
public class Poll {
	/**
	 * Description of the property description.
	 */
	public String description = "";

	/**
	 * Description of the property items.
	 */
	public HashSet<Item> items = new HashSet<Item>();

	/**
	 * Description of the property start.
	 */
	public Date start = new Date();

	/**
	 * Description of the property end.
	 */
	public Date end = new Date();

	/**
	 * Description of the property titel.
	 */
	public String titel = "";

	// Start of user code (user defined attributes for Poll)

	// End of user code

	/**
	 * The constructor.
	 */
	public Poll() {
		// Start of user code constructor for Poll)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for Poll)

	// End of user code
	/**
	 * Returns description.
	 * 
	 * @return description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets a value to attribute description.
	 * 
	 * @param newDescription
	 */
	public void setDescription(String newDescription) {
		this.description = newDescription;
	}

	/**
	 * Returns items.
	 * 
	 * @return items
	 */
	@OneToMany(mappedBy = "Poll")
	public HashSet<Item> getItems() {
		return this.items;
	}

	/**
	 * Sets a value to attribute items.
	 * 
	 * @param newItems
	 */
	public void setItems(HashSet<Item> newItems) {
		this.items = newItems;
	}

	/**
	 * Returns start.
	 * 
	 * @return start
	 */
	public Date getStart() {
		return this.start;
	}

	/**
	 * Sets a value to attribute start.
	 * 
	 * @param newStart
	 */
	public void setStart(Date newStart) {
		this.start = newStart;
	}

	/**
	 * Returns end.
	 * 
	 * @return end
	 */
	public Date getEnd() {
		return this.end;
	}

	/**
	 * Sets a value to attribute end.
	 * 
	 * @param newEnd
	 */
	public void setEnd(Date newEnd) {
		this.end = newEnd;
	}

	/**
	 * Returns titel.
	 * 
	 * @return titel
	 */
	public String getTitel() {
		return this.titel;
	}

	/**
	 * Sets a value to attribute titel.
	 * 
	 * @param newTitel
	 */
	public void setTitel(String newTitel) {
		this.titel = newTitel;
	}

}
