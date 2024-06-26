package com.example.translucentcolorborderexample

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.translucentcolorborderexample.ui.theme.TranslucentColorBorderExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TranslucentColorBorderExampleTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize().background(Color.White)) { innerPadding ->
                    Example(
                        modifier = Modifier.fillMaxSize().padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Example(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        ExampleBox(
            shape = standardShape,
            borderColor = translucentColor
        ) //this is how the color should look like
        Spacer(Modifier.height(16.dp))
        ExampleBox(
            shape = customShape,
            borderColor = translucentColor
        ) // but if we try to apply it to a Shape that has Outline.Generic outline, it looks like this,
        // almost invisible in this case
        Spacer(Modifier.height(16.dp))
        ExampleBoxWithAWorkAround(
            shape = customShape,
            borderColor = translucentColor
        ) //so we use a workaround, where we directly give Brush with linearGradient, not Color, to the Modifier.border()
    }
}

@Composable
fun ExampleBox(
    shape: Shape,
    borderColor: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(50.dp)
            .clip(shape)
            .background(
                color = Color.Transparent,
                shape = shape,
            )
            .border(
                width = 2.dp,
                color = borderColor,
                shape = shape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "A",
        )
    }
}

@Composable
fun ExampleBoxWithAWorkAround(
    shape: Shape,
    borderColor: Color,
    modifier: Modifier = Modifier,
    ) {
    Box(
        modifier = modifier
            .size(50.dp)
            .clip(shape)
            .background(
                color = Color.Transparent,
                shape = shape,
            )
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(listOf(borderColor, borderColor)),  // workaround here
                shape = shape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "A",
        )
    }
}

class CustomShape(
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            addRect(Rect(Offset.Zero, size))
        }
        return Outline.Generic(path) // problem start with Outline.Generic in combo with a translucent Color
    }
}

val translucentColor = Color(0x1C121212)
val customShape = CustomShape()
val standardShape = RectangleShape

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TranslucentColorBorderExampleTheme {
        Example()
    }
}
