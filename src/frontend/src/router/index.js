import { createRouter, createWebHistory} from "vue-router";
import movie from "@/components/movie.vue";
// eslint-disable-next-line
import seating from "@/components/seating.vue"

const routes = [
    {
      path: '/',
      name: 'home',
      component: movie,
    },
    {
        path: '/seating/:id',
        name: 'seating',
        component: seating,
        props: true
    }
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
});

export default router;