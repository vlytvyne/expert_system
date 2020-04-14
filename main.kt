import conditions.AND
import conditions.Fact
import conditions.NOT
import conditions.UndefinedFactException
import rules.IMPLY
import rules.Rule

val facts = HashMap<Char, Fact>()
val queriedFacts = ArrayList<Fact>()
val rules = ArrayList<Rule>()

fun main() {
	facts['A'] = Fact('A').apply { setToTrue() }
	facts['B'] = Fact('B')
	facts['C'] = Fact('C')
	facts['D'] = Fact('D')

	queriedFacts += facts['C']!!
	queriedFacts += facts['D']!!

	val rule1 = IMPLY(
		AND(facts['A']!!, NOT(facts['B']!!)),
		NOT(AND(facts['C']!!, facts['D']!!)))
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