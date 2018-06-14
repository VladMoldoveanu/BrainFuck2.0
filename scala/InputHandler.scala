import scala.io.StdIn

object InputHandler {
  private var _input: List[Char] = List()
  private def read(): Unit = {
    if(_input.nonEmpty) return
    _input = StdIn.readLine.toList
  }
  def getCh: Char = {
    read()
    if(_input.isEmpty) return 0.toChar
    val c = _input.head
    _input = _input.tail
    c
  }
}
