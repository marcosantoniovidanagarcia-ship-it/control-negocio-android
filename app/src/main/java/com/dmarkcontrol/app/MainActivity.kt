package com.dmarkcontrol.app

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val money = NumberFormat.getCurrencyInstance(Locale("es", "CU"))

    private var sales = 0.0
    private var expenses = 0.0
    private var cash = 0.0
    private var transfers = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showHome()
    }

    private fun showHome() {

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 48, 32, 32)
        }

        val title = TextView(this).apply {
            text = "D’Mark Control"
            textSize = 30f
            setPadding(0, 0, 0, 24)
        }

        root.addView(title)

        val summary = TextView(this).apply {
            textSize = 18f
            text = summaryText()
            setPadding(0, 0, 0, 20)
        }

        root.addView(summary)

        addButton(root, "➕ Registrar venta") {
            registerSale(summary)
        }

        addButton(root, "📦 Inventario") {
            showMessage("Inventario", "Módulo de inventario.")
        }

        addButton(root, "💸 Registrar gasto") {
            registerExpense(summary)
        }

        addButton(root, "💵 Caja") {
            showCash()
        }

        addButton(root, "📊 Ganancias") {
            showProfit()
        }

        addButton(root, "📈 Punto de equilibrio") {
            showBreakEven()
        }

        setContentView(root)
    }

    private fun addButton(
        root: LinearLayout,
        text: String,
        action: () -> Unit
    ) {
        root.addView(Button(this).apply {
            this.text = text
            textSize = 16f
            setOnClickListener { action() }
        })
    }

    private fun summaryText(): String {
        return """
            Ventas: ${money.format(sales)}
            Gastos: ${money.format(expenses)}
            Ganancia: ${money.format(sales - expenses)}
            
            Efectivo: ${money.format(cash)}
            Transferencias: ${money.format(transfers)}
        """.trimIndent()
    }

    private fun registerSale(summary: TextView) {

        val input = EditText(this).apply {
            hint = "Importe de la venta"
            inputType = 2 or 8192
        }

        AlertDialog.Builder(this)
            .setTitle("Registrar venta")
            .setView(input)
            .setPositiveButton("Continuar") { _, _ ->

                val value =
                    input.text.toString().toDoubleOrNull() ?: 0.0

                if (value <= 0) return@setPositiveButton

                AlertDialog.Builder(this)
                    .setTitle("Método de pago")
                    .setItems(
                        arrayOf("Efectivo", "Transferencia")
                    ) { _, which ->

                        sales += value

                        if (which == 0) {
                            cash += value
                        } else {
                            transfers += value
                        }

                        summary.text = summaryText()
                    }
                    .show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun registerExpense(summary: TextView) {

        val input = EditText(this).apply {
            hint = "Importe del gasto"
            inputType = 2 or 8192
        }

        AlertDialog.Builder(this)
            .setTitle("Registrar gasto")
            .setView(input)
            .setPositiveButton("Guardar") { _, _ ->

                val value =
                    input.text.toString().toDoubleOrNull() ?: 0.0

                if (value > 0) {
                    expenses += value
                    summary.text = summaryText()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun showCash() {

        AlertDialog.Builder(this)
            .setTitle("Caja")
            .setMessage(
                """
                Efectivo:
                ${money.format(cash)}
                
                Transferencias:
                ${money.format(transfers)}
                
                Total:
                ${money.format(cash + transfers)}
                """.trimIndent()
            )
            .setPositiveButton("Aceptar", null)
            .show()
    }

    private fun showProfit() {

        AlertDialog.Builder(this)
            .setTitle("Ganancias")
            .setMessage(
                """
                Ventas: ${money.format(sales)}
                
                Gastos: ${money.format(expenses)}
                
                Ganancia:
                ${money.format(sales - expenses)}
                """.trimIndent()
            )
            .setPositiveButton("Aceptar", null)
            .show()
    }

    private fun showBreakEven() {

        AlertDialog.Builder(this)
            .setTitle("Punto de equilibrio")
            .setMessage(
                "En la siguiente versión calcularemos automáticamente " +
                "cuántos productos debes vender para cubrir tus gastos."
            )
            .setPositiveButton("Aceptar", null)
            .show()
    }

    private fun showMessage(title: String, message: String) {

        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Aceptar", null)
            .show()
    }
}