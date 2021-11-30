package br.com.poker.controle.utils.validadores;

import java.util.ArrayList;
import java.util.List;

import br.com.poker.controle.exceptions.NegocioException;

public class Validador<T> {
	protected List<RegrasValidador<T>> regras;
	protected T objetoParaValidar;
	protected List<String> erros;

	public Validador(T objetoParaValidar) {
		regras = new ArrayList<>();
		this.setObjetoParaValidar(objetoParaValidar);
	}

	public Validador(T objetoParaValidar, List<RegrasValidador<T>> regras) {
		this.objetoParaValidar = objetoParaValidar;
		this.regras = new ArrayList<>();
		for (RegrasValidador<T> regra : regras) {
			adicionarRegra(regra);
		}
		erros = new ArrayList<>();
	}

	public void adicionarRegra(RegrasValidador<T> regra) {
		if (regra == null) {
			throw new NullPointerException("A regra não pode ser null");
		}
		regras.add(regra);
	}

	/**
	 * Valida o objeto do Validador com as regras atribuidas.
	 * 
	 * @return true se o objeto for válido.
	 */
	public boolean validar() {
		erros.clear();

		if (objetoParaValidar == null) {
			erros.add("O objeto para validar não pode ser null");
			return false;
		}

		try {
			for (RegrasValidador<T> regra : regras) {
				regra.validar(objetoParaValidar);
			}
		} catch (NegocioException e) {
			if (e.getMensagemErros() != null && e.getMensagemErros().size() > 0) {
				this.erros = e.getMensagemErros();
			} else {
				erros.add(e.getMessage());
			}
			return false;
		}

		return true;
	}

	public List<String> getErros() {
		return this.erros;
	}

	public T getObjetoParaValidar() {
		return this.objetoParaValidar;
	}

	public void setObjetoParaValidar(T objeto) {
		this.objetoParaValidar = objeto;
		erros = new ArrayList<>();
	}
}
