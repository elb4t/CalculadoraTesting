package es.elb4t.calculadoratesting

import android.support.annotation.NonNull


/**
 * Created by eloy on 3/3/18.
 */
class MockExpression: Expression {
    var symbolAdded = false
    var symbolReplaced = false

    override fun read(@NonNull expression: String): String {
        return null.toString()
    }

    override fun write(expression: String): String {
        return null.toString()
    }

    override fun addSymbol(expression: String, symbol: String): String {
        symbolAdded = true
        return null.toString()
    }

    override fun removeSymbol(expression: String): String {
        return null.toString()
    }

    override fun replaceSymbol(expression: String, symbol: String): String {
        symbolReplaced = true
        return null.toString()
    }

    override fun tokenize(expression: String): Array<String> {
        return arrayOf()
    }

}