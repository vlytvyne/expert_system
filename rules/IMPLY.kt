package rules

import conditions.Condition
import conditions.Fact
import conditions.UndefinedFactException
import defineFact

//http://www.linuxcookbook.ru/books/informatika1/ch_07_sheets/01_logic/03_implies/index.html
open class IMPLY(antecedent: Condition, consequence: Condition): Rule(antecedent, consequence) {

	override val dependentFacts: List<Fact>
		get() =  consequence.memberFacts

	override fun determineFact(fact: Fact) {
		try {
			if (antecedent.isTrue) {
				consequence.setToTrue()
			}
		} catch (e: UndefinedFactException) {
			defineFact(e.fact)
			determineFact(fact)
		}
	}

	override fun toString() = "$antecedent => $consequence"

}