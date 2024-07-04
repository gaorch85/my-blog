<template>
    <div>
      <el-container>
        <!-- 导航栏 -->
        <el-header class="header">
          <el-row :gutter="20" style="width: 100%;">
            <el-col :span="5">
              <div class="logo">
                <h2>极简博客</h2>
              </div>
            </el-col>
            <el-col :span="11">
              <div class="nav-links">
                <router-link to="/blog/list" exact class="nav-item" active-class="active-link">首页</router-link>
                <router-link to="/blog/create" class="nav-item" active-class="active-link">创建博客</router-link>
                <router-link to="/personal" class="nav-item" active-class="active-link">个人中心</router-link>
              </div>
            </el-col>
            <el-col :span="4" :offset="4">
              <div class="login-area">
                <template v-if="!isLogin">
                  <router-link to="/login" class="btn btn-primary">登录</router-link>
                </template>
                <template v-else>
                  <router-link to="/personal" class="username">{{ username }}</router-link>
                </template>
              </div>
            </el-col>
          </el-row>
        </el-header>
        <el-divider></el-divider>
        <!-- 下方主体 -->
        <el-main class="custom-main">
          <router-view @blogDeleted="handleBlogDeleted"></router-view>
        </el-main>
      </el-container>
    </div>
  </template>
  
  <script>
  import { removeToken, getToken } from '@/utils/auth';
  import { api_getInfo } from '@/api/personal';
  
  export default {
    data() {
      return {
        isLogin: false,
        username: ''
      };
    },
    mounted() {
      this.isLogin = !!getToken();
      if (this.isLogin) {
        this.fetchUserInfo();
      }
    },
    methods: {
      fetchUserInfo() {
        api_getInfo().then(response => {
          this.username = response.data.data.items.username;
        });
      },
      logout() {
        removeToken();
        this.isLogin = false;
        this.username = '';
        this.$router.push('/login');
      }
    }
  };
  </script>
  
  <style scoped>

  .header {
    height: 7vh;
    display: flex;
    align-items: center;
    border-bottom: 1px solid #dcdcdc;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    padding: 0 20px;
  }
  
  .logo h2 {
    margin: 0;
    color: black;
    font-size: 24px;
  }
  
  .nav-links {
    display: flex;
    align-items: center;
  }
  
  .nav-item {
    margin-right: 50px; 
    text-decoration: none;
    color: black;
    font-weight: bold;
    font-size: 20px;
    transition: color 0.3s, transform 0.3s;
  }
  
  .nav-item:hover {
    color: #66b1ff;
    transform: scale(1.1);
  }
  
  .active-link {
    color: #66b1ff;
  }
  
  .login-area {
    display: flex;
    align-items: center;
    justify-content: flex-end;
  }
  
  .custom-main {
    padding: 20px;
    background-color: #fff;
  }
  
  .el-divider--horizontal {
    margin: 0;
  }
  
  .btn {
    display: inline-block;
    padding: 10px 20px;
    margin: 0 10px; /* 调整间距 */
    border-radius: 4px;
    text-decoration: none;
    font-weight: bold;
    background-color: #ffd700;
    color: #333;
    transition: background-color 0.3s, transform 0.3s;
  }
  
  .btn-primary {
    background-color: #409EFF;
    color: white;
  }
  
  .btn-primary:hover {
    background-color: #66b1ff;
    transform: scale(1.1);
  }
  
  .username {
    margin-right: 20px; 
    color: black;
    text-decoration: none;
  }
  </style>
  