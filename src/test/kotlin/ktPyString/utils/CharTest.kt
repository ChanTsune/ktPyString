package ktPyString.utils

import kotlin.test.*

class CharTest {
    @Test
    fun test_isWhiteSpace() {
        assertTrue { ' '.isWhiteSpace() }
        assertFalse { 'A'.isWhiteSpace() }
    }

    @Test
    fun test_repeat() {
        assertEquals("aaa", 'a'.repeat(3))
        assertEquals("", 'a'.repeat(0))
        assertEquals("", 'a'.repeat(-1))
    }
}
