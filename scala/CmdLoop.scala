import scala.io.StdIn
import java.io.PrintWriter

object CmdLoop {
  private val chars = List('>', '<', '.', ',', '+', '-', '|', '[', ']', '/', '\\', '!')
  private var cmd = ""
  private var timed = false
  private var fileSave: Option[String] = None
  private var saveString = ""
  private val helpStr = ":q => exit program\n" +
                        ":l [filename] => compile and run file\n" +
                        ":t => toggle timer\n" +
                        ":s [filename] => toggle saving to file\n" +
                        "type expressions to evaluate\n" +
                        "type [ to start a while loop - no other characters in the row\n" +
                        "type ] to finish creating the loop and execute it\n" +
                        "type ~ to start building functions - no other characters in the row\n" +
                        "type ~ to finish building a function\n" +
                        "type # for pointer location and number of functions\n" +
                        "type ## for data, pointer and no. functions\n"
  private val errStr = "Command not understood. Type :h for help\n"
  def run(): Unit = {
    cmd = StdIn.readLine
    while(cmd != ":q"){
      if(cmd.nonEmpty) {
        if (cmd.head == ':') {
          cmd = cmd.tail
          runSpecial()
        }
        else cmd match {
          case "~" => makeFunction()
          case "#" => CompileRun.basicInfo()
          case "##" => CompileRun.info()
          case _ => create()
        }
      }
      cmd = StdIn.readLine
    }
    if(fileSave.isDefined) save()
  }
  private def runSpecial(): Unit = {
    if(cmd.isEmpty) println(errStr)
    else
      cmd.head match {
        case 'l' => {
          cmd = cmd.tail
          if(cmd.isEmpty)println(errStr)
          else if (cmd.head != ' ') println(errStr)
          else {
            cmd = cmd.tail
            for(file <- cmd.split(' ')) {
              CompileRun.compile(file)
              CompileRun.execute()
            }
          }
        }
        case 's' => {
          if(fileSave.isEmpty) {
            cmd = cmd.tail
            if (cmd.isEmpty) println(errStr)
            else if (cmd.head != ' ') println(errStr)
            else {
              cmd = cmd.tail
              fileSave = Some(cmd)
            }
          }
          else save()
        }
        case 't' => timed = !timed
        case 'h' => println(helpStr)
        case _ => println(errStr)
      }
  }
  private def create(): Unit = {
    var loopsOpen = 0
    var cmds = ""
    var first = true
    do {
      if(first) first = false
      else cmd = StdIn.readLine()
      if(cmd.nonEmpty) {
        if (validCmd) {
          cmds += cmd
          for (ch <- cmd)
            if (ch == '[') loopsOpen += 1
            else if (ch == ']') {
              if (loopsOpen == 0) {
                println("Non-existing loop closed, nothing executes"); return
              }
              else loopsOpen -= 1
            }
        }
        else {
          println("Command contains forbidden characters, nothing executes"); return
        }
      }
    } while (loopsOpen > 0)
    if(fileSave.isDefined) saveString += cmds
    val rd = new StringReader(cmds)
    while(rd.nonEmpty)
      CompileRun.addOp(Operation.dispatch(rd))
    Timer.timeIf(timed)(CompileRun.execute())
  }
  private def makeFunction(): Unit = {
    cmd = StdIn.readLine()
    var cmds = ""
    var loopsOpen = 0
    while(cmd != "~"){
      if(cmd.nonEmpty) {
        if (validCmd) {
          cmds += cmd
          for(ch <- cmd)
            if(ch == '[') loopsOpen += 1
            else if(ch == ']') {
              if(loopsOpen == 0) {println("Non-existing loop closed, leaving function creation"); return}
              else loopsOpen -= 1
            }
        }
        else {println("Command contains forbidden characters, leaving function construction"); return}
      }
      cmd = StdIn.readLine()
    }
    cmds += "~"
    if(loopsOpen > 0) {
      println("Unclosed loops, not creating any function")
      return
    }
    if(fileSave.isDefined) saveString += cmds
    FunOp.createFn(new StringReader(cmds))
    FunOp.pushFunctions()
    println("Created function with id " + (FunOp.number - 1))
  }
  private def validCmd: Boolean = cmd.forall(chars.contains(_))
  private def save(): Unit = {
    new PrintWriter(fileSave.get.filter(_ != '#')) {write(saveString); close}
    fileSave = None
    saveString = ""
  }
}
