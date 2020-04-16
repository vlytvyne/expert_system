import kotlin.system.exitProcess

fun Char.isFact() = this.isLetter() && this.isUpperCase()

fun invalidExit(errorMsg: String) {
	println(errorMsg)
	exitProcess(1)
}