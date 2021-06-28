package document;

import java.util.ArrayList;
import java.util.List;

import logicalcollections.LogicalList;

/**
 * @invar | getTag() != null
 * @invar | getKinderen() != null
 * @invar | LogicalList.distinct(getKinderen())
 * @invar | getKinderen().stream().allMatch(kind -> kind != null && kind.getOuder() == this)
 */
public final class Element extends Knoop {
	
	/**
	 * @invar | tag != null
	 * @invar | kinderen != null
	 * @invar | LogicalList.distinct(kinderen)
	 * @invar | kinderen.stream().allMatch(kind -> kind != null && kind.ouder == this)
	 */
	String tag;
	/**
	 * @representationObject
	 * @peerObjects
	 */
	List<Knoop> kinderen = new ArrayList<>();

	/**
	 * @immutable
	 */
	public String getTag() { return tag; }
	
	/**
	 * @creates | result
	 * @peerObjects
	 */
	public List<Knoop> getKinderen() { return List.copyOf(kinderen); }
	
	/**
	 * @throws IllegalArgumentException | tag == null
	 * @post | getOuder() == null
	 * @post | getTag().equals(tag)
	 * @post | getKinderen().isEmpty()
	 */
	public Element(String tag) {
		if (tag == null)
			throw new IllegalArgumentException("`tag` is null");
		this.tag = tag;
	}
	
	/**
	 * @pre | 0 <= index && index <= getKinderen().size()
	 * @pre | kind != null && kind.getOuder() == null && kind != this && !getVoorouders().contains(kind)
	 * 
	 * @mutates_properties | getKinderen(), kind.getOuder()
	 * 
	 * @post | kind.getOuder() == this
	 * @post | getKinderen().equals(LogicalList.plusAt(old(getKinderen()), index, kind))
	 */
	public void addKind(int index, Knoop kind) {
		kinderen.add(index, kind);
		kind.ouder = this;
	}
	
	@Override
	public String toString() {
		String result = "<" + tag + ">";
		for (Knoop kind : kinderen)
			result += kind;
		result += "</" + tag + ">";
		return result;
	}
	
}
