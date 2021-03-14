package ru.byprogminer.programengineeringtask.notes

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface NoteRepository: JpaRepository<Note, Long> {

    @Query("select n from Note n where locate(:query, n.title) > 0 or locate(:query, n.content) > 0")
    fun findByQuery(query: String): Set<Note>
}
