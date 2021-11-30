package br.com.poker.controle.models.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentDTO<T> {
	private int code;
	private String status;
	private List<String> messages;
	private T result;

	public ContentDTO() {
	}

	public ContentDTO(T t, HttpStatus httpStatus) {
		this.result = t;
		this.code = httpStatus.value();
		this.status = httpStatus.getReasonPhrase();
	}

	public ContentDTO(T t, HttpStatus httpStatus, List<String> messages) {
		this.result = t;
		this.code = httpStatus.value();
		this.status = httpStatus.getReasonPhrase();
		this.messages = messages;
	}
	
	public ContentDTO (T t, HttpStatus httpStatus, String message) {
        this.result = t;
        this.code = httpStatus.value();
        this.status = httpStatus.getReasonPhrase();
        this.messages = new ArrayList<>();
        this.messages.add(message);
    }
}
