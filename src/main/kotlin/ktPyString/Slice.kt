package ktPyString


class Slice(stop: Int?) {
    private var start: Int? = null
    private var stop: Int? = stop
    private var step: Int? = null

    constructor(start: Int? = null, stop: Int? = null, step: Int? = null) : this(stop) {
        this.start = start
        this.step = step
    }

    override fun toString(): String = "Slice($start, $stop, $step)"

    fun adjustIndex(length: Int): Triple<Int, Int, Int> {
        fun _PyLong_Sign(n: Int): Int =
                when {
                    n == 0 -> 0
                    n > 0 -> 1
                    else -> -1
                }

        var step: Int = this.step ?: 1
        var start: Int
        var stop: Int
        var upper: Int
        var lower: Int

        // Convert step to an integer; raise for zero step.
        var stepSign: Int = _PyLong_Sign(step)
        if (stepSign == 0) {
            throw Exception("ValueError: slice step cannot be zero")
        }
        var stepIsNegative: Boolean = stepSign < 0

        /* Find lower and upper bounds for start and stop. */
        if (stepIsNegative) {
            lower = -1
            upper = length + lower
        } else {
            lower = 0
            upper = length
        }

        // Compute start.
        if (this.start == null) {
            start = if (stepIsNegative) upper else lower
        } else {
            start = this.start!!

            if (_PyLong_Sign(start) < 0) {
                start += length

                if (start < lower /* Py_LT */) {
                    start = lower
                }
            } else {
                if (start > upper /* Py_GT */) {
                    start = upper
                }
            }
        }

        // Compute stop.
        if (this.stop == null) {
            stop = if (stepIsNegative) lower else upper
        } else {
            stop = this.stop!!

            if (_PyLong_Sign(stop) < 0) {
                stop += length
                if (stop < lower /* Py_LT */) {
                    stop = lower
                }
            } else {
                if (stop > upper /* Py_GT */) {
                    stop = upper
                }
            }
        }
        return Triple<Int, Int, Int>(start, stop, step)
    }
}

