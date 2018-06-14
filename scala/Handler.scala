object Handler {
  private var error = false
  def notifyError: Unit = error = true
  def hasError: Boolean = error
  def handled() = error = false
}
