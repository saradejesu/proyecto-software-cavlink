<template>
  <div class="page">
    <header class="header">
      <h2>Catálogo de Productos</h2>
      <p class="muted">Explora nuestra tecnología disponible</p>
    </header>
    
    <div class="grid">
      <div v-for="p in productos" :key="p.id" class="card">
        <div class="card__body">
          <h3>{{ p.nombre }}</h3>
          <p class="category">{{ p.categoria }}</p>
          <div class="price">${{ p.precio }}</div>
          <p class="stock">Stock disponible: {{ Math.max(0, p.cantidad) }}</p>
        </div>
        <div class="card__footer" v-if="user && user.rol === 'USUARIO'">
          <button class="btn" @click="addToCart(p)" :disabled="p.cantidad <= 0">
            {{ p.cantidad > 0 ? 'Añadir al carrito' : 'Sin stock' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'ConsultarProducto',
  data() {
    return { productos: [], user: null }
  },
  async created() {
    const u = localStorage.getItem('user')
    if (u) this.user = JSON.parse(u)
    
    try {
      const res = await axios.get('/api/productos')
      this.productos = res.data
    } catch (err) {
      console.error('Error cargando productos', err)
    }
  },
  methods: {
    addToCart(p) {
      let cart = JSON.parse(localStorage.getItem('cart') || '[]')
      const index = cart.findIndex(item => item.id === p.id)
      if (index !== -1) {
        cart[index].cantidad++
      } else {
        cart.push({ ...p, cantidad: 1 })
      }
      localStorage.setItem('cart', JSON.stringify(cart))
      alert(`${p.nombre} añadido al carrito`)
    }
  }
}
</script>

<style scoped>
.page { max-width: 1000px; margin: 0 auto; padding: 20px; }
.header { margin-bottom: 24px; text-align: center; }
.grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
  gap: 20px;
}
.card {
  background: white;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  display: flex;
  flex-direction: column;
}
.card__body { padding: 16px; flex-grow: 1; }
.card__body h3 { margin: 0 0 8px; font-size: 1.1rem; }
.category { font-size: 0.85rem; color: #6b7280; text-transform: uppercase; margin-bottom: 12px; }
.price { font-size: 1.25rem; font-weight: 700; color: #2563eb; margin-bottom: 8px; }
.stock { font-size: 0.85rem; color: #374151; }
.card__footer { padding: 12px; border-top: 1px solid #f3f4f6; }
.btn {
  width: 100%;
  background: #2563eb;
  color: white;
  border: none;
  padding: 8px;
  border-radius: 8px;
  cursor: pointer;
}
.btn:disabled {
  background: #94a3b8;
  cursor: not-allowed;
}
.muted { color: #6b7280; }
</style>