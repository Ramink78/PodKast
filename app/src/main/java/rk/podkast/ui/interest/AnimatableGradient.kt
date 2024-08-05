package rk.podkast.ui.interest

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import kotlin.math.max
import kotlin.time.Duration.Companion.seconds

@Composable
fun AnimatableBoxGradient(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val colors = listOf(
        MaterialTheme.colorScheme.surface,
        MaterialTheme.colorScheme.primary.copy(alpha = .5f)
    )
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(30.seconds.inWholeMilliseconds.toInt()),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    val scale by infiniteTransition.animateFloat(
        initialValue = 2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(20.seconds.inWholeMilliseconds.toInt()),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    val shader = remember {
        Brush.linearGradient(
            colors = colors
        )
    }
    Box(
        modifier = modifier
            .drawBehind {
                scale(scale = scale, pivot = Offset.Zero) {
                    rotate(angle) {
                        drawCircle(
                            brush = shader,
                            radius = max(size.width, size.height).toFloat()
                        )
                    }
                }
            }, content = content
    )
}

