import conditions.*
import rules.EQUAL
import rules.IMPLY

infix fun Condition.and(condition: Condition) = AND(this, condition)
infix fun Condition.or(condition: Condition) = OR(this, condition)
infix fun Condition.xor(condition: Condition) = XOR(this, condition)
infix fun Condition.imply(condition: Condition) = IMPLY(this, condition)
infix fun Condition.equal(condition: Condition) = EQUAL(this, condition)
operator fun Condition.not() = NOT(this)
