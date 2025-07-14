package `in`.co.prepx

object QuestionBank {
    fun getQuestions(subject: String): List<Question> {
        return when (subject) {
            "History" -> listOf(
                Question(
                    "Who was the first Emperor of the Maurya Empire?",
                    listOf("Chandragupta Maurya", "Ashoka", "Bindusara", "Samprati"),
                    0,
                    "Chandragupta Maurya was the founder and first emperor of the Maurya Empire."
                ),
                Question(
                    "In which year did India gain independence from British rule?",
                    listOf("1945", "1946", "1947", "1948"),
                    2,
                    "India gained independence on August 15, 1947."
                ),
                Question(
                    "Who was known as the 'Father of the Nation' in India?",
                    listOf(
                        "Jawaharlal Nehru",
                        "Mahatma Gandhi",
                        "Subhas Chandra Bose",
                        "Sardar Patel"
                    ),
                    1,
                    "Mahatma Gandhi is known as the 'Father of the Nation' in India."
                )
            )

            "Geography" -> listOf(
                Question(
                    "What is the capital of India?",
                    listOf("Mumbai", "Delhi", "Kolkata", "Chennai"),
                    1,
                    "New Delhi is the capital of India."
                ),
                Question(
                    "Which is the longest river in India?",
                    listOf("Ganga", "Yamuna", "Brahmaputra", "Godavari"),
                    0,
                    "The Ganga is the longest river in India."
                ),
                Question(
                    "Which mountain range runs along the northern border of India?",
                    listOf("Western Ghats", "Eastern Ghats", "Himalayas", "Aravalli"),
                    2,
                    "The Himalayas run along the northern border of India."
                )
            )

            "Polity" -> listOf(
                Question(
                    "How many fundamental rights are guaranteed by the Indian Constitution?",
                    listOf("5", "6", "7", "8"),
                    1,
                    "The Indian Constitution guarantees 6 fundamental rights."
                ),
                Question(
                    "Who is the head of the Indian government?",
                    listOf("President", "Prime Minister", "Chief Justice", "Speaker"),
                    1,
                    "The Prime Minister is the head of the Indian government."
                ),
                Question(
                    "In which year was the Indian Constitution adopted?",
                    listOf("1947", "1949", "1950", "1951"),
                    1,
                    "The Indian Constitution was adopted on November 26, 1949."
                )
            )

            "Mathematics" -> listOf(
                Question(
                    "What is the value of π (pi) to two decimal places?",
                    listOf("3.12", "3.14", "3.16", "3.18"),
                    1,
                    "The value of π is approximately 3.14159..."
                ),
                Question(
                    "What is the square root of 144?",
                    listOf("10", "11", "12", "13"),
                    2,
                    "12 × 12 = 144, so the square root of 144 is 12."
                ),
                Question(
                    "What is 15% of 200?",
                    listOf("25", "30", "35", "40"),
                    1,
                    "15% of 200 = (15/100) × 200 = 30."
                )
            )

            "Science" -> listOf(
                Question(
                    "What is the chemical symbol for gold?",
                    listOf("Ag", "Au", "Fe", "Cu"),
                    1,
                    "Au is the chemical symbol for gold (from Latin 'aurum')."
                ),
                Question(
                    "Which planet is known as the Red Planet?",
                    listOf("Venus", "Mars", "Jupiter", "Saturn"),
                    1,
                    "Mars is known as the Red Planet due to its reddish appearance."
                ),
                Question(
                    "What is the hardest natural substance on Earth?",
                    listOf("Steel", "Iron", "Diamond", "Granite"),
                    2,
                    "Diamond is the hardest natural substance on Earth."
                )
            )

            else -> listOf(
                Question(
                    "What is the capital city of France?",
                    listOf("Paris", "London", "Berlin", "Madrid"),
                    0,
                    "Paris is the capital city of France."
                ),
                Question(
                    "What is the largest planet in our solar system?",
                    listOf("Earth", "Jupiter", "Mars", "Venus"),
                    1,
                    "Jupiter is the largest planet in our solar system."
                )
            )
        }
    }
}