import conditions.*
import rules.IMPLY
import rules.Rule

val facts = HashMap<Char, Fact>()
val queriedFacts = ArrayList<Fact>()
val rules = ArrayList<Rule>()

fun main() {
	val A = Fact('A')
	val B = Fact('B')
	val C = Fact('C')
	val D = Fact('D')
	val G = Fact('G').apply { setToTrue() }

	queriedFacts += D

	val rule1 = IMPLY(
		OR(OR(A, B), C),
		D)
	val rule2 = IMPLY(
		G, C
	)
	rules += rule1
	rules += rule2

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