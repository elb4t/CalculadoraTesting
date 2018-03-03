package es.elb4t.calculadoratesting


/**
 * Created by eloy on 28/2/18.
 */
class MathExpression : Expression {
    val ADDITION = "+"
    val SUBTRACTION = "-"
    val MULTIPLICATION = "x"
    val DIVISION = "/"
    val EXPONENTIATION = "^"
    val SQUARE_ROOT = "r"
    val SQUARE_ROOT_SCREEN = "sqrt"
    val FACTORIAL = "f"
    val FACTORIAL_SCREEN = "fact"

    override fun read(expression: String): String {
        return expression.replace(SQUARE_ROOT_SCREEN, SQUARE_ROOT)
                .replace(FACTORIAL_SCREEN, FACTORIAL).replace("\\s".toRegex(), "")
    }

    override fun write(expression: String): String {
        return expression.replace("(?<=[-fr+x/^)])|(?=[-fr+x/^(])".toRegex(), "$0 ")
                .replace(SQUARE_ROOT, SQUARE_ROOT_SCREEN)
                .replace(FACTORIAL, FACTORIAL_SCREEN)
    }

    @Throws(ExpressionException::class)
    override fun addSymbol(expression: String, symbol: String): String {
        throwsIfSymbolIsInvalid(symbol)
        return write(read(expression) + symbol)
    }

    override fun removeSymbol(expression: String): String {
        val expression = read(expression)
        val START_INDEX = 0
        val END_INDEX = expression.length - 1
        return write(expression.substring(START_INDEX, END_INDEX))
    }

    override fun replaceSymbol(expression: String,
                               symbol: String): String {
        var expression = expression
        expression = removeSymbol(expression)
        return write(expression + symbol)
    }

    override fun tokenize(expression: String): Array<String> {
        return expression.split("(?<=[fr+x/^])|(?=[-fr+x/^])".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }

    @Throws(ExpressionException::class)
    private fun throwsIfSymbolIsInvalid(expression: String) {
        for (symbol in expression.toCharArray()) {
            if (isSymbolInvalid(symbol.toString())) {
                throw ExpressionException(
                        String.format("symbol %s is invalid", symbol))
            }
        }
    }

    private fun isSymbolInvalid(symbol: String): Boolean {
        return !symbol.matches("([0-9]|[-+x/]|[.]|[()^fr])".toRegex())
    }
}