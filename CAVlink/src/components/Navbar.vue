<template>
  <nav class="navbar">
    <div class="navbar__container">
      <div class="navbar__brand">CAVlink</div>
      <div class="navbar__links">
        <router-link to="/catalogo" class="nav-item">Catálogo</router-link>

        <template v-if="!user">
          <router-link to="/login" class="nav-item">Login</router-link>
          <router-link to="/registro" class="nav-item">Registro</router-link>
        </template>

        <template v-else>
          <router-link to="/perfil" class="nav-item">Perfil</router-link>
          
          <template v-if="user.rol === 'ADMIN'">
            <router-link to="/crear-producto" class="nav-item">Crear Producto</router-link>
          </template>
          
          <template v-if="user.rol === 'USUARIO'">
            <router-link to="/carrito" class="nav-item">Consultar Carrito</router-link>
          </template>
          
          <button @click="logout" class="nav-btn">Cerrar Sesión</button>
        </template>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: 'Navbar',
  data() { return { user: null } },
  created() { this.updateUser() },
  watch: { '$route': 'updateUser' },
  methods: {
    updateUser() {
      const u = localStorage.getItem('user')
      this.user = u ? JSON.parse(u) : null
    },
    logout() {
      localStorage.removeItem('user')
      this.user = null
      this.$router.push({ name: 'Login' })
    }
  }
}
</script>

<style scoped>
.navbar {
  background: #ffffff;
  border-bottom: 1px solid #e5e7eb;
  padding: 12px 0;
  margin-bottom: 24px;
}
.navbar__container {
  max-width: 1000px;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.navbar__brand {
  font-weight: 800;
  font-size: 1.25rem;
  color: #2563eb;
}
.navbar__links {
  display: flex;
  gap: 16px;
  align-items: center;
}
.nav-item {
  text-decoration: none;
  color: #374151;
  font-size: 14px;
  font-weight: 500;
}
.nav-item:hover {
  color: #2563eb;
}
.nav-btn {
  background: #ef4444;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 6px;
  font-size: 13px;
  cursor: pointer;
}
.nav-btn:hover {
  background: #dc2626;
}
</style>