<template>
  <!-- File: CrearProducto.vue - Interfaz exclusiva para ADMIN -->
  <div>
    <h2>Crear Producto</h2>
    <form @submit.prevent="onSubmit">
      <div><label>Nombre</label><input v-model="nombre" /></div>
      <div><label>Categoria</label><input v-model="categoria" /></div>
      <div><label>Descripcion</label><input v-model="descripcion" /></div>
      <div><label>Precio</label><input v-model.number="precio" type="number" step="0.01" /></div>
      <div><label>Cantidad</label><input v-model.number="cantidad" type="number" /></div>
      <button type="submit">Crear</button>
    </form>
  </div>
</template>

<script>
// File: CrearProducto.vue - Funcionalidad: Validación frontend y POST a /api/productos/crear
import axios from 'axios'
export default {
  name: 'CrearProducto',
  data() { return { nombre: '', categoria: '', descripcion: '', precio: 0, cantidad: 0 } },
  methods: {
    async onSubmit() {
      if (!this.nombre || !this.categoria) return alert('Campos obligatorios')
      if (this.precio < 0 || this.cantidad < 0) return alert('Valores deben ser positivos')
      try {
        const prod = { nombre: this.nombre, categoria: this.categoria, descripcion: this.descripcion, precio: this.precio, cantidad: this.cantidad }
        await axios.post('/api/productos/crear', prod)
        alert('Producto creado')
        this.$router.push({ name: 'ConsultarProducto' })
      } catch (err) {
        alert(err.response?.data || 'Error creando producto')
      }
    }
  }
}
</script>
