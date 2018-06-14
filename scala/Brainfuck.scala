object Brainfuck {
  var timer = false
  def main(args: Array[String]): Unit = {
    for(arg <- args) {
      println("Compiling " + arg)
      CompileRun.compile(arg)
    }
    println(FunOp.number + " functions created")
    println("File(s) Compiled. Executing...")
    Timer.time(CompileRun.execute())
    CmdLoop.run()
  }
}