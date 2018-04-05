package jimmy.calc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {
    private var _screen: TextView? = null
    private var display = ""
    private var currentOperator = ""
    private var result = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _screen = findViewById<View>(R.id.textView2) as TextView
        _screen!!.text = display
    }

    private fun clear() {
        display = ""
        currentOperator = ""
        result = ""
    }

    private fun updateScreen() {
        _screen!!.text = display
    }

    private fun operate(a: String, b: String, op: String): Double {
         when (op) {
              "+" -> return java.lang.Double.valueOf(a)!! + java.lang.Double.valueOf(b)!!
              "-" -> return java.lang.Double.valueOf(a)!! - java.lang.Double.valueOf(b)!!
              "x" -> return java.lang.Double.valueOf(a)!! * java.lang.Double.valueOf(b)!!
              "÷" -> {
                  try {
                      return java.lang.Double.valueOf(a)!! / java.lang.Double.valueOf(b)!!
                  } catch (e: Exception) {
                      Log.d("Calc", e.message)
                  }
                  return -1.0
              }
              else -> return -1.0
         }
    }

    private fun getResult(): Boolean {
        if (currentOperator === "") return false
        val operation = display.split(Pattern.quote(currentOperator).toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        if (operation.size < 2) return false
        result = operate(operation[0], operation[1], currentOperator).toString()
        return true
    }

    fun onClickClear() {
        clear()
        updateScreen()
    }

    fun onClickNumber(v: View) {
        if (result !== "") {
            clear()
            updateScreen()
        }
        val b = v as Button
        display += b.text
        updateScreen()
    }

    private fun isOperator(op: Char): Boolean {
        return when (op) {
            '+', '-', 'x', '÷' -> true
            else -> false
        }
    }

    fun onClickOperator(v: View) {
        if (display === "") return

        val b = v as Button

        if (result !== "") {
            val _display = result
            clear()
            display = _display
        }

        if (currentOperator !== "") {
            Log.d("CalcX", "" + display[display.length - 1])
            if (isOperator(display[display.length - 1])) {
                display = display.replace(display[display.length - 1], b.text[0])
                updateScreen()
                return
            } else {
                getResult()
                display = result
                result = ""
            }
            currentOperator = b.text.toString()
        }
        display += b.text
        currentOperator = b.text.toString()
        updateScreen()
    }

    fun onClickEqual() {
        if (display === "") return
        if (!getResult()) return
        _screen!!.text = display + "\n" + result
    }
}