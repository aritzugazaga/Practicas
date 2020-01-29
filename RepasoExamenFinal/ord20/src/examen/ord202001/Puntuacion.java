package examen.ord202001;

public class Puntuacion implements Comparable<Puntuacion> {
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

	@Override
	public int compareTo(Puntuacion o) {
		if (this.getP() > o.getP()) {
			return -1;
		} else if (this.getP() < o.getP()) {
			return 1;
		} else {
			if (this.getT() > o.getT()) {
				return -1;
			} else if (this.getT() > o.getT()) {
				return 1;
			} else {
				return this.getNick().compareTo(o.getNick());
			}
		}
	}

}
