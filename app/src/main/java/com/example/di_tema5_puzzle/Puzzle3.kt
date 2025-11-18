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

data class PieceShape(
    val top: Boolean = false,
    val right: Boolean = false,
    val bottom: Boolean = false,
    val left: Boolean = false,
)

// Mapa de piezas para tablero 2x2
val pieceMap: List<PieceShape> = listOf(
    // Fila 0, Columna 0
    PieceShape(top = false, right = true, bottom = true, left = false),
    // Fila 0, Columna 1
    PieceShape(top = false, right = false, bottom = true, left = true),
    // Fila 1, Columna 0
    PieceShape(top = true, right = true, bottom = false, left = false),
    // Fila 1, Columna 1
    PieceShape(top = true, right = false, bottom = false, left = true)
)
// Igual que puzzle 2 pero sin los datos hardcodeados
@Composable
fun Puzzle3(modifier: Modifier = Modifier) {

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
                    Piezas3(modifier, elemento) // Dibujamos pieza según su índice
                }

            }
        }
    }
}

@Composable
// Dibuja una pieza con forma (incluyendo cabezas/lados)
fun Piezas3(modifier: Modifier = Modifier, indice: Int) {

    var OffsetX by remember { mutableStateOf(0.dp) } // Posición X de la pieza
    var OffsetY by remember { mutableStateOf(0.dp) } // Posición Y de la pieza
    var density = LocalDensity.current               // Para convertir px a dp
    val shape = pieceMap[indice]

    // Llamamos a la forma custom para dibujarla
    FormaPieza2(
        modifier
            .offset(x = OffsetX, y = OffsetY) // Posición movida por drag
            .size(100.dp)                     // Tamaño de la pieza
            .pointerInput(Unit) {             // Detecta arrastre
                detectDragGestures { change, dragAmount ->
                    change.consume()          // Consumimos el evento
                    OffsetX += with(density) { dragAmount.x.toDp() } // Mover X
                    OffsetY += with(density) { dragAmount.y.toDp() } // Mover Y
                }
            },
        color = ColorPiezas3(indice),         // Color según índice
        top = shape.top,                    // Si la pieza tiene cabeza arriba
        right = shape.right,                  // Si tiene cabeza derecha
        bottom = shape.bottom,                 // Cabeza abajo
        left = shape.left                    // Cabeza izquierda
    )
}

@Composable
// Devuelve un color según el índice de la pieza
fun ColorPiezas3(indice: Int): Color {
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
fun FormaPieza2(
    modifier: Modifier = Modifier,
    color: Color,
    top: Boolean,
    bottom: Boolean,
    right: Boolean,
    left:Boolean
) {

    Canvas(modifier) {                         // Componente para dibujar
        val w = size.width                     // Ancho del canvas
        val h = size.height                    // Alto del canvas
        val knobSize = w * 0.2f                // Tamaño de la "cabeza" de la pieza

        val path = Path().apply {              // Ruta que define la forma

            moveTo(0f, 0f)                     // Empezamos en la esquina superior izquierda

            // borde superior
            if (top) {                         // Si tiene cabeza arriba
                lineTo(w * 0.4f, 0f)           // Comienzo del arco
                cubicTo(                       // Curva hacia afuera
                    w * 0.45f, -knobSize,
                    w * 0.55f, -knobSize,
                    w * 0.6f, 0f
                )
                lineTo(w, 0f)                  // Fin del borde superior
            } else {
                lineTo(w, 0f)                  // Borde recto
            }

            // borde derecho
            if (right) {                       // Cabeza derecha
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
            if (bottom) {                      // Cabeza inferior
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
            if (left) {                        // Cabeza izquierda
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

            close()                            // Cerramos la figura
        }

        drawPath(path, color)                  // Dibujamos la forma con el color
    }
}