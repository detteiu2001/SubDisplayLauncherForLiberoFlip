package jp.ac.jec.cm0128.subdisplaylauncherforliberoflip

import android.app.AlertDialog
import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val displayManager = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        if(displayManager.displays.size > 1 && display?.displayId == displayManager.displays[1].displayId){
            AppLauncherActivity.start(this@MainActivity, displayManager)
            finish()
        } else {
            AlertDialog.Builder(this).run {
                val displays = displayManager.displays
                if (displays.size >= 2) {
                    setTitle("サブディスプレイランチャーを起動します")
                    setMessage("Fibero Flip本体を閉じ、サブディスプレイを操作してください")
                    setPositiveButton("OK") { _, _ ->
                        AppLauncherActivity.start(this@MainActivity, displayManager)
                        finish()
                    }
                    setNeutralButton("設定") { _, _ ->
                        SettingActivity.start(this@MainActivity)
                        finish()
                    }
                } else {
                    setTitle("サブディスプレイが検出されませんでした")
                    setMessage("開発者はFibero Flipでしか動作確認をしていません")
                }
                setCancelable(false)
                setNegativeButton("閉じる") { _, _ ->
                    finish()
                }
                show()
            }
        }
    }
}