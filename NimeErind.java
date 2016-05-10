
public class NimeErind extends RuntimeException {
	private String teade;

	public NimeErind(String teade) {
		super();
		this.setTeade(teade);
	}

	public String getTeade() {
		return teade;
	}

	public void setTeade(String teade) {
		this.teade = teade;
	}
	
}
