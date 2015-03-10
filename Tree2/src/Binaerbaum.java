import java.util.ArrayList;

/**
 * Eine Binärbaum Klasse, welche die Methoden und Utilities bereitstellt um
 * einen Binären Suchbaum aufzubauen
 * 
 * @author Justin Mertmann, Wayne Stams
 */
class Binaerbaum {
	private Element rootElement;
	private int totalnodes = 0;
	private int maxheight = 0;
	private ArrayList<String> traversierungsListe = new ArrayList<String>();

	/**
	 * Konstruktor
	 */
	public Binaerbaum() {
		rootElement = null;
	}

	/**
	 * Berechnet die Tiefe des Baumes
	 * 
	 * @param t
	 *            - Übergebener Gefüllter Binärbaum
	 * @return Höhe der Tiefe
	 */
	public int berechneHoehe(Element t) {
		if (t == null)
			return -1;
		else
			return 1 + werteVergleich(berechneHoehe(t.getLinkesKind()),
					berechneHoehe(t.getRechtesKind()));
	}

	/**
	 * Vergleicht die Werte, das höhere wird zurückgegeben
	 * 
	 * @param a
	 *            Wert A
	 * @param b
	 *            Wert B
	 * @return a oder b, je nachdem welcher höher ist
	 */
	public int werteVergleich(int a, int b) {
		if (a > b)
			return a;
		else
			return b;
	}

	/**
	 * Traversiert "In-Order" durch den Baum und setzt dabei die Positionen der
	 * Element für die GUI fest
	 */
	public void berechneElementPositionen() {
		int depth = 1;
		totalnodes = 0;
		inorderTraversierung(rootElement, depth);
	}

	/**
	 * traversiert "In-Order" durch den Binärbaum
	 * 
	 * @param t
	 *            Binärbaum
	 * @param depth
	 *            Tiefe
	 */
	public void inorderTraversierung(Element t, int depth) {
		if (t != null) {
			inorderTraversierung(t.getLinkesKind(), depth + 1);
			t.setXpos(totalnodes++);
			traversierungsListe.add(t.getNutzdaten());
			t.setYpos(depth);
			inorderTraversierung(t.getRechtesKind(), depth + 1);
		}
	}

	/**
	 * Fügt ein neues Element dem Baum hinzu
	 * 
	 * @param nutzdaten
	 *            Nutzdaten im Element
	 */
	public void elementHinzufuegen(String nutzdaten) {
		Element neuElement = new Element(nutzdaten);

		if (getRootElement() == null) {
			setRootElement(neuElement);
		} else {
			Element aktElement = getRootElement();

			Element vaterElement;

			while (true) {
				vaterElement = aktElement;

				if (nutzdaten.compareTo(aktElement.getNutzdaten()) < 0) {
					aktElement = aktElement.getLinkesKind();

					if (aktElement == null) {
						vaterElement.setLinkesKind(neuElement);
						return;
					}
				} else {
					aktElement = aktElement.getRechtesKind();

					if (aktElement == null) {
						vaterElement.setRechtesKind(neuElement);
						return;
					}
				}
			}
		}
	}

	/**
	 * Löscht ein Element aus dem Baum
	 * 
	 * @param loeschElement
	 *            zu löschendes Element
	 * @return gibt true zurück wenn Löschung erfolgreich
	 */
	public boolean elementLoeschen(Element loeschElement) {
		Element wegfallendesElement = sucheElement(loeschElement,
				this.rootElement);

		if (wegfallendesElement == null) {
			return false;
		} else {
			if (hatNurLinkesKind(wegfallendesElement)) {
				this.loescheElementMitNurLinkemKind(wegfallendesElement);
			} else if (hatNurRechtesKind(wegfallendesElement)) {
				this.loescheElementMitNurRechtemKind(wegfallendesElement);
			} else if (hatKeineKinder(wegfallendesElement)) {
				this.loescheElementOhneKind(wegfallendesElement);
			} else if (hatBeideKinder(wegfallendesElement)) {
				this.loescheElementDasZweiKinderHat(wegfallendesElement);
			}
			return true;
		}
	}

	/**
	 * Löschroutine für Elemente ohne Kindelemente
	 * 
	 * @param element
	 *            zu löschendes Element
	 */
	private void loescheElementOhneKind(Element element) {
		Element parent = findParent(element.getNutzdaten());

		if (parent == null) {
			this.rootElement = null;
		} else {
			if (isLinkesKind(element)) {
				parent.setLinkesKind(null);
			} else if (isRechtesKind(element)) {
				parent.setRechtesKind(null);
			}
		}
	}

	/**
	 * Löschroutine für Elemente mit nur einem rechten Kind
	 * 
	 * @param element
	 *            zu löschendes Element
	 */
	private void loescheElementMitNurRechtemKind(Element element) {
		Element parentVomWegfall = findParent(element.getNutzdaten());

		if (parentVomWegfall == null) {
			this.rootElement = this.rootElement.getRechtesKind();
		} else {
			if (isLinkesKind(element)) {
				parentVomWegfall.setLinkesKind(element.getRechtesKind());
			} else {
				parentVomWegfall.setRechtesKind(element.getRechtesKind());
			}
		}
	}

	/**
	 * Löschroutine für Elemente mit nur einem linken Kind
	 * 
	 * @param element
	 *            zu löschendes Element
	 */
	private void loescheElementMitNurLinkemKind(Element element) {
		Element parentVomWegfall = findParent(element.getNutzdaten());

		if (parentVomWegfall == null) {
			this.rootElement = this.rootElement.getLinkesKind();
		} else {
			if (isLinkesKind(element)) {
				parentVomWegfall.setLinkesKind(element.getLinkesKind());
			} else {
				parentVomWegfall.setRechtesKind(element.getLinkesKind());
			}
		}
	}

	/**
	 * Löschroutine für Elemente mit zwei Kindern
	 * 
	 * @param element
	 *            zu löschendes Element
	 */
	private void loescheElementDasZweiKinderHat(Element element) {
		Element ersatzElement = kleinstesElementAusDemRechtenZweig(element);
		Element parent = findParent(element.getNutzdaten());
		Element parentVomErsatz = findParent(ersatzElement.getNutzdaten());

		if (parent == null) {
			if (isLinkesKind(ersatzElement)) {
				parentVomErsatz.setLinkesKind(null);
			} else {
				parentVomErsatz.setRechtesKind(null);
			}

			if (element.getRechtesKind() != null) {
				ersatzElement.setRechtesKind(element.getRechtesKind());
			}
			if (element.getLinkesKind() != null) {
				ersatzElement.setLinkesKind(element.getLinkesKind());
			}

			element.setLinkesKind(null);
			element.setRechtesKind(null);

			this.rootElement = ersatzElement;
		} else {
			if (isLinkesKind(ersatzElement)) {
				parentVomErsatz.setLinkesKind(null);
			} else {
				parentVomErsatz.setRechtesKind(null);
			}

			if (isLinkesKind(element)) {
				parent.setLinkesKind(ersatzElement);
			} else {
				parent.setRechtesKind(ersatzElement);
			}

			if (element.getRechtesKind() != null) {
				ersatzElement.setRechtesKind(element.getRechtesKind());
			}
			if (element.getLinkesKind() != null) {
				ersatzElement.setLinkesKind(element.getLinkesKind());
			}

			element.setLinkesKind(null);
			element.setRechtesKind(null);

		}

	}

	/**
	 * Sucht das kleinste Element aus dem rechten Zweig des gewählten Elementes
	 * 
	 * @param suchElement
	 *            Element, wessen kleinstes Kind im rechten Zwei herausgefunden
	 *            werden soll
	 * @return gefundenes Element
	 */
	public Element kleinstesElementAusDemRechtenZweig(Element suchElement) {
		Element aktElement = sucheElement(suchElement, this.rootElement)
				.getRechtesKind();
		while (hatLinkesKind(aktElement)) {
			aktElement = aktElement.getLinkesKind();
		}
		return aktElement;
	}

	/**
	 * Sucht das größte Element aus dem linken Zweig des suchElements
	 * 
	 * @param suchElement
	 *            Element aus dem das größte Kind des linken Zweigs
	 *            herausgefunden wird
	 * @return das gefundene Element
	 */
	public Element groesstesElementAusDemLinkenZweig(Element suchElement) {
		Element aktElement = sucheElement(suchElement, this.rootElement)
				.getLinkesKind();
		while (hatRechtesKind(aktElement)) {
			aktElement = aktElement.getRechtesKind();
		}
		return aktElement;
	}

	/**
	 * Traversiert "Pre-Order" durch den Baum
	 * 
	 * @return ArrayList mit allen Nutzdaten in Reihenfolge von "Pre-Order"
	 */

	public ArrayList<String> preorderTraversierung() {
		this.resetTravListe();
		this.preorderTraversierung(this.rootElement);
		return traversierungsListe;
	}

	/**
	 * Traversiert "In-Order" durch den Baum
	 * 
	 * @return ArrayList mit allen Nutzdaten in Reihenfolge von "Pre-Order"
	 */

	public ArrayList<String> inorderTraversierung() {
		this.resetTravListe();
		this.inorderTraversierung(rootElement, 1);
		return traversierungsListe;
	}

	/**
	 * Traversiert "Pre-Order" durch den Baum
	 * 
	 * @param aktElement
	 *            das derzeitig fokussierte Element
	 */
	public void preorderTraversierung(Element aktElement) {
		if (aktElement != null) {
			traversierungsListe.add(aktElement.getNutzdaten());
			preorderTraversierung(aktElement.getLinkesKind());
			preorderTraversierung(aktElement.getRechtesKind());
		}
	}

	/**
	 * Traversiert "Post-Order" durch den Baum
	 * 
	 * @return ArrayList mit allen Nutzdaten in Reihenfolge von "Pre-Order"
	 */
	public ArrayList<String> postorderTraversierung() {
		this.resetTravListe();
		this.postorderTraversierung(this.rootElement);
		return traversierungsListe;
	}

	/**
	 * Leert die Traversierungsliste
	 */
	public void resetTravListe() {
		traversierungsListe = new ArrayList<String>();
	}

	/**
	 * Traversiert "Post-Order" durch den Baum
	 * 
	 * @param aktElement
	 *            das derzeitig fokussierte Element
	 */
	public void postorderTraversierung(Element aktElement) {
		if (aktElement != null) {
			postorderTraversierung(aktElement.getLinkesKind());
			postorderTraversierung(aktElement.getRechtesKind());
			traversierungsListe.add(aktElement.getNutzdaten());
		}
	}

	/**
	 * Sucht ein Element aus dem Baum
	 * 
	 * @param suchElement
	 *            zu suchendes Element
	 * @param aktElement
	 *            aktuell fokussiertes Element
	 * @return Element das gefundene Element
	 */
	public Element sucheElement(Element suchElement, Element aktElement) {
		Element gefundenesElement = null;

		if (aktElement != null) {
			if (aktElement.getNutzdaten().equals(suchElement.getNutzdaten())) {
				gefundenesElement = aktElement;
			} else {
				if (suchElement.getNutzdaten().compareTo(
						aktElement.getNutzdaten()) > 0) {
					gefundenesElement = sucheElement(suchElement,
							aktElement.getRechtesKind());
				} else {
					gefundenesElement = sucheElement(suchElement,
							aktElement.getLinkesKind());
				}
			}
		}
		return gefundenesElement;
	}

	/**
	 * Sucht das Parent-Element vom übergebenen Nutzdaten-String
	 * 
	 * @param suchNutzdaten
	 *            der String der im Element steht, dessen Parent gefunden werde
	 *            soll
	 * @return das gefundene Parent-Element
	 */
	public Element findParent(String suchNutzdaten) {
		return findParent(suchNutzdaten, this.rootElement, null);
	}

	/**
	 * Sucht das Parent-Element vom übergebenen Nutzdaten-String
	 * 
	 * @param suchNutzdaten
	 *            der String der im Element steht, dessen Parent gefunden werde
	 *            soll
	 * @param aktElement
	 *            derzeitig fokussiertes Element
	 * @param parent
	 *            das Parent-Element
	 * @return gefundenes Parent-Element
	 */
	public Element findParent(String suchNutzdaten, Element aktElement,
			Element parent) {
		if (aktElement == null) {
			return null;
		} else if (!aktElement.getNutzdaten().equals(suchNutzdaten)) {
			parent = findParent(suchNutzdaten, aktElement.getLinkesKind(),
					aktElement);
			if (parent == null) {
				parent = findParent(suchNutzdaten, aktElement.getRechtesKind(),
						aktElement);
			}
		}
		return parent;
	}

	/**
	 * @return aktuelles Root-Element
	 */
	public Element getRootElement() {
		return rootElement;
	}

	/**
	 * Setzt das Root-Element
	 * 
	 * @param rootElement
	 *            zu setzendes Root-Element
	 */
	public void setRootElement(Element rootElement) {
		this.rootElement = rootElement;
	}

	/**
	 * Prüft, ob das Element ein linkes Kind ist
	 * 
	 * @param suchElement
	 *            zu untersuchendes Element
	 * @return true, wenn es ein linkes Kind ist
	 */
	public boolean isLinkesKind(Element suchElement) {
		System.out.println("IsLinkesKind: " + suchElement.getNutzdaten());
		Element parent = this.findParent(suchElement.getNutzdaten());

		if (parent.getLinkesKind() == null) {
			return false;
		}
		if (parent.getLinkesKind() != null
				&& parent.getLinkesKind().getNutzdaten()
						.equals(suchElement.getNutzdaten()))
			return true;
		else
			return false;
	}

	/**
	 * Prüft, ob das Element ein rechtes Kind ist
	 * 
	 * @param suchElement
	 *            zu untersuchendes Element
	 * @return true, wenn es ein rechtes Kind ist
	 */
	public boolean isRechtesKind(Element suchElement) {
		Element parent = this.findParent(suchElement.getNutzdaten());

		if (parent.getRechtesKind() == null) {
			return false;
		}

		if (parent.getRechtesKind() != null
				&& parent.getRechtesKind().getNutzdaten()
						.equals(suchElement.getNutzdaten()))
			return true;
		else
			return false;
	}

	/**
	 * Prüft ob das Element kein linkes Kind hat und nur ein rechtes
	 * 
	 * @param suchElement
	 *            zu untersuchendes Element
	 * @return true, wenn nur rechtes Kind vorhanden
	 */
	public boolean hatNurRechtesKind(Element suchElement) {
		if (suchElement.getRechtesKind() != null
				&& suchElement.getLinkesKind() == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Prüft ob das Element kein rechtes Kind hat und nur ein linkes
	 * 
	 * @param suchElement
	 *            zu untersuchendes Element
	 * @return true, wenn nur linkes Kind vorhanden
	 */
	public boolean hatNurLinkesKind(Element suchElement) {
		if (suchElement.getLinkesKind() != null
				&& suchElement.getRechtesKind() == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Prüft ob das Element kein Kind hat
	 * 
	 * @param suchElement
	 *            zu untersuchendes Element
	 * @return true, wenn kein kind vorhanden
	 */
	public boolean hatKeineKinder(Element suchElement) {
		if (suchElement.getLinkesKind() == null
				&& suchElement.getRechtesKind() == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Prüft ob das Element ein Kind hat
	 * 
	 * @param suchElement
	 *            suchElement zu untersuchendes Element
	 * @return true, wenn Kinder vorhanden
	 */
	public boolean hatKinder(Element suchElement) {
		if (suchElement.getLinkesKind() != null
				|| suchElement.getRechtesKind() != null)
			return true;
		else
			return false;
	}

	/**
	 * Prüft ob das Element ein rechtes Kind hat
	 * 
	 * @param suchElement
	 *            zu untersuchendes Element
	 * @return true, wenn ein rechtes Kind vorhanden
	 */
	public boolean hatRechtesKind(Element suchElement) {
		if (suchElement.getRechtesKind() != null)
			return true;
		else
			return false;
	}

	/**
	 * Prüft ob das Element ein linkes Kind hat
	 * 
	 * @param suchElement
	 *            zu untersuchendes Element
	 * @return true, wenn ein linkes Kind vorhanden
	 */
	public boolean hatLinkesKind(Element suchElement) {
		if (suchElement.getLinkesKind() != null)
			return true;
		else
			return false;
	}

	/**
	 * Prüft ob das Element zwei Kind hat
	 * 
	 * @param suchElement
	 *            zu untersuchendes Element
	 * @return true, wenn zwei Kinder vorhanden
	 */
	public boolean hatBeideKinder(Element suchElement) {
		if (suchElement.getLinkesKind() != null
				&& suchElement.getRechtesKind() != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gibt das aktuelle Root-Element zurück
	 * 
	 * @return rootElement
	 */
	public Element getRoot() {
		return rootElement;
	}

	/**
	 * Setzt ein Root-Element
	 * 
	 * @param root
	 *            zu setzendes Element
	 */
	public void setRoot(Element root) {
		this.rootElement = root;
	}

	/**
	 * Anzahl der Elemente
	 * 
	 * @return Anzahl der Elemente
	 */
	public int getTotalnodes() {
		return totalnodes;
	}

	/**
	 * Anzahl der Elemente setzen
	 * 
	 * @param totalnodes
	 *            anzahl der Elemente im Baum
	 */
	public void setTotalnodes(int totalnodes) {
		this.totalnodes = totalnodes;
	}

	/**
	 * Maximale Tiefe ermitteln
	 * 
	 * @return maximale Tiefe
	 */
	public int getMaxheight() {
		return maxheight;
	}

	/**
	 * Maximale Tiefe des Baumes setzen
	 * 
	 * @param maxheight
	 *            zu setzende maximale Tiefe
	 */
	public void setMaxheight(int maxheight) {
		this.maxheight = maxheight;
	}

	/**
	 * Startmethode um den Binärbaum zu balancieren
	 */
	public void balanceTree() {
		ArrayList<Element> listOfNodes = new ArrayList<Element>();

		fuelleListeInReihenfolge(this.rootElement, listOfNodes);
		loescheKinder(listOfNodes);

		this.rootElement = null;
		int count = totalnodes;
		totalnodes = 0;

		balanciereBaum(0, count - 1, listOfNodes);
	}

	/**
	 * Balanciert den Baum aus, damit der rechte und linke Zwei je ungefähr
	 * gleich groß sind
	 * 
	 * @param min
	 *            Das kleinste Element
	 * @param max
	 *            Das größte Element
	 * @param list
	 *            Die Liste mit allen Elementen
	 */
	private void balanciereBaum(int min, int max, ArrayList<Element> list) {
		if (min <= max) {
			int middleNode = (int) Math.ceil(((double) min + max) / 2);

			elementHinzufuegen(list.get(middleNode).getNutzdaten());

			balanciereBaum(min, middleNode - 1, list);

			balanciereBaum(middleNode + 1, max, list);
		}
	}

	/**
	 * Füllt eine ArrayList mit allen Elementen Inorder!
	 * 
	 * @param node
	 *            Element welches hinzugefügt wird
	 * @param list
	 *            Liste mit allen Elementen in aufsteigender Reihenfolge
	 */
	private void fuelleListeInReihenfolge(Element node, ArrayList<Element> list) {
		if (node != null) {
			fuelleListeInReihenfolge(node.getLinkesKind(), list);

			list.add(node);

			fuelleListeInReihenfolge(node.getRechtesKind(), list);
		}
	}

	/**
	 * Löscht die Kinder aller Element in der Liste
	 * 
	 * @param list
	 *            eine Liste aus Elementen
	 */
	private void loescheKinder(ArrayList<Element> list) {
		for (Element node : list) {
			node.setLinkesKind(null);
			node.setRechtesKind(null);
		}
	}
}