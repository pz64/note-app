package pzy64.xnotes.data.eventbusmodel

class FabButtonActionModel(val action: Action)

enum class Action   {
    SAVE_NOTE,
    NOTE_SAVED
}