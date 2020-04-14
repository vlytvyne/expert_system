package conditions

class XOR(private val firstCondition: Condition,
		  private val secondCondition: Condition): Condition() {

	override val isTrue: Boolean
		get() = firstCondition.isTrue xor secondCondition.isTrue

	override val memberFacts
		get() = firstCondition.memberFacts + secondCondition.memberFacts

	override fun setToTrue() {
		throw UnsupportedOperation("Can't set XOR to TRUE")
	}

	override fun setToFalse() {
		throw UnsupportedOperation("Can't set XOR to FALSE")
	}

	override fun toString() = "$firstCondition | $secondCondition"

}