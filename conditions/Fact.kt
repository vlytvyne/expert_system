package conditions

import java.lang.Exception

class Fact(val name: Char): Condition() {

	var isDefined = false
	private var factIsTrue = false

	override val isTrue: Boolean
		@Throws(UndefinedFactException::class)
		get() = if (isDefined) factIsTrue else throw UndefinedFactException(this)

	override val memberFacts: List<Fact>
		get() = listOf(this)

	@Throws(AmbiguousFactException::class)
	override fun setToTrue() {
		if (isDefined && !factIsTrue) {
			throw AmbiguousFactException(this)
		}
		isDefined = true
		factIsTrue = true
	}

	@Throws(AmbiguousFactException::class)
	override fun setToFalse() {
		if (isDefined && factIsTrue) {
			throw AmbiguousFactException(this)
		}
		isDefined = true
		factIsTrue = false
	}

	override fun toString() = name.toString()
}

class UndefinedFactException(val fact: Fact, msg: String = "no message"): Exception(msg)
class AmbiguousFactException(val fact: Fact, msg: String = "no message"): Exception(msg)