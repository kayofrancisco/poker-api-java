package br.com.poker.controle.utils.validadores.alertas;

public abstract class AlertasPartida {

	public static String alertaValorNulo() {
		return "O valor de ganho ou perda da partida deve ser informado";
	}
	
	public static String alertaQuantidadeMaosNuloOuMenorQueZero() {
		return "O número de mãos da partida deve ser informado";
	}
	
	public static String alertaContaNulo() {
		return "A conta que foi jogada a partida deve ser informada";
	}
	
	public static String alertaLimiteNulo() {
		return "O limite jogado na partida deve ser informado";
	}

	public static String alertaDataCriacaoNulo() {
		return "A data da partida deve ser informada";
	}
}
