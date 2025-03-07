package jp.ac.jec.cm0128.subdisplaylauncherforliberoflip

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Rect
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.util.Log
import android.view.Display
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import jp.ac.jec.cm0128.subdisplaylauncherforliberoflip.ui.theme.SubDisplayLauncherForLiberoFlipTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AppLauncherActivity : ComponentActivity()  {
    lateinit var displays: Array<Display>
    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        displays = (getSystemService(Context.DISPLAY_SERVICE) as DisplayManager).displays
        setContent {
            SubDisplayLauncherForLiberoFlipTheme {
                Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),contentAlignment = Alignment.Center){
                    CircularProgressIndicator(modifier = Modifier.size(40f.dp))
                }
            }
        }
        CoroutineScope(Dispatchers.Default).launch {
            val flags =
                PackageManager.MATCH_UNINSTALLED_PACKAGES or PackageManager.MATCH_DISABLED_COMPONENTS
            val installedAppList = packageManager.getInstalledApplications(flags)
            Log.i(TAG, "onCreate: ${packageManager.hasSystemFeature(PackageManager.FEATURE_FREEFORM_WINDOW_MANAGEMENT)}")
            Log.i(TAG, "onCreate: ${packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)}")

            // ランチャーに表示されるアプリを取得
            val launcherIntent = Intent(Intent.ACTION_MAIN, null).apply {
                addCategory(Intent.CATEGORY_LAUNCHER)
            }
            val launcherAppList = packageManager.queryIntentActivities(launcherIntent, 0)
                .map { it.activityInfo.applicationInfo.packageName }
                .toSet()

            val appDataList = installedAppList
                .filter {
                    (it.flags and ApplicationInfo.FLAG_SYSTEM) == 0 || it.packageName in launcherAppList
                }
                .map {
                    async {
                        AppData(
                            label = it.loadLabel(packageManager).toString(),
                            icon = it.loadIcon(packageManager),
                            packageName = it.packageName,
                        )
                    }.await()
                }

            val sortedList = appDataList.sortedWith(compareBy { it.label })

            runOnUiThread{
                setContent {
                    SubDisplayLauncherForLiberoFlipTheme {
                        View(list = sortedList, modifier = Modifier.fillMaxSize(), this@AppLauncherActivity, displays)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (displays.size != 2 || displays[1].displayId != (display?.displayId ?: -1)) {
            android.app.AlertDialog.Builder(this)
                .setTitle("サブ画面でない場所で開いている可能性があります")
                .setMessage("サブディスプレイに最適化されたレイアウトのため、このまま続行するとレイアウトが崩れる可能性があります。")
                .setPositiveButton("閉じる") { _, _ ->
                    finish()
                }
                .setNegativeButton("続行する", null)
                .show()
        }
    }

    companion object {
        private const val TAG = "AppLauncherActivity"
        @JvmStatic
        fun start(context: Context, displayManager: DisplayManager) {
            val displays = displayManager.displays
            val starter = Intent(context, AppLauncherActivity::class.java)
            starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val option = ActivityOptions.makeBasic()
            option.launchDisplayId = displays[1].displayId
            context.startActivity(starter, option.toBundle())
        }
    }
}

@Composable
fun View(list: List<AppData>, modifier: Modifier = Modifier, context: Context, displays: Array<Display>) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        ScalingLazyColumn(
            modifier = modifier.padding(innerPadding),
        ) {
            items(list){
                AppItem(
                    app = it,
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            val launchIntent =
                                context.packageManager.getLaunchIntentForPackage(it.packageName)
                            if (launchIntent != null) {
                                val option = ActivityOptions.makeBasic()
                                option.launchDisplayId = displays[1].displayId
                                option.launchBounds = Rect(68, 68, 397, 397)
                                startActivity(context, launchIntent, option.toBundle())
                            }
                        }
                )
            }
        }
    }
}

@Composable
fun AppItem(app: AppData, modifier: Modifier = Modifier){
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.fillMaxWidth()) {
        Image(
            painter = rememberDrawablePainter(drawable = app.icon),
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = app.label, fontWeight = FontWeight.Bold)
            Text(text = app.packageName)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    SubDisplayLauncherForLiberoFlipTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(modifier = Modifier.size(40f.dp))
        }

    }
}