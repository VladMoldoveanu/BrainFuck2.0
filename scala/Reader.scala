import scala.io.Source

class Reader(filename: String) extends Read {
  private val _valid = List('>', '<', '.', ',', '+', '-', '[', ']', '~', '|', '#', '/', '\\', '!')
  private var commands = Source.fromFile(filename).
    getLines.mkString.toList
  if(commands.head == '[') {
    while (commands.nonEmpty && commands.head != ']')
      commands = commands.tail
    commands = commands.tail
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
}
