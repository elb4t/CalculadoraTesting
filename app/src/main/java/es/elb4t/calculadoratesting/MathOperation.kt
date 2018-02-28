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
    fun resta(operand1: Double, operand2: Double): Double {
        throwsIfValuesAreInvalid(operand1, operand2)
        return operand1 - operand2
    }

    @Throws(OperationException::class)
    fun multiplicacion(operand1: Double, operand2: Double): Double {
        throwsIfValuesAreInvalid(operand1, operand2)
        return operand1 * operand2
    }

    @Throws(OperationException::class)
    fun division(operand1: Double, operand2: Double): Double {
        throwsIfValuesAreInvalid(operand1, operand2)
        return operand1 / operand2
    }

    @Throws(OperationException::class)
    private fun throwsIfValuesAreInvalid(vararg values: Double) {
        for (value in values) {
            if (value == java.lang.Double.MAX_VALUE || java.lang.Double.isInfinite(value)
                    || java.lang.Double.isNaN(value))
                throw OperationException()
        }
    }

    @Throws(OperationException::class)
    fun exponentiation(base: Double, exponent: Double): Double {
        var result = 1.0
        var exponent = exponent
        val expoNegative = exponent < 0
        if (expoNegative)
            exponent = -exponent
        while (!exponent.equals(0)) {
            result = multiplicacion(result, base)
            exponent--
            throwsIfValuesAreInvalid(result);
        }
        return if (expoNegative) 1 / result else result
    }

    fun squareRoot(radicand: Double): Double {
        var aux: Double
        var squareRoot = radicand / 2
        do {
            aux = squareRoot
            squareRoot = (division(addition(aux, radicand),aux)) / 2
        } while (aux != squareRoot)
        return squareRoot
    }

    fun factorial(operand: Double): Double {
        var operand = operand
        var result = 1.0
        while (operand > 0) {
            result = multiplicacion(result, operand)
            operand--
        }
        return result
    }
}