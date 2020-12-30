package ktPyString.utils

import kotlin.test.*

class CharTest {
    @Test
    fun test_isWhiteSpace() {
        assertTrue { ' '.isWhiteSpace() }
        assertFalse { 'A'.isWhiteSpace() }
    }
}
