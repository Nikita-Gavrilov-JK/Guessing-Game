package com.example.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.guessinggame.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: GameViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
//        updateScreen()
        viewModel.inCorrectGuesses.observe(viewLifecycleOwner, Observer {
            newValue -> binding.incorrectGuesses.text = "Incorrect guesses: $newValue" })
        viewModel.levelLife.observe(viewLifecycleOwner, Observer {
            newValue -> binding.lives.text = "You have $newValue lives left" })
        viewModel.secretWordDisplay.observe(viewLifecycleOwner, Observer {
            newValue -> binding.word.text = newValue
        })
        binding.guessButton.setOnClickListener() {
            viewModel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
//            updateScreen()
            if (viewModel.isWon() || viewModel.isLost()) {
                val action = GameFragmentDirections
                    .actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    fun updateScreen() {
//        binding.word.text = viewModel.secretWordDisplay
//        binding.lives.text = "You have ${viewModel.levelLife} lives left."
//        binding.incorrectGuesses.text = "Incorrect guesses: ${viewModel.inCorrectGuesses}"
//    }
}