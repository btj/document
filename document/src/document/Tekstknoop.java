package document;

/**
 * @invar | getTekst() != null
 */
public class Tekstknoop extends Knoop {
	
	/** @invar | tekst != null */
	private final String tekst;
	
	/**
	 * @post | result != null
	 * @immutable
	 */
	String getTekstInternal() { return tekst; }
	
	/** @immutable */
	public String getTekst() { return getTekstInternal(); }
	
	/**
	 * @throws IllegalArgumentException | tekst == null
	 * 
	 * @post | getOuder() == null
	 * @post | getTekst().equals(tekst)
	 */
	public Tekstknoop(String tekst) {
		if (tekst == null)
			throw new IllegalArgumentException("`tekst` is null");
		this.tekst = tekst;
	}
	
	@Override
	public String toString() {
		return tekst;
	}

}
