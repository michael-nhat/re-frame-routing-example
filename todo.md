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

