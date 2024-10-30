package com.example.examenjetpackcomposeneosrafaelruizsanchez

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.examenjetpackcomposeneosrafaelruizsanchez.ui.theme.ExamenJetPackComposeNeosRafaelRuizSanchezTheme

class MainActivity : ComponentActivity() {
    /**
     * Al crear el prpgramadefinimos como pantalla inicial PantallaSexo y implementamos el navController
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            val navController = rememberNavController()
            ExamenJetPackComposeNeosRafaelRuizSanchezTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "PantallaSexo",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("PantallaSexo") {
                            PantallaSexo(
                                navController = navController
                            )
                        }
                        composable("PantallaAltura/{nombre},{sexo}") { backStackEntry ->
                            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
                            val sexo = backStackEntry.arguments?.getString("sexo") ?: ""
                            PantallaAltura(
                                navController = navController,
                                nombre = nombre,
                                sexo = sexo
                            )
                        }
                        composable("PantallaResultado/{nombre},{sexo},{altura},{peso}") { backStackEntry ->
                            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
                            val sexo = backStackEntry.arguments?.getString("sexo") ?: ""
                            val altura = backStackEntry.arguments?.getString("altura") ?: ""
                            val peso = backStackEntry.arguments?.getString("peso") ?: ""
                            PantallaResultado(nombre, sexo, altura, peso)
                        }
                    }
                }
            }
        }
    }

    /**
     * Funcion Pantalla sexo  recibe como  parametros ,nombre ,sexo y el navController se encarga de recojer datos y llamar a  PantallaAltura
     */
    @Composable
    fun PantallaSexo(navController: NavHostController) {
        var nombre by remember { mutableStateOf("") }
        var sexo by remember { mutableStateOf("") }
        Column(
            Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Introduzca Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            Row {
                Button(
                    onClick = {
                        sexo = "mujer"
                        navController.navigate("PantallaAltura/$nombre,$sexo")
                    }
                ) {
                    Text(text = "Mujer")
                }
                Button(
                    onClick = {
                        sexo = "Hombre"
                        navController.navigate("PantallaAltura/$nombre,$sexo")
                    }
                ) {
                    Text(text = "Hombre")
                }
            }
        }
    }

    /**
     * Funcion Pantalla altura muestra los datos pedidios anteriormente y pide el camopo altura
     * recibe como parametros nombre,sexo,modifier y el navController y se mueve a pantalla sexo
     */
    @Composable
    fun PantallaAltura(
        navController: NavHostController,
        nombre: String,
        sexo: String,
        modifier: Modifier = Modifier
    ) {
        var altura by remember { mutableStateOf("") }
        var peso by remember { mutableStateOf("") }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Bienvenid@ $nombre eres un@ $sexo!",
                modifier = modifier
            )
            TextField(
                value = altura,
                onValueChange = { altura = it },
                label = { Text("Introduce tu altura en Metros Ej:1.70") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = peso,
                onValueChange = { peso = it },
                label = { Text("Introduce tu peso en Kilos Ej:60.5") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    navController.navigate("PantallaResultado/$nombre,$sexo,$altura,$peso")
                }
            ) {
                Text(text = "Mostrar Resultado")
            }
        }


    }

    /**
     * Funcion Resultado Calcula el Imc y escribe si el peso es correcto
     * recive como parametros nombre,sexo,altura,peso
     */
    @Composable
    fun PantallaResultado(
        nombre: String,
        sexo: String,
        altura: String,
        peso: String,
        modifier: Modifier = Modifier
    ) {
        var imc by remember { mutableDoubleStateOf(0.0) }
        var coeficiente by remember { mutableDoubleStateOf(0.0) }
        var imcRecomendacionPeso by remember { mutableStateOf("") }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp)
                    .background(Color.LightGray)
            ) {
                Text(
                    text = "Sus datos son: Nombre: $nombre  , Sexo: $sexo , Altura: $altura , Peso: $peso",
                    modifier = modifier
                )
            }
            coeficiente = if (sexo == "Hombre") 1.0 else 0.95
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp)
                    .background(Color.LightGray)
            ) {
                Text(
                    text = "Su coeficiente es: $coeficiente ",
                    modifier = modifier
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            imc = (peso.toDouble() / (altura.toDouble() * altura.toDouble())) * coeficiente
            if (imc < 18.5) {
                imcRecomendacionPeso = "un Peso bajo"
            }
            if (imc in 18.5..24.9) {
                imcRecomendacionPeso = "un Peso normal"
            }
            if (imc in 25.0..29.9) {
                imcRecomendacionPeso = "Sobrepeso"
            }
            if (imc >= 30.0) {
                imcRecomendacionPeso = "Obesidad"
            }
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp)
                    .background(Color.LightGray)
            ) {
                Text(
                    text = "Su IMC es: $imc  y tiene $imcRecomendacionPeso",
                    modifier = modifier
                )
            }
        }
    }


}