package com.example.guessinggame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel(){
    private val words = listOf("Android", "Activity", "Fragment")
    private val secretWord = words.random().uppercase()
    private val _secretWordDisplay = MutableLiveData<String>("")
    val secretWordDisplay : LiveData<String>
        get() = _secretWordDisplay
    private var correctGuesses = ""
    private val _inCorrectGuesses = MutableLiveData<String>("")
    val inCorrectGuesses : LiveData<String>
        get() = _inCorrectGuesses
    private val _levelLife = MutableLiveData<Int>(8)
    val levelLife : LiveData<Int>
        get() = _levelLife

    init {
        _secretWordDisplay.value = deriveSecretWordDisplay()
    }

    private fun deriveSecretWordDisplay() : String {
        var display = ""
        secretWord.forEach {
            display += checkLetter(it.toString())
        }
        return display
    }

    private fun checkLetter(str: String) = when (correctGuesses.contains(str)) {
        true -> str
        false -> "_"
    }
    fun makeGuess(guess: String) {
        if (guess.length == 1) {
            if (secretWord.contains(guess)) {
                correctGuesses += guess
                _secretWordDisplay.value = deriveSecretWordDisplay()
            } else {
                _inCorrectGuesses.value += "$guess "
                _levelLife.value = levelLife.value?.minus(1)
            }
        }
    }
    fun isWon() = secretWord.equals(secretWordDisplay.value, true)
    fun isLost() = (levelLife.value ?: 0) <= 0
    fun wonLostMessage() : String {
        var message = ""
        if (isWon()) message = "You won!"
        else if (isLost()) message = "You lost!"
        message += " The word was $secretWord."
        return message
    }
}