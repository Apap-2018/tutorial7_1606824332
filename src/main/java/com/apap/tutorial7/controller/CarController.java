package com.apap.tutorial7.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apap.tutorial7.model.CarModel;
import com.apap.tutorial7.model.DealerModel;
import com.apap.tutorial7.service.CarService;
import com.apap.tutorial7.service.DealerService;

@RestController
@RequestMapping("/car")
public class CarController {
	@Autowired
	private CarService carService;
	
	@Autowired
	private DealerService dealerService;
	
	@PutMapping(value = "/{carId}")
	private String updateCarSubmit(
			@PathVariable(value = "carId") long carId,
			@RequestParam(value="brand", required=false) String brand,
			@RequestParam(value="type", required=false) String type,
			@RequestParam(value="price", required=false) Long price,
			@RequestParam(value="amount", required=false) Integer amount,
			@RequestParam(value = "dealerId", required=false) Long dealerId) {
		Optional<CarModel> optCar = carService.getCarDetail(carId);
		if (!optCar.isPresent()) {
			return "Couldn't find your car";
		}
		CarModel car = optCar.get();
		if (brand != null) {
			car.setBrand(brand);
		}if (type != null) {
			car.setType(type);
		}if (price != null) {
			car.setPrice(price);
		}if (amount != null) {
			car.setAmount(amount);
		}if (dealerId != null) {
			Optional<DealerModel> optDealer = dealerService.getDealerDetailById(dealerId);
			DealerModel dealer;
			if (!optDealer.isPresent()) {
				return "Couldn't find your dealer";
			}else {
				dealer = optDealer.get();
			}
			car.setDealer(dealer);
		}
		carService.update(car);
		return "car update success";
	}
	
	@PostMapping(value = "/add")
	private CarModel add(@RequestBody CarModel car) {
		return carService.addCar(car);
	}
	
	@GetMapping(value = "/{carId}")
	private CarModel viewDealer(@PathVariable ("carId") long carId, Model model) {
		System.out.println("MASUK DEALER "+carId);
		return carService.getCarDetail(carId).get();
	}
	
	@GetMapping()
	private List<CarModel> viewAllDealer(Model model){
		return carService.findAllByPriceAsc();
	}
	
	@DeleteMapping()
	private String deleteDealer(@RequestParam("carId") long id, Model model) {
		CarModel car = carService.getCarDetail(id).get();
		carService.deleteCar(car);
		return "Car has been delete";
	}
	
	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		ArrayList<CarModel> cars = new ArrayList<CarModel>();
		cars.add(new CarModel());
		dealer.setListCar(cars);
		
		model.addAttribute("dealer", dealer);
		model.addAttribute("title", "Add Car");
		return "addCar-dynamic";
	}
	
	@RequestMapping(value="/car/add/{dealerId}", params={"addRow"})
	public String addRow(@ModelAttribute DealerModel dealer, final BindingResult bindingResult, Model model) {
		if (dealer.getListCar()==null) {
			dealer.setListCar(new ArrayList<CarModel>());
		}
		dealer.getListCar().add(new CarModel());
		for (int i=0;i<dealer.getListCar().size();i++) {
			System.out.println(dealer.getListCar().get(i));
		}
		model.addAttribute("dealer", dealer);
	    return "addCar-dynamic";
	}

	@RequestMapping(value="/car/add/{dealerId}", params={"removeRow"})
	public String removeRow(@PathVariable(value = "dealerId") Long dealerId,
	        @ModelAttribute DealerModel dealer, final BindingResult bindingResult, 
	        final HttpServletRequest req, Model model) {
	    final Integer carId = Integer.valueOf(req.getParameter("removeRow"));
	    System.out.println(carId);
	    dealer.getListCar().remove(carId.intValue());
	    model.addAttribute("dealer", dealer);
	    return "addCar-dynamic";
	}
	
	@RequestMapping(value="/car/add/{dealerId}", params={"save"})
	public String saveRow(@PathVariable(value = "dealerId") Long dealerId,
			@ModelAttribute DealerModel dealer, final BindingResult bindingResult) {
		for (CarModel car : dealer.getListCar()) {
			System.out.println(dealer.getId());
			car.setDealer(dealer);
			carService.addCar(car);
		}
		return "add";
	}
	
	@RequestMapping(value = "/car/add", method = RequestMethod.POST)
	private String addCarSubmit(@ModelAttribute CarModel car, Model model) {
		carService.addCar(car);
		model.addAttribute("title", "Berhasil! Add Car");
		return "add";
	}
	
	@RequestMapping(value = "/car/delete/{carId}")
	private String deleteCar(@PathVariable(value = "carId") Long carId, Model model) {
		System.out.println("masuk");
		carService.deleteById(carId);
		model.addAttribute("title", "Berhasil! Delete Car");
		return "delete";
	}
	
	@RequestMapping(value = "/car/update/{carId}", method = RequestMethod.GET)
	private String updateForm(@PathVariable(value = "carId") Long carId, Model model) {
		CarModel car = carService.getCarDetail(carId).get();
		System.out.println(car.getId());
		model.addAttribute("car", car);
		model.addAttribute("title", "Update Car");
		return "updateCar";
	}
	
	@RequestMapping(value = "/car/update/{carId}", method = RequestMethod.POST)
	private String updateCar(@PathVariable(value = "carId") Long carId, @ModelAttribute CarModel car, Model model) {
		car.setId(carId);
		carService.update(car);
		model.addAttribute("title", "Berhasil! Update Car");
		return "add";
	}
	
	@RequestMapping(value = "/car/delete", method = RequestMethod.POST)
	private String delete(@ModelAttribute DealerModel dealer, Model model) {
		for (CarModel car : dealer.getListCar()) {
			carService.deleteCar(car);
			model.addAttribute("title", "Berhasil! Delete Car");
		}
		return "delete";
	}
	
	
}
