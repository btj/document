package document.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import document.Element;
import document.Knoop;
import document.KnoopUtils;
import document.Tekstknoop;

class DocumentTest {

	@Test
	void test() {
		Element k1 = new Element("paragraaf");
		assertEquals(null, k1.getOuder());
		assertEquals("paragraaf", k1.getTag());
		assertEquals(List.of(), k1.getKinderen());
		
		Tekstknoop k2 = new Tekstknoop("De Franse filosoof ");
		assertEquals(null, k2.getOuder());
		assertEquals("De Franse filosoof ", k2.getTekst());
		
		k1.addKind(0, k2);
		assertEquals(k1, k2.getOuder());
		assertEquals(List.of(k2), k1.getKinderen());
		
		Element k3 = new Element("vetgedrukt");
		k1.addKind(1, k3);
		assertEquals(List.of(k2, k3), k1.getKinderen());
		
		Tekstknoop k4 = new Tekstknoop("René Descartes");
		k3.addKind(0, k4);
		
		assertEquals("<paragraaf>De Franse filosoof <vetgedrukt>René Descartes</vetgedrukt></paragraaf>", k1.toString());
		assertEquals("De Franse filosoof René Descartes", KnoopUtils.getPlatteTekst(k1));
		
		k2.verwijderVanOuder();
		assertEquals(null, k2.getOuder());
		assertEquals(List.of(k3), k1.getKinderen());
		
		k3.addKind(0, k2);
		assertEquals("<paragraaf><vetgedrukt>De Franse filosoof René Descartes</vetgedrukt></paragraaf>", k1.toString());
		
		Element k5 = new Element("verborgen");
		k5.addKind(0, k1);
		assertEquals("", KnoopUtils.getPlatteTekst(k5));
		
		ArrayList<Element> voorouders = new ArrayList<>();
		for (Iterator<Element> i = KnoopUtils.getVooroudersIterator(k4); i.hasNext(); )
			voorouders.add(i.next());
		assertEquals(List.of(k3, k1, k5), voorouders);
		
		ArrayList<Knoop> afstammelingen = new ArrayList<>();
		KnoopUtils.forEachAfstammeling(k5, k -> afstammelingen.add(k));
		assertEquals(List.of(k1, k3, k2, k4), afstammelingen);
	}

}
