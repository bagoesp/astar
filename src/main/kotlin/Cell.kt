data class Cell(
    var x: Int = 0,
    var y: Int = 0,
    var top: Boolean = false,
    var right: Boolean = false,
    var bottom: Boolean = false,
    var left: Boolean = false,
    var g: Int = 0,
    var h: Int = 0,
    var f: Int = 0,
    var parent: Cell? = null
)
