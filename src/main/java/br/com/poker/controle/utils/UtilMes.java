package br.com.poker.controle.utils;

public class UtilMes {
	private static final String JANEIRO = "JAN";
	private static final String FEVEIRO = "FEV";
	private static final String MARCO = "MAR";
	private static final String ABRIL = "ABR";
	private static final String MAIO = "MAI";
	private static final String JUNHO = "JUN";
	private static final String JULHO = "JUL";
	private static final String AGOSTO = "AGO";
	private static final String SETEMBRO = "SET";
	private static final String OUTUBURO = "OUT";
	private static final String NOVEMBRO = "NOV";
	private static final String DEZEMBRO = "DEZ";

	public static String retornaMesPorNumero(Integer numeroMes) {
		switch (numeroMes) {
		case 1:
			return JANEIRO;
		case 2:
			return FEVEIRO;
		case 3:
			return MARCO;
		case 4:
			return ABRIL;
		case 5:
			return MAIO;
		case 6:
			return JUNHO;
		case 7:
			return JULHO;
		case 8:
			return AGOSTO;
		case 9:
			return SETEMBRO;
		case 10:
			return OUTUBURO;
		case 11:
			return NOVEMBRO;
		case 12:
			return DEZEMBRO;
		default:
			return JANEIRO;
		}
	}

}
