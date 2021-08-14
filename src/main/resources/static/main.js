//GET users
const userList = document.getElementById("userTable");
const URLGetUsersList = "/api/allUsers"

//get users in table
fetch(URLGetUsersList).then(
    res => {
        res.json().then(
            data => {
                console.log(data);
                if (data.length > 0) {
                    let users = "";
                    users += `<table class="table bg-white">
                <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">First Name</th>
                    <th scope="col">Last Name</th>
                    <th scope="col">Age</th>
                    <th scope="col">Email</th>
                    <th scope="col">Role</th>
                    <th scope="col">Edit</th>
                    <th scope="col">Delete</th>
                </tr>
                </thead>`;
                    users += `<tbody id="allUsers">`
                    data.forEach((user) => {
                        users += `
                <tr data-id=${user.id} id="user-${user.id}">
                    <td class="main-id" id="main-id-${user.id}">${user.id}</td>
                    <td class="main-name" id="main-name-${user.id}">${user.username}</td>
                    <td class="main-surname" id="main-surname-${user.id}">${user.surname}</td>
                    <td class="main-age" id="main-age-${user.id}">${user.age}</td>
                    <td class="main-email" id="main-email-${user.id}">${user.email}</td>
                    <td class="main-roles" id="main-roles-${user.id}">`

                        user.roles.forEach(val => {
                            console.log(val)
                            users += `
                        ${val.name}
                        `
                        })
                        users += `   
                    </td>
                    <td>
                        <button type="button" class="btn btn-info" data-toggle="modal"
                                data-target="#editModal" id="edit-user"> Edit </button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger" data-toggle="modal"
                                data-target="#deleteModal" id="delete-user"> Delete </button>
                    </td></tr>
               `;
                    })
                    users += ` </tbody>
            </table>`
                    userList.innerHTML = users;
                }
            }
        )
    }
)

//Modal delete \edit

const modalDelete = document.querySelector('.modal-delete-form');
const modalEdit = document.querySelector('.modal-edit-form');


let deleteId
let editId

userList.addEventListener('click', (e) => {
    e.preventDefault();

    let editButtonIsPressed = e.target.id === 'edit-user'
    let deleteButtonIsPressed = e.target.id === 'delete-user'


    const parent = e.target.parentElement.parentElement
    let editName = parent.querySelector('.main-name').textContent
    let editSurname = parent.querySelector('.main-surname').textContent
    let editAge = parent.querySelector('.main-age').textContent
    let editEmail = parent.querySelector('.main-email').textContent
    let editRoles = parent.querySelector('.main-roles').textContent
    deleteId = e.target.parentElement.parentElement.dataset.id
    editId = e.target.parentElement.parentElement.dataset.id
    let insideEditId = parent.querySelector('.main-id').textContent


    if (deleteButtonIsPressed) {

        document.getElementById('idDelete').value = deleteId;
        document.getElementById('nameDelete').value = editName;
        document.getElementById('surnameDelete').value = editSurname;
        document.getElementById('ageDelete').value = editAge;
        document.getElementById('emailDelete').value = editEmail;
        document.getElementById('rolesDelete').value = editRoles.replace(/\r?\n|\r/g, ' ').trim();

    }
    if (editButtonIsPressed) {

        document.getElementById('idEdit').value = editId;
        document.getElementById('nameEdit').value = editName;
        document.getElementById('surnameEdit').value = editSurname;
        document.getElementById('ageEdit').value = editAge;
        document.getElementById('emailEdit').value = editEmail;
        document.getElementById('rolesEdit').value = editRoles;
        const select = document.getElementById('rolesEdit').getElementsByTagName('option');
        let rolesForUser = 0;
        const arrayOfRoles = editRoles.replace(/\r?\n|\r/g, ' ').trim().split(' ');

        for (let i of arrayOfRoles) {
            if (i === 'ADMIN') {
                select[0].selected = true;
            } else if (i === 'USER') {
                select[1].selected = true;
            }
        }
    }

    modalDelete.addEventListener('click', watchModalDelete)
    modalEdit.addEventListener('click', watchModalEdit)
})

function watchModalDelete(e) {
    let insideDeleteId = deleteId;
    e.preventDefault();
    let deleteModalButtonIsPressed = e.target.id === 'delete-inside-modal'

    if (deleteModalButtonIsPressed) {
        fetch(`/api/${insideDeleteId}/delete`, {
            method: 'DELETE'
        })
            .then(() => {
                document.getElementById('user-' + insideDeleteId).innerHTML ="" ;
            })
    }
}

function watchModalEdit(e) {

    let insideEditId = editId;
    let editModalButtonIsPressed = e.target.id === 'edit-inside-modal'

    if (editModalButtonIsPressed) {

        fetch(`/api/edit/${insideEditId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            body: JSON.stringify({
                id: document.getElementById('idEdit').value,
                username: document.getElementById('nameEdit').value,
                surname: document.getElementById('surnameEdit').value,
                age: document.getElementById('ageEdit').value,
                email: document.getElementById('emailEdit').value,
                password: document.getElementById('passwordEdit').value,
                roleString: $('#rolesEdit').val()

            })
        })
            .then(res => {
                res.json().then(
                    data => {
                        console.log(data);
                                        let users = "";
                                            users += `
                            <tr data-id=${data.id} id="user-${data.id}">
                                <td class="main-id" id="main-id-${data.id}">${data.id}</td>
                                <td class="main-name" id="main-name-${data.id}">${data.username}</td>
                                <td class="main-surname" id="main-surname-${data.id}">${data.surname}</td>
                                <td class="main-age" id="main-age-${data.id}">${data.age}</td>
                                <td class="main-email" id="main-email-${data.id}">${data.email}</td>
                                <td class="main-roles" id="main-roles-${data.id}">`

                        data.roles.forEach(val => {
                                                users += `
                                    ${val.name}
                                    `
                                            })
                                            users += `   
                                </td>
                                <td>
                                    <button type="button" class="btn btn-info" data-toggle="modal"
                                            data-target="#editModal" id="edit-user"> Edit </button>
                                </td>
                                <td>
                                    <button type="button" class="btn btn-danger" data-toggle="modal"
                                            data-target="#deleteModal" id="delete-user"> Delete </button>
                                </td></tr>
                           `;

                                        users += ` </tbody>
                        </table>`
                                    document.getElementById('user-' + insideEditId).innerHTML ="";
                                    document.getElementById('user-' + insideEditId).innerHTML =users;

                    })
                    })

    }
}