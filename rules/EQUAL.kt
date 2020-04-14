package rules

import conditions.Condition
import conditions.Fact

class EQUAL(antecedent: Condition, consequence: Condition): Rule(antecedent, consequence) {

	//EQUAL: A <=> B
	//IMPLY: A => B
	private val imply = IMPLY(antecedent, consequence)
	//IMPLY: B => A
	private val reverseImply = IMPLY(consequence, antecedent)

	//to hide from rules pool to avoid infinite loop like A => B  >>>  B => A  >>>  A => B ...
	private var hideRule = false

	override val dependentFacts: List<Fact>
		get() = if (!hideRule) antecedent.memberFacts + consequence.memberFacts else listOf()

	override fun determineFact(fact: Fact) {
		hideRule = true
		if (antecedent.memberFacts.contains(fact)) {
			reverseImply.determineFact(fact)
		} else {
			imply.determineFact(fact)
		}
		hideRule = false
	}

	override fun toString() = "$antecedent <=> $consequence"

}