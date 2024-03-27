import { createApp } from 'vue'
import App from './App.vue'
import * as VueRouter from 'vue-router'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import SessionsPage from "@/pages/SessionsPage.vue";
import SeatsPage from "@/pages/SeatsPage.vue";

const router = VueRouter.createRouter({
    history: VueRouter.createWebHistory(process.env.BASE_URL),
    routes: [
        {
            path: '/sessions',
            component: SessionsPage,
        },
        {
            path: '/session/:id',
            component: SeatsPage,
        },
    ],
});

const app = createApp(App);

app.use(router);

app.mount('#app');
