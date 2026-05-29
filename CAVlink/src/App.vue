<script setup>
// Root app: router-view will display routed components
</script>

<template>
  <div id="app">
    <header>
      <h1>CAVlink</h1>
      <nav>
        <router-link to="/">Inicio</router-link>
        |
        <router-link to="/catalogo">Catálogo</router-link>
        |
        <router-link to="/carrito">Carrito</router-link>
        |
        <router-link to="/perfil">Perfil</router-link>
        |
        <router-link to="/login">Login</router-link>
      </nav>
    </header>

    <main>
      <router-view />
    </main>

    <footer>
      <small>© CAVlink</small>
    </footer>
  </div>
</template>

<style scoped>
#app { max-width: 900px; margin: 24px auto; font-family: system-ui, Arial, sans-serif; }
header { display:flex; align-items:center; justify-content:space-between; margin-bottom: 16px; }
nav a { color:#2c3e50; text-decoration:none; margin:0 6px; }
nav a.router-link-active { font-weight:600; }
main { padding: 8px 0; }
footer { margin-top: 24px; text-align:center; color:#666; }
</style>

