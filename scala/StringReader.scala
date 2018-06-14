class StringReader(private var str: String) extends Read {
  override def nextCh: Char = {
    val ch = str.head
    str = str.tail
    ch
  }
  override def isEmpty: Boolean = str.isEmpty
  override def nonEmpty: Boolean = str.nonEmpty
  override def peek: Char = str.head
}
