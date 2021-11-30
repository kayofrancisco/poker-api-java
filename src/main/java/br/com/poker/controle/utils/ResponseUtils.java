package br.com.poker.controle.utils;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.dto.ContentDTO;

public class ResponseUtils {

	public static <T> ResponseEntity<ContentDTO<T>> sucesso() {
		ContentDTO<T> dto = new ContentDTO<T>(null, HttpStatus.OK);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	public static <T> ResponseEntity<ContentDTO<T>> sucesso(T conteudo) {
		ContentDTO<T> dto = new ContentDTO<T>(conteudo, HttpStatus.OK);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	public static <T> ResponseEntity<ContentDTO<T>> sucessoCriacao(T conteudo) {
		ContentDTO<T> dto = new ContentDTO<T>(conteudo, HttpStatus.CREATED);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	public static <T> ResponseEntity<ContentDTO<T>> falha(String mensagem, HttpStatus status) {
		ContentDTO<T> dto = new ContentDTO<T>(null, status, mensagem);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	public static <T> ResponseEntity<ContentDTO<T>> falha(List<String> mensagens, HttpStatus status) {
		ContentDTO<T> dto = new ContentDTO<T>(null, status, mensagens);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	public static <T> ResponseEntity<ContentDTO<T>> falha(Exception e) {
		if (e instanceof NegocioException) {
			NegocioException negocioException = (NegocioException) e;
			return ResponseUtils.falha(negocioException.getMensagemErros(), HttpStatus.BAD_REQUEST);
		}

		return ResponseUtils.falha("Erro interno. Por favor, tente novamente mais tarde (#1)",
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
