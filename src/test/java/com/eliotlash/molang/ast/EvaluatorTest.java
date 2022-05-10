package com.eliotlash.molang.ast;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;

import com.eliotlash.molang.variables.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.eliotlash.molang.TestBase;

class EvaluatorTest extends TestBase {

	private Evaluator eval;

	@BeforeEach
	void setUp() throws Exception {
		eval = new Evaluator();
	}

	@Test
	void visitAccess() {
		Expr.Access acc = access("temp", "test");
		eval.visitAssignment(new Expr.Assignment(acc, new Expr.Constant(5)));
		assertEquals(5, eval.visitAccess(acc));
	}

	@Test
	void visitAssignment() {
		Expr.Access acc = access("query", "test");
		assertEquals(5, eval.visitAssignment(new Expr.Assignment(acc, new Expr.Constant(5))));
	}

	@Test
	void visitCall() {
		assertEquals(2, eval.visitCall(new Expr.Call(v("math"), "min", List.of(c(2), c(5)))));
	}

	@Test
	void visitConstant() {
		assertEquals(20, eval.visitConstant(c(20)));
	}

	@Test
	void visitGroup() {
		assertEquals(20, eval.visitGroup(paren(c(20))));
	}

	@Test
	void visitVariable() {
		assertEquals(0, eval.visitVariable(new Expr.Variable("query.test")));

		ExecutionContext context = new ExecutionContext(eval);
		eval.setExecutionContext(context);
		context.setVariable("q.test", 5);
		context.setVariable("query.me", 5);
		context.setVariable("hello", 5);
		assertEquals(5, eval.visitVariable(new Expr.Variable("query.test")));
		assertEquals(5, eval.visitVariable(new Expr.Variable("q.test")));
		assertEquals(5, eval.visitVariable(new Expr.Variable("q.me")));
		assertEquals(5, eval.visitVariable(new Expr.Variable("hello")));
	}

	@Test
	void visitNegate() {
		assertEquals(-20, eval.visitNegate(new Expr.Negate(c(20))));
	}

	@Test
	void visitNot() {
		assertEquals(0, eval.visitNot(new Expr.Not(c(20))));
		assertEquals(1, eval.visitNot(new Expr.Not(c(0))));
	}

	@Test
	void visitTernary() {
		assertEquals(10, eval.visitTernary(new Expr.Ternary(c(1), c(10), c(30))));
		assertEquals(30, eval.visitTernary(new Expr.Ternary(c(0), c(10), c(30))));
	}

	public static Stream<BinOpTest> binOpTestProvider() {
		return BinOpTest.provider();
	}

	@ParameterizedTest
	@MethodSource("binOpTestProvider")
	void visitBinOp(BinOpTest args) {
		Double result = new Expr.BinOp(args.op(), c(args.lhs()), c(args.rhs())).accept(eval);
		assertEquals(args.expectedResult(), result, 0.000001);
	}
}
