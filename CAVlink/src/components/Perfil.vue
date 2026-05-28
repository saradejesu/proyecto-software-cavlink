<template>
  <!-- File: Perfil.vue - Componente Perfil con vistas diferenciadas por rol -->
  <div>
    <h2>Perfil</h2>
    <div v-if="user">
      <p><strong>Nombre:</strong> {{ user.nombre }}</p>
      <p><strong>Correo:</strong> {{ user.correo }}</p>
      <p><strong>Rol:</strong> {{ user.rol }}</p>

      <div v-if="user.rol === 'USUARIO'">
        <p>Vista Usuario: datos básicos</p>
      </div>
      <div v-else>
        <p>Vista Admin/Tecnico: herramientas de gestión</p>
        <router-link to="/crear-producto">Crear Producto</router-link>
        <router-link to="/consultar-producto">Consultar Productos</router-link>
      </div>
    </div>
    <div v-else>
      <p>No autenticado</p>
    </div>
  </div>
</template>

<script>
// File: Perfil.vue - Funcionalidad: Muestra perfil desde localStorage; el tecnico podrá crear perfiles especiales via UI futura
export default {
  name: 'Perfil',
  data() { return { user: null } },
  created() {
    const u = localStorage.getItem('user')
    if (u) this.user = JSON.parse(u)
    else this.$router.push({ name: 'Login' })
  }
}
</script>
