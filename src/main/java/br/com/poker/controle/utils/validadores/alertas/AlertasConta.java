package br.com.poker.controle.utils.validadores.alertas;

public abstract class AlertasConta {

	public static String alertaNickNuloOuVazio() {
		return "O nick da conta deve ser informado";
	}
	
	public static String alertaUsuarioNulo() {
		return "O usuário da conta deve ser informado";
	}
	
	public static String alertaPlataformaNula() {
		return "A plataforma da conta deve ser informada";
	}
	
	public static String alertaNickExistente() {
		return "Este nick já está em uso por outro usuário";
	}
}
