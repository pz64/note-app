package pzy64.xnotes.ui.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pzy64.xnotes.data.model.Note

class CreateNoteViewModel : ViewModel() {

    val currentColorIndex = MutableLiveData(0)

    val currentFontIndex = MutableLiveData(0)

    val currentNote = MutableLiveData<Note>()


}
