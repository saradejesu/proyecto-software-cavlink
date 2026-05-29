<template>
  <!-- File: Registro.vue - Componente de registro para rol USUARIO -->
  <div>
    <h2>Registro Usuario</h2>
    <form @submit.prevent="onSubmit">
      <div>
        <label>Nombre</label>
        <input v-model="nombre" />
      </div>
      <div>
        <label>Correo</label>
        <input v-model="correo" type="email" />
      </div>
      <div>
        <label>Contraseña</label>
        <input v-model="contraseña" type="password" />
      </div>
      <button type="submit">Registrar</button>
    </form>
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
        const res = await axios.post('http://localhost:8080/api/usuarios/register', user, {
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
