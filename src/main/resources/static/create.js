//POST user to table
const addUser = document.getElementById("userprofile_form")
let username = document.getElementById('fn')
let surname = document.getElementById('ln')
let age = document.getElementById('age')
let email = document.getElementById('email')
let password = document.getElementById('password')



addUser.addEventListener('submit', function (e) {
    e.preventDefault()

    fetch("/api/adduser", {
        method: 'POST',
        body: JSON.stringify({
            username: username.value,
            surname: surname.value,
            age: age.value,
            email: email.value,
            password: password.value,
            roleString: $('#newRoles').val()

        }),
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(function (response) {
            return response.json()
        })
        .then(function (data) {
            console.log(data)
        }).then(function (){
        location.href = "/admin"
    })

})



