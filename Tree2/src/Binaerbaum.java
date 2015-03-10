class Binaerbaum {
	private Element rootElement;
	private int totalnodes = 0; 
	private int maxheight = 0;

	public Binaerbaum() {
		rootElement = null;
	}

	public int treeHeight(Element t) {
		if (t == null)
			return -1;
		else
			return 1 + werteVergleich(treeHeight(t.getLinkesKind()), treeHeight(t.getRechtesKind()));
	}

	public int werteVergleich(int a, int b) {
		if (a > b)
			return a;
		else
			return b;
	}

	public void berechneElementPositionen() {
		int depth = 1;
		inorderTraversierung(rootElement, depth);
	}

	public void inorderTraversierung(Element t, int depth) {
		if (t != null) {
			inorderTraversierung(t.getLinkesKind(), depth + 1);
			t.setXpos(totalnodes++);
			t.setYpos(depth); 
			inorderTraversierung(t.getRechtesKind(), depth + 1);
		}
	}

	public Element insert(Element root, String s) {
		if (root == null) {
			root = new Element(s, null, null);
			return root;
		} else {
			if (s.compareTo((String) (root.getData())) == 0) {
				return root;
			} else if (s.compareTo((String) (root.getData())) < 0)
				root.setLinkesKind(insert(root.getLinkesKind(), s));
			else
				root.setRechtesKind(insert(root.getRechtesKind(), s));
			return root;
		}
	}
	
	public void elementLoeschen(Element loeschElement) {
		Element wegfallendesElement = sucheElement(loeschElement, this.rootElement);
		
		if(hatNurLinkesKind(wegfallendesElement)) {
			this.loescheElementMitNurLinkemKind(wegfallendesElement);
		} else if(hatNurRechtesKind(wegfallendesElement)) {
			this.loescheElementMitNurRechtemKind(wegfallendesElement);
		} else if(hatKeineKinder(wegfallendesElement)) {
			this.loescheElementOhneKind(wegfallendesElement);
		} else if(hatBeideKinder(wegfallendesElement)) {
			this.loescheElementDasZweiKinderHat(wegfallendesElement);
		}
	}
	
	private void loescheElementOhneKind(Element element){
		Element parent = findParent(element.getData());
		
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
		Element parentVomWegfall = findParent(element.getData());
		
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
		Element parentVomWegfall = findParent(element.getData());
		
		if(parentVomWegfall == null) {
			this.rootElement.setLinkesKind(element.getLinkesKind());
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
		Element parent = findParent(element.getData());
		Element parentVomErsatz = findParent(ersatzElement.getData());
		
		System.out.println(parent);
		System.out.println("--");
		System.out.println(parentVomErsatz.getData() + " -- parent Vom Ersatz");
		System.out.println(parentVomErsatz.getLinkesKind().getData() + " -- LeftChild");
		System.out.println(parentVomErsatz.getRechtesKind().getData() + " -- RightChild");
		
		System.out.println("Ersatz: " + ersatzElement.getData());
		if(ersatzElement.getRechtesKind() != null) {
			System.out.println("ErsatzR: " + ersatzElement.getRechtesKind().getData());
		}
		if(ersatzElement.getLinkesKind() != null) {
			System.out.println("ErsatzL: " + ersatzElement.getLinkesKind().getData());
		}
		
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

	/**
	 * 
	 * @param aktElement
	 */
	public void preorderTraversieren(Element aktElement) {
		if (aktElement != null) {
			System.out.println(aktElement);
			preorderTraversieren(aktElement.getLinkesKind());
			preorderTraversieren(aktElement.getRechtesKind());
		}
	}

	/**
	 * 
	 * @param aktElement
	 */
	public void postorderTraversieren(Element aktElement) {
		if (aktElement != null) {
			preorderTraversieren(aktElement.getLinkesKind());
			preorderTraversieren(aktElement.getRechtesKind());
			System.out.println(aktElement);
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
			if (aktElement.getData().equals(suchElement.getData())) {
				gefundenesElement = aktElement;
			} else {
				if (suchElement.getData().compareTo(
						aktElement.getData()) > 0) {
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
		} else if (!aktElement.getData().equals(suchNutzdaten)) {
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

	public Element getLowestRightChild(Element suchElement) {

		return null;

	}

	public boolean isLinkesKind(Element suchElement) {
		System.out.println("IsLinkesKind: " + suchElement.getData());
		Element parent = this.findParent(suchElement.getData());

		if(parent.getLinkesKind() == null) {
			return false;
		}
		if (parent.getLinkesKind() != null && parent.getLinkesKind().getData().equals(suchElement.getData()))
			return true;
		else
			return false;
	}

	public boolean isRechtesKind(Element suchElement) {
		Element parent = this.findParent(suchElement.getData());
		
		if(parent.getRechtesKind() == null) {
			return false;
		}
		
		if (parent.getRechtesKind() != null && parent.getRechtesKind().getData().equals(suchElement.getData()))
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