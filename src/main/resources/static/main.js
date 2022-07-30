// Таблица пользователей
$(async function() {
    await allUsers();
});

const allUsersUrl = 'http://localhost:8080/api/users/'
const table = $('#table-all-users');
async function allUsers() {
    table.empty()
    fetch(allUsersUrl)
        .then(res => res.json())
        .then(data => {
            data.forEach(user => {
                let tableUsers = `$(
                    <tr class="border-top">
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.fatherName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.age}</td>
                        <td>${user.username}</td>
                        <td class="text-break">${user.password}</td>
                        <td>${user.role.map(role => " " + role.roleName)}</td>
                        <td>
                            <button type="button" class="btn btn-primary" data-toggle="modal" 
                            data-id = "${user.id}" data-target="#editModal">Редактировать</button>
                        </td>
                        <td>
                            <button type="button" class="btn btn-danger" data-toggle="modal" 
                            data-id = "${user.id}" data-target="#deleteModal">Удалить</button>
                        </td>
                    </tr>)`;
            table.append(tableUsers);
        })
    })
}
// Добавление нового пользователя
$(async function() {
    await newUser();
});

const roleUrl = 'http://localhost:8080/api/roles'
async function newUser() {
    await fetch(roleUrl)
        .then(res => res.json())
        .then(roles => {
            roles.forEach(role => {
                let el = document.createElement("option");
                el.text = role.roleName;
                el.value = role.id;
                $('#newUserRole')[0].appendChild(el);
            })
        })

    const form = document.forms["newUserForm"];

    form.addEventListener('submit', addNewUser)

    function addNewUser(e) {
        e.preventDefault();
        let newUserRole = [];
        for (let i = 0; i < form.newUserRole.options.length; i++) {
            if (form.newUserRole.options[i].selected) newUserRole.push({
                id : form.newUserRole.options[i].value,
                roleName : form.newUserRole.options[i].text
            })
        }
        fetch(allUsersUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                name: form.newUserName.value,
                fatherName: form.newUserFatherName.value,
                lastName: form.newUserLastName.value,
                age: form.newUserAge.value,
                login: form.newUserLogin.value,
                password: form.newUserPassword.value,
                role: newUserRole
            })
        }).then(() => {
            form.reset();
            allUsers();
            $('#nav-home-tab').click();
        })
    }
}
// Текущий пользователь
$(async function() {
    await thisUser();
});

const authUser = 'http://localhost:8080/api/viewUser'
async function thisUser() {
    fetch(authUser)
        .then(res => res.json())
        .then(data => {
            $('#auth-user-name').append(data.login);
            let role = data.role.map(role => " " + role.roleName);
            $('#auth-user-role').append(role);

            let user = `$(
            <tr class="border-top">
                <td>${data.id}</td>
                <td>${data.name}</td>
                <td>${data.fatherName}</td>
                <td>${data.lastName}</td>
                <td>${data.age}</td>
                <td>${data.login}</td>
                <td>${data.password}</td>
                <td>${role}</td>)`;
            $('#auth-user-table').append(user);
        })
}

async function getUser(id) {
    let url = allUsersUrl + id;
    let response = await fetch(url);
    return await response.json();
}

//Модальное окно редактирования пользователя
$('#editModal').on('show.bs.modal', ev => {
    let button = $(ev.relatedTarget);
    let id = button.data('id');
    showEditModal(id);
})

async function showEditModal(id) {
    let user = await getUser(id);
    let form = document.forms["modal-form-edit-user"];
    form.id.value = user.id;
    form.name.value = user.name;
    form.fatherName.value = user.fatherName;
    form.lastName.value = user.lastName;
    form.age.value = user.age;
    form.login.value = user.login;
    form.password.value = user.password;

    $('#editUserRole').empty();

    await fetch(roleUrl)
        .then(res => res.json())
        .then(roles => {
            roles.forEach(role => {
                let selectedRole = false;
                for (let i = 0; i < user.role.length; i++) {
                    if (user.role[i].roleName === role.roleName) {
                        selectedRole = true;
                        break;
                    }
                }
                let el = document.createElement("option");
                el.text = role.roleName;
                el.value = role.id;
                if (selectedRole) el.selected = true;
                $('#editUserRole')[0].appendChild(el);
            })
        })
}
// Редактирование пользователя
$(async function() {
    editUser();

});
function editUser() {
    const editForm = document.forms["modal-form-edit-user"];
    editForm.addEventListener("submit", ev => {
        ev.preventDefault();
        let editUserRole = [];
        for (let i = 0; i < editForm.role.options.length; i++) {
            if (editForm.role.options[i].selected) editUserRole.push({
                id : editForm.role.options[i].value,
                name : editForm.role.options[i].text
            })
        }

        fetch(allUsersUrl + editForm.id.value, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: editForm.id.value,
                name: editForm.name.value,
                fatherName: editForm.fatherName.value,
                lastName: editForm.lastName.value,
                age: editForm.age.value,
                login: editForm.login.value,
                password: editForm.password.value,
                role: editUserRole
            })
        }).then(() => {
            $('#editUserModalCloseButton').click();
            allUsers();
        })
    })
}
//Модальное окно удаления пользователя
$('#deleteModal').on('show.bs.modal', ev => {
    let button = $(ev.relatedTarget);
    let id = button.data('id');
    showDeleteModal(id);
})

async function showDeleteModal(id) {
    let user = await getUser(id);
    let form = document.forms["modal-form-delete-user"];
    form.id.value = user.id;
    form.name.value = user.name;
    form.fatherName.value = user.fatherName;
    form.lastName.value = user.lastName;
    form.age.value = user.age;
    form.login.value = user.login;
    form.password.value = user.password;

    $('#deleteUserRole').empty();

    await fetch(roleUrl)
        .then(res => res.json())
        .then(roles => {
            roles.forEach(role => {
                let selectedRole = false;
                for (let i = 0; i < user.role.length; i++) {
                    if (user.role[i].roleName === role.roleName) {
                        selectedRole = true;
                        break;
                    }
                }
                let el = document.createElement("option");
                el.text = role.roleName;
                el.value = role.id;
                if (selectedRole) el.selected = true;
                $('#deleteUserRole')[0].appendChild(el);
            })
        });
}
// Удаление пользователя
$(async function() {
    deleteUser();
});

function deleteUser(){
    const deleteForm = document.forms["modal-form-delete-user"];
    deleteForm.addEventListener("submit", ev => {
        ev.preventDefault();
        fetch(allUsersUrl + deleteForm.id.value, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(() => {
                $('#deleteUserModalCloseButton').click();
                allUsers();
            })
    })
}