package jp.ac.jec.cm0128.subdisplaylauncherforliberoflip

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.display.DisplayManager
import android.os.IBinder
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED

class MyBackService : AccessibilityService(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private val X_SHAKE_THRESHOLD =30.0f
    private val Y_SHAKE_THRESHOLD =30.0f

    private var isResist = false

    private lateinit var receiver: MyScreenStateReceiver

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType == TYPE_WINDOW_STATE_CHANGED) {
//            val packageName = event.packageName?.toString()
            val className = event.className?.toString()
//            Log.i(TAG, "onAccessibilityEvent: $packageName / $className")
            if(className == AppLauncherActivity::class.java.name || className == "com.zte.mifavor.outerscreen.home.HomeMainActivity"){
                if(accelerometer != null){
                    Log.i(TAG, "onAccessibilityEvent: register")
                    if(!isResist) {
                        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
                        isResist = true
                    }
                }
            } else if(className == "com.android.launcher3.uioverrides.QuickstepLauncher") {
                Log.i(TAG, "onAccessibilityEvent: unregister")
                if(isResist) {
                    sensorManager.unregisterListener(this)
                    isResist = false
                }
            }
        }
    }

    override fun onInterrupt() {
        //
    }

    override fun onServiceConnected() {
        super.onServiceConnected()

        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPES_ALL_MASK
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            flags = AccessibilityServiceInfo.FLAG_REQUEST_FILTER_KEY_EVENTS
        }
        this.serviceInfo = info

        Log.i(TAG, "onServiceConnected: ${AppLauncherActivity::class.java.name}")

        receiver = MyScreenStateReceiver()
        receiver.register(this, this)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if(accelerometer != null){
            isResist = true
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = event.values[0]  // X軸の加速度
            val y = event.values[1]  // Y軸の加速度

            if (x > X_SHAKE_THRESHOLD || x < -X_SHAKE_THRESHOLD) {
                performGlobalAction(GLOBAL_ACTION_BACK)  // 戻るアクションを実行
            } else if (y > Y_SHAKE_THRESHOLD || y < -Y_SHAKE_THRESHOLD) {
                AppLauncherActivity.start(baseContext, getSystemService(Context.DISPLAY_SERVICE) as DisplayManager)
            } else {

            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onDestroy() {
        super.onDestroy()
        // サービス停止時にセンサーリスナーを解除
        sensorManager.unregisterListener(this)
        receiver.unregister(this)
    }

    companion object{
        private const val TAG = "MyBackService"
    }
}