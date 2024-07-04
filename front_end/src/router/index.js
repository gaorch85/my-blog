import Vue from 'vue'
import Router from 'vue-router'
import Login from "@/views/User/Login.vue"
import Personal from "@/views/User/Personal.vue"
import Error_403 from "@/views/error/403.vue"
import Error_404 from "@/views/error/404.vue"
import Blog from "@/views/Blog/Blog.vue"
import BlogPost from '@/views/Blog/BlogPost.vue'
import BlogList from "@/views/Blog/BlogList.vue"
import CreateBlog from "@/views/Blog/CreateBlog.vue"
import EditBlog from '@/views/Blog/EditBlog.vue'
import Layout from "@/layout"

Vue.use(Router)

const routes = [
    // 重定向默认至登录页面
    {
      path: '/',
      redirect: '/login'
    },

    {
      path: '/login',
      name: 'Login',
      component: Login
    },

    {
      path: '/404',
      name: '404',
      component: Error_404
    },

    {
      path: '*',
      redirect: '/404'
    },

    {
      path: '/',
      component: Layout,
      children: [
        {
          path: '/403',
          name: '403',
          component: Error_403,
          meta: { title: '未授权'}
        }
     ]
    },

    {
      path: '/',
      component: Layout,
      children: [
        {
          path: 'blog/details/:id',  // 使用占位符 :id
          name: 'BlogPost',
          component: BlogPost,
          meta: { title: '默认标题'}
        }
      ]
    },

    {
      path: '/',
      component: Layout,
      children: [
        {
          path: 'blog/edit/:id',  // 使用占位符 :id
          name: 'EditBlog',
          component: EditBlog,
          meta: { title: '编辑'}
        }
      ]
    },



    {
      path: '/',
      component: Layout,
      children: [
        {
          path: '/blog',
          name: 'Blog',
          component: Blog,
          redirect: '/blog/list',
          meta: { title: '博客', icon: 'el-icon-s-order' },
          children: [
            {
              path: 'list',
              name: 'Blog-List',
              component: BlogList,
              meta: { title: '博客列表', icon: 'el-icon-s-order' }
            },
            
            {
              path: 'create',
              name: 'Blog-Create',
              component: CreateBlog,
              meta: { title: '创建博客', icon: 'el-icon-s-order' }
            },
            
          ]
        }
     ]
    },

    {
      path: '/',
      component: Layout,
      children: [
        {
          path: '/personal',
          name: 'Personal',
          component: Personal,
          meta: { title: '个人中心', icon: 'el-icon-s-custom' }
        }
      ]
    }

  ]
  
  const router = new Router({
    mode: 'history',
    routes
  })


  // 路由守卫，设置动态标题
  router.beforeEach((to, from, next) => {
    if (to.name === 'BlogPost' && to.params.title) {
      to.meta.title = to.params.title;
    }
    next();
  });
  
  export default router