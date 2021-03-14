package ru.byprogminer.programengineeringtask.notes.api.v1

import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import ru.byprogminer.programengineeringtask.notes.Note
import ru.byprogminer.programengineeringtask.notes.NoteService
import kotlin.math.min

@RestController
@RequestMapping("/notes")
class NotesController(
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Value("\${notes.content_title_length:5}")
    private val contentTitleLength: Int,
    private val service: NoteService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Добавление заметки")
    fun addNote(@RequestBody note: NoteDto): NoteDto =
        service.addNote(note.toEntity()).map { it.toDto() }
            .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "content cannot be null") }

    @GetMapping("/{id}")
    @ApiOperation("Получение заметки по id")
    fun getNote(@PathVariable id: Long): ResponseEntity<NoteDto> =
        service.getNote(id).map { it.toDto() }.map { ResponseEntity.ok(it) }
            .orElseGet { ResponseEntity.notFound().build() }

    @PutMapping("/{id}")
    @ApiOperation("Изменение заметки по id")
    fun updateNote(@PathVariable id: Long, @RequestBody note: NoteDto): ResponseEntity<NoteDto> =
        service.updateNote(id, note.toEntity()).map { it.toDto() }.map { ResponseEntity.ok(it) }
            .orElseGet { ResponseEntity.notFound().build() }

    @GetMapping
    @ApiOperation("Поиск заметок по ключевому слову или получение всех")
    fun findNotes(@RequestParam(required = false) query: String?): Set<NoteDto> =
        if (query.isNullOrBlank()) {
            service.getNotes()
        } else {
            service.findNotes(query)
        }.map { it.toDto() }.toSet()

    @DeleteMapping("/{id}")
    @ApiOperation("Удаление заметки по id")
    fun deleteNote(@PathVariable id: Long): ResponseEntity<Void> =
        if (service.deleteNote(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }

    private fun NoteDto.toEntity(): Note = Note(null, title, content)

    private fun Note.toDto(): NoteDto = NoteDto(
        id,
        title ?: content?.let { content -> content.substring(0 until min(content.length, contentTitleLength)) },
        content ?: throw IllegalArgumentException(),
    )
}
