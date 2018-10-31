package com.apap.tutorial7.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.apap.tutorial7.model.CarModel;
import com.apap.tutorial7.model.DealerModel;

public interface CarService {
	CarModel addCar(CarModel car);
	
	List<CarModel> findAllByPriceAsc();
	
	void deleteById(long id);
	
	void update(CarModel car);
	
	Optional<CarModel> getCarDetail(long Id);
	
	List<CarModel> findByDealerOrderByPriceAsc(DealerModel dealer);
	
	void deleteCar(CarModel car);
	
	void saveAll(ArrayList<CarModel> cars);
}
