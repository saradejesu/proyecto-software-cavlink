import { createRouter, createWebHistory } from 'vue-router'
import Registro from '../components/Registro.vue'
import Login from '../components/Login.vue'
import Catalogo from '../components/catalogo.vue'
import Perfil from '../components/Perfil.vue'
import CrearProducto from '../components/CrearProducto.vue'
import ConsultarProducto from '../components/ConsultarProducto.vue'
import ConsultarCarrito from '../components/ConsultarCarrito.vue'
import RegistrarPedido from '../components/RegistrarPedido.vue'

const routes = [
  { path: '/', name: 'Registro', component: Registro },
  { path: '/registro', name: 'RegistroPage', component: Registro },
  { path: '/login', name: 'Login', component: Login },
  { path: '/catalogo', name: 'Catalogo', component: Catalogo },
  { path: '/perfil', name: 'Perfil', component: Perfil },
  { path: '/crear-producto', name: 'CrearProducto', component: CrearProducto },
  { path: '/producto/:id', name: 'ConsultarProducto', component: ConsultarProducto, props: true },
  { path: '/carrito', name: 'Carrito', component: ConsultarCarrito },
  { path: '/registrar-pedido', name: 'RegistrarPedido', component: RegistrarPedido }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
