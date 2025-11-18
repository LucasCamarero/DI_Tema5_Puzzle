package com.example.di_tema5_puzzle

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
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun Puzzle1(modifier: Modifier = Modifier) {

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
                    Piezas(modifier, elemento)
                }

            }
        }
    }
}

@Composable
// dibuja las piezas
fun Piezas(modifier: Modifier = Modifier, indice: Int) {
    var OffsetX by remember { mutableStateOf(0.dp) }
    var OffsetY by remember { mutableStateOf(0.dp) }
    var density = LocalDensity.current

    Box(
        modifier
            .offset(x = OffsetX, y = OffsetY)
            .size(100.dp)
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(ColorPiezas(indice))
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    OffsetX += with(density) { dragAmount.x.toDp() }
                    OffsetY += with(density) { dragAmount.y.toDp() }
                }
            }
    ) {

    }
}

@Composable
// da color a las piezas
fun ColorPiezas(indice: Int): Color {
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