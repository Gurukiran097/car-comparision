package com.gk.car.data.services;

import com.gk.car.data.dto.CarSimilarityDto;
import com.gk.car.data.dto.CarVariantDto;
import com.gk.car.data.dto.CarVariantListDto;
import java.util.List;

public interface CarReadService {

  CarVariantDto getCar(String carVariantId);

  CarVariantListDto getCars(List<String> carVariants);

  CarVariantListDto getCarDifferences(List<String> carVariants);

  CarSimilarityDto getSimilarCars(String carVariantId);
}
