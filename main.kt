import conditions.*
import rules.EQUAL
import rules.IMPLY
import rules.Rule

val queriedFacts = ArrayList<Fact>()
val rules = ArrayList<Rule>()

//https://en.wikipedia.org/wiki/Backward_chaining
fun main() {
	val A = Fact('A').apply { setToFalse() }
	val B = Fact('B')
	val C = Fact('C')
	val D = Fact('D').apply { setToTrue() }
	val G = Fact('G')

	queriedFacts += B

	val rule1 = EQUAL(A, B)
	rules += rule1

	queriedFacts.forEach { fact -> defineFact(fact) }

	queriedFacts.forEach(::println)
}

fun defineFact(fact: Fact) {
	val rulesWithDependentFact =
		rules.filter { rule -> rule.dependentFacts.contains(fact) }

	rulesWithDependentFact.forEach {
		it.determineFact(fact)
	}
	if (!fact.isDefined) {
		fact.setToFalse()
	}
}