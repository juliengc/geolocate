var currentMarker = null;

function handlePointClick(event) {
	if (currentMarker === null) {
		document.getElementById('lat').value = event.latLng.lat();
		document.getElementById('lng').value = event.latLng.lng();

		currentMarker = new google.maps.Marker({
			position : new google.maps.LatLng(event.latLng.lat(), event.latLng
					.lng())
		});

		PF('geomap').addOverlay(currentMarker);

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
	map = new google.maps.Map(document.getElementById("geomap"), {
		zoom : 13,
		center : new google.maps.LatLng(43.6043401, 7.0174095),
		mapTypeId : google.maps.MapTypeId.HYBRID
	});
	if (navigator.geolocation)
		var watchId = navigator.geolocation.watchPosition(successCallback, null, {
			enableHighAccuracy : true
		});
	else
		alert("Votre navigateur ne prend pas en compte la géolocalisation HTML5");
}

function successCallback(position) {
	map.panTo(new google.maps.LatLng(position.coords.latitude,
			position.coords.longitude));
}


var makerzin;
function overOverLay(lattg,latng)
{     
	 alert('toto');
   makerzin = new google.maps.Marker({position:new google.maps.LatLng(lattg, latng)});
   makerzin.setTitle('toto'); 
   
   map.addOverlay(makerzin);
   //var title = document.getElementById('title'); 
  
}      

function handleComplete(){
	alert('toto');
	   var gmap = gMapWV.getMap();
	   for(var i in gmap.markers)
	   {
	      var newMarker = eval("args.marker"+i);
	      var oldMarker = gmap.markers[i];
	      oldMarker.icon = newMarker.icon;
	      oldMarker.setMap(gmap);
	   }   
	}

function geocode() {
    PF('map').geocode(document.getElementById('addressg').value);
}

