package com.eliotlash.molang.ast;

import java.util.List;

public interface Expr {

	<R> R accept(Visitor<R> visitor);

	/**
	 * target.member
	 */
	record Access(Variable target, String member) implements Expr, Assignable {

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visitAccess(this);
		}
	}

	record Struct(Variable target, List<Struct> innerStructs) implements Expr, Assignable {

		@Override
		public <R> R accept(Visitor<R> visitor) {
			//todo implement evaluation
			return null;
		}
	}

	/**
	 * variable = expression
	 */
	record Assignment(Assignable variable, Expr expression) implements Expr {

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visitAssignment(this);
		}
	}

	/**
	 * left op right
	 */
	record BinOp(Operator operator, Expr left, Expr right) implements Expr {

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visitBinOp(this);
		}
	}

	/**
	 * {
	 *     stmt;
	 *     stmt;
	 *     ...
	 * }
	 */
	record Block(List<Stmt> statements) implements Expr {
		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visitBlock(this);
		}
	}

	/**
	 * left op right
	 */
	record Coalesce(Expr value, Expr fallback) implements Expr {

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visitCoalesce(this);
		}
	}

	/**
	 * target.member(arguments)
	 */
	record Call(Variable target, String member, List<Expr> arguments) implements Expr {

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visitCall(this);
		}
	}

	/**
	 * 2.4
	 */
	record Constant(double value) implements Expr {

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visitConstant(this);
		}
	}

	/**
	 * ( expr )
	 */
	record Group(Expr value) implements Expr {

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visitGroup(this);
		}
	}

	/**
	 * -expr
	 */
	record Negate(Expr value) implements Expr {
		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visitNegate(this);
		}
	}

	/**
	 * !expr
	 */
	record Not(Expr value) implements Expr {

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visitNot(this);
		}
	}

	/**
	 * condition ? ifTrue : ifFalse
	 */
	record Ternary(Expr condition, Expr ifTrue, Expr ifFalse) implements Expr {
		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visitTernary(this);
		}
	}

	/**
	 * player.bone -> rotation
	 */
	record SwitchContext(Expr.Access left, Expr right) implements Expr {
		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visitSwitchContext(this);
		}
	}

	/**
	 * some_identifier
	 */
	record Variable(String name) implements Expr {

		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visitVariable(this);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Variable variable = (Variable) o;

			return name != null ? name.equalsIgnoreCase(variable.name) : variable.name == null;
		}

		@Override
		public int hashCode() {
			return name != null ? name.toLowerCase().hashCode() : 0;
		}
	}

	interface Visitor<R> {
		default R visit(Expr node) {
			return node.accept(this);
		}

		R visitAccess(Access expr);
		R visitAssignment(Assignment expr);
		R visitBinOp(BinOp expr);
		R visitBlock(Block expr);
		R visitCall(Call expr);
		R visitCoalesce(Coalesce expr);
		R visitConstant(Constant expr);
		R visitGroup(Group expr);
		R visitNegate(Negate expr);
		R visitNot(Not expr);
		R visitTernary(Ternary expr);
		R visitSwitchContext(SwitchContext expr);
		R visitVariable(Variable expr);
	}
}
