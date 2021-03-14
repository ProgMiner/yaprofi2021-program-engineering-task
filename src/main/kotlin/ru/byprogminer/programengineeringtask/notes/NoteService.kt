package ru.byprogminer.programengineeringtask.notes

import java.util.*

interface NoteService {

    fun getNotes(): Set<Note>
    fun getNote(id: Long): Optional<Note>
    fun findNotes(query: String): Set<Note>

    fun addNote(note: Note): Optional<Note>
    fun updateNote(id: Long, updates: Note): Optional<Note>
    fun deleteNote(id: Long): Boolean
}
