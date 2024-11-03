package jp.ac.jec.cm0128.subdisplaylauncherforliberoflip

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.Sensor
import android.hardware.SensorEventCallback
import android.hardware.SensorEventListener
import android.hardware.SensorEventListener2
import android.hardware.SensorManager
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import kotlin.text.Typography.registered

class MyScreenStateReceiver : BroadcastReceiver() {
    private var registered = false
    private var sensorEventListener : SensorEventListener? = null

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        if(intent.action == Intent.ACTION_SCREEN_ON){
            val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

            if(accelerometer != null && sensorEventListener != null){
                sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
            }
        } else if(intent.action == Intent.ACTION_SCREEN_OFF){
            val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

            if(accelerometer != null && sensorEventListener != null){
                sensorManager.unregisterListener(sensorEventListener)
            }
        }
    }

    fun register(context: Context, listener: SensorEventListener) {
        if (!registered) {
            sensorEventListener = listener
            val filter = IntentFilter()
            // どのイベントを受け取るのかを定義
            filter.addAction(Intent.ACTION_SCREEN_ON)
            filter.addAction(Intent.ACTION_SCREEN_OFF)
            context.registerReceiver(this, filter)
            registered = true
        }
    }
    fun unregister(context: Context) {
        if (registered) {
            context.unregisterReceiver(this)
            registered = false
        }
    }

    companion object{
        private const val TAG = "MyScreenStateReceiver"
    }
}