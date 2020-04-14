package conditions

class NOT(private val condition: Condition): Condition() {

	override val isTrue: Boolean
		get() = !condition.isTrue

	override val memberFacts
		get() = condition.memberFacts

	override fun setToTrue() {
		condition.setToFalse()
	}

	override fun setToFalse() {
		condition.setToTrue()
	}

	override fun toString() = "!($condition)"

}