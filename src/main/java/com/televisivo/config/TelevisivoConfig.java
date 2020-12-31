package com.televisivo.config;

import javax.servlet.http.HttpServletRequest;

import com.televisivo.model.Usuario;
import com.televisivo.security.UsuarioSistema;

import org.springframework.security.core.context.SecurityContextHolder;

public class TelevisivoConfig {

	private TelevisivoConfig() {
		throw new IllegalStateException("Configuração de Televisivo");
	}

	public static final int INITIAL_PAGE = 0;
	public static final int INITIAL_PAGE_SIZE = 10;
	public static final int[] PAGE_SIZES = { 5, 10, 15, 20 };
	public static final Integer  LISTA_VAZIA = -1;
	public static final String NAO_DELETADO = "registro_deletado = false";
	public static final String INCLUSAO = "INSERT";
	public static final String ALTERACAO = "UPDATE";
	public static final String EXCLUSAO = "DELETE";
	public static final int TOKEN_VALID = 1;
	public static final int TOKEN_EXPIRED = 2;
	public static final int TOKEN_INVALID = 0;

	public static Usuario pegarUsuario() {
		UsuarioSistema usuarioSistema = (UsuarioSistema) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usuarioSistema.getUsuario();
	}

	public static String getAppUrl(HttpServletRequest request) {
		return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	}
}