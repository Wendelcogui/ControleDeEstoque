package com.example.controledeestoque

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nomeProduto = findViewById<EditText>(R.id.nomeProduto)
        val quantidadeProduto = findViewById<EditText>(R.id.quantidadeProduto)
        val botaoAdicionar = findViewById<Button>(R.id.botaoAdicionar)
        val listaProdutos = findViewById<TextView>(R.id.listaProdutos)

        botaoAdicionar.setOnClickListener {
            val produto = Produto(
                nomeProduto.text.toString(),
                quantidadeProduto.text.toString().toInt()
            )
            listaProdutos.append("${produto.nome} - ${produto.quantidade}\n")
        }
    }
}
