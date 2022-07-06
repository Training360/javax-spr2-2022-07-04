window.onload = function() {

    fetch('http://localhost:8080/api/employees', {method: 'GET'})
        .then(response => response.json())
        .then(employees => console.log(employees));

}