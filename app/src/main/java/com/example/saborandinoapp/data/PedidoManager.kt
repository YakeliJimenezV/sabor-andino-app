package com.example.saborandinoapp.data

object PedidoManager {

    val pedido = mutableListOf<Plato>()

    fun agregar(plato: Plato) {
        pedido.add(plato)
    }

    fun eliminar(plato: Plato) {
        pedido.remove(plato)
    }

    fun limpiar() {
        pedido.clear()
    }

    fun total(): Double {
        return pedido.sumOf { it.precio }
    }
}
