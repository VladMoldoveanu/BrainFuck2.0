import scala.collection.mutable

object CompileRun {
  private val arr = new ArrayHandler(128, true)
  private val ops: mutable.Queue[Operation] = mutable.Queue[Operation]()
  def compile(file: String): Unit = {
    val rd = new Reader(file)
    val nq = mutable.Queue[Operation]()
    Timer.timeCompile(file) {
      while (rd.nonEmpty) nq.enqueue(Operation.dispatch(rd))
    }
    if(Handler.hasError){
      println("File contained errors, nothing changed")
      FunOp.discardFunctions()
      Handler.handled()
    }
    else{
      while(nq.nonEmpty) ops.enqueue(nq.dequeue())
      FunOp.pushFunctions()
    }
  }
  def execute(): Unit = {
    while(ops.nonEmpty) ops.dequeue().execute(arr)
  }
  def hasOps: Boolean = ops.nonEmpty
  def basicInfo(): Unit = {
    println("Pointer at " + arr.getPtr)
    println("Functions: " + FunOp.number)
  }
  def info(): Unit = arr.printData()
  def addOp(op: Operation): Unit = ops.enqueue(op)
}
