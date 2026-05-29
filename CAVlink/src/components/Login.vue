<template>
  <!-- File: Login.vue - Componente de login -->
  <div>
    <h2>Login</h2>
    <form @submit.prevent="onSubmit">
      <div>
        <label>Correo</label>
        <input v-model="correo" type="email" />
      </div>
      <div>
        <label>Contraseña</label>
        <input v-model="contraseña" type="password" />
      </div>
      <button type="submit">Entrar</button>
    </form>
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
        const res = await axios.post('http://localhost:8080/api/usuarios/login', { correo: this.correo, contraseña: this.contraseña })
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
