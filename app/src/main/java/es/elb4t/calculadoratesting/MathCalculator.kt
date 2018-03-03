package es.elb4t.calculadoratesting

import android.support.annotation.VisibleForTesting
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols


/**
 * Created by eloy on 28/2/18.
 */
class MathCalculator() {
    companion object {
        val ADDITION = "+"
        val SUBTRACTION = "-"
        val MULTIPLICATION = "x"
        val DIVISION = "/"
        val EXPONENTIATION = "^"
        val SQUARE_ROOT = "r"
        val FACTORIAL = "f"
        val SQUARE_ROOT_SCREEN = "sqrt"
        val FACTORIAL_SCREEN = "fact"
        val PARENTHESIS_START = "("
        val PARENTHESIS_END = ")"
        val DOT = "."
        val NONE = "none"
        val EMPTY_STRING = ""
    }

    var expression: Expression? = null
    var operation: MathOperation? = null

    fun MathCalculator(expression: Expression, operation: MathOperation) {
        this.expression = expression
        this.operation = operation
    }
    constructor(expression: Expression, operation: MathOperation?) : this() {
        this.expression = expression
        this.operation = operation
    }

    fun addSymbol(to: String, symbol: String): String {
        var symbol = symbol
        if (isAnOperator(symbol)) {
            symbol = if (isAnUnaryOperator(symbol))
                symbol + MathCalculator.PARENTHESIS_START
            else
                symbol
            return if (endsWithOperator(to)) {
                expression!!.replaceSymbol(to, symbol)
            } else {
                expression!!.addSymbol(to, symbol)
            }
        }
        return expression!!.addSymbol(to, symbol)
    }

    fun removeSymbol(from: String): String {
        return expression!!.removeSymbol(from)
    }

    @Throws(OperationException::class, ExpressionException::class)
    fun calculate(from: String): String {
        var from = from
        from = expression!!.read(from)
        while (containsParenthesis(from)) {
            val parenthesis = getParenthesisExpression(from)
            from = replaceParenthesis(from, resolve(parenthesis))
        }
        return resolve(from)
    }

    @VisibleForTesting
    fun containsParenthesis(expression: String): Boolean {
        return expression!!.contains(PARENTHESIS_START)
    }

    @VisibleForTesting
    fun getParenthesisExpression(from: String): String {
        return removeParenthesis(getRightmostParenthesis(from))
    }

    @VisibleForTesting
    fun getRightmostParenthesis(from: String): String {
        val START_INDEX = getParenthesisStartIndex(from)
        val END_INDEX = getParenthesisEndIndex(from)
        return from.substring(START_INDEX, END_INDEX)
    }

    private fun getParenthesisStartIndex(from: String): Int {
        return from.lastIndexOf(PARENTHESIS_START)
    }

    private fun getParenthesisEndIndex(from: String): Int {
        var index = from.indexOf(PARENTHESIS_END, getParenthesisStartIndex(from))
        if (index == -1) {
            return from.length
        }
        return ++index
    }

    private fun removeParenthesis(from: String): String {
        return from.replace(PARENTHESIS_START, EMPTY_STRING)
                .replace(PARENTHESIS_END, EMPTY_STRING).trim()
    }

    private fun replaceParenthesis(from: String, with: String): String {
        var with = with
        with = addOperators(from, with)
        return StringBuilder(from)
                .replace(getParenthesisStartIndex(from),
                        getParenthesisEndIndex(from), with)
                .toString()
    }

    private fun addOperators(expression: String, to: String): String {
        var to = to
        to = addOperatorBeforeParenthesis(expression, to)
        to = addOperatorAfterParenthesis(expression, to)
        return to
    }

    private fun addOperatorBeforeParenthesis(from: String, to: String): String {
        try {
            val previousCharacter = from[getParenthesisStartIndex(from) - 1].toString()
            if (!isAnOperator(previousCharacter) && previousCharacter != PARENTHESIS_START)
                return MULTIPLICATION + to
        } catch (exception: IndexOutOfBoundsException) {
            return to
        }

        return to
    }

    private fun addOperatorAfterParenthesis(from: String, to: String): String {
        try {
            val nextCharacter: String = from[getParenthesisEndIndex(from)].toString()
            if (!isAnOperator(nextCharacter) && nextCharacter != PARENTHESIS_END)
            return to + MULTIPLICATION
        } catch (e: IndexOutOfBoundsException) {
            return to
        }
        return to
    }

    @Throws(OperationException::class, ExpressionException::class)
    private fun resolve(from: String): String {
        if (from.isEmpty()) return ""
        var result = 0.0
        var unaryOperator = NONE
        var binaryOperator = NONE
        var symbols: Array<String> = expression!!.tokenize(from)
        for (symbol in symbols) {
            if (symbol.isEmpty()) continue
            if (isAnUnaryOperator(symbol)) {
                unaryOperator = symbol
                if (binaryOperator.equals(NONE) && result != 0.0)
                    binaryOperator = MULTIPLICATION
            } else if (isABinaryOperator(symbol)) {
                binaryOperator = symbol
            } else {
                if (unaryOperator.equals(NONE) && binaryOperator.equals(NONE))
                    result = operation!!.addition(result, symbol.toDouble())
                if (unaryOperator != NONE) {
                    var symbol = calculateUnaryOperation(symbol.toDouble(), unaryOperator).toString()
                    result = if (binaryOperator == NONE) symbol.toDouble() else result
                    unaryOperator = NONE
                }

                if (binaryOperator != NONE) {
                    result = calculateBinaryOperation(result, symbol.toDouble(), binaryOperator)
                    binaryOperator = NONE
                }
            }
        }
        return getFormattedNumber(result)
    }

    private fun isAnOperator(symbol: String): Boolean {
        return isABinaryOperator(symbol) || isAnUnaryOperator(symbol)
    }

    private fun isABinaryOperator(symbol: String): Boolean {
        return symbol == ADDITION ||
                symbol == SUBTRACTION || symbol == MULTIPLICATION || symbol == DIVISION || symbol == EXPONENTIATION
    }

    private fun isAnUnaryOperator(symbol: String): Boolean {
        return symbol == SQUARE_ROOT || symbol == FACTORIAL
    }

    private fun endsWithOperator(expression: String): Boolean {
        var expression = expression.trim()
        expression = expression.trim { it <= ' ' }
        return expression.endsWith(ADDITION) || expression.endsWith(SUBTRACTION) ||
                expression.endsWith(MULTIPLICATION) || expression.endsWith(DIVISION) ||
                expression.endsWith(EXPONENTIATION) || expression.endsWith(SQUARE_ROOT_SCREEN) ||
                expression.endsWith(FACTORIAL_SCREEN) || expression.endsWith(DOT)
    }

    private fun calculateUnaryOperation(operand: Double, operator: String): Double {
        var result = 0.0
        when (operator) {
            SQUARE_ROOT -> result = operation!!.squareRoot(operand)
            FACTORIAL -> result = operation!!.factorial(operand)
        }
        return result
    }

    private fun calculateBinaryOperation(accumulated: Double, value: Double, operator: String): Double {
        var result = 0.0
        when (operator) {
            ADDITION -> result = operation!!.addition(accumulated, value)
            SUBTRACTION -> result = operation!!.subtraction(accumulated, value)
            MULTIPLICATION -> result = operation!!.multiplication(accumulated, value)
            DIVISION -> result = operation!!.division(accumulated, value)
            EXPONENTIATION -> result = operation!!.exponentiation(accumulated, value)
        }
        return if (accumulated == 0.0) value else result
    }

    private fun getFormattedNumber(value: Double): String {
        val symbols = DecimalFormatSymbols()
        symbols.decimalSeparator = '.'
        val formatter = DecimalFormat("0", symbols)
        formatter.maximumFractionDigits = 8
        return formatter.format(value)
    }
}