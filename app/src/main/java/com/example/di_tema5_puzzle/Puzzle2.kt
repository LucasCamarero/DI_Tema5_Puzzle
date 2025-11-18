package com.example.di_tema5_puzzle

import android.graphics.Canvas
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

// Puzzle con cabezas y agujeros
@Composable
fun Puzzle2(modifier: Modifier = Modifier) {

    var lista = listOf(0,1,2,3).shuffled()

    LazyColumn(
        modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {

            // Dibujamos los cuatro cuadrados grises
            for (i in 0..1) {
                Row() {
                    for (j in 0..1) {
                        Box(
                            modifier
                                .size(120.dp)
                                .padding(4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.LightGray)
                        ) {

                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier.height(40.dp))
        }

        item {
            Row(
                modifier
                    .fillMaxWidth()
            ) {
                // Dibujamos las piezas
                lista.forEach() {elemento ->
                    Piezas2(modifier, elemento)
                }

            }
        }
    }
}

@Composable
// dibuja las piezas
fun Piezas2(modifier: Modifier = Modifier, indice: Int) {
    var OffsetX by remember { mutableStateOf(0.dp) }
    var OffsetY by remember { mutableStateOf(0.dp) }
    var density = LocalDensity.current

    FormaPieza(modifier
        .offset(x = OffsetX, y = OffsetY)
        .size(100.dp)
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consume()
                OffsetX += with(density) { dragAmount.x.toDp() }
                OffsetY += with(density) { dragAmount.y.toDp() }
            }
        },
        color = ColorPiezas2(indice),
        top = indice == 0,
        right = indice == 1,
        bottom = indice == 2,
        left = indice == 3
        )
}

@Composable
// da color a las piezas
fun ColorPiezas2(indice: Int): Color {
    if (indice == 0) {
        return Color.Cyan
    } else if (indice == 1) {
        return Color.Green
    } else if (indice == 2) {
        return Color.Blue
    } else if (indice == 3) {
        return Color.Yellow
    } else {
        return Color.Black
    }
}

@Composable
fun FormaPieza(modifier: Modifier = Modifier, color:Color, top: Boolean, bottom: Boolean, right: Boolean, left:Boolean) {

    Canvas(modifier) {
        val w = size.width
        val h = size.height
        val knobSize = w * 0.2f

        val path = Path().apply {
            moveTo(0f, 0f)

            // borde superior
            if (top) {
                lineTo(w * 0.4f, 0f)
                cubicTo(
                    w * 0.45f, -knobSize,
                    w * 0.55f, -knobSize,
                    w * 0.6f, 0f
                )
                lineTo(w, 0f)
            } else {
                lineTo(w, 0f)
            }

            // borde derecho
            if (right) {
                lineTo(w, h * 0.4f)
                cubicTo(
                    w + knobSize, h * 0.45f,
                    w + knobSize, h * 0.55f,
                    w, h * 0.6f
                )
                lineTo(w, h)
            } else {
                lineTo(w, h)
            }

            // borde inferior
            if (bottom) {
                lineTo(w * 0.6f, h)
                cubicTo(
                    w * 0.55f, h + knobSize,
                    w * 0.45f, h + knobSize,
                    w * 0.4f, h
                )
                lineTo(0f, h)
            } else {
                lineTo(0f, h)
            }

            // borde izquierdo
            if (left) {
                lineTo(0f, h * 0.6f)
                cubicTo(
                    -knobSize, h * 0.55f,
                    -knobSize, h * 0.45f,
                    0f, h * 0.4f
                )
                lineTo(0f, 0f)
            } else {
                lineTo(0f, 0f)
            }

            close()
        }

        drawPath(path, color)
    }
}
