var latitud = -34.86157;
var longitud = -56.17938;
var mapa;

function geoloc() {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(showPosition);
	}

}

function showPosition(position)  
	{
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

		var xlng = 0.000256;
		var xlat = 0.000200;

		mapa.on('click', function(e) {

			$ds("input[id*='" + alias + "Latitud']").val(e.latlng.lat);
			$ds("input[id*='" + alias + "Longitud']").val(e.latlng.lng);


			/*
					L.polygon([
						[e.latlng.lat - xlat, e.latlng.lng - xlng],
						[e.latlng.lat + xlat, e.latlng.lng - xlng],
						[e.latlng.lat - xlat, e.latlng.lng + xlng],
						[e.latlng.lat + xlat, e.latlng.lng + xlng],
					]).addTo(map);
			
					L.polyline([
						[e.latlng.lat, e.latlng.lng - xlng],
						[e.latlng.lat, e.latlng.lng + xlng]
					]).addTo(map);
			*/
		});
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
