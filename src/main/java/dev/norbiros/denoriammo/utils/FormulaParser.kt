package dev.norbiros.denoriammo.utils

import bsh.EvalError
import bsh.Interpreter

// Thanks to https://gitlab.com/phoenix-dvpmt/mythiclib/-/blob/master/mythiclib-plugin/src/main/java/io/lumine/mythic/lib/comp/formula/FormulaParser.java
class FormulaParser {
    private val interpreter: Interpreter = Interpreter()
    private val mathFunctions: List<String> = mutableListOf(
        "pow", "sqrt", "sin", "cos", "tan", "asin", "acos",
        "atan", "atan2", "exp", "log", "random", "abs", "max", "min"
    )

    init {
        try {
            interpreter.eval("import java.lang.Math;")
        } catch (error: EvalError) {
            throw RuntimeException(error)
        }
    }

    @Throws(EvalError::class)
    fun eval(str: String): Double {
        var expr = str
        // Parse random(expr1,expr2) to Math.random() * (expr2 - expr1) + expr1
        expr = expr.replace("random\\((.*?),(.*?)\\)".toRegex(), "random() * ($2 - $1) + ($1)")
        for (function in mathFunctions) expr = expr.replace("$function(", "Math.$function(")
        return interpreter.eval(expr).toString().toDoubleOrNull() ?: 0.0
    }
}

