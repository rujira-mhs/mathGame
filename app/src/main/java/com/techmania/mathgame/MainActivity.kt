package com.techmania.mathgame

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    //Dialog myDialog;

    lateinit var myDialog:Dialog
    lateinit var textLife : TextView
    lateinit var textTime : TextView //เวลา

    lateinit var textQuestion : TextView
    lateinit var editTextAnswer: EditText

    lateinit var buttonOk: Button
    lateinit var buttonNext : Button

    lateinit var congrats : TextView
    lateinit var congrats2 : TextView

    lateinit var correctt : ImageView
    lateinit var wrongg : ImageView

    lateinit var number : TextView

    lateinit var home : Button

    var correctAnswer = 0
    var userLife = 3
    var countNum = 1
    var maxNo = 5
    var userAnswer = 0

    lateinit var timer: CountDownTimer
    private val startTimerInMillis : Long = 30000
    var timeLeftInMillis : Long = startTimerInMillis

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // myDialog = new Dialog(this)

        //supportActionBar!!.title = "Addition"

        number = findViewById(R.id.Number)
        textLife = findViewById(R.id.textNumLife)
        textTime = findViewById(R.id.textNumTime)
        textQuestion = findViewById(R.id.textQuestion)
        editTextAnswer = findViewById(R.id.editTextNumber)
        buttonOk = findViewById(R.id.buttonOk)
        buttonNext = findViewById(R.id.buttonNext)
        congrats = findViewById(R.id.congrats)
        congrats2 = findViewById(R.id.congrats2)
        correctt = findViewById(R.id.correctt)
        wrongg = findViewById(R.id.wrongg)


        var correctAnswer = 0

        congrats.isVisible = false
        buttonNext.isVisible = false
        correctt.isVisible = false
        wrongg.isVisible = false
        congrats2.isVisible = false

        correctAnswer = gameContinue()
        println(" line 66 CorrectAnswer $correctAnswer")
////////////////
        /*val number1 = Random.nextInt(0,50)
        val number2 = Random.nextInt(0,50)

        textQuestion.text = "$number1 + $number2"  //$เพื่อที่จะไม่ได้บวกกันจริงๆ แค่โชว์

        correctAnswer = number1 + number2

        println( "correct answer at gameContinue $correctAnswer" )*/
       /////////////////////////
        buttonOk.setOnClickListener{

            val input = editTextAnswer.text.toString()
            if(input ==""){
                Toast.makeText(applicationContext,"กรุณาป้อนคำตอบ",Toast.LENGTH_LONG).show()
            }
            else
            {
                pauseTimer()

                userAnswer = input.toInt()
                println("user answer $userAnswer")
                println("correct answer at main $correctAnswer")
                if(userAnswer == correctAnswer){ // ถ้าคำตอบถูก
                    buttonNext.isVisible = true
                    congrats.isVisible = true
                    correctt.isVisible = true
                   // textQuestion.text = "คำตอบถูกต้องจย้้า \n ไปข้อต่อไปกันเล้ย !"
                    countNum++
                  //  number.text = countNum.toString()

                    if(countNum == maxNo){ //ขำนวนข้อ 5 ข้อครบแล้ว ถึงเวลาแสดง pop-up
                        popup()
                    }

                }
                else{ // ถ้าคำตอบผิด
                    buttonNext.isVisible = true
                    wrongg.isVisible = true
                    userLife--
                   // textQuestion.text = "ผิดจย้า ลองใหม่น้า"
                    textLife.text = userLife.toString()
                }

            }

        }

        buttonNext.setOnClickListener{
            pauseTimer()
            resetTimer()
            correctAnswer = gameContinue()
            if (userAnswer == correctAnswer){
                println("$userAnswer == $correctAnswer")
                number.text = countNum.toString()
            }
            editTextAnswer.setText("")
            congrats.isVisible = false
            congrats2.isVisible = false
            correctt.isVisible = false
            buttonNext.isVisible = false
        }


    }

    private fun popup() { // พอครบ 5 ข้อแล้วจะแสดง pop up ขึ้นมา มีปุ่มให้กลับไป main ด้วย
        val back_to_main_popup = layoutInflater.inflate(R.layout.back_to_main_popup,null)

        val myDialog = Dialog(this)
        myDialog.setContentView(back_to_main_popup)

        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable((ColorDrawable(Color.TRANSPARENT)))
        myDialog.show()

    }


    private fun gameContinue() : Int  {
        val number1 = Random.nextInt(0,10)
        val number2 = Random.nextInt(0,10)

        val flag = Random.nextInt(0,2)

        if (flag==0){
            textQuestion.text = "$number1 + $number2"  //$เพื่อที่จะไม่ได้บวกกันจริงๆ แค่โชว์

            correctAnswer = number1 + number2

            println( "correct answer at plus  $correctAnswer" )
        }else{
            if(number1>number2){
                textQuestion.text = "$number1 - $number2"
                correctAnswer = number1 - number2
                println( "correct answer at minus1 $correctAnswer" )
            }else{
                textQuestion.text = "$number2 - $number1"
                correctAnswer = number2 - number1
                println( "correct answer at minus2 $correctAnswer" )
            }

        }

        println( "correct answer at gameContinue $correctAnswer" )

        startTimer()
        return correctAnswer

    }

    private fun startTimer(){
        timer = object : CountDownTimer(timeLeftInMillis,1000){
            override fun onTick(milliUntilFinished: Long) {
               timeLeftInMillis = milliUntilFinished
                updateText()
            }

            override fun onFinish() {
                pauseTimer()
                resetTimer()
                updateText()

                userLife--
                textLife.text = userLife.toString()
                congrats2.isVisible = true

                buttonNext.isVisible = true


            }


        }.start()

    }

    fun pauseTimer(){
        timer.cancel()

    }

    private fun updateText() {
        val remainingTime : Int = (timeLeftInMillis/1000).toInt()
        textTime.text = String.format(Locale.getDefault(),"%02d",remainingTime)

    }

    fun resetTimer(){
        timeLeftInMillis = startTimerInMillis
        updateText()

    }
}