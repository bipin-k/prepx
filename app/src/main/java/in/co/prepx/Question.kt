package `in`.co.prepx

data class Question(
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val description: String
) : java.io.Serializable