<template>
  <div class="page">
    <header class="page__header">
      <div>
        <h2>Registrar pedido</h2>
        <p class="muted">Selecciona productos y confirma tu compra</p>
      </div>
      <div class="summary">
        <span>Items: {{ itemsSeleccionados }}</span>
        <strong>Total: {{ totalSeleccionado.toFixed(2) }}</strong>
      </div>
    </header>

    <div class="list">
      <div v-for="p in productos" :key="p.id" class="item">
        <div>
          <div class="item__title">{{ p.nombre }}</div>
          <div class="item__meta">{{ p.categoria }} · {{ p.precio }} | Stock: {{ p.cantidad }}</div>
        </div>
        <input class="qty" type="number" v-model.number="p.seleccion" min="0" :max="p.cantidad" />
      </div>
    </div>

    <button class="btn" @click="registrar">Registrar pedido</button>
  </div>
</template>

<script>
// File: RegistrarPedido.vue - Funcionalidad: Construye pedido y POST /api/pedidos/registrar
import axios from 'axios'
export default {
  name: 'RegistrarPedido',
  data() { return { productos: [], user: null } },
  computed: {
    totalSeleccionado() {
      return this.productos.reduce((s, p) => s + (p.seleccion || 0) * p.precio, 0)
    },
    itemsSeleccionados() {
      return this.productos.reduce((s, p) => s + (p.seleccion || 0), 0)
    }
  },
  async created() {
    const u = localStorage.getItem('user')
    if (!u) return this.$router.push({ name: 'Login' })
    this.user = JSON.parse(u)
    const res = await axios.get('/api/productos/')
    this.productos = res.data
      .map(p => ({ ...p, cantidad: Math.max(0, p.cantidad), seleccion: 0 }))
      .filter(p => p.cantidad > 0)
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
        this.$router.push('/carrito')
      } catch (err) {
        alert(err.response?.data || err.message || 'Error registrando pedido')
      }
    }
  }
}
</script>

<style scoped>
.page {
  max-width: 900px;
  margin: 0 auto;
}
.page__header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 16px;
  margin-bottom: 16px;
}
.summary {
  display: grid;
  gap: 4px;
  padding: 10px 12px;
  border: 1px solid #e5e7eb;
  border-radius: 10px;
  background: #f9fafb;
  font-size: 14px;
}
.list {
  display: grid;
  gap: 10px;
  margin-bottom: 16px;
}
.item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  border: 1px solid #e5e7eb;
  border-radius: 10px;
  padding: 12px;
  background: #ffffff;
}
.item__title {
  font-weight: 600;
}
.item__meta {
  color: #6b7280;
  font-size: 14px;
}
.qty {
  width: 90px;
  padding: 8px 10px;
  border: 1px solid #d1d5db;
  border-radius: 8px;
}
.btn {
  background: #2563eb;
  color: #ffffff;
  border: none;
  padding: 10px 14px;
  border-radius: 8px;
  cursor: pointer;
}
.muted {
  color: #6b7280;
}
@media (max-width: 700px) {
  .page__header {
    flex-direction: column;
    align-items: flex-start;
  }
  .item {
    flex-direction: column;
    align-items: flex-start;
  }
  .qty {
    width: 100%;
  }
}
</style>
