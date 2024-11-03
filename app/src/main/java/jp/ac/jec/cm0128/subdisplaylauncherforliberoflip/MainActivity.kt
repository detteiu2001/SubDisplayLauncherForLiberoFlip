package jp.ac.jec.cm0128.subdisplaylauncherforliberoflip

import android.app.AlertDialog
import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import jp.ac.jec.cm0128.subdisplaylauncherforliberoflip.ui.theme.SubDisplayLauncherForLiberoFlipTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val display = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        AlertDialog.Builder(this).run {
            val displays = display.displays
            if(displays.size == 2) {
                setTitle("サブディスプレイランチャーを起動します")
                setMessage("Fibero Flip本体を閉じ、サブディスプレイを操作してください")
                setPositiveButton("OK") { _, _ ->
                    AppLauncherActivity.start(this@MainActivity, display)
                    finish()
                }
                setNeutralButton("設定"){_, _->
                    SettingActivity.start(this@MainActivity)
                    finish()
                }
            } else {
                setTitle("サブディスプレイが検出されませんでした")
                setMessage("開発者はFibero Flipでしか動作確認をしていません")
            }
            setCancelable(false)
            setNegativeButton("キャンセル"){_, _ ->
                finish()
            }
            show()
        }
    }
}