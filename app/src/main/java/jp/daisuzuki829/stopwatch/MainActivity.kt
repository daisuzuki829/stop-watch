package jp.daisuzuki829.stopwatch

import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    val handler = Handler()
    var timeValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timeText    = findViewById(R.id.timeText) as TextView
        val startButton = findViewById(R.id.bt_start) as Button
        val stopButton  = findViewById(R.id.bt_stop)  as Button
        val resetButton = findViewById(R.id.bt_reset) as Button

        val runnable = object : Runnable {
            override fun run() {
                timeValue++

                timeToText(timeValue)?.let {
                    timeText.text = it
                }
                handler.postDelayed(this, 1000)
            }
        }

        // startButton押下時
        startButton.setOnClickListener {
            handler.post(runnable)
        }

        // stopButton押下時
        stopButton.setOnClickListener {
            handler.removeCallbacks(runnable)
        }

        // resetButton押下時
        resetButton.setOnClickListener {
            handler.removeCallbacks(runnable)
            timeValue = 0
            timeToText()?.let {
                timeText.text = it
            }
        }
    }

    // 時間をテキストに変換
    private fun timeToText(time: Int = 0): String? {
        return if (time < 0) {
            null
        } else if (time == 0) {
            "00:00:00"
        } else {
            val h = time / 3600
            val m = time % 3600 / 60
            val s = time % 60
            "%1$02d:%2$02d:%3$02d".format(h, m, s)
        }
    }
}