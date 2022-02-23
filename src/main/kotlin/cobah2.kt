fun main() {
    val closedList = mutableListOf<Cell>()

    val cells = Array(3) { Array(3) { Cell() } }

    for (x in 0 until 3) {
        for (y in 0 until 3) {
            cells[x][y].x = x
            cells[x][y].y = y
        }
    }

    closedList.add(cells[0][0])
    cells[1][0].parent = cells[0][0]
    closedList.add(cells[1][0])
    cells[1][1].parent = cells[1][0]
    closedList.add(cells[1][1])

    cariRute(closedList.last(), closedList)

}

fun cariRute(current : Cell, list: MutableList<Cell>) {
    println("${current.x},${current.y}")
    val parent = current.parent
    if (parent != null) {
        cariRute(parent, list)
    }
}