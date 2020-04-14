package rules

import conditions.Condition
import conditions.Fact
import conditions.UndefinedFactException
import defineFact

class IMPLY(antecedent: Condition, consequence: Condition): Rule(antecedent, consequence) {

	override val dependentFacts: List<Fact>
		get() =  consequence.memberFacts

	override fun execute() {
		try {
			if (antecedent.isTrue) {
				consequence.setToTrue()
			}
		} catch (e: UndefinedFactException) {
			defineFact(e.fact)
			execute()
		}
	}

	override fun toString() = "$antecedent => $consequence"

}