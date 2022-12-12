function createMap(latitude, longitude) {

    const $map = L.map('map').setView([latitude, longitude], 13);

    const tiles = L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo($map);

    const marker = L.marker([latitude, longitude])
        .addTo($map)
        .bindPopup('<b>Hello world!</b><br />I am a popup.')
        .openPopup()

    const popup = L.popup()
        .setLatLng([latitude, longitude])
        .setContent('I am a standalone popup.')
        .openOn($map);

    function onMapClick(e) {
        popup
            .setLatLng(e.latlng)
            .setContent(`You clicked the map at ${e.latlng.toString()}`)
            .openOn($map);
    }

    $map.on('click', onMapClick);

    return $map;
}