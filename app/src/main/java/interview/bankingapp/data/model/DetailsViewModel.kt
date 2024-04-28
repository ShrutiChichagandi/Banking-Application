package interview.bankingapp.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailsViewModel : ViewModel() {

    var _isPanValid = MutableLiveData<Boolean>()
    val isPanValid: LiveData<Boolean> = _isPanValid

    var _isDateValid = MutableLiveData<Boolean>()
    val isDateValid: LiveData<Boolean> = _isDateValid

    var _isMonthValid = MutableLiveData<Boolean>()
    val isMonthValid: LiveData<Boolean> = _isMonthValid

    var _isYearValid = MutableLiveData<Boolean>()
    val isYearValid: LiveData<Boolean> = _isYearValid

    var _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    fun validatePAN(pan: String, date: String, month: String, year: String) {
        //make api call
        _success.value = true
    }

    private val _finalResult = MediatorLiveData<Boolean>()
    val finalResult: LiveData<Boolean> = _finalResult

    init {
        _finalResult.addSource(isPanValid) { validateAllFields() }
        _finalResult.addSource(isDateValid) { validateAllFields() }
        _finalResult.addSource(isMonthValid) { validateAllFields() }
        _finalResult.addSource(isYearValid) { validateAllFields() }
    }

    private fun validateAllFields() {
        if (isPanValid.value != null && isDateValid.value != null && isMonthValid.value != null && isYearValid.value != null) {
            _finalResult.value =
                isPanValid.value!! && isDateValid.value!! && isMonthValid.value!! && isYearValid.value!!

        }
    }
}
