package rules

import conditions.Condition
import conditions.Fact
import conditions.UndefinedFactException
import defineFact

//hard imply will set consequence to false if antecedent is false
class HARD_IMPLY(antecedent: Condition, consequence: Condition): IMPLY(antecedent, consequence) {

	override fun determineFact(fact: Fact) {
		println(this)
		try {
			if (antecedent.isTrue) {
				consequence.setToTrue()
			} else {
				consequence.setToFalse()
			}
		} catch (e: UndefinedFactException) {
			defineFact(e.fact)
			determineFact(fact)
		}
	}

}

