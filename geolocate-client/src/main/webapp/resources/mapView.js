var currentMarker = null;

function handlePointClick(event) {
	if (currentMarker === null) {
		document.getElementById('lat').value = event.latLng.lat();
		document.getElementById('lng').value = event.latLng.lng();

		currentMarker = new google.maps.Marker({
			position : new google.maps.LatLng(event.latLng.lat(), event.latLng
					.lng())
		});

		PF('map').addOverlay(currentMarker);

		PF('dlg').show();
	}
}

function markerAddComplete() {
	var title = document.getElementById('title');
	currentMarker.setTitle(title.value);
	title.value = "";

	currentMarker = null;
	PF('dlg').hide();
}

function cancel() {
	PF('dlg').hide();
	currentMarker.setMap(null);
	currentMarker = null;

	return false;
}

function initialize() {
	map = new google.maps.Map(document.getElementById("gmap"), {
		zoom : 19,
		center : new google.maps.LatLng(48.858565, 2.347198),
		mapTypeId : google.maps.MapTypeId.ROADMAP
	});
}

if (navigator.geolocation)
	var watchId = navigator.geolocation.watchPosition(successCallback, null, {
		enableHighAccuracy : true
	});
else
	alert("Votre navigateur ne prend pas en compte la géolocalisation HTML5");

function successCallback(position) {
	map.panTo(new google.maps.LatLng(position.coords.latitude,
			position.coords.longitude));
}