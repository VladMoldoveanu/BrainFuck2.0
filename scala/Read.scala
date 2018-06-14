trait Read {
  def nextCh: Char
  def peek: Char
  def nonEmpty: Boolean
  def isEmpty: Boolean
  def hasTwo: Boolean
  def peekTwo: (Char, Char)
}
