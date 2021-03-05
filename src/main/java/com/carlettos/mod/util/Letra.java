package com.carlettos.mod.util;

public enum Letra {
	AMAN("a", "1234/3/12/23"),
	ERSA("e", "2/4/14/23"),
	IOR("i", "2/14/1/23"),
	OSHI("o", "2/3/13/234"),
	UNK("u", "2/2/1234/2"),
	KEL("k", "134/14/123/1"),
	RUDU("r", "1234/14/34/3"),
	LIR("l", "1234/2/24/34"),
	TRAK("t", "234/4/123/13"),
	DUR("d", "1234/3/4/123"),
	PRUM("p", "234/124/1/1"),
	HUL("h", "234/4/14/23"),
	SILA("s", "234/4/3/123"),
	NAK("n", "134/13/2/23"),
	MIH("m", "134/13/14/234"),
	FEN("f", "134/23/4/34");
	
	public final String fonema;
	public final String representacion;
	private Letra(String fonema, String representacion) {
		this.fonema = fonema;
		this.representacion = representacion;
	}
	
	public static Letra getLetraFromFonema(String fonema) {
		for (Letra letra : Letra.values()) {
			if(letra.fonema.equals(fonema.toLowerCase())) {
				return letra;
			}
		}
		return null;
	}
}
