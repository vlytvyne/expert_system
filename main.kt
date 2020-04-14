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

	queriedFacts += facts['C']!!
	queriedFacts += facts['D']!!

	val rule1 = IMPLY(AND(AND(facts['A']!!, facts['G']!!), facts['C']!!), facts['D']!!)
	val rule2 = IMPLY(facts['B']!!, facts['C']!!)
	rules += rule1
	rules += rule2

	queriedFacts.forEach { fact -> if (!fact.isDefined) defineFact(fact) }

	queriedFacts.forEach(::println)
}

fun defineFact(fact: Fact) {
	val rulesWithFactInConsequence = rules.filter { rule -> rule.dependentFacts.contains(fact) }

	rulesWithFactInConsequence.forEach { if (!fact.isDefined) executeRuleAndDefineAllNeededFacts(it) }
	if (!fact.isDefined) {
		fact.setToFalse()
	}


}

fun executeRuleAndDefineAllNeededFacts(rule: Rule) {
	while (true) {
		try {
			println(rule)
			rule.execute()
			break
		} catch (e: UndefinedFactException) {
			defineFact(e.fact)
		}
	}
}