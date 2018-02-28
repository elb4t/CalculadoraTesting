package es.elb4t.calculadoratesting



/**
 * Created by eloy on 28/2/18.
 */
interface Expression {
    fun read(expression: String): String
    fun write(expression: String): String
    fun addSymbol(expression: String, symbol: String): String
    fun removeSymbol(expression: String): String
    fun replaceSymbol(expression: String, symbol: String): String
    fun tokenize(expression: String): Array<String>
}