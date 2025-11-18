package com.example.di_tema5_puzzle   // Paquete del proyecto

import androidx.compose.foundation.background   // Para poner colores de fondo
import androidx.compose.foundation.gestures.detectDragGestures   // Gestos de arrastre
import androidx.compose.foundation.layout.Arrangement            // Alineación en layouts
import androidx.compose.foundation.layout.Box                    // Contenedor básico
import androidx.compose.foundation.layout.Row                    // Fila
import androidx.compose.foundation.layout.Spacer                 // Espaciador
import androidx.compose.foundation.layout.fillMaxSize            // Ocupa todo el espacio disponible
import androidx.compose.foundation.layout.fillMaxWidth           // Ocupa todo el ancho disponible
import androidx.compose.foundation.layout.height                 // Altura fija
import androidx.compose.foundation.layout.offset                 // Permite mover el elemento
import androidx.compose.foundation.layout.padding                // Margen interior
import androidx.compose.foundation.layout.size                   // Tamaño fijo
import androidx.compose.foundation.lazy.LazyColumn               // Lista perezosa en columna
import androidx.compose.foundation.shape.RoundedCornerShape      // Esquinas redondeadas
import androidx.compose.runtime.Composable                      // Anotación de función composable
import androidx.compose.runtime.getValue                         // Delegado getter
import androidx.compose.runtime.mutableStateOf                   // Estado mutable
import androidx.compose.runtime.remember                         // Mantiene el estado entre recomposiciones
import androidx.compose.runtime.setValue                         // Delegado setter
import androidx.compose.ui.Alignment                             // Alineación de UI
import androidx.compose.ui.Modifier                              // Modificador de UI
import androidx.compose.ui.draw.clip                             // Recorta la forma del elemento
import androidx.compose.ui.graphics.Color                        // Colores
import androidx.compose.ui.input.pointer.pointerInput            // Para detectar gestos
import androidx.compose.ui.platform.LocalDensity                 // Conversión de px a dp
import androidx.compose.ui.unit.dp                               // Unidad de medida dp

// Puzzle simple
@Composable
fun Puzzle1(modifier: Modifier = Modifier) {

    var lista = listOf(0,1,2,3).shuffled()   // Lista de piezas mezcladas aleatoriamente

    LazyColumn(                                // Columna "perezosa", eficiente para scroll
        modifier
            .fillMaxSize(),                    // Ocupa toda la pantalla
        horizontalAlignment = Alignment.CenterHorizontally,   // Centra horizontalmente
        verticalArrangement = Arrangement.Center              // Centra verticalmente
    ) {

        item {                                 // Primer bloque del LazyColumn

            // Dibujamos los cuatro cuadrados grises
            for (i in 0..1) {                  // Recorre 2 filas
                Row() {                        // Crea una fila
                    for (j in 0..1) {          // Recorre 2 columnas
                        Box(
                            modifier
                                .size(120.dp)          // Tamaño del cuadro gris
                                .padding(4.dp)         // Espacio entre cuadros
                                .clip(RoundedCornerShape(8.dp))  // Bordes redondeados
                                .background(Color.LightGray)      // Color gris
                        ) {

                        }
                    }
                }
            }
        }

        item {                                 // Segundo bloque
            Spacer(modifier.height(40.dp))     // Espacio entre el tablero y las piezas
        }

        item {                                 // Tercer bloque: piezas de colores
            Row(
                modifier
                    .fillMaxWidth()            // Fila del ancho completo
            ) {
                // Dibujamos las piezas
                lista.forEach() { elemento ->  // Recorre la lista desordenada
                    Piezas(modifier, elemento) // Dibuja cada pieza según su índice
                }

            }
        }
    }
}

@Composable
// dibuja las piezas
fun Piezas(modifier: Modifier = Modifier, indice: Int) {

    var OffsetX by remember { mutableStateOf(0.dp) }   // Posición horizontal de la pieza
    var OffsetY by remember { mutableStateOf(0.dp) }   // Posición vertical de la pieza
    var density = LocalDensity.current                 // Para convertir px → dp

    Box(
        modifier
            .offset(x = OffsetX, y = OffsetY)          // Desplaza la pieza según arrastre
            .size(100.dp)                              // Tamaño de la pieza
            .padding(4.dp)                             // Espacio entre piezas
            .clip(RoundedCornerShape(8.dp))            // Bordes redondeados
            .background(ColorPiezas(indice))           // Color según índice
            .pointerInput(Unit) {                      // Detecta eventos táctiles
                detectDragGestures { change, dragAmount ->
                    change.consume()                   // Consume el evento
                    OffsetX += with(density) { dragAmount.x.toDp() }   // Mueve en X
                    OffsetY += with(density) { dragAmount.y.toDp() }   // Mueve en Y
                }
            }
    ) {

    }
}

@Composable
// da color a las piezas
fun ColorPiezas(indice: Int): Color {
    if (indice == 0) {
        return Color.Cyan     // Pieza 0 → color cian
    } else if (indice == 1) {
        return Color.Green    // Pieza 1 → verde
    } else if (indice == 2) {
        return Color.Blue     // Pieza 2 → azul
    } else if (indice == 3) {
        return Color.Yellow   // Pieza 3 → amarillo
    } else {
        return Color.Black    // Cualquier otro caso → negro
    }
}
