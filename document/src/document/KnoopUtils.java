package document;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class KnoopUtils {

	public static String getPlatteTekst(Knoop knoop) {
		if (knoop instanceof Tekstknoop)
			return ((Tekstknoop)knoop).getTekst();
		else if (((Element)knoop).getTag().equals("verborgen")) {
			return "";
		} else {
			return ((Element)knoop).getKinderen().stream()
					.map(kind -> getPlatteTekst(kind))
					.collect(Collectors.joining());
		}
	}
	
	public static Iterator<Element> getVooroudersIterator(Knoop k) {
		return new Iterator<Element>() {
			Element e = k.getOuder();
			@Override
			public boolean hasNext() {
				return e != null;
			}
			@Override
			public Element next() {
				Element result = e;
				e = e.getOuder();
				return result;
			}
		};
	}
	
	public static void forEachAfstammeling(Knoop knoop, Consumer<? super Knoop> consumer) {
		if (knoop instanceof Element) {
			Element e = (Element)knoop;
			for (Knoop kind : e.getKinderen()) {
				consumer.accept(kind);
				forEachAfstammeling(kind, consumer);
			}
		}
	}
}
