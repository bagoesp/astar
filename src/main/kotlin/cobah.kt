fun main() {
    val cell1 = Cell(x = 0, y = 0, g = 10)
    val cell2 = Cell(x = 0, y = 0, g = 15)
    println(cell1)
    println(cell2)

    val openList = mutableListOf<Cell>()
    openList.add(cell1)

    if (checkExistence(openList, cell2))
        println("Ada")
    else
        println("Nda ada bos!")

    if (openList.contains(cell2))
        println("Ada")
    else
        println("Nda ada bos!")
}

fun checkExistence(list: MutableList<Cell>, current: Cell): Boolean {
    for (cell in list ) {
        if (cell.x == current.x && cell.y == current.y)
            return true
    }
    return false
}