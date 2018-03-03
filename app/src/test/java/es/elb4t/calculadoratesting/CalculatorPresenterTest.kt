package es.elb4t.calculadoratesting

import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


/**
 * Created by eloy on 3/3/18.
 */
class CalculatorPresenterTest {

    private var presenter: CalculatorPresenterImp? = null
    @Mock
    private var mockedView: CalculatorView? = null
    @Mock
    private var mockedCalculator: Calculator? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mockedView = Mockito.mock(CalculatorView::class.java)
        mockedCalculator = Mockito.mock(Calculator::class.java)
        presenter = CalculatorPresenterImp(mockedView, mockedCalculator)
    }

    @Test
    fun addSymbolShouldCallShowOperationsWhenInputIsValid() {
        `when`(mockedCalculator!!.addSymbol(anyString(), anyString())).thenReturn("+")
        presenter!!.addSymbol(anyString(), anyString())
        verify(mockedView, times(1))!!.showOperations(anyString())
        verify(mockedView, times(0))!!.showError()
    }

    @Test
    fun addSymbolShouldCallShowErrorWhenCalculatorThrowsAnOperationException() {
        `when`(mockedCalculator!!.addSymbol(anyString(), anyString())).thenThrow(OperationException::class.java)
        presenter!!.addSymbol(anyString(), anyString())
        verify(mockedView, times(0))!!.showOperations(anyString())
        verify(mockedView, times(1))!!.showError()
    }

    @Test
    fun addSymbolShouldCallShowErrorWhenCalculatorThrowsAnExpressionException() {
        `when`(mockedCalculator!!.addSymbol(anyString(), anyString())).thenThrow(ExpressionException::class.java)
        presenter!!.addSymbol(anyString(), anyString())
        verify(mockedView, times(0))!!.showOperations(anyString())
        verify(mockedView, times(1))!!.showError()
    }

    @Test
    fun removeSymbolShouldCallShowOperationsWhenInputIsValid() {
        `when`(mockedCalculator!!.removeSymbol(anyString())).thenReturn("+")
        presenter!!.removeSymbol(anyString())
        verify(mockedView, times(1))!!.showOperations(anyString())
        verify(mockedView, times(0))!!.showError()
    }

    @Test
    fun clearScreenShouldCallShowOperations() {
        presenter!!.clearScreen()
        verify(mockedView, times(1))!!.showOperations(anyString())
        verify(mockedView, times(0))!!.showError()
    }

    @Test
    fun calculateShouldCallShowOperationsWhenInputIsValid() {
        `when`(mockedCalculator!!.calculate(anyString())).thenReturn("+")
        presenter!!.calculate(anyString())
        verify(mockedView, times(1))!!.showResult(anyString())
        verify(mockedView, times(0))!!.showError()
    }

    @Test
    fun calculateShouldCallShowErrorWhenCalculatorThrowsAnNullPointer() {
        `when`(mockedCalculator!!.calculate(null)).thenReturn("+")
        presenter!!.calculate(null)
        verify(mockedView, times(0))!!.showResult(null)
        verify(mockedView, times(1))!!.showError()
    }

}