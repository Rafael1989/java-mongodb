function autoComplete() {
	var input = document.getElementById('endereco');
	autocomplete = new google.maps.places.Autocomplete(input);
}

function toggleBounce() {
	if (marker.getAnimation() !== null) {
		marker.setAnimation(null);
	} else {
		marker.setAnimation(google.maps.Animation.BOUNCE);
	}
}

function initMap() {
	var brasil = {
		lat : -14.239183,
		lng : -51.913726
	};

	var map = new google.maps.Map(document.getElementById('map'), {
		center : brasil,
		scrollwheel : false,
		zoom : 12,
		styles : [ {
			elementType : 'geometry',
			stylers : [ {
				color : '#242f3e'
			} ]
		}, {
			elementType : 'labels.text.stroke',
			stylers : [ {
				color : '#242f3e'
			} ]
		}, {
			elementType : 'labels.text.fill',
			stylers : [ {
				color : '#746855'
			} ]
		}, {
			featureType : 'administrative.locality',
			elementType : 'labels.text.fill',
			stylers : [ {
				color : '#d59563'
			} ]
		}, {
			featureType : 'poi',
			elementType : 'labels.text.fill',
			stylers : [ {
				color : '#d59563'
			} ]
		}, {
			featureType : 'poi.park',
			elementType : 'geometry',
			stylers : [ {
				color : '#263c3f'
			} ]
		}, {
			featureType : 'poi.park',
			elementType : 'labels.text.fill',
			stylers : [ {
				color : '#6b9a76'
			} ]
		}, {
			featureType : 'road',
			elementType : 'geometry',
			stylers : [ {
				color : '#38414e'
			} ]
		}, {
			featureType : 'road',
			elementType : 'geometry.stroke',
			stylers : [ {
				color : '#212a37'
			} ]
		}, {
			featureType : 'road',
			elementType : 'labels.text.fill',
			stylers : [ {
				color : '#9ca5b3'
			} ]
		}, {
			featureType : 'road.highway',
			elementType : 'geometry',
			stylers : [ {
				color : '#746855'
			} ]
		}, {
			featureType : 'road.highway',
			elementType : 'geometry.stroke',
			stylers : [ {
				color : '#1f2835'
			} ]
		}, {
			featureType : 'road.highway',
			elementType : 'labels.text.fill',
			stylers : [ {
				color : '#f3d19c'
			} ]
		}, {
			featureType : 'transit',
			elementType : 'geometry',
			stylers : [ {
				color : '#2f3948'
			} ]
		}, {
			featureType : 'transit.station',
			elementType : 'labels.text.fill',
			stylers : [ {
				color : '#d59563'
			} ]
		}, {
			featureType : 'water',
			elementType : 'geometry',
			stylers : [ {
				color : '#17263c'
			} ]
		}, {
			featureType : 'water',
			elementType : 'labels.text.fill',
			stylers : [ {
				color : '#515c6d'
			} ]
		}, {
			featureType : 'water',
			elementType : 'labels.text.stroke',
			stylers : [ {
				color : '#17263c'
			} ]
		} ]
	});

	for (index = 0; index < alunos.length; ++index) {
		var latitude = alunos[index].contato.coordinates[0];
		var longitude = alunos[index].contato.coordinates[1];
		var coordenadas = {
			lat : latitude,
			lng : longitude
		};
		var marker = new google.maps.Marker({
			position : coordenadas,
			draggable : true,
			animation : google.maps.Animation.DROP,
			label : alunos[index].nome
		});
		marker.setMap(map);
		marker.addListener('click', toggleBounce);
	}
}