package com.nakyung.assignment_nakyung.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LanguageWithCircle(language: String) {
    val circleColor: Color =
        when (language) {
            "Kotlin" -> Color(0xFF7F52FF)
            "Java" -> Color(0xFFB07219)
            "JavaScript" -> Color(0xFFF7DF1E)
            "TypeScript" -> Color(0xFF31859C)
            "Dart" -> Color(0xFF98BAD6)
            "PHP" -> Color(0xFF4F5D95)
            "HTML" -> Color(0xFFe44b23)
            "Swift" -> Color(0xFFffac45)
            "C" -> Color(0xFF555555)
            "Python" -> Color(0xFF3581ba)
            else -> Color(0xFFededed)
        }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .size(12.dp)
                    .background(
                        color = circleColor,
                        shape = CircleShape,
                    ),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = language,
            color = Color.Gray,
        )
    }
}
