package rules

import conditions.Condition
import conditions.Fact

abstract class Rule(protected val antecedent: Condition, protected val consequence: Condition) {

	abstract val dependentFacts: List<Fact>

	abstract fun execute()
}