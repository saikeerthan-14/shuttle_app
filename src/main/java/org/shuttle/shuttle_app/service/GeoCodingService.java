package org.shuttle.shuttle_app.service;

import org.shuttle.shuttle_app.entity.Coordinates;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.naming.ServiceUnavailableException;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

@Service
public class GeoCodingService {

    public static final String NOMINATIM_BASE_URL = "https://nominatim.openstreetmap.org/search";
    private final RestTemplate restTemplate;

    public GeoCodingService() {
        this.restTemplate = new RestTemplate();
    }

    public Coordinates getCoordinates(String address) throws ServiceUnavailableException {
        URI uri = UriComponentsBuilder.fromHttpUrl(NOMINATIM_BASE_URL)
                .queryParam("q", address)
                .queryParam("format", "json")
                .queryParam("limit", 1)
                .build().toUri();
        LinkedHashMap<String, String> response = (LinkedHashMap<String, String>) Objects.requireNonNull(restTemplate.getForObject(uri, List.class)).getFirst();
        if (response == null) {
            throw new ServiceUnavailableException("Unable to retrieve coordinates for the given address");
        }
        return new Coordinates(Double.parseDouble(response.get("lat")), Double.parseDouble(response.get("lon")));
    }

}
