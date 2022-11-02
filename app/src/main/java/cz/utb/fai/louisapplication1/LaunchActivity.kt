package cz.utb.fai.louisapplication1

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.CalendarContract
import android.util.Log
import android.util.LogPrinter
import android.widget.Button
import android.widget.TextView
import java.util.*

class LaunchActivity : AppCompatActivity() {

    lateinit var textView: TextView
    var GAME_STATE_KEY = "GAME_STATE_KEY"
    var TEXT_VIEW_KEY = "textViewKey"

    // some transient state for the activity instance
    var gameState: String? = "LEVEL 1"

    var LOG_TAG = "MYAPP"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // recovering the instance state
        gameState = savedInstanceState?.getString(GAME_STATE_KEY)

        // initialize member TextView so we can manipulate it later


        if(gameState == null)
            gameState = "LEVEL 1"
        setContentView(R.layout.activity_launch)
        textView = findViewById(R.id.textView)
        Log.v(LOG_TAG, "onCreate")

        findViewById<Button>(R.id.callButton).setOnClickListener {
            val callIntent: Intent
            val callUri : Uri = Uri.parse("tel:+420 776 423 625")

            callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data  = callUri
            startActivity(callIntent)
        }


        findViewById<Button>(R.id.mapButton).setOnClickListener {
            // Map point based on address
            val mapIntent: Intent = Uri.parse(
                "geo:0,0?q=Brno"
            ).let { location ->
                // Or map point based on latitude/longitude
                // val location: Uri = Uri.parse("geo:37.422219,-122.08364?z=14") // z param is zoom level
                Intent(Intent.ACTION_VIEW, location)
            }
            startActivity(mapIntent)
        }


        findViewById<Button>(R.id.webButton).setOnClickListener {
            val webIntent: Intent = Uri.parse("https://www.utb.cz/").let { webpage ->
                Intent(Intent.ACTION_VIEW, webpage)
            }
            startActivity(webIntent)
        }

        findViewById<Button>(R.id.calenderButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI).apply {
                val beginTime: Calendar = Calendar.getInstance().apply {
                    set(2022, 12, 31, 7, 30)
                }
                val endTime = Calendar.getInstance().apply {
                    set(2022, 12, 31, 10, 30)
                }
                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.timeInMillis)
                putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.timeInMillis)
                putExtra(CalendarContract.Events.TITLE, "New Year's Eve")
                putExtra(CalendarContract.Events.EVENT_LOCATION, "Tomas Bata")
            }

            startActivity(intent)

        }
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        textView.text = savedInstanceState?.getString(GAME_STATE_KEY)
        Log.v(LOG_TAG, "Restored")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.run {
            putString(GAME_STATE_KEY, gameState)
            Log.v(LOG_TAG, "Saved"+ gameState)
        }

    }



    override fun onStart() {
        super.onStart()
        Log.v(LOG_TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.v(LOG_TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.v(LOG_TAG, "onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(LOG_TAG, "onDestroy()")
    }
}