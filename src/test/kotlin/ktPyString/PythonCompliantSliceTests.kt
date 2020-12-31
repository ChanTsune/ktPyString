package ktPyString

import kotlin.test.*

class PythonCompliantSliceTests {
    @Test
    fun test_cmp() {
        val s1 = Slice(1, 2, 3)
        val s2 = Slice(1, 2, 3)
        val s3 = Slice(1, 2, 4)
        assertEquals(s1, s2)
        assertNotEquals(s1, s3)
    }

    @Test
    fun test_members() {
        var s = Slice(1)
        assertEquals(s.start, null)
        assertEquals(s.stop, 1)
        assertEquals(s.step, null)
        s = Slice(1, 2)
        assertEquals(s.start, 1)
        assertEquals(s.stop, 2)
        assertEquals(s.step, null)
        s = Slice(1, 2, 3)
        assertEquals(s.start, 1)
        assertEquals(s.stop, 2)
        assertEquals(s.step, 3)
    }

    @Test
    fun test_indices() {
        assertEquals(Slice(null).indices(10), Triple(0, 10, 1))
        assertEquals(Slice(null, null, 2).indices(10), Triple(0, 10, 2))
        assertEquals(Slice(1, null, 2).indices(10), Triple(1, 10, 2))
        assertEquals(Slice(null, null, -1).indices(10), Triple(9, -1, -1))
        assertEquals(Slice(null, null, -2).indices(10), Triple(9, -1, -2))
        assertEquals(Slice(3, null, -2).indices(10), Triple(3, -1, -2))
        assertEquals(Slice(null, -9).indices(10), Triple(0, 1, 1))
        assertEquals(Slice(null, -10).indices(10), Triple(0, 0, 1))
        assertEquals(Slice(null, -11).indices(10), Triple(0, 0, 1))
        assertEquals(Slice(null, -10, -1).indices(10), Triple(9, 0, -1))
        assertEquals(Slice(null, -11, -1).indices(10), Triple(9, -1, -1))
        assertEquals(Slice(null, -12, -1).indices(10), Triple(9, -1, -1))
        assertEquals(Slice(null, 9).indices(10), Triple(0, 9, 1))
        assertEquals(Slice(null, 10).indices(10), Triple(0, 10, 1))
        assertEquals(Slice(null, 11).indices(10), Triple(0, 10, 1))
        assertEquals(Slice(null, 8, -1).indices(10), Triple(9, 8, -1))
        assertEquals(Slice(null, 9, -1).indices(10), Triple(9, 9, -1))
        assertEquals(Slice(null, 10, -1).indices(10), Triple(9, 9, -1))
        assertEquals(Slice(-100, 100).indices(10), Slice(null).indices(10))
        assertEquals(Slice(100, -100, -1).indices(10), Slice(null, null, -1).indices(10))
        assertEquals(Slice(-100, 100, 2).indices(10), Triple(0, 10, 2))
    }
}
