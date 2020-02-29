import Vue from 'vue'
import Router from 'vue-router'
import upload from '@/components/upload'

import Home from '@/components/Home'
import EmpBasic from '@/components/emp/EmpBasic'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      children: [
        {
          path: '/emp/basic',
          name: 'EmpBasic',
          component: EmpBasic
        }
      ]
    },
  ]
})
