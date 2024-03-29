package br.com.poker.controle.utils;

import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.Base64;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.stella.validation.CPFValidator;

public class Utils {
	public static Boolean emailValido(String email) {
		boolean isEmailIdValid = false;
		if (email != null && email.length() > 0) {
			String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			if (matcher.matches()) {
				isEmailIdValid = true;
			}
		}
		return isEmailIdValid;
	}

	public static Boolean cpfValido(String cpf) {
		CPFValidator validadorCpf = new CPFValidator();

		try {
			validadorCpf.assertValid(cpf);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String encodeSenha(String senha) throws NoSuchAlgorithmException {
		byte[] bytes = Base64.getEncoder().encode(senha.getBytes());
		return new String (bytes);
	}

	public static String decodeSenha(String senha) throws NoSuchAlgorithmException {
		byte[] bytes = Base64.getDecoder().decode(senha);
		
		return new String(bytes);
	}

	public static String bytesToHex(byte[] hash) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}

	public static Boolean isNuloOuVazio(String string) {
		return string == null;
//		return string == null || string.isBlank();
	}

	public static String removerTodosOsCaracteresNaoNumericos(String s) {
		if (s == null) {
			return null;
		}
		return s.replaceAll("[^\\d.]", "");
	}

	public static Optional<String> sanitizar(String s) {
		if (s == null) {
			return Optional.empty();
		}
		return Optional.of(removerTodosOsCaracteresNaoNumericos(removerCaracteresEspeciais(s)).replaceAll("[.]", ""));
	}

	public static String removerCaracteresEspeciais(String string) {
		string = Normalizer.normalize(string, Normalizer.Form.NFD);
		string = string.replaceAll("[^\\p{ASCII}]", "");
		return string;
	}
}
