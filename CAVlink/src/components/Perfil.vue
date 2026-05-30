<template>
  <div class="page">
    <section v-if="user" class="card">
      <header class="card__header">
        <h2>Perfil</h2>
        <p class="muted">Datos basicos de tu cuenta</p>
      </header>
      <div class="info">
        <div class="info__item">
          <span>Nombre</span>
          <strong>{{ user.nombre }}</strong>
        </div>
        <div class="info__item">
          <span>Correo</span>
          <strong>{{ user.correo }}</strong>
        </div>
        <div class="info__item">
          <span>Rol</span>
          <strong>{{ user.rol }}</strong>
        </div>
      </div>
    </section>
    <section v-else class="card">
      <h2>Perfil</h2>
      <p class="muted">No autenticado</p>
      <router-link class="btn" to="/login">Ir a login</router-link>
    </section>
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

<style scoped>
.page {
  max-width: 760px;
  margin: 0 auto;
}
.card {
  background: #ffffff;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  padding: 20px;
}
.card__header {
  margin-bottom: 16px;
}
.info {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
  gap: 12px;
  margin-bottom: 16px;
}
.info__item {
  display: grid;
  gap: 4px;
  background: #f9fafb;
  border: 1px solid #e5e7eb;
  border-radius: 10px;
  padding: 10px 12px;
  font-size: 14px;
}
.panel {
  background: #f3f4f6;
  border-radius: 10px;
  padding: 12px;
}
.actions {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 8px;
}
.btn {
  background: #2563eb;
  color: #ffffff;
  border: none;
  padding: 8px 12px;
  border-radius: 8px;
  text-decoration: none;
}
.btn.ghost {
  background: #e5e7eb;
  color: #111827;
}
.link {
  color: #1d4ed8;
  text-decoration: none;
  font-weight: 600;
}
.muted {
  color: #6b7280;
}
.hint {
  margin-top: 8px;
  color: #6b7280;
  font-size: 14px;
}
</style>
