import conditions.*
import rules.Rule

val queriedFacts = ArrayList<Fact>()
val rules = ArrayList<Rule>()

//https://en.wikipedia.org/wiki/Backward_chaining
fun main() {
//	val A = Fact('A').apply { setToTrue() }
//	val B = Fact('B')
//	val C = Fact('C')
//	val D = Fact('D').apply { setToTrue() }
//	val G = Fact('G')
//	val H = Fact('H')
//	val E = Fact('E')
//	val F = Fact('F')
//
//	queriedFacts += C
//
//	val rule1 = C equal B
//	val rule2 = D imply !B
//	rules += rule1
//	rules += rule2
//
//	queriedFacts.forEach { fact -> defineFact(fact) }
//
//	queriedFacts.forEach(::println)

	// i|A+BCD
	println(RuleExtractor("(A | !B) + C => D").extractRule())
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