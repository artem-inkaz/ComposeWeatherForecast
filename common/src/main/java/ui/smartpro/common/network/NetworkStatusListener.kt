package ui.smartpro.common.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class NetworkStatusListener @Inject constructor( context: Context) {

    private val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val networkStatus = callbackFlow {
        val networkStatusCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onUnavailable() {
                trySend(ConnectionState.Unavailable)
            }

            override fun onLost(network: Network) {
                trySend(ConnectionState.Unavailable)
            }

            override fun onAvailable(network: Network) {
                trySend(ConnectionState.Available)
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        cm.registerNetworkCallback(request, networkStatusCallback)

        if (isNetworkAvailable()) trySend(ConnectionState.Available)
        else trySend(ConnectionState.Unavailable)

        awaitClose {
            cm.unregisterNetworkCallback(networkStatusCallback)
        }
    }

    private fun isNetworkAvailable() = cm.run {
        getNetworkCapabilities(activeNetwork)?.run {
            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    }
}