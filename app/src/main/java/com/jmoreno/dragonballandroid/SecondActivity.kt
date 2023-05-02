package com.jmoreno.dragonballandroid

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.jmoreno.dragonballandroid.databinding.ActivityMainBinding
import com.jmoreno.dragonballandroid.databinding.ActivitySecondBinding
import kotlinx.coroutines.launch

class SecondActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySecondBinding
    companion object {
        const val TOKEN = "TOKEN"
    }
    private val viewModel: SecondActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val token = intent.getStringExtra("TOKEN")
        //binding.textView3.text = token.toString()
        viewModel.downloadListOfHeroes(token.toString())

        val fragmentList = supportFragmentManager.beginTransaction()
        fragmentList.add(R.id.fFragmentList,nextFragment)
        fragmentList.commit()

        lifecycleScope.launch {

            viewModel.uiState.collect{
                when (it){
                    is SecondActivityViewModel.UiState.OnListReceived ->
                        //binding.textView3.text = it.toString()
                    //is SecondActivityViewModel.UiState.Error -> binding.textView3.text = it.error
                    else -> Unit

                }
            }
        }
    }

    fun getIntent(context: Context,token: String): Intent {
        val intent = Intent(context, SecondActivity::class.java)
        intent.putExtra(TOKEN, token)
        return intent

    }
}