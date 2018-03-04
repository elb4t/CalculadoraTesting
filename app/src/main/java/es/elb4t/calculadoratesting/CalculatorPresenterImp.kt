package es.elb4t.calculadoratesting


/**
 * Created by eloy on 3/3/18.
 */
class CalculatorPresenterImp(private var view: CalculatorView?, private var calculator: Calculator?) : CalculatorPresenter {

    override fun addSymbol(expression: String, symbol: String) {
        try {
            view!!.showOperations(calculator!!.addSymbol(expression, symbol))
        } catch (exception: RuntimeException) {
            view!!.showError()
        }
    }

    override fun removeSymbol(expression: String) {
        if (!expression!!.isEmpty()) {
            view!!.showOperations(calculator!!.removeSymbol(expression))
        }else{
            view!!.showOperations(expression)
        }
    }

    override fun clearScreen() {
        view!!.showOperations("")
    }

    override fun calculate(expression: String?) {
        try {
            if (expression!!.isEmpty()) {
                view!!.showResult(expression!!)
                return
            }
            view!!.showResult(calculator!!.calculate(expression!!))
        } catch (exception: RuntimeException) {
            view!!.showError()
        }

    }

}