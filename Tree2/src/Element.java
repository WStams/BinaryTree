/**
 * Element-Klasse. Enthält die Daten eines Elementes im Binärbaum. 
 * @author Wayne Stams, Justin Mertmann
 */
class Element {  //standard Binary Tree node
    private String nutzdaten;
    private Element linkesKind;
    private Element rechtesKind;
    private int xpos;
    private int ypos;  
    
    /**
     * Konstruktor um ein neues Element anzulegen
     * @param s Nutzdaten des Elements
     */
    public Element(String s) {
    	this.nutzdaten = s;
    }

    /**
     * Konstruktor um ein Element samt Kinder anzulegen
     * @param s Nutzdaten des Elements
     * @param l linkes Kind des Elements
     * @param r rechtes Kind des Elements
     */
    public Element(String s, Element l, Element r) {
      this.linkesKind = l;
      this.rechtesKind = r;
      this.nutzdaten = s;
    }

	/**
	 * Nutzdaten erhalten
	 * @return nutzdaten - String mit den Daten im Element
	 */
	public String getNutzdaten() {
		return nutzdaten;
	}

	/**
	 * Nutzdaten für dieses Element setzen
	 * @param data - String mit den Nutzdaten
	 */
	public void setData(String data) {
		this.nutzdaten = data;
	}

	/**
	 * Linkes Kind erhalten
	 * @return linkesKind - linkes Kind des Elements
	 */
	public Element getLinkesKind() {
		return linkesKind;
	}

	/**
	 * linkes Kind setzen
	 * @param linkesKind - zu setzendes Element als linkesKind
	 */
	public void setLinkesKind(Element linkesKind) {
		this.linkesKind = linkesKind;
	}

	
	/**
	 * RechtesKind erhalten
	 * @return rechtesKind des aktuellen Elements
	 */
	public Element getRechtesKind() {
		return rechtesKind;
	}

	/**
	 * Rechtes Kind des Elements setzen
	 * @param right - das rechte kind des Elements erhalten
	 */
	public void setRechtesKind(Element right) {
		this.rechtesKind = right;
	}

	/**
	 * X-Position des aktuellen Elements erhalten
	 * @return xpos - die aktuelle X-Position im Baum
	 */
	public int getXpos() {
		return xpos;
	}

	/**
	 * X-Position des Elements setzen
	 * @param xpos - berechnete X-Position setzen
	 */
	public void setXpos(int xpos) {
		this.xpos = xpos;
	}

	
	/**
	 * Y-Position des aktuellen Elements erhalten
	 * @return ypos - die aktuelle Y-Position im Baum
	 */
	public int getYpos() {
		return ypos;
	}

	/**
	 * Y-Position des Elements setzen
	 * @param ypos - berechnete Y-Position setzen
	 */
	public void setYpos(int ypos) {
		this.ypos = ypos;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return nutzdaten;
	}
  }
