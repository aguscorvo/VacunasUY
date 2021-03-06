var latitud = -34.86157;
var longitud = -56.17938;
var mapa;
var marker;

function geoloc() {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(showPosition);
	}

}

function showPosition(position) {
	latitud = position.coords.latitude;
	longitud = position.coords.longitude;
}


function create_map(div, alias) {
	geoloc();


	if (BoolMapInit(mapa, div)) {
		RemoveExistingMap(mapa);
	}

	mapa = L.map(div).setView([latitud, longitud], 11);

	L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
		attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
	}).addTo(mapa);

	mapa.on('click', function(e) {
		if (marker != null)
			mapa.removeLayer(marker);

		$ds("input[id*='" + alias + "Latitud']").val(e.latlng.lat);
		$ds("input[id*='" + alias + "Longitud']").val(e.latlng.lng);

		var vacIcon = L.icon({
			iconUrl: IconVacUrl,
			iconSize: [32, 43], // size of the icon
			iconAnchor: [16, 43], // point of the icon which will correspond to marker's location
			popupAnchor: [-0, -45] // point from which the popup should open relative to the iconAnchor
		});
		//{ draggable: true }, 

		marker = new L.Marker(e.latlng, { icon: vacIcon })
			.bindPopup('Vacunatorio<br>Nueva ubicación')
			.openPopup();

		mapa.addLayer(marker);
	});
}

function create_mapView(div, lat, lon, text) {
	
	if (BoolMapInit(mapa, div)) {
		RemoveExistingMap(mapa);
	}

	mapa = L.map(div).setView([lat, lon], 11);

	L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
		attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
	}).addTo(mapa);

	if (marker != null)
			mapa.removeLayer(marker);

		var vacIcon = L.icon({
			iconUrl: IconVacUrl,
			iconSize: [32, 43], // size of the icon
			iconAnchor: [16, 43], // point of the icon which will correspond to marker's location
			popupAnchor: [-0, -45] // point from which the popup should open relative to the iconAnchor
		});
		//{ draggable: true }, 

		marker = new L.Marker([lat, lon], { icon: vacIcon })
			.bindPopup(text)
			.openPopup();

		mapa.addLayer(marker);
}


function BoolMapInit(map, mapDiv) {
	return (map != null && map._container.id == mapDiv);
}

function RemoveExistingMap(map) {
	if (map != null) {
		map.remove();
		map = null;
	}
}

function VerUpdVacunatorio(lat, lon, text){
	
	if (marker != null)
			mapa.removeLayer(marker);

		var vacIcon = L.icon({
			iconUrl: IconVacUrl,
			iconSize: [32, 43], // size of the icon
			iconAnchor: [16, 43], // point of the icon which will correspond to marker's location
			popupAnchor: [-0, -45] // point from which the popup should open relative to the iconAnchor
		});
		//{ draggable: true }, 

		marker = new L.Marker([lat, lon], { icon: vacIcon })
			.bindPopup(text)
			.openPopup();

		mapa.addLayer(marker);
		mapa.setView(new L.LatLng(lat, lon), 11);
}
