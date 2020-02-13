package ktPyString.properties

import kotlin.test.Test
import kotlin.test.assertEquals


internal class NumericTypeTest {
    @Test
    fun init(){
        assertEquals(NumericType.NOT_NUMERIC,' '.numericType)
    }
}
