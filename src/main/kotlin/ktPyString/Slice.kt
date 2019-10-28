package ktPyString

import kotlin.math.sign

import ktPyString.utils.Quad

class Slice(stop: Int?) {
    private var start: Int? = null
    private var stop: Int? = stop
    private var step: Int? = null

    constructor(start: Int? = null, stop: Int? = null, step: Int? = null) : this(stop) {
        this.start = start
        this.step = step
    }

    override fun toString(): String = "Slice($start, $stop, $step)"

    fun adjustIndex(length: Int): Quad<Int, Int, Int, Int> {
        val step: Int = if (this.step == 0) 1 else (this.step ?: 1)
        var start: Int
        var stop: Int
        var upper: Int
        var lower: Int

        // Convert step to an integer; raise for zero step.
        val stepSign: Int = step.sign
        if (stepSign == 0) {
            throw Exception("ValueError: slice step cannot be zero")
        }
        val stepIsNegative: Boolean = stepSign < 0

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

            if (start.sign < 0) {
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

            if (stop.sign < 0) {
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
        var loop = 0
        if (step < 0) {
            if (stop < start) {
                loop = (start - stop - 1) / (-step) + 1
            }
        } else {
            if (start < stop) {
                loop = (stop - start - 1) / step + 1
            }
        }
        return Quad(start, stop, step, loop)
    }
}

