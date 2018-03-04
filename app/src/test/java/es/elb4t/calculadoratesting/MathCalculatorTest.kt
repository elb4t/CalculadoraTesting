package es.elb4t.calculadoratesting

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import junitparams.JUnitParamsRunner
import junitparams.JUnitParamsRunner.`$`
import junitparams.Parameters
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


/**
 * Created by eloy on 3/3/18.
 */
@RunWith(JUnitParamsRunner::class)
class MathCalculatorTest {

    private var calculator: MathCalculator? = null
    @Mock private var mockedExpression: Expression? = null
    @Mock private var mockedOperation: MathOperation? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mockedExpression = Mockito.mock(Expression::class.java)
        mockedOperation = Mockito.mock(MathOperation::class.java)
        calculator = MathCalculator(mockedExpression!!, mockedOperation)
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

    @Test
    @Parameters(method = "addSymbolData")
    fun addSymbolShouldCallAddSymbolInExpression(to: String, symbol: String) {
        //Arrange
        val mockedExpression = MockExpression()
        val dummyOperation: MathOperation? = null
        val calculator = MathCalculator(mockedExpression, dummyOperation)
        //Act
        calculator.addSymbol(to, symbol)
        //Assert
        assertThat(mockedExpression.symbolAdded).isTrue()
        assertThat(mockedExpression.symbolReplaced).isFalse()
    }

    private fun addSymbolData(): Array<Any>? {
        return `$`(
                `$`("", "-"),
                `$`("2", "+"),
                `$`("5", "."),
                `$`("4.3", "f")
        )
    }

    @Test
    @Parameters(method = "replaceSymbolData")
    fun addSymbolShouldCallReplaceSymbolInExpression(to: String, symbol: String) {
        //Arrange
        val mockedExpression = MockExpression()
        val dummyOperation: MathOperation? = null
        val calculator = MathCalculator(mockedExpression, dummyOperation)
        //Act
        calculator.addSymbol(to, symbol)
        //Assert
        assertThat(mockedExpression.symbolAdded).isFalse()
        assertThat(mockedExpression.symbolReplaced).isTrue()
    }

    private fun replaceSymbolData(): Array<Any>? {
        return `$`(
                `$`("-", "+"),
                `$`("-5+", "x"),
                `$`("3x4/", "f"),
                `$`("3sqrt", "x"),
                `$`("3.", "/")
        )
    }

    @Test
    @Parameters(method = "addSymbolData")
    fun addSymbolShouldCallAddSymbol(to: String, symbol: String) {
        calculator!!.addSymbol(to, symbol)
        verify(mockedExpression, times(1))!!.addSymbol(anyString(), anyString())
        verify(mockedExpression, times(0))!!.replaceSymbol(anyString(), anyString())
    }

    @Test
    @Parameters(method = "replaceSymbolData")
    fun addSymbolShouldCallReplaceSymbol(to: String, symbol: String) {
        calculator!!.addSymbol(to, symbol)
        verify(mockedExpression, times(1))!!.replaceSymbol(anyString(), anyString())
        verify(mockedExpression, times(0))!!.addSymbol(anyString(), anyString())
    }

    @Test()
    @Parameters(method = "resolveData")
    fun resolveShouldReturnExpectedExpression(from: String, tokens: Array<String>, expected: String) {
        val operation = MathOperation()
        val calculator = MathCalculator(mockedExpression!!, operation)
        `when`(mockedExpression!!.tokenize(from)).thenReturn(tokens)
        assertThat(calculator.resolve(from)).isEqualTo(expected)
    }

    private fun resolveData(): Array<Any>? {
        return `$`(
                `$`("", arrayOf(""), ""),
                `$`("-", arrayOf("-"), "0"),
                `$`("-5", arrayOf("-5"), "-5"),
                `$`("3.4", arrayOf("3.4"), "3.4"),
                `$`("3-2", arrayOf("3", "-2"), "1"),
                `$`("3+2", arrayOf("3", "+", "2"), "5"),
                `$`("f3", arrayOf("f", "3"), "6"),
                `$`("2f3", arrayOf("2", "f", "3"), "12"),
                `$`("9/r9", arrayOf("9", "/", "r", "9"), "3"),
                `$`("3^f3", arrayOf("3", "^", "f", "3"), "729"),
                `$`("3--4", arrayOf("3", "-", "-4"), "7")
        )
    }

    @Test(timeout = 100)
    @Parameters(method = "resolveAdditionData")
    fun resolveShouldCallXTimesAdditionMethod(from: String, tokens: Array<String>, times: Int) {
        `when`(mockedExpression!!.tokenize(from)).thenReturn(tokens)
        calculator!!.resolve(from)
        verify(mockedOperation, times(times))!!.addition(anyDouble(), anyDouble())
    }

    private fun resolveAdditionData(): Array<Any>? {
        return `$`(
                `$`("2+2", arrayOf("2", "+", "2"), 2),
                `$`("", arrayOf(""), 0),
                `$`("2", arrayOf("2"), 1),
                `$`("2+2x3-5", arrayOf("2", "+", "2", "x", "3", "-5"), 3)
        )
    }
}