package conditions

import java.lang.Exception

class Fact(val name: Char): Condition() {

	var isDefined = false
	private var factIsTrue = false

	override val isTrue: Boolean
		get() = if (isDefined) factIsTrue else throw UndefinedFactException(this)

	override val memberFacts: List<Fact>
		get() = listOf(this)

	override fun setToTrue() {
		isDefined = true
		factIsTrue = true
	}

	override fun setToFalse() {
		isDefined = true
		factIsTrue = false
	}

	override fun toString() = "Fact: $name | isDefined: $isDefined | isTrue: $factIsTrue"
}

class UndefinedFactException(val fact: Fact, msg: String = "no message"): Exception(msg)