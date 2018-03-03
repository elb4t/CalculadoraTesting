package es.elb4t.calculadoratesting

import com.google.common.truth.Truth
import junitparams.JUnitParamsRunner
import junitparams.JUnitParamsRunner.`$`
import junitparams.Parameters
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by eloy on 3/3/18.
 */
@RunWith(JUnitParamsRunner::class)
class MathCalculatorTest {
    private var calculator: MathCalculator? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        calculator = MathCalculator()
    }

    @Parameters(method = "containsParenthesesData")
    @Test
    fun containsParenthesesShouldReturnTrue(original: String, expected: Boolean) {
        val result = calculator!!.containsParenthesis(original)
        Truth.assertThat(result).isEqualTo(expected) //Librería Truth
    }

    private fun containsParenthesesData(): Array<Any>? {
        return `$`(
                `$`("(", true),
                `$`("2+(4-2)", true),
                `$`("4-1", false)
        )
    }

    @Parameters(method = "getParenthesisData")
    @Test
    fun getParenthesisExpressionShouldReturnWhitoutParenthesis(original: String, expected: String) {
        val result = calculator!!.getParenthesisExpression(original)
        Truth.assertThat(result).isEqualTo(expected) //Librería Truth
    }

    private fun getParenthesisData(): Array<Any>? {
        return `$`(
                `$`("(", ""),
                `$`("2+(4-2)", "4-2"),
                `$`("2+(4-2*(5-1))", "5-1")
        )
    }

    @Parameters(method = "getRightmostParenthesisData")
    @Test
    fun getRightmostParenthesisExpressionShouldReturnWhitoutParenthesis(original: String, expected: String) {
        val result = calculator!!.getRightmostParenthesis(original)
        Truth.assertThat(result).isEqualTo(expected) //Librería Truth
    }

    private fun getRightmostParenthesisData(): Array<Any>? {
        return `$`(
                `$`("(", "("),
                `$`("2+(4-2)", "(4-2)"),
                `$`("2+(4-2*(5-1))", "(5-1)")
        )
    }
}