<template>
  <div class="page">
    <header class="header">
      <h2>Tu Carrito</h2>
      <p class="muted">Gestiona tus productos seleccionados</p>
    </header>

    <div v-if="cart.length === 0" class="empty">
      <p>No hay productos en el carrito.</p>
      <router-link to="/catalogo">Ir al catálogo</router-link>
    </div>

    <div v-else class="cart-list">
      <div v-for="item in cart" :key="item.id" class="item">
        <div class="item__info">
          <strong>{{ item.nombre }}</strong>
          <span>Cantidad: {{ item.cantidad }}</span>
        </div>
        <div class="item__price">
          ${{ (item.precio * item.cantidad).toFixed(2) }}
        </div>
        <button @click="removeItem(item.id)" class="btn-remove">Eliminar</button>
      </div>

      <div class="total">
        <span>Total estimado:</span>
        <strong>${{ total.toFixed(2) }}</strong>
      </div>

      <button @click="buy" class="btn-buy">Comprar</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ConsultarCarrito',
  data() {
    return { cart: [] }
  },
  computed: {
    total() {
      return this.cart.reduce((sum, item) => sum + (item.precio * item.cantidad), 0)
    }
  },
  created() {
    const c = localStorage.getItem('cart')
    if (c) this.cart = JSON.parse(c)
  },
  methods: {
    removeItem(id) {
      this.cart = this.cart.filter(i => i.id !== id)
      localStorage.setItem('cart', JSON.stringify(this.cart))
    },
    buy() {
      alert('Funcionalidad de compra no implementada (botón informativo)')
    }
  }
}
</script>

<style scoped>
.page { max-width: 600px; margin: 0 auto; padding: 20px; }
.header { margin-bottom: 24px; }
.cart-list { background: white; border: 1px solid #e5e7eb; border-radius: 12px; padding: 20px; }
.item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #f3f4f6;
}
.item__info { display: grid; gap: 4px; }
.item__info span { font-size: 0.85rem; color: #6b7280; }
.btn-remove { background: #fee2e2; color: #ef4444; border: none; padding: 4px 8px; border-radius: 4px; cursor: pointer; }
.total {
  display: flex;
  justify-content: space-between;
  padding: 16px 0;
  font-size: 1.25rem;
}
.btn-buy {
  width: 100%;
  background: #10b981;
  color: white;
  border: none;
  padding: 12px;
  border-radius: 8px;
  font-weight: 600;
  cursor: pointer;
}
.empty { text-align: center; padding: 40px; }
.muted { color: #6b7280; }
</style>