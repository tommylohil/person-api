package com.demo.company.controller;

import com.demo.base.BaseResponse;
import com.demo.base.ListBaseResponse;
import com.demo.base.Metadata;
import com.demo.base.SingleBaseResponse;
import com.demo.company.entity.*;
import com.demo.company.entity.Person;
import com.demo.company.service.EmployeeService;
import com.demo.company.service.PersonService;
import com.demo.dto.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = PersonControllerPath.BASE_PATH)
public class PersonController {

	@Autowired
	private PersonService personService;

	// Create
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse create(@RequestParam String storeId, @RequestParam String channelId,
			@RequestParam String clientId, @RequestParam String requestId, @RequestParam String username,
			@RequestBody PersonCreateRequest request) throws Exception {
		this.personService.create(toPerson(request));
		return new BaseResponse(null, null, true, requestId);
	}

	// Update
	@RequestMapping(value = PersonControllerPath.UPDATE_BY_CODE, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse updateByPersonCode(@RequestParam String storeId, @RequestParam String channelId,
										   @RequestParam String clientId, @RequestParam String requestId, @RequestParam String username, @PathVariable String code,
										   @RequestBody PersonUpdateRequest request) throws Exception {
		this.personService.update(code, toPerson(request));
		return new BaseResponse(null, null, true, requestId);
	}

	// Get
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ListBaseResponse<PersonResponse> findPersons(@RequestParam String storeId, @RequestParam String channelId,
														@RequestParam String clientId, @RequestParam String requestId, @RequestParam String username,
														@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size)
			throws Exception {
		Pageable pageable = PageRequest.of(page, size);
		Page<Person> persons = this.personService.find(pageable);
		List<PersonResponse> personResponses = persons.getContent().stream().map(this::toPersonResponse).collect(Collectors.toList());
		return new ListBaseResponse<>(null, null, true, requestId, personResponses,
				new Metadata(page, size, persons.getTotalElements()));
	}

	// Get by personCode
	@RequestMapping(value = PersonControllerPath.FIND_BY_CODE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public SingleBaseResponse<PersonResponse> findByPersonCode(@RequestParam String storeId,
															   @RequestParam String channelId, @RequestParam String clientId, @RequestParam String requestId,
															   @RequestParam String username, @PathVariable String code) throws Exception {
		Person person = this.personService.findByPersonCode(code);
		PersonResponse personResponse = Optional.ofNullable(person).map(this::toPersonResponse).orElse(null);
		return new SingleBaseResponse<>(null, null, true, requestId, personResponse);
	}

	// Delete by personCode
	@RequestMapping(value = PersonControllerPath.DELETE_BY_CODE, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse deleteByPersonCode(@RequestParam String storeId, @RequestParam String channelId,
										   @RequestParam String clientId, @RequestParam String requestId, @RequestParam String username,
										   @RequestParam String code) throws Exception {
		this.personService.deleteByPersonCode(code);
		return new BaseResponse(null, null, true, requestId);
	}

	// Change Person to PersonResponse
	private PersonResponse toPersonResponse(Person person) {
		return Optional.ofNullable(person).map(p -> {
			PersonResponse personResponse = PersonResponse.builder().build();
			BeanUtils.copyProperties(p, personResponse);
			personResponse.setAddresses(toAddressesResponse(person.getAddresses()));
			return personResponse;
		}).orElse(null);
	}

	// Change Addresses to AddressResponse
	private ArrayList<AddressResponse> toAddressesResponse(ArrayList<Address> addressList) {
		return Optional.ofNullable(addressList).map(a -> {
			ArrayList<AddressResponse> addressResponseList = new ArrayList<AddressResponse>();

			for (Address singleAddress: a) {
				AddressResponse newAddress = AddressResponse.builder().build();
				BeanUtils.copyProperties(singleAddress, newAddress);
				addressResponseList.add(newAddress);
			}

			return addressResponseList;
		}).orElse(null);
	}

	private Person toPerson(PersonUpdateRequest request) {
		return Optional.ofNullable(request).map(e -> {
			Person person = Person.builder().build();
			BeanUtils.copyProperties(e, person);
			person.setAddresses(toAddresses(request.getAddresses()));
			return person;
		}).orElse(null);
	}

	private Person toPerson(PersonCreateRequest request) {
		return Optional.ofNullable(request).map(e -> {
			Person person = Person.builder().build();
			BeanUtils.copyProperties(e, person);
			person.setAddresses(toAddresses(request.getAddresses()));
			return person;
		}).orElse(null);
	}

	// Change AddressRequest to Addresses
	private ArrayList<Address> toAddresses(ArrayList<AddressRequest> request) {
		return Optional.ofNullable(request).map(a -> {
			ArrayList<Address> addressList = new ArrayList<Address>();

			for (AddressRequest singleAddress: a) {
				Address newAddress = Address.builder().build();
				BeanUtils.copyProperties(singleAddress, newAddress);
				addressList.add(newAddress);
			}

			return addressList;
		}).orElse(null);
	}
}
