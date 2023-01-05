package br.com.poker.controle.exceptions;

import java.util.Arrays;
import java.util.List;

public class NegocioException extends Exception {

	private static final long serialVersionUID = 1L;

	private List<String> mensagemErros;
	
	public NegocioException(Exception exception) {
		this.mensagemErros = Arrays.asList(exception.getMessage());
	}

	public NegocioException(List<String> erros) {
		this.mensagemErros = erros;
	}

	public NegocioException(String erro) {
		this.mensagemErros = Arrays.asList(erro);
	}

	public List<String> getMensagemErros() {
		return mensagemErros;
	}
}
