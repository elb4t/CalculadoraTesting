package es.elb4t.calculadoratesting

/**
 * Created by eloy on 3/3/18.
 */
interface CalculatorView {
    fun setPresenter(presenter: CalculatorPresenter)
    fun showOperations(operations: String)
    fun showResult(result: String?)
    fun showError()
}