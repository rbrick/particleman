package com.eliotlash.molang.ast;

import java.util.List;

public interface Stmt {

	<R> R accept(Visitor<R> visitor, StmtContext ctx);

	/**
	 * expr;
	 */
	record Expression(Expr expr) implements Stmt {
		public <R> R accept(Visitor<R> visitor, StmtContext ctx) {
			return visitor.visitExpression(this, ctx);
		}
	}

	/**
	 * return expr;
	 */
	record Return(Expr value) implements Stmt {
		public <R> R accept(Visitor<R> visitor, StmtContext ctx) {
			return visitor.visitReturn(this, ctx);
		}
	}

	/**
	 * break;
	 */
	record Break() implements Stmt {
		public <R> R accept(Visitor<R> visitor, StmtContext ctx) {
			return visitor.visitBreak(this, ctx);
		}
	}

	/**
	 * continue;
	 */
	record Continue() implements Stmt {
		public <R> R accept(Visitor<R> visitor, StmtContext ctx) {
			return visitor.visitContinue(this, ctx);
		}
	}

	/**
	 * loop(expr, expr);
	 */
	record Loop(Expr count, Expr expr) implements Stmt {
		public <R> R accept(Visitor<R> visitor, StmtContext ctx) {
			return visitor.visitLoop(this, ctx);
		}
	}

	record If(Expr condition, Expr.Block body, List<Stmt.If> elifs, Expr.Block elseBlock) implements Stmt{
		public <R> R accept(Visitor<R> visitor, StmtContext ctx) {
			return visitor.visitIf(this, ctx);
		}
	}

	interface Visitor<R> {
		default R visit(Stmt stmt, StmtContext stmtContext) {
			return stmt.accept(this, stmtContext);
		}

		R visitExpression(Expression stmt, StmtContext stmtContext);
		R visitReturn(Return stmt, StmtContext stmtContext);
		R visitBreak(Break stmt, StmtContext stmtContext);
		R visitContinue(Continue stmt, StmtContext stmtContext);
		R visitLoop(Loop stmt, StmtContext stmtContext);
		R visitIf(If stmt, StmtContext stmtContext);
	}
}
