class Element {  //standard Binary Tree node
    private String nutzdaten;
    private Element linkesKind;
    private Element rechtesKind;
    private int xpos;
    private int ypos;  
    
    public Element(String s) {
    	this.nutzdaten = s;
    }

    Element(String s, Element l, Element r) {
      this.linkesKind = l;
      this.rechtesKind = r;
      this.nutzdaten = s;
    }

	public String getNutzdaten() {
		return nutzdaten;
	}

	public void setData(String data) {
		this.nutzdaten = data;
	}

	public Element getLinkesKind() {
		return linkesKind;
	}

	public void setLinkesKind(Element linkesKind) {
		this.linkesKind = linkesKind;
	}

	public Element getRechtesKind() {
		return rechtesKind;
	}

	public void setRechtesKind(Element right) {
		this.rechtesKind = right;
	}

	public int getXpos() {
		return xpos;
	}

	public void setXpos(int xpos) {
		this.xpos = xpos;
	}

	public int getYpos() {
		return ypos;
	}

	public void setYpos(int ypos) {
		this.ypos = ypos;
	}
  }
