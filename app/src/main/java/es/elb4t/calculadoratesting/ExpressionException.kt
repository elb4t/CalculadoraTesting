package es.elb4t.calculadoratesting

/**
 * Created by eloy on 28/2/18.
 */
class ExpressionException : RuntimeException {
    constructor() {}
    constructor(message: String) : super(message) {}
}