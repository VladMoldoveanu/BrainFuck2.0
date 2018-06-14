import scala.io.Source

class Reader(filename: String) extends Read {
  private val _valid = List('>', '<', '.', ',', '+', '-', '[', ']', '~', '|', '#', '/', '\\', '!')
  private var commands = Source.fromFile(filename).
    getLines.mkString.toList
  if(commands.head == '[') {
    while (commands.nonEmpty && commands.head != ']')
      commands = commands.tail
    if(commands.nonEmpty) commands = commands.tail
  }
  commands = commands.filter(_valid.contains(_))
  def nextCh : Char = {
    val ch = commands.head
    commands = commands.tail
    ch
  }
  def peek : Char = commands.head
  def nonEmpty: Boolean = commands.nonEmpty
  def isEmpty: Boolean = commands.isEmpty
  override def hasTwo: Boolean = commands.nonEmpty && commands.tail.nonEmpty
  override def peekTwo: (Char, Char) = (commands.head, commands.tail.head)
}
