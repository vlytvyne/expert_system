package conditions

class AND(private val firstCondition: Condition,
		  private val secondCondition: Condition): Condition() {

	override val isTrue: Boolean
		get() = firstCondition.isTrue && secondCondition.isTrue

	override val memberFacts
		get() = firstCondition.memberFacts + secondCondition.memberFacts

	override fun setToTrue() {
		firstCondition.setToTrue()
		secondCondition.setToTrue()
	}

	//True => !(A + B)
	override fun setToFalse() {
		throw UnsupportedOperation("Can't set AND to FALSE")
	}

	override fun toString() = "($firstCondition + $secondCondition)"

}