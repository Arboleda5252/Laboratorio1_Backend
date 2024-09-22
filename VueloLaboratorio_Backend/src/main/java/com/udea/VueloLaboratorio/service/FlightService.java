package com.udea.VueloLaboratorio.service;

import com.udea.VueloLaboratorio.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.udea.VueloLaboratorio.repository.flightRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class FlightService {

    @Autowired
    private flightRepository flightRepository;

    public List<Flight> findFlights(LocalDate startDate, LocalDate endDate, String origin, String destination, Double maxPrice) {

        // Generar una clave de combinación de parámetros para usar en el switch
        String key = (origin != null ? "1" : "0") +
                (destination != null ? "1" : "0") +
                (maxPrice != null ? "1" : "0");

        switch (key) {
            case "111": // origin, destination, maxPrice no son nulos
                return flightRepository.findByDateBetweenAndOriginContainingIgnoreCaseAndDestinationContainingIgnoreCaseAndPriceLessThanEqual(
                        startDate, endDate, origin, destination, maxPrice);
            case "110": // origin, destination no son nulos, maxPrice es nulo
                return flightRepository.findByDateBetweenAndOriginContainingIgnoreCaseAndDestinationContainingIgnoreCase(
                        startDate, endDate, origin, destination);
            case "101": // origin y maxPrice no son nulos, destination es nulo
                return flightRepository.findByDateBetweenAndOriginContainingIgnoreCaseAndPriceLessThanEqual(
                        startDate, endDate, origin, maxPrice);
            case "011": // destination y maxPrice no son nulos, origin es nulo
                return flightRepository.findByDateBetweenAndDestinationContainingIgnoreCaseAndPriceLessThanEqual(
                        startDate, endDate, destination, maxPrice);
            case "100": // solo origin no es nulo
                return flightRepository.findByDateBetweenAndOriginContainingIgnoreCase(startDate, endDate, origin);
            case "010": // solo destination no es nulo
                return flightRepository.findByDateBetweenAndDestinationContainingIgnoreCase(startDate, endDate, destination);
            case "001": // solo maxPrice no es nulo
                return flightRepository.findByDateBetweenAndPriceLessThanEqual(startDate, endDate, maxPrice);
            case "000": // todos son nulos
            default:
                return flightRepository.findByDateBetween(startDate, endDate);

        }
    }
}
