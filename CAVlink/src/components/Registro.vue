<template>
  <div class="page">
    <section class="card">
      <header class="card__header">
        <h2>Registro</h2>
        <p class="muted">Crea tu cuenta para comprar tecnologia</p>
      </header>
      <form class="form" @submit.prevent="onSubmit">
        <label class="field">
          <span>Nombre</span>
          <input v-model="nombre" />
        </label>
        <label class="field">
          <span>Correo</span>
          <input v-model="correo" type="email" />
        </label>
        <label class="field">
          <span>Contrasena</span>
          <input v-model="contraseña" type="password" />
        </label>
        <button class="btn" type="submit">Registrar</button>
      </form>
      <p class="muted">
        Ya tienes cuenta?
        <router-link to="/login">Entrar</router-link>
      </p>
    </section>
  </div>
</template>

<script>
// File: Registro.vue - Funcionalidad: Registro de usuarios con validación frontend
import axios from 'axios'
export default {
  name: 'Registro',
  data() {
    return { nombre: '', correo: '', contraseña: '' }
  },
  methods: {
    async onSubmit() {
      if (!this.nombre || !this.correo || !this.contraseña) return alert('Complete todos los campos')
      const emailRe = /^[^\@\s]+@[^\@\s]+\.[^\@\s]+$/
      if (!emailRe.test(this.correo)) return alert('Correo inválido')
      try {
        const user = { nombre: this.nombre, correo: this.correo, contraseña: this.contraseña, rol: 'USUARIO' }
        console.log('Enviando datos:', user)
        const res = await axios.post('/api/usuarios/register', user, {
          headers: { 'Content-Type': 'application/json' }
        })
        console.log('Respuesta:', res.data)
        alert('Registro exitoso')
        this.$router.push({ name: 'Login' })
      } catch (err) {
        console.error('Error completo:', err)
        console.error('Error response:', err.response)
        let errorMsg = 'Error en registro'
        if (err.response?.data) {
          errorMsg = typeof err.response.data === 'string' ? err.response.data : JSON.stringify(err.response.data)
        } else if (err.message) {
          errorMsg = err.message
        }
        alert(errorMsg)
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
