package es.elb4t.calculadoratesting

import com.google.common.truth.Truth.assertThat
import junitparams.JUnitParamsRunner
import junitparams.JUnitParamsRunner.`$`
import junitparams.Parameters
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by eloy on 28/2/18.
 */
@RunWith(JUnitParamsRunner::class)
class MathExpressionTest {
    private var expression: MathExpression? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        expression = MathExpression()
    }

    @Parameters(method = "readExpressionData")
    @Test
    fun readShouldReturnExpectedExpression(
            original: String, expected: String) {
        val result = expression!!.read(original)
        assertThat(result).isEqualTo(expected) //Librería Truth
    }

    private fun readExpressionData(): Array<Any>? {
        return `$`(
                `$`(" ", ""),
                `$`("", ""),
                `$`("fact", "f"),
                `$`("sqrt", "r"),
                `$`("(3 + 2)", "(3+2)"),
                `$`("fact (3 + 2) /3 + 2", "f(3+2)/3+2")
        )
    }

    @Parameters(method = "writeExpressionData")
    @Test
    fun writeShouldReturnExpectedExpression(
            input: String, output: String) {
        val result = expression!!.write(input)
        assertThat(result).isEqualTo(output)
    }

    private fun writeExpressionData(): Array<Any>? {
        return `$`(
                `$`("f", " fact "),
                `$`("r", " sqrt "),
                `$`("3.2", "3.2"),
                `$`("3+2.5", "3 + 2.5"),
                `$`("3-2.5", "3 - 2.5"),
                `$`("3^2.5", "3 ^ 2.5"),
                `$`("3x2.5", "3 x 2.5"),
                `$`("3/2.5", "3 / 2.5"),
                `$`("3f(2.5", "3 fact (2.5"),
                `$`("3r(2.5", "3 sqrt (2.5"),
                `$`("2.5(3", "2.5 (3"),
                `$`("3)2.5", "3) 2.5")
        )
    }

    @Parameters(method = "addSymbolExpressionData")
    @Test
    fun addSymbolShouldReturnExpectedExpression(
            original: String, symbol: String, expected: String) {
        val result = expression!!.addSymbol(original, symbol)
        assertThat(result).isEqualTo(expected)
    }

    private fun addSymbolExpressionData(): Array<Any>? {
        return `$`(
                `$`("3", "2", "32"),
                `$`("3.", "2", "3.2"),
                `$`("3", "+", "3 + "),
                `$`("3-", "3", "3 - 3"),
                `$`("4(", "2", "4 (2"),
                `$`("3.2)", "/", "3.2) / "),
                `$`("f(", "7", " fact (7"),
                `$`("r(3x", "5", " sqrt (3 x 5")
        )
    }

    @Parameters(method = "addInvalidSymbolInput")
    @Test(expected = ExpressionException::class)
    fun addSymbolShouldThrowWhenSymbolIsInvalid(symbol: String) {
        expression!!.addSymbol("", symbol)
    }

    private fun addInvalidSymbolInput(): Array<Any>? {
        return `$`(
                `$`("&"),
                `$`("E"),
                `$`("e"),
                `$`("*"),
                `$`("{"),
                `$`("X")
        )
    }
}