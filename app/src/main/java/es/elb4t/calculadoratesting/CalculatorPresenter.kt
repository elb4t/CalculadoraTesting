package es.elb4t.calculadoratesting

/**
 * Created by eloy on 3/3/18.
 */
interface CalculatorPresenter {
    fun addSymbol(expression: String, character: String)
    fun removeSymbol(expression: String)
    fun clearScreen()
    fun calculate(expression: String)
}