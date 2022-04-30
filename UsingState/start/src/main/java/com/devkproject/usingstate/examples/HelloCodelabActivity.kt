package com.devkproject.usingstate.examples

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devkproject.usingstate.databinding.ActivityHelloCodelabBinding

//구조화되지 않은 상태
class HelloCodelabActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHelloCodelabBinding
    var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelloCodelabBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // doAfterTextChange is an event that modifies state
        binding.textInput.doAfterTextChanged { text ->
            name = text.toString()
            updateHello()
        }
    }

    private fun updateHello() {
        binding.helloText.text = "Hello, $name"
    }
}

//단방향 데이터 흐름 사용
//이벤트는 위로 흐르고, 상태는 아래로 흐르는 디자인
class HelloCodelabViewModel: ViewModel() {

    //LiveData holds state which is observed by the UI
    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    //onNameChanged is an event we're defining that the UI can invoke
    fun onNameChanged(newName: String) {
        _name.value = newName
    }
}

class HelloCodeLabActivityWithViewModel: AppCompatActivity() {
    private val helloViewModel by viewModels<HelloCodelabViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHelloCodelabBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //doAfterTextChange is an event that triggers an event on the ViewModel
        binding.textInput.doAfterTextChanged {
            //onNameChanged is an event on the ViewModel
            helloViewModel.onNameChanged(it.toString())
        }

        helloViewModel.name.observe(this) { name ->
            binding.helloText.text = "Hello, $name"
        }
    }
}