package es.elb4t.calculadoratesting

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by eloy on 27/2/18.
 */
class MathOperationTest {
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
}