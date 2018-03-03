package es.elb4t.calculadoratesting

import android.animation.Animator
import android.annotation.TargetApi
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.Button
import android.widget.TextSwitcher
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_math_calculator.*
import kotlinx.android.synthetic.main.element_math_calculator.*
import java.util.*


class MainActivity : AppCompatActivity(), TextWatcher, Animator.AnimatorListener, CalculatorView {
    private var buttons: List<Button>? = null
    private var presenter: CalculatorPresenter? = null
    private lateinit var resultText: TextSwitcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_math_calculator)
        initializeViewComponents()
        initializeViewListeners()
    }


    private fun initializeViewComponents() {
        resultText = findViewById(R.id.result)
        buttons = Arrays.asList(
                bt_parenthesis_start,
                bt_parenthesis_end,
                bt_addition,
                bt_subtraction,
                bt_multiplication,
                bt_division,
                bt_exponentiation,
                bt_square_root,
                bt_factorial,
                bt0,
                bt00,
                bt1,
                bt2,
                bt3,
                bt4,
                bt5,
                bt6,
                bt7,
                bt8,
                bt9,
                bt_dot)
        setPresenter(CalculatorPresenterImp(
                this,
                MathCalculator(MathExpression(), MathOperation())
        ))
    }

    private fun initializeViewListeners() {
        operations.addTextChangedListener(this)
        resultText.setFactory {
            val resultView = TextView(this@MainActivity)
            resultView.gravity = Gravity.CENTER
            resultView.textSize = 30f
            resultView
        }

        resultText.setInAnimation(this, R.anim.result_anim)
        bt_clear.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                circularRevealCard(result_screen)
            else
                presenter!!.clearScreen()

        }
        bt_remove_last.setOnClickListener {
            val expression = operations.text.toString()
            presenter!!.removeSymbol(expression)
        }
        bt_equal.setOnClickListener {
            resultText.setText((result.currentView as TextView).text)
            (resultText.currentView as TextView).setTextColor(ContextCompat.getColor(
                    this@MainActivity, R.color.colorAccent
            ))
        }
        for (button in buttons!!) {
            button.setOnClickListener {
                var expression: String = operations.text.toString()
                presenter!!.addSymbol(expression, button.tag.toString())
            }
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun circularRevealCard(view: View) {
        val finalRadius = Math.max(view.width, view.height).toFloat()
        val circularReveal = ViewAnimationUtils.createCircularReveal(view,
                0,
                view.height,
                0f,
                finalRadius * 1.5f)
        circularReveal.duration = 500
        circularReveal.addListener(this)
        circularReveal.start()
    }

    override fun beforeTextChanged(s: CharSequence, start: Int,
                                   count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence, start: Int,
                               before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable) {
        presenter!!.calculate(s.toString())
    }

    override fun onAnimationStart(animation: Animator) {
        result_screen.setBackgroundColor(ContextCompat.getColor(
                this, R.color.colorPrimary))
    }

    override fun onAnimationEnd(animation: Animator) {
        result_screen.setBackgroundColor(ContextCompat.getColor(
                this, android.R.color.transparent))
        presenter!!.clearScreen()
    }

    override fun onAnimationCancel(animation: Animator) {}
    override fun onAnimationRepeat(animation: Animator) {}
    override fun setPresenter(presenter: CalculatorPresenter) {
        this.presenter = presenter
    }

    override fun showOperations(operations: String) {
        this.operations.text = operations
    }

    override fun showResult(result: String?) {
        (resultText.currentView as TextView).setTextColor(Color.rgb(150, 150, 150))
        resultText.setCurrentText(result)
    }

    override fun showError() {
        resultText.setText("ERROR")
        (resultText.currentView as TextView).setTextColor(
                ContextCompat.getColor(this, R.color.colorAccent))
    }
}
