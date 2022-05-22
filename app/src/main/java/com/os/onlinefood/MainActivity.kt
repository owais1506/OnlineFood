package com.os.onlinefood


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.os.onlinefood.databinding.ActivityMainBinding
import com.os.onlinefood.ui.fragment.DonutFragment
import com.os.onlinefood.ui.fragment.ToppingFragment
import com.os.onlinefood.ui.viewmodel.MainViewModel
import com.os.onlinefood.util.Result
import com.os.onlinefood.util.interfaces.DonutInterface
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity(), DonutInterface {
    val className = MainActivity::class.java.name
    lateinit var mainUi: ActivityMainBinding
    private var fragment: Fragment? = null
    private var tag = ""
    val mainVm by inject<MainViewModel>()
    val app by inject<AppController>()
    private var fragmentManager: FragmentManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        mainUi = DataBindingUtil.setContentView(this, R.layout.activity_main)
        fragmentManager = supportFragmentManager

        if(mainVm.getDonutCount() == 0) {
            if (app.isConnected()) {
                mainVm.getAllDonutApi()
            } else {
                app.showErrorMsg("No Internet Connection")
            }
        }
    }


    override fun onBackPressed() {


        if(fragment is DonutFragment){
            finish()
        }


        if (fragment is ToppingFragment) {
            setUpFragment(DonutFragment(),DonutFragment.TAG)
            return
        }
        //super.onBackPressed()

    }


    override fun onResume() {
        super.onResume()

        Log.e(className,"OnResume")
        if(fragment == null) {
            setUpFragment(DonutFragment(),DonutFragment.TAG)
        }



        mainVm.result.observe(this, Observer {

            if (it != null) {

                when (it) {
                    is Result.Loading -> {
                        if (it.status) {
                            mainUi.pbCircular.visibility = View.VISIBLE
                        } else {
                            mainUi.pbCircular.visibility = View.GONE
                        }
                    }


                    is Result.SuccessMsg -> {
                        if (it.msg.startsWith("S")) {
                            app.showMsgSuccess(it.msg)
                        } else {
                            app.showErrorMsg(it.msg)
                        }
                    }

                    is Result.Error.RecoverableError -> {
                        app.showErrorMsg(it.exception.message)
                    }

                    is Result.Error.NonRecoverableError -> {
                        app.showErrorMsg(it.exception.message)
                    }

                    else -> {
                        app.showErrorMsg("Oops something went wrong...")
                    }
                }
            }
        })

    }

    override fun onDonutClick(id: Int) {
        setUpFragment(ToppingFragment(),ToppingFragment.TAG,id)
    }

    fun setUpFragment(fragment : Fragment, tag : String,id:Int =0){
        Log.e(className,"SetupFragment")
        val mBundle = Bundle()
        this.fragment = fragment
        this.tag = tag
        mBundle.putInt("Id", id)

        this.fragment?.arguments = mBundle
        fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment, this.fragment!!)
                ?.addToBackStack(tag)
                ?.commit()
    }


}