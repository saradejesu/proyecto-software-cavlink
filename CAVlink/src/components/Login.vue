<template>
  <div class="page">
    <section class="card">
      <header class="card__header">
        <h2>Login</h2>
        <p class="muted">Accede para comprar y gestionar pedidos</p>
      </header>
      <form class="form" @submit.prevent="onSubmit">
        <label class="field">
          <span>Correo</span>
          <input v-model="correo" type="email" />
        </label>
        <label class="field">
          <span>Contrasena</span>
          <input v-model="contraseña" type="password" />
        </label>
        <button class="btn" type="submit">Entrar</button>
      </form>
      <p class="muted">
        No tienes cuenta?
        <router-link to="/registro">Crear cuenta</router-link>
      </p>
    </section>
  </div>
</template>

<script>
// File: Login.vue - Funcionalidad: Login universal con validación frontend y llamada Axios
import axios from 'axios'
export default {
  name: 'Login',
  data() {
    return {
      correo: '',
      contraseña: ''
    }
  },
  methods: {
    async onSubmit() {
      // Validación frontend
      if (!this.correo || !this.contraseña) return alert('Complete todos los campos')
      const emailRe = /^[^\@\s]+@[^\@\s]+\.[^\@\s]+$/
      if (!emailRe.test(this.correo)) return alert('Correo inválido')
      try {
        const res = await axios.post('/api/usuarios/login', { correo: this.correo, contraseña: this.contraseña })
        // Almacenar usuario en localStorage para simplicidad
        localStorage.setItem('user', JSON.stringify(res.data))
        this.$router.push({ name: 'Perfil' })
      } catch (err) {
        alert(err.response?.data || 'Error en login')
      }
    }
  }
}
</script>

<style scoped>
.page {
  max-width: 560px;
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
.form {
  display: grid;
  gap: 12px;
}
.field {
  display: grid;
  gap: 6px;
  font-size: 14px;
}
.field input {
  padding: 10px 12px;
  border: 1px solid #d1d5db;
  border-radius: 8px;
}
.btn {
  background: #2563eb;
  color: #ffffff;
  border: none;
  padding: 10px 12px;
  border-radius: 8px;
  cursor: pointer;
}
.muted {
  color: #6b7280;
  margin-top: 12px;
}
.muted a {
  color: #1d4ed8;
  text-decoration: none;
  margin-left: 4px;
}
</style>
