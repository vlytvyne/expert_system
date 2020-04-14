package rules

import conditions.Condition
import conditions.Fact

class IMPLY(antecedent: Condition, consequence: Condition): Rule(antecedent, consequence) {

	override val dependentFacts: List<Fact>
		get() =  consequence.memberFacts

	override fun execute() {
		if (antecedent.isTrue) {
			consequence.setToTrue()
		}
	}

	override fun toString() = "$antecedent => $consequence"

}