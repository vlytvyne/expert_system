package conditions

class OR(private val firstCondition: Condition,
		 private val secondCondition: Condition): Condition() {

	override val isTrue: Boolean
		get() = firstCondition.isTrue || secondCondition.isTrue

	override val memberFacts
		get() = firstCondition.memberFacts + secondCondition.memberFacts

	override fun setToTrue() {
		throw UnsupportedOperation("Can't set OR to TRUE")
	}

	//True => !(A | B)
	override fun setToFalse() {
		memberFacts.forEach { it.setToFalse() }
	}

	override fun toString() = "($firstCondition | $secondCondition)"

}