class StringReader(private var str: String) extends Read {
  override def nextCh: Char = {
    val ch = str.head
    str = str.tail
    ch
  }
  override def isEmpty: Boolean = str.isEmpty
  override def nonEmpty: Boolean = str.nonEmpty
  override def peek: Char = str.head
  override def hasTwo: Boolean = str.nonEmpty && str.tail.nonEmpty
  override def peekTwo: (Char, Char) = (str.head, str.tail.head)
}
