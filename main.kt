import conditions.AND
import conditions.Fact
import conditions.UndefinedFactException
import rules.IMPLY
import rules.Rule

val facts = HashMap<Char, Fact>()
val queriedFacts = ArrayList<Fact>()
val rules = ArrayList<Rule>()

fun main() {
	facts['A'] = Fact('A').apply { setToTrue() }
	facts['B'] = Fact('B').apply { setToTrue() }
	facts['C'] = Fact('C')
	facts['D'] = Fact('D')
	facts['G'] = Fact('G')

	queriedFacts += facts['B']!!

	val rule1 = IMPLY(facts['A']!!, facts['B']!!)
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