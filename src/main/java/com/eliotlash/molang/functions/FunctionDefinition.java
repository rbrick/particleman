package com.eliotlash.molang.functions;

import com.eliotlash.molang.ast.Expr;

public record FunctionDefinition(Expr.Variable target, String member) {

}
