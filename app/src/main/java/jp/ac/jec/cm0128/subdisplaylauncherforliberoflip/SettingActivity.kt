package jp.ac.jec.cm0128.subdisplaylauncherforliberoflip

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.SeekBar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import jp.ac.jec.cm0128.subdisplaylauncherforliberoflip.ui.theme.SubDisplayLauncherForLiberoFlipTheme

class SettingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SubDisplayLauncherForLiberoFlipTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SettingView(
                        context = this,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, SettingActivity::class.java)
            context.startActivity(starter)
        }
    }
}

@Composable
fun SettingView(modifier: Modifier = Modifier, context: Context) {
    var isShowPackageName by remember {
        mutableStateOf(true)
    }
    var xPower by remember {
        mutableFloatStateOf(30f)
    }
    var yPower by remember {
        mutableFloatStateOf(30f)
    }
    Column {
        Text(
            text = "設定",
            modifier = modifier,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
            Icon(Icons.Filled.QuestionMark, "パッケージ名")
            Text("アプリ一覧にパッケージ名を表示する", modifier = Modifier.weight(1f))
            Switch(checked = isShowPackageName, onCheckedChange = {
                isShowPackageName = it
            }, modifier = modifier)
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
            Icon(Icons.Filled.Vibration, "振動")
            Column(modifier = Modifier.padding(end = 24.dp)) {
                Text(text = "「戻る」の反応する強さ")
                Slider(value = xPower,
                    onValueChange = {
                    xPower = it

                    },
                    valueRange = 10f..50f,
                    steps = 7)
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
            Icon(Icons.Filled.Vibration, "振動")
            Column(modifier = Modifier.padding(end = 18.dp)) {
                Text(text = "「メニューの起動」の反応する強さ")
                Slider(value = yPower,
                    onValueChange = {
                        yPower = it
                    },
                    valueRange = 10f..50f,
                    steps = 7)
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
            Icon(Icons.Filled.Accessibility, "パッケージ名")
            Text("ユーザー補助", modifier = Modifier.weight(1f))
            Button(onClick = {
                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                startActivity(context, intent, Bundle())
            },
                modifier = modifier)
            {
                Text("設定画面へ")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SubDisplayLauncherForLiberoFlipTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SettingView(
                context = SettingActivity(),
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}