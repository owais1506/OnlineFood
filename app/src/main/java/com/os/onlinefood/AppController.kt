package com.os.onlinefood

import android.annotation.SuppressLint
import android.app.Application
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.os.onlinefood.data.di.ApplicationModule
import com.os.onlinefood.data.di.DatabaseModule
import com.os.onlinefood.data.di.MainVM
import com.os.onlinefood.data.di.NetworkDependency
import com.os.onlinefood.util.baseUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AppController : Application() {

    companion object{
        @Volatile
        var instance: AppController ? = null

        private val LOCK = Any()

        operator fun invoke() = instance ?: synchronized(LOCK){
            getAppInstance()
        }

        fun  getAppInstance() :AppController{
            if(instance == null){
                instance = AppController()
            }
            return instance as AppController
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initKoin()
    }

    private fun initKoin(){
        startKoin {
            androidContext(this@AppController)
            androidLogger()
            modules(
                listOf(
                    MainVM,
                    ApplicationModule,
                    NetworkDependency,
                    DatabaseModule
                )
            )
        }
    }

    fun getRetrofitClient() = retrofitClient(baseUrl,getHttpClient())

    fun retrofitClient(baseUrl: String, httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().run {
            baseUrl(baseUrl)
            client(httpClient)
            addConverterFactory(GsonConverterFactory.create())
            build()
            //addConverterFactory(GsonConverterFactory.create())
        }

    fun getHttpClient() : OkHttpClient {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().run {
            interceptors().add(interceptor)
            readTimeout(60, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)
            build()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun showMsgSuccess(msg: String?) {
        val _context = applicationContext
        val toast = Toast.makeText(_context, "", Toast.LENGTH_SHORT)
        val inflater = LayoutInflater.from(_context)
        val toastRoot: View = inflater.inflate(R.layout.toast_msg, null)
        val errorText = toastRoot.findViewById<TextView>(R.id.errorText)
        val messageLayOut = toastRoot.findViewById<LinearLayout>(R.id.connectionErrorLayout)
        errorText.text = msg
        messageLayOut.background = _context.resources.getDrawable(R.color.cpb_green)
        toast.duration = Toast.LENGTH_LONG
        toast.setView(toastRoot)
        toast.setGravity(Gravity.FILL_HORIZONTAL or Gravity.BOTTOM, 0, 0)
        toast.show()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun showErrorMsg(msg: String?) {
        val _context = applicationContext
        val toast = Toast.makeText(_context, "", Toast.LENGTH_SHORT)
        val inflater = LayoutInflater.from(_context)
        val toastRoot: View = inflater.inflate(R.layout.toast_msg, null)
        val errorText = toastRoot.findViewById<TextView>(R.id.errorText)
        val messageLayOut = toastRoot.findViewById<LinearLayout>(R.id.connectionErrorLayout)
        errorText.text = msg
        messageLayOut.background = _context.resources.getDrawable(R.color.toastBg)
        toast.duration = Toast.LENGTH_LONG
        toast.setView(toastRoot)
        toast.setGravity(Gravity.FILL_HORIZONTAL or Gravity.BOTTOM, 0, 0)
        toast.show()
    }

    fun isConnected(): Boolean {
        var result = false
        val connectivityManager =
            applicationContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return result
    }


}