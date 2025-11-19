package com.example.di_tema5_puzzle

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

data class PieceShape10(
    val top: Boolean = false,
    val right: Boolean = false,
    val bottom: Boolean = false,
    val left: Boolean = false,
)

// Mapa de piezas para tablero 2x2
val pieceMap2: List<PieceShape10> = listOf(
    // Fila 0, Columna 0
    PieceShape10(top = false, right = true, bottom = true, left = false),
    // Fila 0, Columna 1
    PieceShape10(top = false, right = false, bottom = true, left = true),
    // Fila 1, Columna 0
    PieceShape10(top = true, right = true, bottom = false, left = false),
    // Fila 1, Columna 1
    PieceShape10(top = true, right = false, bottom = false, left = true)
)
// Igual que puzzle 3 pero con cabezas y huecos
@Composable
fun Puzzle4(modifier: Modifier = Modifier) {
    var lista = listOf(0,1,2,3).shuffled() // Mezclamos los índices de las piezas

    LazyColumn(
        modifier
            .fillMaxSize(),               // La columna ocupa toda la pantalla
        horizontalAlignment = Alignment.CenterHorizontally, // Centrado horizontal
        verticalArrangement = Arrangement.Center            // Centrado vertical
    ) {
        item {

            // Dibujamos los cuatro cuadrados grises (tablero base)
            for (i in 0..1) {              // Dos filas
                Row() {                    // Fila de celdas
                    for (j in 0..1) {      // Dos columnas
                        Box(
                            modifier
                                .size(120.dp)                 // Tamaño de la celda
                                .padding(4.dp)                // Separación
                                .clip(RoundedCornerShape(8.dp)) // Bordes redondeados
                                .background(Color.LightGray)     // Color gris
                        ) {

                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier.height(40.dp)) // Espacio entre tablero y piezas
        }

        item {
            Row(
                modifier
                    .fillMaxWidth()         // Fila para las piezas
            ) {
                // Dibujamos las piezas
                lista.forEach() { elemento -> // Recorremos cada índice desordenado
                    Piezas10(modifier, elemento) // Dibujamos pieza según su índice
                }

            }
        }
    }
}

@Composable
// Dibuja una pieza con forma (incluyendo cabezas/lados)
fun Piezas10(modifier: Modifier = Modifier, indice: Int) {

    val density = LocalDensity.current
    var offsetX by remember { mutableStateOf(0.dp) }
    var offsetY by remember { mutableStateOf(0.dp) }
    val color = ColorPiezas3(indice)

    val shape = pieceMap2[indice]

    FormaPieza10(
        modifier = Modifier
            .offset(x = offsetX, y = offsetY)
            .size(100.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += with(density) { dragAmount.x.toDp() }
                    offsetY += with(density) { dragAmount.y.toDp() }
                }
            },
        color = color,
        topHead = shape.top,   // cabeza arriba
        rightHole = shape.right, // agujero a la derecha
        bottomHead = shape.bottom,
        leftHole = shape.left
    )
}

@Composable
// Devuelve un color según el índice de la pieza
fun ColorPiezas10(indice: Int): Color {
    if (indice == 0) {
        return Color.Cyan      // Pieza 0 → cian
    } else if (indice == 1) {
        return Color.Green     // Pieza 1 → verde
    } else if (indice == 2) {
        return Color.Blue      // Pieza 2 → azul
    } else if (indice == 3) {
        return Color.Yellow    // Pieza 3 → amarillo
    } else {
        return Color.Black     // Por defecto, negro
    }
}

@Composable
// Dibuja la forma de la pieza de puzzle usando Canvas y Path
fun FormaPieza10(
    modifier: Modifier = Modifier,
    color: Color,
    topHead: Boolean = false,
    topHole: Boolean = false,
    rightHead: Boolean = false,
    rightHole: Boolean = false,
    bottomHead: Boolean = false,
    bottomHole: Boolean = false,
    leftHead: Boolean = false,
    leftHole: Boolean = false
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val knobSize = w * 0.2f

        val path = Path().apply {
            moveTo(0f, 0f)

            // borde superior
            if (topHead) {
                lineTo(w * 0.4f, 0f)
                cubicTo(w * 0.45f, -knobSize, w * 0.55f, -knobSize, w * 0.6f, 0f)
                lineTo(w, 0f)
            } else if (topHole) {
                lineTo(w * 0.4f, 0f)
                cubicTo(w * 0.45f, +knobSize, w * 0.55f, +knobSize, w * 0.6f, 0f)
                lineTo(w, 0f)
            } else {
                lineTo(w, 0f)
            }

            // borde derecho
            if (rightHead) {
                lineTo(w, h * 0.4f)
                cubicTo(w + knobSize, h * 0.45f, w + knobSize, h * 0.55f, w, h * 0.6f)
                lineTo(w, h)
            } else if (rightHole) {
                lineTo(w, h * 0.4f)
                cubicTo(w - knobSize, h * 0.45f, w - knobSize, h * 0.55f, w, h * 0.6f)
                lineTo(w, h)
            } else {
                lineTo(w, h)
            }

            // borde inferior
            if (bottomHead) {
                lineTo(w * 0.6f, h)
                cubicTo(w * 0.55f, h + knobSize, w * 0.45f, h + knobSize, w * 0.4f, h)
                lineTo(0f, h)
            } else if (bottomHole) {
                lineTo(w * 0.6f, h)
                cubicTo(w * 0.55f, h - knobSize, w * 0.45f, h - knobSize, w * 0.4f, h)
                lineTo(0f, h)
            } else {
                lineTo(0f, h)
            }

            // borde izquierdo
            if (leftHead) {
                lineTo(0f, h * 0.6f)
                cubicTo(-knobSize, h * 0.55f, -knobSize, h * 0.45f, 0f, h * 0.4f)
                lineTo(0f, 0f)
            } else if (leftHole) {
                lineTo(0f, h * 0.6f)
                cubicTo(+knobSize, h * 0.55f, +knobSize, h * 0.45f, 0f, h * 0.4f)
                lineTo(0f, 0f)
            } else {
                lineTo(0f, 0f)
            }

            close()
        }

        drawPath(path, color)
    }
}