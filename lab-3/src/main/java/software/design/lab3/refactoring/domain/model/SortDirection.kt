package software.design.lab3.refactoring.domain.model

enum class SortDirection(val sql: String) {
    ASC("ASC"),
    DESC("DESC"),
    ;
}
