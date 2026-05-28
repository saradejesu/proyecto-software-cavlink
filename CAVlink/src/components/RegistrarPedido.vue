<template>
  <!-- File: RegistrarPedido.vue - Interfaz para seleccionar productos y registrar pedido -->
  <div>
    <h2>Registrar Pedido</h2>
    <div v-for="p in productos" :key="p.id">
      <p>{{ p.nombre }} - {{ p.precio }} | Stock: {{ p.cantidad }}</p>
      <input type="number" v-model.number="p.seleccion" min="0" :max="p.cantidad" />
    </div>
    <button @click="registrar">Registrar Pedido</button>
  </div>
</template>

<script>
// File: RegistrarPedido.vue - Funcionalidad: Construye pedido y POST /api/pedidos/registrar
import axios from 'axios'
export default {
  name: 'RegistrarPedido',
  data() { return { productos: [], user: null } },
  async created() {
    const u = localStorage.getItem('user')
    if (!u) return this.$router.push({ name: 'Login' })
    this.user = JSON.parse(u)
    const res = await axios.get('/api/productos/')
    this.productos = res.data.map(p => ({ ...p, seleccion: 0 }))
  },
  methods: {
    async registrar() {
      const seleccionados = this.productos.filter(p => p.seleccion && p.seleccion > 0).map(p => ({ ...p }))
      if (seleccionados.length === 0) return alert('Seleccione al menos un producto')
      const total = seleccionados.reduce((s, p) => s + p.precio * p.seleccion, 0)
      const pedido = { idUsuario: this.user.id, productos: seleccionados.map(p => ({ id: p.id, nombre: p.nombre, cantidad: p.seleccion, precio: p.precio })), total: total, estado: 'PENDIENTE' }
      try {
        await axios.post('/api/pedidos/registrar', pedido)
        alert('Pedido registrado')
        this.$router.push({ name: 'ConsultarCarrito' })
      } catch (err) { alert(err.response?.data || 'Error registrando pedido') }
    }
  }
}
</script>
