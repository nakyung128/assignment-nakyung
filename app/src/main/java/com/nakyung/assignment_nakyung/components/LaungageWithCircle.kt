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
import com.nakyung.assignment_nakyung.ui.theme.CColor
import com.nakyung.assignment_nakyung.ui.theme.DartColor
import com.nakyung.assignment_nakyung.ui.theme.HTMLColor
import com.nakyung.assignment_nakyung.ui.theme.JavaColor
import com.nakyung.assignment_nakyung.ui.theme.JavaScriptColor
import com.nakyung.assignment_nakyung.ui.theme.KotlinColor
import com.nakyung.assignment_nakyung.ui.theme.OtherColor
import com.nakyung.assignment_nakyung.ui.theme.PHPColor
import com.nakyung.assignment_nakyung.ui.theme.PythonColor
import com.nakyung.assignment_nakyung.ui.theme.SwiftColor
import com.nakyung.assignment_nakyung.ui.theme.TypeScriptColor

@Composable
fun LanguageWithCircle(language: String) {
    val circleColor: Color =
        when (language) {
            "Kotlin" -> KotlinColor
            "Java" -> JavaColor
            "JavaScript" -> JavaScriptColor
            "TypeScript" -> TypeScriptColor
            "Dart" -> DartColor
            "PHP" -> PHPColor
            "HTML" -> HTMLColor
            "Swift" -> SwiftColor
            "C" -> CColor
            "Python" -> PythonColor
            else -> OtherColor
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
