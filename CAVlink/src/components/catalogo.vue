<template>
  <div class="page">
    <section class="hero">
      <div>
        <h2>Catalogo de tecnologia</h2>
        <p class="muted">Laptops, accesorios, perifericos y mas.</p>
      </div>
      <router-link class="btn" to="/productos">Ver productos</router-link>
    </section>

    <section class="grid">
      <article class="card">
        <h3>Computo</h3>
        <p class="muted">Portatiles, monitores y estaciones.</p>
      </article>
      <article class="card">
        <h3>Accesorios</h3>
        <p class="muted">Teclados, mouse, hubs y mas.</p>
      </article>
      <article class="card">
        <h3>Audio</h3>
        <p class="muted">Audifonos, microfonos y bocinas.</p>
      </article>
    </section>
  </div>
</template>

<script>
export default {
  name: 'CatalogoComponent'
}
</script>

<style scoped>
.page {
  max-width: 980px;
  margin: 0 auto;
}
.hero {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 16px;
  padding: 18px;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  background: #ffffff;
  margin-bottom: 16px;
}
.grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 12px;
}
.card {
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  padding: 14px;
  background: #ffffff;
}
.btn {
  background: #2563eb;
  color: #ffffff;
  padding: 8px 12px;
  border-radius: 8px;
  text-decoration: none;
}
.muted {
  color: #6b7280;
}
@media (max-width: 720px) {
  .hero {
    flex-direction: column;
    align-items: flex-start;
  }
}
</style>