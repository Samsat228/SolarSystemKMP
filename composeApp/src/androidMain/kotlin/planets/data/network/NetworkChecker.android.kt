package planets.data.network


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object AndroidContextHolder {
    lateinit var appContext: Context
}

actual fun isNetworkAvailable(): Boolean {
    val context = AndroidContextHolder.appContext
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetwork ?: return false
    val caps = cm.getNetworkCapabilities(network) ?: return false
    return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}