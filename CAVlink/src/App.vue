<template>
  <div id="app">
    <header class="topbar">
      <div class="brand">
        <span class="brand__dot"></span>
        <span>CAVlink</span>
      </div>
      <nav class="nav">
        <router-link to="/catalogo">Catálogo</router-link>

        <template v-if="!user">
          <router-link to="/login">Login</router-link>
        </template>

        <template v-else-if="user.rol === 'ADMIN'">
          <router-link to="/perfil">Perfil</router-link>
          <router-link to="/crear-producto">Crear Producto</router-link>
          <button class="nav-button" @click="logout">Cerrar sesión</button>
        </template>

        <template v-else>
          <router-link to="/perfil">Perfil</router-link>
          <router-link to="/registrar-pedido">Registrar Pedido</router-link>
          <router-link to="/carrito">Consultar Carrito</router-link>
          <button class="nav-button" @click="logout">Cerrar sesión</button>
        </template>
      </nav>
    </header>

    <main class="content">
      <router-view />
    </main>

    <footer class="footer">
      <small>© CAVlink | Tecnología para todos</small>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      user: null
    }
  },
  created() {
    this.refreshUser()
  },
  watch: {
    '$route'() {
      this.refreshUser()
    }
  },
  methods: {
    refreshUser() {
      const stored = localStorage.getItem('user')
      this.user = stored ? JSON.parse(stored) : null
    },
    logout() {
      localStorage.removeItem('user')
      localStorage.removeItem('cart')
      this.user = null
      this.$router.push({ path: '/catalogo' })
    }
  }
}
</script>

<style scoped>
#app {
  max-width: 1100px;
  margin: 0 auto;
  padding: 16px;
  font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
  color: #1f2937;
}
.topbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  padding: 12px 16px;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  background: #ffffff;
  position: sticky;
  top: 12px;
  z-index: 10;
}
.brand {
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 700;
}
.brand__dot {
  width: 10px;
  height: 10px;
  border-radius: 999px;
  background: #2563eb;
}
.nav {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
}
.nav a,
.nav-button {
  text-decoration: none;
  color: #1f2937;
  padding: 6px 10px;
  border-radius: 8px;
  background: none;
  border: none;
  cursor: pointer;
}
.nav a.router-link-active {
  background: #eef2ff;
  color: #1d4ed8;
  font-weight: 600;
}
.nav-button:hover {
  background: #f3f4f6;
}
.content {
  margin-top: 16px;
}
.footer {
  margin: 24px 0 12px;
  text-align: center;
  color: #6b7280;
}
@media (max-width: 720px) {
  .topbar {
    flex-direction: column;
    align-items: flex-start;
  }
}
</style>

