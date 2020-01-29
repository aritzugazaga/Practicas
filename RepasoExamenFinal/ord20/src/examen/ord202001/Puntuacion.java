package examen.ord202001;

public class Puntuacion {
	private String nick;
	private double t;
	private int p;
	
	public Puntuacion(String nick, double t, int p) {
		super();
		this.nick = nick;
		this.t = t;
		this.p = p;
	}
	
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public double getT() {
		return t;
	}
	public void setT(float t) {
		this.t = t;
	}
	public int getP() {
		return p;
	}
	public void setP(int p) {
		this.p = p;
	}

	@Override
	public String toString() {
		return "Puntuacion [nick=" + nick + ", t=" + t + ", p=" + p + "]";
	}

}
