let map;
let service;
let userLocation;
let marcadores = [];

function initMap(position) {
    userLocation = {
        lat: position.coords.latitude,
        lng: position.coords.longitude
    };

    map = new google.maps.Map(document.getElementById("map"), {
        center: userLocation,
        zoom: 15,
    });

    const marker = new google.maps.Marker({
        position: userLocation,
        map: map,
        title: "Você está aqui!",
    });

    marcadores.push(marker);
}

function buscarPorCategoria(palavraChave) {
    limparResultados(); // limpa anteriores

    const request = {
        location: userLocation,
        radius: 500,
        keyword: palavraChave,
    };

    service = new google.maps.places.PlacesService(map);
    service.nearbySearch(request, (results, status) => {
        if (status === google.maps.places.PlacesServiceStatus.OK && results.length > 0) {
            document.getElementById("resultados").innerHTML = `<h2>RESULTADOS PARA: ${palavraChave}</h2>`;

            results.forEach((lugar) => {
                const marker = new google.maps.Marker({
                    position: lugar.geometry.location,
                    map: map,
                    title: lugar.name,
                });
                marcadores.push(marker);

                const div = document.createElement("div");
                div.classList.add("resultado");

                const foto = lugar.photos ? lugar.photos[0].getUrl({ maxWidth: 300 }) : "";
                const destino = lugar.geometry.location;
                const linkRota = `https://www.google.com/maps/dir/?api=1&origin=${userLocation.lat},${userLocation.lng}&destination=${destino.lat()},${destino.lng()}`;

                div.innerHTML = `
          <img src="${foto}" alt="${lugar.name}" class="foto-local">
          <h3>${lugar.name}</h3>
          <p>${lugar.vicinity}</p>
          <a href="${linkRota}" target="_blank" class="botao-rota">
            <img  src="https://img.icons8.com/color/24/000000/google-maps.png" />
            Ver rota
          </a>
        `;

                document.getElementById("resultados").appendChild(div);
            });
        } else {
            document.getElementById("resultados").innerHTML = `<p>Nenhum resultado encontrado para "${palavraChave}".</p>`;
        }
    });
}

function limparResultados() {
    marcadores.forEach(marker => marker.setMap(null));
    marcadores = [];
    document.getElementById("resultados").innerHTML = "";
}

window.onload = function () {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(initMap, () => {
            alert("Não foi possível acessar sua localização.");
        });
    } else {
        alert("Geolocalização não suportada.");
    }
};