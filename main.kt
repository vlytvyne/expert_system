import conditions.*
import rules.IMPLY
import rules.Rule

val facts = HashMap<Char, Fact>()
val queriedFacts = ArrayList<Fact>()
val rules = ArrayList<Rule>()

fun main() {
	val A = Fact('A')
	val B = Fact('B').apply { setToTrue() }
	val C = Fact('C')
	val D = Fact('D')
	val G = Fact('G')

	queriedFacts += C

	val rule1 = IMPLY(
		XOR(A, B), C
	)
	rules += rule1

	queriedFacts.forEach { fact -> defineFact(fact) }

	queriedFacts.forEach(::println)
}

fun defineFact(fact: Fact) {
	val rulesWithFactInConsequence = rules.filter { rule -> rule.dependentFacts.contains(fact) }

	rulesWithFactInConsequence.forEach {
		println(it)
		it.execute()
	}
	if (!fact.isDefined) {
		fact.setToFalse()
	}
}