package pl.moras.service;

import pl.moras.models.House;
import pl.moras.models.HouseInmateDto;
import pl.moras.models.Inmate;

public interface IAuthService {
    House addHouse(HouseInmateDto houseInmateDto);

    Inmate addInmate(HouseInmateDto houseInmateDto);

    boolean authenticate(HouseInmateDto houseInmateDto);
}
