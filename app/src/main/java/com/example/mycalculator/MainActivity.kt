package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    var lastNumeric:Boolean = false;
    var lastDot:Boolean=false;

//    var tvInput1=findViewById<Button>(R.id.btnOne)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

//    append numeric button to the screen text view
    fun onDigit(view: View){
        tvInput.append((view as Button).text)
        lastNumeric=true
    }

    //append dot(.) to the text view
    fun onDecimalPoint(view:View){
        //if the last appened value is numeric then append (.) or don't
        if(lastNumeric && !lastDot){
            tvInput.append(".")
            lastNumeric=false //update the numeric flag
            lastDot=true
        }
    }

    /**
     * It is used to check whether any of the operator is used or not.
     */
    private fun isOperatorAdded(value:String):Boolean{
        /**
         * check first if the value starts with "-" then ignore it
         */
        return if(value.startsWith("-")){
            false
        } else{
            (value.contains("/") || value.contains("*")
                    || value.contains("+") || value.contains("-"))
        }
    }

    //append + - / * operators to the TV
    fun onOperator(view:View){
        if(lastNumeric && !isOperatorAdded(tvInput.text.toString())){
            tvInput.append((view as Button).text)
            lastNumeric=false
            lastDot=false //reset the dot flag
        }
    }

    fun onClear(view:View){
        tvInput.text=""
        lastNumeric=false
        lastDot=false
    }

    fun onEqual(view:View){
        if(lastNumeric){
            //read the full text view value
            var value=tvInput.text.toString();
            var prefix=""
            try{
              //if the value starts with '-' then separate it and perform the calculation
                if(value.startsWith("-")){
                    prefix="-"
                    value=value.substring(1)
                }

                //if other operators
                if(value.contains("/")){
                    val splitedValue=value.split("/")

                    var one=splitedValue[0]
                    var two=splitedValue[1]

                    if(!prefix.isEmpty()){
                        //if prefix is not empty then append it with first value (for the minus operation)
                        one=prefix+one
                    }

                    tvInput.text=removeZeroAfterDot((one.toDouble()/two.toDouble()).toString())
                } else if(value.contains("*")){
                    val splitedValue=value.split("*")
                     var one=splitedValue[0]
                    var two=splitedValue[1]

                    if(!prefix.isEmpty()){
                        one=prefix+one
                    }

                    tvInput.text=removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }else if(value.contains("-")){
                    val splitedValue=value.split("-")
                    var one=splitedValue[0]
                    var two=splitedValue[1]

                    if(!prefix.isEmpty()){
                        one=prefix+one
                    }

                    tvInput.text=removeZeroAfterDot((one.toDouble()-one.toDouble()).toString())
                }else if(value.contains("+")){
                    val splitedValue=value.split("+")
                    var one=splitedValue[0]
                    var two=splitedValue[0]

                    if(!prefix.isEmpty()){
                        one=prefix+one
                    }

                    tvInput.text=removeZeroAfterDot((one.toDouble()+two.toDouble()).toString())
                }
            }catch (e:ArithmeticException){
                e.printStackTrace()
            }
        }
    }


    private fun removeZeroAfterDot(result:String):String{
        var value=result
        if(result.contains(".0")){
            value=result.substring(0,result.length-2)
        }
        return value
    }

}