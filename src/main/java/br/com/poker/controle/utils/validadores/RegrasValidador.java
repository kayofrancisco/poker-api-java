package br.com.poker.controle.utils.validadores;

import br.com.poker.controle.exceptions.NegocioException;

public interface RegrasValidador<T> {
    void validar(T objetoParaValidar) throws NegocioException;
}
