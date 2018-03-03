package es.elb4t.calculadoratesting


/**
 * Created by eloy on 28/2/18.
 */
interface Calculator {
    fun addSymbol(to: String, symbol: String): String
    fun removeSymbol(from: String): String
    fun calculate(from: String?): String
}