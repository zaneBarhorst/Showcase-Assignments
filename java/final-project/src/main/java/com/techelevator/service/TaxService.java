package com.techelevator.service;

import com.techelevator.model.TaxResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Locale;

@Component
public class TaxService { //The TaxService class.

    private final static String API_BASE_URL = "https://teapi.netlify.app/api/statetax?state=";
    private final RestTemplate restTemplate = new RestTemplate();

    public BigDecimal getTaxRate(String stateCode){ //Gets the corresponding tax rate based on the state code provided utilizing the statetax api provided in the readme.
        String api = API_BASE_URL + stateCode.toUpperCase();

        try{
            TaxResponseDto taxResponseDto = restTemplate.getForObject(api, TaxResponseDto.class);
            return taxResponseDto.getSalesTax().divide(new BigDecimal("100"));
        }catch (HttpClientErrorException e){
            if(e.getRawStatusCode() == 404){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tax rate not found for the state " + stateCode.toUpperCase());
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was a error when getting the taxrate.", e);
            }
        }
    }
}
