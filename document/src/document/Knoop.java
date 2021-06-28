package document;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import logicalcollections.LogicalList;
import logicalcollections.LogicalSet;

/**
 * @invar | getOuder() == null || getOuder().getKinderen().contains(this)
 * @invar | !getVoorouders().contains(this)
 */
public abstract class Knoop {

	/**
	 * @invar | !getVooroudersPrivate().contains(this)
	 * @peerObject
	 */
	private Element ouder;
	
	private Set<Element> getVooroudersPrivate() {
		Set<Element> result = new HashSet<>();
		Element e = ouder;
		while (e != null && !result.contains(e)) {
			result.add(e);
			e = ((Knoop)e).ouder;
		}
		return result;
	}
	
	/**
	 * @invar | true
	 * @invar | getOuderInternal() == null || getOuderInternal().getKinderenInternal().contains(this)
	 * 
	 * @peerObject (class-level)
	 */
	Element getOuderInternal() { return ouder; }
	
	/**
	 * @post | Objects.equals(result, LogicalSet.<Element>matching(voorouders ->
	 *       |     (getOuderInternal() == null || voorouders.contains(getOuderInternal())) &&
	 *       |     voorouders.allMatch(voorouder ->
	 *       |         voorouder.getOuderInternal() == null || voorouders.contains(voorouder.getOuderInternal()))))
	 */
	Set<Element> getVooroudersInternal() { return getVooroudersPrivate(); }
	
	/**
	 * @peerObject
	 */
	public Element getOuder() { return getOuderInternal(); }

	/**
	 * @post | Objects.equals(result, LogicalSet.<Element>matching(voorouders ->
	 *       |     (getOuder() == null || voorouders.contains(getOuder())) &&
	 *       |     voorouders.allMatch(voorouder ->
	 *       |         voorouder.getOuder() == null || voorouders.contains(voorouder.getOuder()))))
	 */
	public Set<Element> getVoorouders() {
		return getVooroudersInternal();
	}
	
	/**
	 * @post | getOuderInternal() == null
	 */
	Knoop() {}
	
	/**
	 * @pre | ouder == null || ouder != this && !ouder.getVooroudersInternal().contains(this)
	 * @mutates | getOuderInternal()
	 * @post | getOuderInternal() == ouder
	 */
	void setOuder(Element ouder) {
		this.ouder = ouder;
	}
	
	/**
	 * @pre | getOuder() != null
	 * 
	 * @mutates_properties | getOuder(), getOuder().getKinderen()
	 * 
	 * @post | getOuder() == null
	 * @post | old(getOuder()).getKinderen().equals(LogicalList.minus(old(getOuder().getKinderen()), this))
	 */
	public void verwijderVanOuder() {
		ouder.verwijderKind(this);
		ouder = null;
	}
	
}
