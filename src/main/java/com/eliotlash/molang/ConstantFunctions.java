package com.eliotlash.molang;

import com.eliotlash.molang.ast.Expr;
import com.eliotlash.molang.functions.FunctionDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * This holds a list of function that can be compiled without needing a context.
 * These functions, if only passed constants, can be compiled ahead of time into a constant.
 * <p>
 * You are able to add your own functions into this list.
 *
 * @author FX
 */
public class ConstantFunctions {

    private static final List<FunctionDefinition> CONSTANT_FUNCTIONS = new ArrayList<>();

    public static boolean isConstant(Expr.Call expr) {
        FunctionDefinition functionDefinition = new FunctionDefinition(expr.target(), expr.member());
        return isFunctionDefinitionConstant(functionDefinition) && areArgumentsConstant(expr.arguments());
    }

    public static boolean isFunctionDefinitionConstant(FunctionDefinition functionDefinition) {
        return CONSTANT_FUNCTIONS.contains(functionDefinition);
    }

    public static boolean areArgumentsConstant(List<Expr> arguments) {
        for (Expr expr : arguments) {
            if (!(expr instanceof Expr.Constant)) {
                return false;
            }
        }
        return true;
    }

    public static void addConstantFunction(FunctionDefinition functionDefinition) {
        CONSTANT_FUNCTIONS.add(functionDefinition);
    }
}
