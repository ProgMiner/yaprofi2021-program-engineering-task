package ru.byprogminer.programengineeringtask.notes

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
open class Note(
    @Id @GeneratedValue
    open var id: Long?,
    open var title: String?,
    @Column(nullable = false)
    open var content: String?,
) {

    constructor(): this(null, null, null)
    constructor(title: String?, content: String): this(null, title, content)
}
