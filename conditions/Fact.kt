package conditions

import isFact
import java.lang.Exception

class Fact private constructor(val name: Char): Condition() {

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

	override fun toString() = "Fact $name | isDefined: $isDefined | isTrue: $factIsTrue"

	companion object {

		private val facts = HashMap<Char, Fact>()

		@Throws(CharCantBeFactException::class)
		fun getFact(name: Char): Fact {
			if (!name.isFact()) {
				throw CharCantBeFactException(name)
			}
			if (!facts.containsKey(name)) {
				facts[name] = Fact(name)
			}
			return facts[name]!!
		}
	}
}

class UndefinedFactException(val fact: Fact, msg: String = "Undefined fact ${fact.name}"): Exception(msg)
class AmbiguousFactException(val fact: Fact, msg: String = "Ambiguous fact ${fact.name}"): Exception(msg)
class CharCantBeFactException(val char: Char, msg: String = "Char $char can't be a fact, only uppercase letter allowed"): Exception(msg)