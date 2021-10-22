package ir.alimatin.memorial.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ir.alimatin.memorial.R
import ir.alimatin.memorial.viewmodel.UserVerifyActivityViewModel
import kotlinx.android.synthetic.main.activity_user_verify.*

class UserVerifyActivity : AppCompatActivity() {
    private var btnUserVerify: Button? = null
    lateinit var userVerifyActivityViewModel: UserVerifyActivityViewModel
    //private var phoneMaskManager: PhoneMaskManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_verify)

        init()

        btnUserVerify?.setOnClickListener {
            if (et_Mobile.length() < 13){
                showToast("length < 13")
            } else {
                userVerifyActivityViewModel = ViewModelProvider(this).get(UserVerifyActivityViewModel::class.java)

                //wp7progressBar.showProgressBar()

                //userVerifyActivityViewModel.getUserVerify()!!.observe(this, Observer { dataUser ->

                    //wp7progressBar.hideProgressBar()

                    //val msg = dataUser.firstName

                    //lblResponse.text = msg

                    startActivity(Intent(this@UserVerifyActivity, CodeVerifyActivity::class.java))

                //})
            }
        }
    }

    private fun init(){
        btnUserVerify = findViewById(R.id.btn_UserVerify)
        /*phoneMaskManager = PhoneMaskManager()

        phoneMaskManager
                .withMask(" ### ### ####")
                .withRegion("+98")
                .bindTo(findViewById(R.id.et_Mobile))*/
    }

    private fun showToast(toast: String) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
    }
}


/*
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.emedinaa.kotlinmvvm.view.MuseumAdapter
import ir.alimatin.memorial.R
import ir.alimatin.memorial.di.UserVerifyInjection
import ir.alimatin.memorial.model.UserVerifyModel
import ir.alimatin.memorial.viewmodel.UserVerifyViewModel
import kotlinx.android.synthetic.main.activity_museum.*
import kotlinx.android.synthetic.main.layout_error.*

/**
 * @author Eduardo Medina
 */
class UserVerifyActivity : AppCompatActivity() {

    private lateinit var viewModel: UserVerifyViewModel
    private lateinit var adapter: MuseumAdapter


    /**
    //Consider this, if you need to call the service once when activity was created.
    Log.v(TAG,"savedInstanceState $savedInstanceState")
    if(savedInstanceState==null){
    viewModel.loadMuseums()
    }
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_verify)

        setupViewModel()
        setupUI()
    }

    //ui
    private fun setupUI() {
        adapter = MuseumAdapter(viewModel.museums.value ?: emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    //view model
    private fun setupViewModel() {
        viewModel = ViewModelProvider(
                this,
                UserVerifyInjection.provideViewModelFactory()
        ).get(UserVerifyViewModel::class.java)

        viewModel.museums.observe(this, renderMuseums)
        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
        viewModel.isEmptyList.observe(this, emptyListObserver)
    }

    //observers
    private val renderMuseums = Observer<List<UserVerifyModel>> {
        Log.v(TAG, "data updated $it")
        //layoutError.visibility = View.GONE
        //layoutEmpty.visibility = View.GONE
        adapter.update(it)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        Log.v(TAG, "isViewLoading $it")
        val visibility = if (it) View.VISIBLE else View.GONE
        //progressBar.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.v(TAG, "onMessageError $it")
        //layoutError.visibility = View.VISIBLE
        //layoutEmpty.visibility = View.GONE
        //textViewError.text = "Error $it"
    }

    private val emptyListObserver = Observer<Boolean> {
        Log.v(TAG, "emptyListObserver $it")
        //layoutEmpty.visibility = View.VISIBLE
        //layoutError.visibility = View.GONE
    }

    //If you require updated data, you can call the method "loadMuseum" here
    override fun onResume() {
        super.onResume()
        viewModel.loadUserVerify()
    }

    companion object {
        const val TAG = "CONSOLE"
    }
}
*/