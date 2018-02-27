package es.elb4t.calculadoratesting

/**
 * Created by eloy on 27/2/18.
 */
class MathOperation {
    @Throws(OperationException::class)
    fun addition(operand1: Double, operand2: Double): Double {
        throwsIfValuesAreInvalid(operand1, operand2)
        return operand1 + operand2
    }

    @Throws(OperationException::class)
    private fun throwsIfValuesAreInvalid(vararg values: Double) {
        for (value in values) {
            if (value == java.lang.Double.MAX_VALUE || java.lang.Double.isInfinite(value)
                    || java.lang.Double.isNaN(value))
                throw OperationException()
        }
    }

}