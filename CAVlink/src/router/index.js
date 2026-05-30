import { createRouter, createWebHistory } from 'vue-router'
import Registro from '../components/Registro.vue'
import Login from '../components/Login.vue'
import Perfil from '../components/Perfil.vue'
import CrearProducto from '../components/CrearProducto.vue'
import ConsultarProducto from '../components/ConsultarProducto.vue'
import ConsultarCarrito from '../components/ConsultarCarrito.vue'
import RegistrarPedido from '../components/RegistrarPedido.vue'

const routes = [
  { path: '/', name: 'Home', component: ConsultarProducto },
  { path: '/registro', name: 'Registro', component: Registro },
  { path: '/login', name: 'Login', component: Login },
  { path: '/catalogo', name: 'Catalogo', component: ConsultarProducto },
  { path: '/perfil', name: 'Perfil', component: Perfil, meta: { requiresAuth: true } },
  { path: '/crear-producto', name: 'CrearProducto', component: CrearProducto, meta: { requiresAuth: true, requiresAdmin: true } },
  { path: '/productos', name: 'Productos', component: ConsultarProducto },
  { path: '/carrito', name: 'ConsultarCarrito', component: ConsultarCarrito, meta: { requiresAuth: true, requiresUser: true } },
  { path: '/registrar-pedido', name: 'RegistrarPedido', component: RegistrarPedido, meta: { requiresAuth: true, requiresUser: true } },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach((to, from, next) => {
  const stored = localStorage.getItem('user')
  const user = stored ? JSON.parse(stored) : null

  if (to.meta.requiresAuth && !user) {
    return next({ name: 'Login' })
  }

  if (to.meta.requiresAdmin && user?.rol !== 'ADMIN') {
    return next({ name: 'Catalogo' })
  }

  if (to.meta.requiresUser && user?.rol !== 'USUARIO') {
    return next({ name: 'Catalogo' })
  }

  if (to.name === 'Login' && user) {
    return next({ name: 'Perfil' })
  }

  next()
})

export default router
