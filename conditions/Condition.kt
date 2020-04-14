package conditions

abstract class Condition {

	abstract val isTrue: Boolean

	abstract val memberFacts: List<Fact>

	abstract fun setToTrue()

	abstract fun setToFalse()
}