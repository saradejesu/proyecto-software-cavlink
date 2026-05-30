<template>
  <div class="page">
    <section class="card">
      <header class="card__header">
        <h2>Crear Producto</h2>
        <p class="muted">Añade nuevos artículos al catálogo (Rol Admin)</p>
      </header>
      <form @submit.prevent="onSubmit" class="form">
        <label class="field">
          <span>Nombre del Producto</span>
          <input v-model="form.nombre" required />
        </label>
        <label class="field">
          <span>Categoría</span>
          <input v-model="form.categoria" required />
        </label>
        <label class="field">
          <span>Precio</span>
          <input type="number" v-model.number="form.precio" step="0.01" required />
        </label>
        <label class="field">
          <span>Cantidad en Stock</span>
          <input type="number" v-model.number="form.cantidad" required />
        </label>
        <button type="submit" class="btn">Guardar Producto</button>
      </form>
    </section>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'CrearProducto',
  data() {
    return {
      form: { nombre: '', categoria: '', precio: 0, cantidad: 0 }
    }
  },
  methods: {
    async onSubmit() {
      try {
        await axios.post('/api/productos/crear', this.form)
        alert('Producto creado exitosamente')
        this.$router.push({ name: 'Catalogo' })
      } catch (err) {
        alert('Error al crear producto')
      }
    }
  }
}
</script>

<style scoped>
.page { max-width: 500px; margin: 0 auto; padding: 20px; }
.card { background: white; border: 1px solid #e5e7eb; border-radius: 12px; padding: 20px; }
.card__header { margin-bottom: 20px; }
.form { display: grid; gap: 16px; }
.field { display: grid; gap: 6px; font-size: 14px; }
.field input { padding: 10px; border: 1px solid #d1d5db; border-radius: 8px; }
.btn {
  background: #2563eb;
  color: white;
  border: none;
  padding: 10px;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 600;
}
.muted { color: #6b7280; font-size: 14px; }
</style>