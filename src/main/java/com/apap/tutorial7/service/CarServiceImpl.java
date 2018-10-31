package com.apap.tutorial7.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tutorial7.model.CarModel;
import com.apap.tutorial7.model.DealerModel;
import com.apap.tutorial7.repository.CarDb;

@Service
@Transactional
public class CarServiceImpl implements CarService{
	@Autowired
	private CarDb carDb;

	@Override
	public CarModel addCar(CarModel car) {
		carDb.save(car);
		return car;
	}

	@Override
	public List<CarModel> findAllByPriceAsc() {
		return carDb.findAllByOrderByPriceAsc();
	}

	@Override
	public void deleteById(long Id) {
		carDb.deleteById(Id);
	}

	@Override
	public void update(CarModel car) {
		carDb.save(car);
	}

	@Override
	public Optional<CarModel> getCarDetail(long Id) {
		return carDb.findById(Id);
	}

	@Override
	public List<CarModel> findByDealerOrderByPriceAsc(DealerModel dealer) {
		return carDb.findByDealerOrderByPriceAsc(dealer);
	}

	@Override
	public void deleteCar(CarModel car) {
		carDb.delete(car);
	}

	@Override
	public void saveAll(ArrayList<CarModel> cars) {
		carDb.saveAll(cars);
	}
}

