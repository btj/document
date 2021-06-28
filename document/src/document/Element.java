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
	 * @invar | kinderen.stream().allMatch(kind -> kind != null)
	 */
	private final String tag;
	/**
	 * @representationObject
	 */
	private final List<Knoop> kinderen = new ArrayList<>();

	/**
	 * @post | result != null
	 * @immutable
	 */
	String getTagInternal() { return tag; }
	
	/**
	 * @invar | getKinderenInternal().stream().allMatch(kind -> kind.getOuderInternal() == this)
	 *
	 * @post | result != null
	 * @post | LogicalList.distinct(result)
	 * @post | result.stream().allMatch(kind -> kind != null)
	 * @creates | result
	 */
	List<Knoop> getKinderenInternal() { return List.copyOf(kinderen); }

	/**
	 * @immutable
	 */
	public String getTag() { return getTagInternal(); }
	
	/**
	 * @creates | result
	 */
	public List<Knoop> getKinderen() { return getKinderenInternal(); }
	
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
	 * @mutates | getKinderenInternal()
	 * @post | getKinderenInternal().equals(LogicalList.minus(old(getKinderenInternal()), kind))
	 */
	void verwijderKind(Knoop kind) {
		kinderen.remove(kind);
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
		kind.setOuder(this);
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
