import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Restful from '@/components/Restful'

Vue.use(Router)

export default new Router({
    routes: [{
        path: '/',
        component: Hello
    }, {
        path: '/restful',
        component: Restful
    }]
})