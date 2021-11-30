package br.com.poker.controle.utils.validadores.alertas;

public abstract class AlertasUsuario {

	public static String alertaNomeNuloOuVazio() {
		return "O nome do usuário deve ser informado";
	}
	
	public static String alertaEmailNuloOuVazio() {
		return "O email do usuário deve ser informado";
	}
	
	public static String alertaCpfNuloOuVazio() {
		return "O cpf do usuário deve ser informado";
	}
	
	public static String alertaSenhaNuloOuVazio() {
		return "A senha do usuário deve ser informada";
	}
	
	public static String alertaUsuarioSemPerfil() {
		return "O perfil do usuário deve ser informado";
	}
	
	public static String alertaCpfInvalido() {
		return "Um cpf válido deve ser informado";
	}

	public static String alertaEmailInvalido() {
		return "Um email válido deve ser informado";
	}
	
	public static String alertaUsuarioExistenteComCpf() {
		return "Já existe usuário cadastrado com este cpf";
	}
	
	public static String alertaUsuarioExistenteComEmail() {
		return "Já existe usuário cadastrado com este email";
	}
}
