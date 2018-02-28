package es.elb4t.calculadoratesting

import junitparams.JUnitParamsRunner
import junitparams.Parameters
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

    @Parameters(method = "getInvalidInput")
    @Test(expected = OperationException::class)
    fun additionShouldReturnExpectedValueWhenOperandsAreValid(operand1: Double, operand2: Double){
        //Act
        var result = mathOperation!!.addition(operand1, operand2)
    }

    private fun getInvalidInputu (): Collection<Array<Any>> {
        return Arrays.asList(
                arrayOf(12, Double.MAX_VALUE),
                arrayOf(Double.POSITIVE_INFINITY, 1),
                arrayOf<Any>(-12.3, Double.NEGATIVE_INFINITY),
                arrayOf(Double.NaN, 12),
                arrayOf(Math.pow(2.0, 1024.0), -1)
        )
    }
}