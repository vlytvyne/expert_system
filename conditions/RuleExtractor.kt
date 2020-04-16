package conditions

import and
import conditions.Fact.Companion.getFact
import equal
import imply
import isFact
import not
import or
import rules.EQUAL
import rules.IMPLY
import rules.Rule
import xor
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.HashMap

const val IMPLY_CHAR = 'i'
const val EQUAL_CHAR = 'e'
const val OPENING_PARENTHESIS = ')'
const val CLOSING_PARENTHESIS = '('

class RuleExtractor(val ruleFormula: String) {

	private val prefixNotation = StringBuilder()
	private val operatorStack = Stack<Char>()

	private lateinit var finalFormula: String

	//https://studfile.net/preview/2069515/page:2/
	private val operatorToPrior = HashMap<Char, Int>().apply { putAll(arrayOf(
		'!' to 10,
		'+' to 9,
		'|' to 8,
		'^' to 7,
		IMPLY_CHAR to 6,
		EQUAL_CHAR to 5
	)) }

	fun extractRule(): Rule {
		val preparedFormula = getPreparedFormulaBeforeNotationTransformation()
		finalFormula = convertToPrefixNotation(preparedFormula)
		return convertFinalFormulaToRule()
	}

	private fun getPreparedFormulaBeforeNotationTransformation(): String {
		val tokens = ruleFormula.split(' ')
		val string = tokens.reduce { acc, string ->
			when (string) {
				"=>" -> acc + IMPLY_CHAR
				"<=>" -> acc + EQUAL_CHAR
				else -> acc + string
			}
		}
		return string.reversed()
	}

	//https://www.youtube.com/watch?v=sJ0VhIbvCtc
	private fun convertToPrefixNotation(preparedFormula: String): String {
		preparedFormula.forEach { char ->
			when {
				char.isFact() -> prefixNotation.insert(0, char)
				char.isOperator() -> handleOperator(char)
				char == OPENING_PARENTHESIS -> operatorStack.push(char)
				char == CLOSING_PARENTHESIS -> handleClosingParenthesis()
			}
		}
		flushOperatorsToPrefixNotationString()
		return prefixNotation.toString()
	}

	private fun handleOperator(char: Char) {
		while (!operatorStack.empty()) {
			if (operatorStack.peek().getPrior() > char.getPrior()) {
				prefixNotation.insert(0, operatorStack.pop())
			} else {
				break
			}
		}
		operatorStack.push(char)
	}

	private fun handleClosingParenthesis() {
		while (!operatorStack.empty()) {
			val operator = operatorStack.pop()
			if (operator == OPENING_PARENTHESIS) {
				break
			} else {
				prefixNotation.insert(0, operator)
			}
		}
	}

	private fun flushOperatorsToPrefixNotationString() {
		while (!operatorStack.empty()) {
			prefixNotation.insert(0, operatorStack.pop())
		}
	}

	private var nextCharIndex = 1

	private fun convertFinalFormulaToRule(): Rule =
		when (finalFormula[0]) {
			IMPLY_CHAR -> getNextCondition() imply getNextCondition()
			EQUAL_CHAR -> getNextCondition() equal getNextCondition()
			else -> throw InvalidRuleFormatting()
		}

	private fun getNextCondition(): Condition {
		val currentChar = finalFormula[nextCharIndex]
		nextCharIndex++
		if (currentChar.isFact()) {
			return getFact(currentChar)
		}
		return when(currentChar) {
			'!' -> !getNextCondition()
			'+' -> getNextCondition() and getNextCondition()
			'|' -> getNextCondition() or getNextCondition()
			'^' -> getNextCondition() xor getNextCondition()
			else -> throw InvalidRuleFormatting()
		}
	}

	private fun Char.getPrior() = if (this == OPENING_PARENTHESIS) 0 else operatorToPrior[this]!!
	private fun Char.isOperator() = operatorToPrior.containsKey(this)
}

class InvalidRuleFormatting(msg: String = "no message"): Exception(msg)