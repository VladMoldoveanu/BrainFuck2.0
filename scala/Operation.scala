trait Operation {
  def execute(ah: ArrayHandler)
}

object Operation {
  def dispatch(reader: Read) : Operation = {
    if(reader.isEmpty) return EmptyOp
    reader.peek match {
      case '>' => {
        val n = amalgamate(reader)
        new Operation {
          override def execute(ah: ArrayHandler): Unit = ah.moveRight(n)
        }
      }
      case '<' => {
        val n = amalgamate(reader)
        new Operation {
          override def execute(ah: ArrayHandler): Unit = ah.moveLeft(n)
        }
      }
      case '-' => {
        val n = amalgamate(reader)
        new Operation {
          override def execute(ah: ArrayHandler): Unit = ah.decrease(n)
        }
      }
      case '+' => {
        val n = amalgamate(reader)
        new Operation {
          override def execute(ah: ArrayHandler): Unit = ah.increase(n)
        }
      }
      case '.' => {reader.nextCh; PrintCh}
      case ',' => {reader.nextCh; ReadCh}
      case '[' => {reader.nextCh; WhileOp.create(reader)}
      case ']' => {
        reader.nextCh
        println("Compile error: ] without [")
        Handler.notifyError
        ErrorOp
      }
      case '~' => {
        reader.nextCh
        FunOp.createFn(reader)
        Operation.dispatch(reader)
      }
      case '|' => {reader.nextCh; FunOp}
      case '#' => {reader.nextCh; Debug}
      case '/' => {
        reader.nextCh
        var args = 0
        if(reader.peek == '\\')
          args = amalgamate(reader)
        new SpecialFunOp(args)
      }
      case '\\' => {
        amalgamate(reader)
        println("Unexpected \\ character without separate array function call")
        Handler.notifyError
        ErrorOp
      }
      case '!' => {
        val x = amalgamate(reader)
        if(x == 1) InsFns
        else new Operation {
          private val n = FunOp.currNumb
          override def execute(ah: ArrayHandler): Unit = ah.update(n)
        }
      }
      case x => {
        reader.nextCh
        println("Unexpected character bypassed filter: " + x)
        Handler.notifyError
        ErrorOp
      }
    }
  }
  private def amalgamate(reader: Read) : Int = {
    val x = reader.nextCh
    var cnt = 1
    while(reader.nonEmpty && reader.peek == x){
      cnt += 1
      reader.nextCh
    }
    cnt
  }
}

object ReadCh extends Operation {
  override def execute(ah: ArrayHandler): Unit = ah.setChar()
}

object PrintCh extends Operation {
  override def execute(ah: ArrayHandler): Unit = ah.printChar()
}

object InsFns extends Operation {
  override def execute(ah: ArrayHandler): Unit = ah.update(FunOp.number)
}

object ErrorOp extends Operation {
  override def execute(ah: ArrayHandler): Unit = {println("Executed error op")}
}

object EmptyOp extends Operation {
  override def execute(ah: ArrayHandler): Unit = {}
}

object Debug extends Operation {
  override def execute(ah: ArrayHandler): Unit = ah.printData()
}