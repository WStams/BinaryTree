import java.util.ArrayList;

class Binaerbaum {
	private Element rootElement;
	private int totalnodes = 0; 
	private int maxheight = 0;
	private ArrayList<String> traversierungsListe = new ArrayList<String>();
	
	
	public Binaerbaum() {
		rootElement = null;
	}

	public int berechneHoehe(Element t) {
		if (t == null)
			return -1;
		else
			return 1 + werteVergleich(berechneHoehe(t.getLinkesKind()), berechneHoehe(t.getRechtesKind()));
	}

	public int werteVergleich(int a, int b) {
		if (a > b)
			return a;
		else
			return b;
	}

	public void berechneElementPositionen() {
		int depth = 1;
		totalnodes = 0;
		inorderTraversierung(rootElement, depth);
	}

	public void inorderTraversierung(Element t, int depth) {
		if (t != null) {
			inorderTraversierung(t.getLinkesKind(), depth + 1);
			t.setXpos(totalnodes++);
			traversierungsListe.add(t.getNutzdaten());
			t.setYpos(depth); 
			inorderTraversierung(t.getRechtesKind(), depth + 1);
		}
	}

	public Element elementHinzufuegen(Element root, String s) {
		if (root == null) {
			root = new Element(s, null, null);
			return root;
		} else {
			if (s.compareTo((String) (root.getNutzdaten())) == 0) {
				return root;
			} else if (s.compareTo((String) (root.getNutzdaten())) < 0)
				root.setLinkesKind(elementHinzufuegen(root.getLinkesKind(), s));
			else
				root.setRechtesKind(elementHinzufuegen(root.getRechtesKind(), s));
			return root;
		}
	}
	
	public boolean elementLoeschen(Element loeschElement) {
		Element wegfallendesElement = sucheElement(loeschElement, this.rootElement);
		
		if(wegfallendesElement == null) {
			return false;
		} else {
			if(hatNurLinkesKind(wegfallendesElement)) {
				this.loescheElementMitNurLinkemKind(wegfallendesElement);
			} else if(hatNurRechtesKind(wegfallendesElement)) {
				this.loescheElementMitNurRechtemKind(wegfallendesElement);
			} else if(hatKeineKinder(wegfallendesElement)) {
				this.loescheElementOhneKind(wegfallendesElement);
			} else if(hatBeideKinder(wegfallendesElement)) {
				this.loescheElementDasZweiKinderHat(wegfallendesElement);
			}
			return true;
		}
	}
	
	private void loescheElementOhneKind(Element element){
		Element parent = findParent(element.getNutzdaten());
		
		if(parent == null) {
			this.rootElement = null;
		} else {
			if(isLinkesKind(element)) {
				parent.setLinkesKind(null);
			} else if(isRechtesKind(element)) {
				parent.setRechtesKind(null);
			}
		}
	}
	
	private void loescheElementMitNurRechtemKind(Element element) { 
		Element parentVomWegfall = findParent(element.getNutzdaten());
		
		if(parentVomWegfall == null) {
			this.rootElement.setRechtesKind(element.getRechtesKind());
		} else {
			if(isLinkesKind(element)) {
				parentVomWegfall.setLinkesKind(element.getRechtesKind());
			} else {
				parentVomWegfall.setRechtesKind(element.getRechtesKind());
			}
		}
	}
	
	private void loescheElementMitNurLinkemKind(Element element) { 
		Element parentVomWegfall = findParent(element.getNutzdaten());
		
		
		if(parentVomWegfall == null) {
			this.rootElement = this.rootElement.getLinkesKind();
		} else {
			if(isLinkesKind(element)) {
				parentVomWegfall.setLinkesKind(element.getLinkesKind());
			} else {
				parentVomWegfall.setRechtesKind(element.getLinkesKind());
			}	 
		}	
	}
	
	private void loescheElementDasZweiKinderHat(Element element) {
		Element ersatzElement = kleinstesElementAusDemRechtenZweig(element);
		Element parent = findParent(element.getNutzdaten());
		Element parentVomErsatz = findParent(ersatzElement.getNutzdaten());
		
		if(parent == null) {
			if(isLinkesKind(ersatzElement)) {
				parentVomErsatz.setLinkesKind(null);
			} else {
				parentVomErsatz.setRechtesKind(null);
			}
			
			if(element.getRechtesKind() != null) {
				ersatzElement.setRechtesKind(element.getRechtesKind());
			}
			if(element.getLinkesKind() != null) {
				ersatzElement.setLinkesKind(element.getLinkesKind());
			}
			
			element.setLinkesKind(null);
			element.setRechtesKind(null);
			
			this.rootElement = ersatzElement;
		} else {
			if(isLinkesKind(ersatzElement)) {
				parentVomErsatz.setLinkesKind(null);
			} else {
				parentVomErsatz.setRechtesKind(null);
			}
			

			
			if(isLinkesKind(element)) {
				parent.setLinkesKind(ersatzElement);
			} else {
				parent.setRechtesKind(ersatzElement);
			}
			
			if(element.getRechtesKind() != null) {
				ersatzElement.setRechtesKind(element.getRechtesKind());
			}
			if(element.getLinkesKind() != null) {
				ersatzElement.setLinkesKind(element.getLinkesKind());
			}
			
			element.setLinkesKind(null);
			element.setRechtesKind(null);
			
		}
		
	}
	
	
	
	
	

	/**
	 * 
	 * @param suchElement
	 */
	public Element kleinstesElementAusDemRechtenZweig(Element suchElement) {
		Element aktElement = sucheElement(suchElement, this.rootElement).getRechtesKind(); 
		while (hatLinkesKind(aktElement)) {
			aktElement = aktElement.getLinkesKind();
		}
		return aktElement;
	}
	
	public Element groesstesElementAusDemLinkenZweig(Element suchElement) {
		Element aktElement = sucheElement(suchElement, this.rootElement).getLinkesKind();
		while (hatRechtesKind(aktElement)) {
			aktElement = aktElement.getRechtesKind();
		}
		return aktElement;
	}

	public ArrayList<String> preorderTraversierung() {
		this.resetTravListe();
		this.preorderTraversierung(this.rootElement);
		return traversierungsListe;
	}
	
	public ArrayList<String> inorderTraversierung() {
		this.resetTravListe();
		this.inorderTraversierung(rootElement, 1);
		return traversierungsListe;
	}
	
	/**
	 * 
	 * @param aktElement
	 */
	public void preorderTraversierung(Element aktElement) {
		if (aktElement != null) {
			traversierungsListe.add(aktElement.getNutzdaten());
			preorderTraversierung(aktElement.getLinkesKind());
			preorderTraversierung(aktElement.getRechtesKind());
		}
	}

	public ArrayList<String> postorderTraversierung() {
		this.resetTravListe();
		this.postorderTraversierung(this.rootElement);
		return traversierungsListe;
	}
	
	public void resetTravListe() {
		traversierungsListe = new ArrayList<String>();
	}
	
	/**
	 * 
	 * @param aktElement
	 */
	public void postorderTraversierung(Element aktElement) {
		if (aktElement != null) {
			postorderTraversierung(aktElement.getLinkesKind());
			postorderTraversierung(aktElement.getRechtesKind());
			traversierungsListe.add(aktElement.getNutzdaten());
		}
	}

	/**
	 * 
	 * @param suchElement
	 * @param aktElement
	 * @return
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
	 * 
	 * @param x
	 * @return
	 */
	public Element findParent(String x) {
		return findParent(x, this.rootElement, null);
	}

	/**
	 * 
	 * @param suchNutzdaten
	 * @param aktElement
	 * @param parent
	 * @return
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
	 * 
	 * @return
	 */
	public Element getRootElement() {
		return rootElement;
	}

	/**
	 * 
	 * @param rootElement
	 */
	public void setRootElement(Element rootElement) {
		this.rootElement = rootElement;
	}

	public boolean isLinkesKind(Element suchElement) {
		System.out.println("IsLinkesKind: " + suchElement.getNutzdaten());
		Element parent = this.findParent(suchElement.getNutzdaten());

		if(parent.getLinkesKind() == null) {
			return false;
		}
		if (parent.getLinkesKind() != null && parent.getLinkesKind().getNutzdaten().equals(suchElement.getNutzdaten()))
			return true;
		else
			return false;
	}

	public boolean isRechtesKind(Element suchElement) {
		Element parent = this.findParent(suchElement.getNutzdaten());
		
		if(parent.getRechtesKind() == null) {
			return false;
		}
		
		if (parent.getRechtesKind() != null && parent.getRechtesKind().getNutzdaten().equals(suchElement.getNutzdaten()))
			return true;
		else
			return false;
	}

	public boolean hatNurRechtesKind(Element suchElement) {
		if (suchElement.getRechtesKind() != null
				&& suchElement.getLinkesKind() == null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hatNurLinkesKind(Element suchElement) {
		if (suchElement.getLinkesKind() != null
				&& suchElement.getRechtesKind() == null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hatKeineKinder(Element suchElement) {
		if (suchElement.getLinkesKind() == null
				&& suchElement.getRechtesKind() == null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hatKinder(Element suchElement) {
		if (suchElement.getLinkesKind() != null
				|| suchElement.getRechtesKind() != null)
			return true;
		else
			return false;
	}

	public boolean hatRechtesKind(Element suchElement) {
		if (suchElement.getRechtesKind() != null)
			return true;
		else
			return false;
	}

	public boolean hatLinkesKind(Element suchElement) {
		if (suchElement.getLinkesKind() != null)
			return true;
		else
			return false;
	}

	public boolean hatBeideKinder(Element suchElement) {
		if (suchElement.getLinkesKind() != null
				&& suchElement.getRechtesKind() != null) {
			return true;
		} else {
			return false;
		}
	}

	public Element getRoot() {
		return rootElement;
	}

	public void setRoot(Element root) {
		this.rootElement = root;
	}

	public int getTotalnodes() {
		return totalnodes;
	}

	public void setTotalnodes(int totalnodes) {
		this.totalnodes = totalnodes;
	}

	public int getMaxheight() {
		return maxheight;
	}

	public void setMaxheight(int maxheight) {
		this.maxheight = maxheight;
	}
}