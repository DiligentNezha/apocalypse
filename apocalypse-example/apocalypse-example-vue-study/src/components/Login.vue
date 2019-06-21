<template>
  <div class="login">
      <input type="text" v-model="mail" placeholder="请输入邮箱">
      <input type="text" v-model="password" placeholder="请输入密码">
      <button @click="login" type="submit">登录</button>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data: function () {
      return {
          mail: '',
          password: ''
      };
  },
  methods: {
      login() {
          console.info(this.$data);
      }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .login {
        font-size: 30px;
        color: green;
    }
</style>
