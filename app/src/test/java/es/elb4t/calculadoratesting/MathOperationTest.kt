package es.elb4t.calculadoratesting

import com.google.common.truth.Truth.assertThat
import junitparams.JUnitParamsRunner
import junitparams.JUnitParamsRunner.`$`
import junitparams.Parameters
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*




/**
 * Created by eloy on 27/2/18.
 */
@RunWith(JUnitParamsRunner::class)
class MathOperationTest {
    private var mathOperation: MathOperation? = null

    @Before
    fun setUp() {
        mathOperation = MathOperation()
    }

    @Test
    fun additionShouldReturnTenWhenOperandsAreThreeAndSeven() {
        //Arrange
        val operation: MathOperation = MathOperation()
        val expectedValue: Double = 10.0
        var result: Double = 0.0
        //Act
        result = operation.addition(3.0, 7.0)
        //Assert
        assertEquals(String.format("Resultado %1s ha de ser igual a %2s",result, expectedValue), expectedValue, result, 0.0)
    }

    @Parameters(method = "getValidAdditionInput")
    @Test
    fun additionShouldReturnExpectedValueWhenOperandsAreValid(operand1: Double, operand2: Double, expectedValue: Double) {
        //Arrange
        val mathOperation = MathOperation()
        //Act
        var result = mathOperation.addition(operand1, operand2)
        //Assert
        assertEquals(String.format("Resultado %1s ha de ser igual a %2s", result, expectedValue), expectedValue, result, 0.0)
    }

    private fun getValidAdditionInput(): Collection<Array<Any>> {
        return Arrays.asList(
                arrayOf<Any>(1, 1, 2),
                arrayOf<Any>(2, -2, 0),
                arrayOf<Any>(-3.5, -2.7, -6.2)
        )
    }

    @Parameters(method = "getInvalidInput")
    @Test(expected = OperationException::class)
    fun additionShouldThrowsWhenValuesAreInvalid(operand1: Double, operand2: Double) {
        //Act
        var result = mathOperation!!.addition(operand1, operand2)
    }

    private fun getInvalidInput(): Collection<Array<Any>> {
        return Arrays.asList(
                arrayOf(12, Double.MAX_VALUE),
                arrayOf(Double.POSITIVE_INFINITY, 1),
                arrayOf<Any>(-12.3, Double.NEGATIVE_INFINITY),
                arrayOf(Double.NaN, 12),
                arrayOf(Math.pow(2.0, 1024.0), -1)
        )
    }

    @Parameters(method = "getValidExponentiationInput")
    @Test(timeout = 100)
    fun exponentiationShouldReturnExpectedValueWhenInputIsValid(
            base: Double, exponent: Double, expectedValue: Double) {
        val result = mathOperation!!.exponentiation(base, exponent)
        assertThat(result).isWithin(1E-13).of(expectedValue)
    }

    private fun getValidExponentiationInput(): Array<Any> {
        return `$`(
                `$`(0, 0, 1),
                `$`(2, 0, 1),
                `$`(2, 1, 2),
                `$`(2.3, 5, 64.36343),
                `$`(-3, 4, 81),
                `$`(-3, 3, -27),
                `$`(2, -2, 0.25)
        )
    }

    @Parameters(method = "getInvalidExponentiationInput")
    @Test(expected = OperationException::class, timeout = 100)
    fun exponentiationShouldThrowWhenInputIsInvalid(
            base: Double, exponent: Double) {
        mathOperation!!.exponentiation(base, exponent)
    }

    private fun getInvalidExponentiationInput(): Array<Any> {
        return `$`(
                `$`(java.lang.Double.NEGATIVE_INFINITY, 2),
                `$`(-3, java.lang.Double.POSITIVE_INFINITY),
                `$`(java.lang.Double.NaN, -1),
                `$`(2, java.lang.Double.MAX_VALUE),
                `$`(2, 1024),
                `$`(5, 3.3),
                `$`(-3, -1.2),
                `$`(1,100000000)
        )
    }
}