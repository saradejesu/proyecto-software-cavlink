<template>
  <!-- File: ConsultarProducto.vue - Interfaz para consultar productos (visible a USUARIO) -->
  <div>
    <h2>Productos</h2>
    <div v-for="p in productos" :key="p.id">
      <h3>{{ p.nombre }}</h3>
      <p>{{ p.categoria }} - {{ p.descripcion }}</p>
      <p>Precio: {{ p.precio }} | Cantidad: {{ p.cantidad }}</p>
    </div>
  </div>
</template>

<script>
// File: ConsultarProducto.vue - Funcionalidad: GET /api/productos/
import axios from 'axios'
export default {
  name: 'ConsultarProducto',
  data() { return { productos: [] } },
  async created() {
    try {
      const res = await axios.get('/api/productos/')
      this.productos = res.data
    } catch (err) { alert('Error cargando productos') }
  }
}
</script>
