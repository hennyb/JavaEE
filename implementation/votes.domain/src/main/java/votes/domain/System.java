/*******************************************************************************
 * 2014, All rights reserved.
 *******************************************************************************/
package votes.domain;

// Start of user code (user defined imports)
import java.util.HashSet;

// End of user code

/**
 * Description of System.
 * 
 * @author Johannes
 */
public class System {
	/**
	 * Description of the property polls.
	 */
	public HashSet<Poll> polls = new HashSet<Poll>();

	// Start of user code (user defined attributes for System)

	// End of user code

	/**
	 * The constructor.
	 */
	public System() {
		// Start of user code constructor for System)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for System)

	// End of user code
	/**
	 * Returns polls.
	 * 
	 * @return polls
	 */
	public HashSet<Poll> getPolls() {
		return this.polls;
	}

	/**
	 * Sets a value to attribute polls.
	 * 
	 * @param newPolls
	 */
	public void setPolls(HashSet<Poll> newPolls) {
		this.polls = newPolls;
	}

}
