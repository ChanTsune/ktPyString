package ktPyString.properties

enum class NumericType {
    NOT_NUMERIC,
    DECIMAL,
    DIGIT,
    NUMERIC
}

val Char.numericType : NumericType
    get(){
        return NumericType.NOT_NUMERIC
    }
