class ArrayHandler(n: Int, startHalf: Boolean) {
  private var arr = new Array[Int](n)
  private var ptr = if(startHalf) n/2 else 0
  private var originPoint = ptr
  private def resizeRight(): Unit = {
    val a = new Array[Int](arr.length * 2)
    Array.copy(arr, 0, a, 0, arr.length)
    arr = a
  }
  private def resizeLeft(): Unit = {
    val n = arr.length
    val a = new Array[Int](n * 2)
    Array.copy(arr, 0, a, n, n)
    ptr += n
    originPoint += n
    arr = a
  }
  def increase(i: Int): Unit = arr(ptr) += i
  def decrease(i: Int): Unit = arr(ptr) -= i
  def moveLeft(i: Int): Unit = {
    while(ptr - i < 0) resizeLeft()
    ptr -= i
  }
  def moveRight(i: Int): Unit = {
    while(ptr + i >= arr.length) resizeRight()
    ptr += i
  }
  def setChar(): Unit = arr(ptr) = InputHandler.getCh.toInt
  def printChar(): Unit = print(arr(ptr).toChar)
  def getChar: Int = arr(ptr)
  def printData(): Unit = {
    for(i <- arr.indices)
      if(arr(i) != 0) println((i - originPoint) + " : " + arr(i))
    println("Pointer at: " + (ptr- originPoint))
  }
  def getPtr: Int = ptr - originPoint
  def update(i: Int): Unit = arr(ptr) = i
  def copyTo(ah: ArrayHandler, n: Int): Unit = {
    for(i <- 0 to n)
      if(i + ptr < arr.length)
        ah.arr(ah.ptr + i) = arr(ptr + i)
  }
}
