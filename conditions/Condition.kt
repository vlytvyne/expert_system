package conditions

abstract class Condition {

	abstract val isTrue: Boolean

	abstract val memberFacts: List<Fact>

	abstract fun setToTrue()

	abstract fun setToFalse()
}

class UnsupportedOperation(msg: String = "no message"): Exception(msg)