package es.elb4t.calculadoratesting

import org.junit.runner.RunWith
import org.junit.runners.Suite


/**
 * Created by eloy on 28/2/18.
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(MathExpressionTest::class,
                    MathOperationTest::class,
                    MathCalculatorTest::class)
class MathSuite