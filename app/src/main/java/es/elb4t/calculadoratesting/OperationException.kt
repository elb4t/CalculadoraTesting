package es.elb4t.calculadoratesting

/**
 * Created by eloy on 27/2/18.
 */
class OperationException : RuntimeException {
    constructor() {}
    constructor(message: String) : super(message) {}
}