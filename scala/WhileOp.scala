class WhileOp(ops: List[Operation]) extends Operation {
  def execute(ah: ArrayHandler): Unit =
    while (ah.getChar != 0)
      for (op <- ops)
        op.execute(ah)
}

object WhileOp {
  def create(reader: Read) : Operation = {
    if(reader.hasTwo && reader.peekTwo == ('-',']')) {
      reader.nextCh
      reader.nextCh
      return SetZero
    }
    var ops: List[Operation] = List()
    while(reader.nonEmpty && reader.peek != ']' && reader.peek != '~')
      ops = Operation.dispatch(reader) :: ops
    if(reader.isEmpty) {
      println("Compile error: [ found without ending ]")
      Handler.notifyError
    }
    else if(reader.peek == '~') {
      println("Compile error: function definitions not allowed in loops")
      Handler.notifyError
    }
    else reader.nextCh
    new WhileOp(ops.reverse)
  }
}