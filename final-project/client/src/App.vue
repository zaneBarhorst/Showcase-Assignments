<template>
  <div id="cart-app">
    <header>
      <img src="img\logo_400x70.png" />
    </header>
    <nav>
      <div id="left">
        <router-link v-bind:to="{ name: 'home' }">Home</router-link>
        <router-link v-bind:to="{ name: 'cart'}">Cart</router-link>
      </div>
      <div id="middle">

      </div>
      <div id="right">
        <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token">
          Logout
        </router-link>
        <router-link v-bind:to="{ name: 'login' }" v-else>Login</router-link>
      </div>
    </nav>
    <main id="product-view">
      <router-view />
    </main>
    <footer>© 2024 All rights reserved</footer>
  </div>
</template>

<style>
header img {
  margin-left: auto;
  margin-right: auto;
  display: block;;
}

header {
  border-style: solid;
  border-color: slateblue;
  background-color: aquamarine;
  grid-area: header;
}

nav {
  display: flex;
  justify-content: space-between;
  border-style: solid;
  grid-area: nav;
  border-color: slateblue;
  background-color: aquamarine;
}

#right{
  margin-right: 10px;
  padding-left: 10px;
  justify-content: left;
}

#left{
  margin-left: 10px;
  justify-content: right;
}

#product-view{
  grid-area: products;
  overflow: auto;
}


footer{
  text-align: center;
  grid-area: footer;
  background-color: aquamarine;
  border-style: solid;
  border-color: slateblue;
}

#cart-app{
  display: grid;
  grid-template-areas: "header"
                       "nav"
                       "products"
                       "footer";
}
</style>