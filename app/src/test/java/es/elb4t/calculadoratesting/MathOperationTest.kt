package es.elb4t.calculadoratesting

import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


/**
 * Created by eloy on 27/2/18.
 */
@RunWith(JUnitParamsRunner::class)
class MathOperationTest {

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
}