class SpecialFunOp(n: Int) extends Operation {
  override def execute(ah: ArrayHandler): Unit = {
    val arr = new ArrayHandler((n + 1)*2, false)
    ah.copyTo(arr, n)
    FunOp.execute(arr)
    ah.update(arr.getChar)
  }
}
