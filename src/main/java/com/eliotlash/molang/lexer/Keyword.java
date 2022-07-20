package com.eliotlash.molang.lexer;

import java.util.Locale;

public enum Keyword {
	THIS("this"),
	RETURN("return"),
	LOOP("loop"),
	FOR_EACH("for_each"),
	BREAK("break"),
	CONTINUE("continue"),
	IF("if"),
	ELSE("else"),
	ELSE_IF("elif");

	private final String lexeme;

	Keyword(String s) {
		lexeme = s;
	}

	public boolean matches(String s) {
		return lexeme.equals(s.toLowerCase(Locale.ROOT));
	}
}
