<template>
  <!-- File: ConsultarCarrito.vue - Interfaz para que USUARIO vea su carrito/pedidos -->
  <div>
    <h2>Mis Pedidos</h2>
    <div v-for="p in pedidos" :key="p.idPedido">
      <h3>Pedido #{{ p.idPedido }} - {{ p.estado }}</h3>
      <p>Total: {{ p.total }}</p>
      <div v-for="item in p.productos" :key="item.id">
        <p>{{ item.nombre }} x {{ item.cantidad }} - {{ item.precio }}</p>
      </div>
    </div>
  </div>
</template>

<script>
// File: ConsultarCarrito.vue - Funcionalidad: GET /api/pedidos/usuario/{id}
import axios from 'axios'
export default {
  name: 'ConsultarCarrito',
  data() { return { pedidos: [], user: null } },
  async created() {
    const u = localStorage.getItem('user')
    if (!u) return this.$router.push({ name: 'Login' })
    this.user = JSON.parse(u)
    try {
      const res = await axios.get(`/api/pedidos/usuario/${this.user.id}`)
      this.pedidos = res.data
    } catch (err) { alert('Error cargando pedidos') }
  }
}
</script>
