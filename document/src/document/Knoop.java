package document;

import java.util.HashSet;
import java.util.Set;
import logicalcollections.LogicalList;

/**
 * @invar | getOuder() == null || getOuder().getKinderen().contains(this)
 * @invar | !getVoorouders().contains(this)
 */
public abstract class Knoop {

	/**
	 * @invar | true
	 * @invar | true
	 * @invar | ouder == null || ouder.kinderen.contains(this)
	 * @invar | !getVooroudersInternal().contains(this)
	 * 
	 * @peerObject
	 */
	Element ouder;
	
	Set<Element> getVooroudersInternal() {
		Set<Element> result = new HashSet<>();
		Element e = ouder;
		while (e != null && !result.contains(e)) {
			result.add(e);
			e = e.ouder;
		}
		return result;
	}
	
	/**
	 * @peerObject
	 */
	public Element getOuder() { return ouder; }
	
	public Set<Element> getVoorouders() {
		return getVooroudersInternal();
	}
	
	Knoop() {}
	
	/**
	 * @pre | getOuder() != null
	 * 
	 * @mutates_properties | getOuder(), getOuder().getKinderen()
	 * 
	 * @post | getOuder() == null
	 * @post | old(getOuder()).getKinderen().equals(LogicalList.minus(old(getOuder().getKinderen()), this))
	 */
	public void verwijderVanOuder() {
		ouder.kinderen.remove(this);
		ouder = null;
	}
	
}
