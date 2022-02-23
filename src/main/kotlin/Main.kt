import kotlin.math.abs

fun main(args: Array<String>) {
    val cols = 4
    val rows = 3
    val cells = Array(cols) { Array(rows) { Cell() } }

    for (x in 0 until cols) {
        for (y in 0 until rows) {
            cells[x][y].x = x
            cells[x][y].y = y
        }
    }

    // top wall
    for (x in 0 until cols) {
        val y = 0
        cells[x][y].top = true
    }

    // left wall
    for (y in 0 until rows) {
        val x = 0
        cells[x][y].left = true
    }

    // bottom wall
    for (x in 0 until cols) {
        val y = rows-1
        cells[x][y].bottom = true
    }

    // right wall
    for (y in 0 until rows) {
        val x = cols-1
        cells[x][y].right = true
    }

    cells[3][1].left = true
    cells[2][1].right = true
    cells[1][2].right = true
    cells[2][2].left = true

    val openList: MutableList<Cell> = mutableListOf()
    val closedList: MutableList<Cell> = mutableListOf()

    val path = mutableListOf<String>()

    val start = cells[0][0]
    val goal = cells[cols-1][rows-1]

    openList.add(start)

    while (openList.isNotEmpty()) {
        openList.sortedBy { it.f }

        val current = openList[0]

        closedList.add(current)
        openList.removeAt(0)

        if (current.x == goal.x && current.y == goal.y) {
            return tracePath(current, closedList, path)
        }

        val neighbours = mutableListOf<Cell>()

        addNeighbour(cells, neighbours, current)

        for (n in neighbours) {
            // 1. avoid closedset
            // 2. if cell is not already exist in openList, generate g,h,f value of cell,
            //      current cell as a parent of neighbour cell, then add to openList
            // 3. if cell is already exist, check betterPath with the g value as a parameter
            //      if not don't do anything
            //      if so, recalculate g,h,f and the parent of neighbour cell

            if (!checkExistence(n, closedList)) {
                if (!checkExistence(n, openList)) {
                    calculateCost(n, current, goal)
                    changeParent(n, current)
                    openList.add(n)
                }
                else {
                    // if n's g value with current cell as parent is less than original n's g value
                    // then, change the original n's g value with the new one
                    // else, don't do anything

                    // calculate n's g value with current as a parent
                    val newG = calculateG(n, current)
                    val oldG = n.g

                    if (newG < oldG) {
                        calculateCost(n, current, goal)
                        changeParent(n, current)
                    }
                }
            }
        }

        neighbours.clear()

    }

    if (path.isNotEmpty()) {
        for (p in path.size-1 downTo 0) {
            println(p)
        }
    }
    else failed()

}

fun failed() {
    println("Failed!")
}

fun tracePath(current: Cell, closedList: MutableList<Cell>, path: MutableList<String>) {
    // start from the last cell that was added to closedlist
    // check the parent of the lastCell
    // find cell in the closedList where x and y value is same as parent of the last cell
    // add the found cell to the pathList
    // loop until the parent of cell is null
    // print all the x and y of cell in the pathList

    path.add("${current.x},${current.y}")
    val parent = current.parent
    if (parent != null) {
        tracePath(parent, closedList, path)
    }
}

fun changeParent(n: Cell, current: Cell) {
    n.parent = current
}

fun calculateCost(n: Cell, current: Cell, goal: Cell) {
    // calculate g
    // need current and move direction as a parameter
    n.g = calculateG(n, current)

    // calculate h with manhattan
    // need goal x and y as parameter
    calculateH(n, goal)

    // calculate f
    // need g and f as parameter
    calculateF(n)
}

fun calculateG(n: Cell, current: Cell) : Int{
    //check the direction
    //vertical
    if (abs(current.y - n.y) == 1 && (current.x - n.x) == 0) {
        return current.g + 10
    }
    //horizontal
    if (abs(current.x - n.x) == 1 && (current.y - n.y) == 0) {
        return current.g + 10
    }
    //diagonal
    if (abs(current.x - n.x) == 1 && abs(current.y - n.y) == 1) {
        return current.g + 14
    }

    else return n.g
}

fun calculateH(n: Cell, goal: Cell) {
    // manhattan distance
    val h = (abs(n.x - goal.x)*10) + ((n.y - goal.y)*10)
    n.h = h
}

fun checkExistence(n: Cell, list: MutableList<Cell>): Boolean {
    for (cell in list) {
        if (cell.x == n.x && cell.y == n.y)
            return true
    }
    return false
}

fun calculateF(n: Cell) {
    n.f = n.g + n.h
}

fun addNeighbour(cells: Array<Array<Cell>>, neighbours: MutableList<Cell>, current: Cell) {
    // avoid wall
    // top
    if (!current.top)
        neighbours.add(cells[current.x][current.y-1])

    //top right
    if (!current.top && !current.right && !cells[current.x][current.y-1].right && !cells[current.x+1][current.y].top)
        neighbours.add(cells[current.x+1][current.y-1])

    // right
    if (!current.right)
        neighbours.add(cells[current.x+1][current.y])

    // bottom right
    if (!current.right && !current.bottom && !cells[current.x+1][current.y].bottom && !cells[current.x][current.y+1].right)
        neighbours.add(cells[current.x+1][current.y+1])

    // bottom
    if (!current.bottom)
        neighbours.add(cells[current.x][current.y+1])

    // bottom left
    if (!current.bottom && !current.left && !cells[current.x-1][current.y].bottom && !cells[current.x][current.y+1].left )
        neighbours.add(cells[current.x-1][current.y+1])

    // left
    if (!current.left)
        neighbours.add(cells[current.x-1][current.y])

    // top left
    if (!current.left && !current.top && !cells[current.x][current.y-1].left && !cells[current.x-1][current.y].top)
        neighbours.add(cells[current.x-1][current.y-1])
}