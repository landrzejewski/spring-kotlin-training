package pl.training.parsers

import org.junit.jupiter.api.Test
import pl.training.parsers.Result.Failure
import pl.training.parsers.Result.Success
import kotlin.comparisons.then
import kotlin.or
import kotlin.rem
import kotlin.test.assertEquals

class ParsersTest {

    @Test
    fun `should parse text to key and value pair`() {
        assertEquals("firstName" to "Jan", keyValuePair("firstName:Jan"))
    }

    @Test
    fun `should parse text to map of key and value pairs`() {
        assertEquals(
            mapOf(
                "firstName" to "Jan",
                "secondName" to "Kowalski"
            ), keyValuePairs("firstName:Jan\nsecondName:Kowalski")
        )
    }

    // ------------------------------------------------------------------------------------

    @Test
    fun `should parse prefix`() {
       val prefixParser = prefix("*  ")
       assertEquals(Success("*  ", "jan"), prefixParser("*  jan"))
       assertEquals(Failure("Expected: \"*  \" prefix", "jan"), prefixParser("jan"))
    }

    @Test
    fun `should parse integer`() {
        assertEquals(Success(1234, "jan"), int("1234jan"))
        assertEquals(Failure("Expected: integer value", "jan"), int("jan"))
    }

    @Test
    fun `should parse whitespace`() {
        assertEquals(Success("   ", "jan"), whitespace("   jan"))
        assertEquals(Failure("Expected: one or more whitespace", "jan"), whitespace("jan"))
    }

    @Test
    fun `should parse sequence`() {
        // val sequenceParser = sequence(prefix("-"), ::int)
        // val sequenceParser = prefix("-") then ::int
        val sequenceParser = prefix("-").then(::int)
        assertEquals(Success(Pair("-", 123), "jan"), sequenceParser("-123jan"))
        assertEquals(Failure("Expected: \"-\" prefix", "123jan"), sequenceParser("123jan"))
    }

    @Test
    fun `should parse one of`() {
        //val onOfParser = oneOf(prefix("a"), prefix("b"))
        val onOfParser = prefix("a") or (prefix("b"))
        assertEquals(Success("a", "b"),onOfParser("ab"))
        assertEquals(Success("b", "c"), onOfParser("bc"))
        assertEquals(Failure("Expected: \"a\" prefix, Expected: \"b\" prefix", "cd"), onOfParser("cd"))
    }

    @Test
    fun `should map result`() {
        assertEquals(Success(false, ""), int("11").map { it % 2 == 0 })
    }

    @Test
    fun `should skip left parser`() {
        val parser = ::int skipLeft (prefix("a"))
        assertEquals(Success("a", ""), parser("1a"))
    }

    @Test
    fun `should skip right parser`() {
        val parser = prefix("a") skipRight (::int)
        assertEquals(Success("a", ""),  parser("a1"))
    }

    @Test
    fun `should parse many`() {
        val manyPrefixes = prefix("a").many()
        assertEquals(Success(listOf("a", "a", "a"), ""), manyPrefixes("aaa"))
    }

    @Test
    fun `should parse many with separator`() {
        val manyWithSeparatorParser = ::int.separatedBy(prefix(","))
        assertEquals(Success(listOf(1, 2), ""), manyWithSeparatorParser("1,2"))
        assertEquals(Failure("Expected: integer value", "a"), manyWithSeparatorParser("1,a"))
        assertEquals( Success(emptyList(), "a"), manyWithSeparatorParser("a"))
    }

    @Test
    fun `should parse expression with array of ints`() {
        val text = "let  ab = [1, 2, 3,  4]"

        val manySpaces = ::whitespace.many()
        val let = prefix("let") then ::whitespace.many()
        val variableName = prefixWhile { it.isLetter() } skipRight manySpaces
        val assigment = prefix("=") skipRight manySpaces
        val numbers = ::int separatedBy sequence(prefix(","), manySpaces)
        val array = prefix("[") skipLeft numbers skipRight prefix("]")
        val parser = let skipLeft variableName skipRight assigment then array

        when (val result = parser(text)) {
            is Success -> println(result.value)
            is Failure -> println(result.expected)
        }
    }

}