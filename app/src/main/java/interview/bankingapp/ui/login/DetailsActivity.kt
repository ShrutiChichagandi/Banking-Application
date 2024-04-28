package interview.bankingapp.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import interview.bankingapp.data.model.DetailsViewModel
import interview.bankingapp.databinding.ActivityLoginBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)

        binding.buttonNext?.setOnClickListener {

            //make api call
            viewModel.validatePAN(
                binding.enterPanNumber.text.toString(),
                binding.enterDate.text.toString(),
                binding.enterMonth.text.toString(),
                binding.enterYear.text.toString()
            )

        }
        viewModel.success.observe(this, { isSuccess ->
            if (isSuccess) {
                Toast.makeText(this, "Details submitted successfully ", Toast.LENGTH_LONG).show()

                finish()
            }
        })
        viewModel.finalResult.observe(this) { isValid ->
            if (isValid) {
                binding.buttonNext.isEnabled = true
            }
        }


        binding.enterPanNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) return

                val panNumber = s.toString()
                if (validatePANFormat(panNumber)) {
                    viewModel._isPanValid.value = true

                } else {
                    binding.enterPanNumber.error =
                        "Invalid PAN number format. It should be in the format: ABCDE1234F"
                }
            }
        })
        binding.enterDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) return

                val enteredDate = s.toString().toIntOrNull()

                if (enteredDate != null && enteredDate in 1..31) {
                    viewModel._isDateValid.value = true

                } else {
                    binding.enterDate.error = "Invalid date. Please enter a date between 1 and 31."
                }
            }
        })


        binding.enterMonth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) return

                val enteredMonth = s.toString().toIntOrNull()
                if (enteredMonth != null && enteredMonth in 1..12) {
                    viewModel._isMonthValid.value = true
                } else {
                    binding.enterMonth.error =
                        "Invalid month. Please enter a month between 1 and 12."
                }
            }
        })

        binding.enterYear.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) return
                if (s.length == 4) {
                    val enteredYear = s.toString().toIntOrNull()

                    if (enteredYear != null && enteredYear >= 1900 && enteredYear.toString()
                            .startsWith("1")
                    ) {
                        viewModel._isYearValid.value = true

                    } else {
                        binding.enterYear.error =
                            "Invalid year. Please enter a year starting with 1 and >= 1900."
                    }
                } else {
                    binding.enterYear.error = null
                }
            }
        })


        binding.panDontHave.setOnClickListener {
            finish()
        }

    }


    private fun validatePANFormat(panNumber: String): Boolean {
        // Regular expression to match the PAN number format
        val panRegex = "[A-Z]{5}[0-9]{4}[A-Z]{1}".toRegex()
        return panNumber.matches(panRegex)
    }
}