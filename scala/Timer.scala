object Timer {
  def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0).toFloat/1000000f + "ms")
    result
  }
  def timeIf[R](timed: Boolean)(block: => R): R = {
    if(timed) return time(block)
    block
  }
  def timeCompile[R](str: String)(block: => R): R = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    println(str + " compiled. Elapsed time: " + (t1 - t0).toFloat/1000000f + "ms")
    result
  }
}
