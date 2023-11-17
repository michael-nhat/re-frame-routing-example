create login page
add user, book



fetch("http://localhost:8081/test", {
        mode: "cors" // <----------------
    })
    .then((res)=>{
        return res.text();
    })
    .then((data)=>{
        console.log(data);
        return new Promise((resolve, reject)=>{
            resolve(data ? JSON.parse(data) : {})
        })
    })

login: post api
then: put username, ... userinfo
catch: console.log("login failed")

make it simple aspossible
cancel if not work

login success event

becare with :https://clojurians-log.clojureverse.org/reagent/2023-03-06
but it is not worried
