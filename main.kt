import conditions.*
import conditions.Fact.Companion.getFact
import rules.Rule
import java.io.FileInputStream
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

val queriedFacts = ArrayList<Fact>()
val rules = ArrayList<Rule>()

//https://en.wikipedia.org/wiki/Backward_chaining
fun main() {
//	queriedFacts += getFact('D')
//	getFact('C').setToTrue()
//
//	val rule1 = RuleExtractor("A ^ B | C => D").extractRule()
//	rules += rule1
//
//	queriedFacts.forEach { fact -> defineFact(fact) }
//
	readInput(FileInputStream("src/input.txt"))
	solve()
	showTheResults()
}

fun readInput(inputStream: InputStream) {
	val scanner = Scanner(inputStream)
	while (scanner.hasNextLine()) {
		var line = scanner.nextLine()
		line = eraseComment(line)
		line = line.trim()
		when {
			line.isBlank() -> Unit
			isLineWithTrueFacts(line) -> extractTrueFacts(line)
			isLineWithQueriedFacts(line) -> extractQueriedFacts(line)
			else -> rules += RuleExtractor(line).extractRule()
		}
	}
}

fun isLineWithTrueFacts(line: String) = line[0] == '='

fun extractTrueFacts(line: String) {
	line.substring(1).forEach { getFact(it).setToTrue() }
}

fun isLineWithQueriedFacts(line: String) = line[0] == '?'

fun extractQueriedFacts(line: String) {
	line.substring(1).forEach { queriedFacts += getFact(it) }
}

private fun eraseComment(line: String): String {
	if (line.contains('#')) {
		val commentLength = line.length - line.split('#')[0].length
		return line.dropLast(commentLength)
	}
	return line
}

fun solve() {
	queriedFacts.forEach { fact -> defineFact(fact) }
}

fun defineFact(fact: Fact) {
	val rulesWithDependentFact =
		rules.filter { rule -> rule.dependentFacts.contains(fact) }

	rulesWithDependentFact.forEach {
		it.determineFact(fact)
	}
	if (!fact.isDefined) {
		fact.setToFalse()
	}
}

fun showTheResults() {
	queriedFacts.forEach(::println)
}