import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MapScreen() {
    val context = LocalContext.current

    // Aarhus, Denmark coordinates
    val aarhusLocation = GeoPoint(56.1629, 10.2039)

    DisposableEffect(Unit) {
        // Initialize osmdroid configuration
        Configuration.getInstance().userAgentValue = context.packageName
        onDispose { }
    }

    AndroidView(
        factory = { ctx ->
            MapView(ctx).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)

                // Set initial view to Aarhus
                controller.setZoom(13.0)
                controller.setCenter(aarhusLocation)

                // Add marker for Aarhus
                val marker = Marker(this)
                marker.position = aarhusLocation
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                marker.title = "Aarhus, Denmark"
                overlays.add(marker)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}