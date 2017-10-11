<template>
    <div>
        <h1>Restful 风格</h1>
        <!--Restful-->
        <div>
            <button @click="getFunc">GET 获取数据</button>
            <button @click="postFunc">POST 创建数据</button>
            <button @click="putFunc">PUT 更新数据</button>
            <button @click="deleteFunc">DELETE 删除数据</button>
            <button @click="getJavaWeb">请求java项目</button>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import Vue from 'vue'

export default {
    data() {
        return {
            title: 1
        }
    },
    methods: {
        getFunc() {
            console.log("GET")

            //发送GET请求
            axios.get('/title/' + this.title).then(function(response) {
                if (response) {
                    console.log("title名 = " + response.data)
                }
            }).catch(function(error) {
                console.log("error");
                console.log(error);
            })
        },
        postFunc() {
            console.log("POST")

            //发送POST请求
            axios.post('/title/' + this.title).then(function(response) {
                if (response) {
                    console.log("content = " + response.data)
                }
            }).catch(function(error) {
                console.log("error");
                console.log(error);
            })
        },
        putFunc() {
            console.log("PUT")

            //发送PUT请求
            axios.put('/title/' + this.title).then(function(response) {
                if (response) {
                    console.log("title名 = " + response.data)
                }
            }).catch(function(error) {
                console.log("error");
                console.log(error);
            })
        },
        deleteFunc() {
            console.log("DELETE")

            //发送DELETE请求
            axios.delete('/title/' + this.title).then(function(response) {
                if (response) {
                    console.log("title名 = " + response.data)
                }
            }).catch(function(error) {
                console.log("error");
                console.log(error);
            })
        },
        getJavaWeb() {
            console.log("getJavaWeb")

            Vue.prototype.$http = axios;
            //发送DELETE请求
            axios.get('http://localhost:8081/apis/activitiOA/api/user/json?id=1').then(function(response) {
                if (response) {
                    console.log("getJavaWeb 请求结果 = ")
                    console.log("data=" + JSON.stringify(response.data));
                    console.log("status=" + response.status);
                    console.log("statusText=" + response.statusText);
                    console.log("headers=" + JSON.stringify(response.headers));
                    console.log("config=" + JSON.stringify(response.config));
                }
            }).catch(function(error) {
                console.log("error");
                console.log(error);
            })
        }
    }
}
</script>

<style scoped>
h1 {
    font-weight: normal;
}
</style>
