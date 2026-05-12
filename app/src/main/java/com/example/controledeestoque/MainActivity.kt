package com.example.controledeestoque

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.content.Context

class MainActivity : AppCompatActivity() {

    private lateinit var listaProdutos: TextView
    private val estoque = mutableMapOf<String, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputProduto = findViewById<EditText>(R.id.inputProduto)
        val inputQuantidade = findViewById<EditText>(R.id.inputQuantidade)
        val botaoAdicionar = findViewById<Button>(R.id.botaoAdicionar)
        val botaoSaida = findViewById<Button>(R.id.botaoSaida)   // 🔸 novo botão
        listaProdutos = findViewById(R.id.listaProdutos)

        // Carregar dados salvos
        carregarEstoque()

        // Botão de adicionar
        botaoAdicionar.setOnClickListener {
            val nome = inputProduto.text.toString()
            val qtd = inputQuantidade.text.toString().toIntOrNull() ?: 0

            if (nome.isNotBlank() && qtd > 0) {
                estoque[nome] = (estoque[nome] ?: 0) + qtd
                atualizarLista()
                salvarEstoque()
            }
        }

        // 🔹 Botão de saída
        botaoSaida.setOnClickListener {
            val nome = inputProduto.text.toString()
            val qtd = inputQuantidade.text.toString().toIntOrNull() ?: 0

            if (nome.isNotBlank() && qtd > 0) {
                val atual = estoque[nome] ?: 0
                val novoValor = atual - qtd
                if (novoValor > 0) {
                    estoque[nome] = novoValor
                } else {
                    estoque.remove(nome) // remove se zerar ou ficar negativo
                }
                atualizarLista()
                salvarEstoque()
            }
        }
    }

    private fun atualizarLista() {
        val builder = StringBuilder()
        for ((nome, qtd) in estoque) {
            builder.append("$nome - $qtd\n")
        }
        listaProdutos.text = builder.toString()
    }

    private fun salvarEstoque() {
        val prefs = getSharedPreferences("estoque", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val dados = estoque.entries.joinToString(";") { "${it.key}:${it.value}" }
        editor.putString("produtos", dados)
        editor.apply()
    }

    private fun carregarEstoque() {
        val prefs = getSharedPreferences("estoque", Context.MODE_PRIVATE)
        val dados = prefs.getString("produtos", "") ?: ""
        if (dados.isNotEmpty()) {
            val itens = dados.split(";")
            for (item in itens) {
                val partes = item.split(":")
                if (partes.size == 2) {
                    val nome = partes[0]
                    val qtd = partes[1].toIntOrNull() ?: 0
                    estoque[nome] = qtd
                }
            }
            atualizarLista()
        }
    }
}
