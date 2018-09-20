package dev.top.controller.views;

public class FormulaireView {

	private String matricule;

	private String pseudo;

	private String urlimage;

	// Getters et setters
	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getUrlimage() {
		return urlimage;
	}

	public void setUrlimage(String urlimage) {
		this.urlimage = urlimage;
	}

	// constructeurs
	public FormulaireView(String matricule, String pseudo, String urlimage) {
		super();
		this.matricule = matricule;
		this.pseudo = pseudo;
		this.urlimage = urlimage;
	}

	public FormulaireView() {
		super();
	}

}
