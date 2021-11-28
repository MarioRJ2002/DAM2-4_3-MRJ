data class Tienda(val nombre: String, val clientes: List<Clientes>)

data class Clientes(val nombre: String, val ciudad: Ciudad, val pedidos: List<Pedido>) {
    override fun toString() = "$nombre from ${ciudad.nombre}"
}

data class Pedido(val productos: List<Producto>, val estaEntregado: Boolean)

data class Producto(val nombre: String, val precio: Double) {
    override fun toString() = "'$nombre' for $precio"
}

data class Ciudad(val nombre: String) {
    override fun toString() = nombre
}

fun Tienda.obtenerConjuntoDeClientes():List<Clientes>{
    return clientes
}

fun Tienda.obtenerCiudadesDeClientes(): Set<Ciudad>{
    var ciudadesClientes = arrayListOf<Ciudad>()
    clientes.forEach{if(ciudadesClientes.contains(it.ciudad)){}else{ciudadesClientes.add(it.ciudad)}}
    return ciudadesClientes.toSet()
}

fun Tienda.obtenerClientesPor(ciudad:Ciudad): List<Clientes>{
    var clientesCiudad = clientes.filter { it.ciudad==ciudad}
    return clientesCiudad
}

fun Tienda.checkTodosClientesSonDe(ciudad : Ciudad): Boolean{return clientes.all{it.ciudad==ciudad}}

fun Tienda.hayClientesDe(ciudad: Ciudad): Boolean{return clientes.any{it.ciudad==ciudad}}

fun Tienda.cuentaClientesDe(ciudad: Ciudad): Int{return clientes.count{it.ciudad==ciudad}}

fun Tienda.obtenerClientesOrdenadosPorPedidos(): List<Clientes>{
    return clientes.sortedByDescending {it.pedidos.size}
}

fun Tienda.obtenerClientesConPedidosSinEngregar(): Set<Clientes>{
    var clientesPedidosNoEntregados = arrayListOf<Clientes>()
    clientes.forEach{if(it.pedidos.any{it.estaEntregado==false}){clientesPedidosNoEntregados.add(it)}}
    return clientesPedidosNoEntregados.toSet()
}

fun Clientes.obtenerProductosPedidos(): List<Producto>{
    var productosPedidos = arrayListOf<Producto>()
    var productosEliminados = arrayListOf<Producto>()
    pedidos.forEach{
        it.productos.forEach{
            if(it in productosPedidos)
            {productosPedidos.remove(it)
             productosEliminados.add(it)}
            else{
                if(it in productosEliminados){
                productosPedidos.add(it)
                }
            }
        }
    }
    return productosPedidos
}

fun Tienda.obtenerProductosPedidos(): Set<Producto>{
    var productosPedidos = arrayListOf<Producto>()
    clientes.forEach{it.pedidos.forEach{it.productos.forEach{if(it in productosPedidos){}else{productosPedidos.add(it)}}}}
    return productosPedidos.toSet()
}

fun Clientes.encuentraProductoMasCaro(): Producto? {
    var maxPrecio = Producto("a",0.0)
    pedidos.forEach{it.productos.forEach {if(it.precio>maxPrecio.precio){maxPrecio=it}}}
    return maxPrecio
}

fun Tienda.obtenerNumeroVecesProductoPedido(producto: Producto): Int {
    var contProductoPedido = 0
    clientes.forEach{it.pedidos.forEach {it.productos.forEach {if (it==producto){contProductoPedido++}}}}
    return contProductoPedido
}

fun Tienda.agrupaClientesPorCiudad(): Map<Ciudad, List<Clientes>>{
    var clientesPorCiudad = clientes.groupBy { it.ciudad }
    return clientesPorCiudad
}

fun Clientes.dineroGastado(): Double{
    var dineroGastado = 0.0
    pedidos.forEach{it.productos.forEach{dineroGastado = dineroGastado + it.precio }}
    return dineroGastado
}

fun Tienda.obtenerClientesConMaxPedidos(): Clientes?{
    return clientes.maxByOrNull { it.pedidos.size }
}


