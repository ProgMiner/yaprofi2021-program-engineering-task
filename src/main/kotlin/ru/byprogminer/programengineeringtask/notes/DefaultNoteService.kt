package ru.byprogminer.programengineeringtask.notes

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class DefaultNoteService(private val repository: NoteRepository): NoteService {

    override fun getNotes(): Set<Note> =
        repository.findAll().toSet()

    override fun getNote(id: Long): Optional<Note>
        = repository.findById(id)

    override fun findNotes(query: String): Set<Note> =
        repository.findByQuery(query)

    override fun addNote(note: Note): Optional<Note> {
        note.id = null

        if (note.title.isNullOrBlank()) {
            note.title = null
        }

        if (note.content.isNullOrBlank()) {
            return Optional.empty()
        }

        return Optional.of(repository.save(note))
    }

    override fun updateNote(id: Long, updates: Note): Optional<Note> {
        val note = repository.findById(id).orElse(null)
            ?: return Optional.empty()

        updates.id = id
        updates.title = updates.title ?: note.title
        updates.content = updates.content ?: note.content

        return Optional.of(repository.save(updates))
    }

    override fun deleteNote(id: Long): Boolean {
        if (!repository.existsById(id)) {
            return false
        }

        repository.deleteById(id)
        return true
    }
}
