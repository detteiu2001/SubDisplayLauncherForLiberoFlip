package jp.ac.jec.cm0128.subdisplaylauncherforliberoflip

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.SeekBar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.startActivity
import jp.ac.jec.cm0128.subdisplaylauncherforliberoflip.ui.theme.SubDisplayLauncherForLiberoFlipTheme

class SettingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SubDisplayLauncherForLiberoFlipTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {innerPadding ->
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

@OptIn(ExperimentalMaterial3Api::class)
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
    var isShowSortDialog by remember {
        mutableStateOf(false)
    }
    var currentSort by remember {
        mutableStateOf(SortOrder.NAME)
    }
    var isShowStyleDialog by remember {
        mutableStateOf(false)
    }
    var currentStyle by remember {
        mutableStateOf(ShowStyle.LIST)
    }
    Column(modifier = modifier.padding(start = 12.dp, end = 12.dp)) {
        Text(
            text = "設定(工事中)",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Vibration, "振動")
            Column {
                Text(text = "「戻る」の反応する強さ")
                Slider(value = xPower,enabled = false,
                    onValueChange = {
                    xPower = it

                    },
                    valueRange = 10f..50f,
                    steps = 7)
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Vibration, "振動")
            Column {
                Text(text = "「メニューの起動」の反応する強さ")
                Slider(value = yPower,enabled = false,
                    onValueChange = {
                        yPower = it
                    },
                    valueRange = 10f..50f,
                    steps = 7)
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 8.dp).clickable { isShowSortDialog = true }) {
            Icon(Icons.Filled.FormatListNumbered, "パッケージ名")
            Text("並び替え", modifier = Modifier.weight(1f))
            Text(currentSort.label)
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "並び替え", modifier = Modifier.size(24.dp))
        }
        if(isShowSortDialog) {
            ModalBottomSheet(onDismissRequest = {
                isShowSortDialog = false
            }){
                SortOrder.entries.forEach {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = currentSort == it, onClick = {
                            currentSort = it
                            isShowSortDialog = false
                        })
                        Text(text = it.label)
                    }
                }
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.FormatListNumbered, "パッケージ名")
            Text("表示スタイル", modifier = Modifier.weight(1f))
            Button(onClick = {
                isShowStyleDialog = true
            }, enabled = false)
            {
                Text(currentStyle.label)
            }
        }
        if(isShowStyleDialog) {
            Dialog(onDismissRequest = { }){
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    ShowStyle.entries.forEach {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(selected = currentStyle == it, onClick = {
                                currentStyle = it
                                isShowStyleDialog = false
                            })
                            Text(text = it.label)
                        }
                    }
                    Row(modifier = Modifier.padding(12.dp)) {
                        Spacer(modifier = Modifier.weight(1f))
                        Button(onClick = {
                            isShowStyleDialog = false
                        }) {
                            Text("閉じる")
                        }
                    }
                }
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.QuestionMark, "パッケージ名")
            Text("アプリ一覧にパッケージ名を表示する", modifier = Modifier.weight(1f))
            Switch(checked = isShowPackageName, onCheckedChange = {
                isShowPackageName = it
            }, modifier = modifier, enabled = false)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Accessibility, "パッケージ名")
            Text("ユーザー補助", modifier = Modifier.weight(1f))
            Button(onClick = {
                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                startActivity(context, intent, Bundle())
            })
            {
                Text("設定画面へ")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    SubDisplayLauncherForLiberoFlipTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SettingView(
                context = SettingActivity(),
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}