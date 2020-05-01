package pl.moras.housemanagement.service;

import pl.moras.housemanagement.models.House;
import pl.moras.housemanagement.models.HouseInmateDto;
import pl.moras.housemanagement.models.Inmate;

public interface IAuthService {
    House addHouse(HouseInmateDto houseInmateDto);

    Inmate addInmate(HouseInmateDto houseInmateDto);

    void authenticate(HouseInmateDto houseInmateDto);
}
