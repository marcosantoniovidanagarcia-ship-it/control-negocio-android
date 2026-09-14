package com.dmarkcontrol.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DMarkControl()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DMarkControl() {
    var ventas by remember { mutableStateOf(0) }
    var gastos by remember { mutableStateOf(0) }

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("D'Mark Control")
                    }
                )
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = "Control de mi negocio",
                    style = MaterialTheme.typography.headlineSmall
                )

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text("Ventas")
                        Text(
                            text = "$ventas CUP",
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = { ventas += 1000 }
                        ) {
                            Text("Registrar venta +1,000 CUP")
                        }
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text("Gastos")
                        Text(
                            text = "$gastos CUP",
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = { gastos += 500 }
                        ) {
                            Text("Registrar gasto +500 CUP")
                        }
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text("Balance")

                        Text(
                            text = "${ventas - gastos} CUP",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
            }
        }
    }
}