package myPackage;

public class Receptek {
	
	private String etelneve;
	private String jelleg;
	private int elk_ido;
	private String utolso_modositas;
	
	public Receptek(String etelneve, String jelleg, int elk_ido, String utolso_modositas) {
		super();
		this.etelneve = etelneve;
		this.jelleg = jelleg;
		this.elk_ido = elk_ido;
		this.utolso_modositas = utolso_modositas;
	}

	public String getEtelneve() {
		return etelneve;
	}

	public String getJelleg() {
		return jelleg;
	}

	public int getElk_ido() {
		return elk_ido;
	}

	public String getUtolso_modositas() {
		return utolso_modositas;
	}
	
	
	
	

}
