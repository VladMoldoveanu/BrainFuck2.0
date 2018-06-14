import scala.collection.mutable

class FunOp(ops: List[Operation]) {
  def run(ah: ArrayHandler): Unit = {
    for(op <- ops)
      op.execute(ah)
  }
}

object FunOp extends Operation {
  private var funs = new Array[Option[FunOp]](8)
  private var fs = 0
  private val temp = mutable.Queue[FunOp]()
  private def resize(): Unit = {
    val a = new Array[Option[FunOp]](funs.length * 2)
    Array.copy(funs, 0, a, 0, funs.length)
    funs = a
  }
  def createFn(reader: Read): Unit = {
    var ops: List[Operation] = List()
    while(reader.nonEmpty && reader.peek != '~')
      ops = Operation.dispatch(reader) :: ops
    if(reader.isEmpty) {
      println("Missing ~ : Function has no ending point")
      Handler.notifyError
    }
    else reader.nextCh
    temp.enqueue(new FunOp(ops.reverse))
  }
  def pushFunctions(): Unit = {
    while(temp.nonEmpty){
      if(fs == funs.length) resize()
      funs(fs) = Some(temp.dequeue())
      fs += 1
    }
  }
  def discardFunctions(): Unit = temp.clear()
  override def execute(ah: ArrayHandler): Unit = {
    val n = ah.getChar
    if(n > fs)
      println("Runtime error: called function " + n + " with only " + fs + " functions defined\n" +
              "Execution of the call function skipped\n")
    else if(n == fs) println("Runtime error: called function " + n + " with " + fs +
                             " functions defined numbered from 0\n")
    else funs(n).get.run(ah)
  }
  def number: Int = fs
  def currNumb: Int = fs + temp.size
}
