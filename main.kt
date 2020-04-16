import conditions.*
import conditions.Fact.Companion.getFact
import rules.Rule
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

val queriedFacts = ArrayList<Fact>()
val rules = ArrayList<Rule>()

//https://en.wikipedia.org/wiki/Backward_chaining
fun main(args: Array<String>) {
	readUserInput(args)
	solve()
	showTheResults()
}

private fun readUserInput(args: Array<String>) {
	if (args.size == 0) {
		invalidExit("Please, provide file name")
	}
	val file = File(args[0])
	if (file.exists() && file.canRead()) {
		try {
			readInput(FileInputStream(file))
		} catch (e: Exception) {
			invalidExit("Invalid formatting")
		}
	} else {
		invalidExit("Make sure that file exists and check the permissions")
	}
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
	try {
		queriedFacts.forEach { fact -> defineFact(fact) }
	} catch (e: Exception) {
		invalidExit(e.message!!)
	}
}

fun defineFact(fact: Fact) {
	val rulesWithDependentFact = rules.filter { rule -> rule.dependentFacts.contains(fact) }
	rulesWithDependentFact.forEach { it.determineFact(fact) }

	if (!fact.isDefined) {
		fact.setToFalse()
	}
}

fun showTheResults() {
	queriedFacts.forEach { it.printResultToUser() }
}