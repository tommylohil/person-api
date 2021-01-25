package com.demo.dto;

import com.demo.company.entity.Address;
import com.demo.company.entity.Person;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonCreateRequest implements Serializable {

	private String personCode;
	private String personName;
	private ArrayList<AddressRequest> addresses = new ArrayList<AddressRequest>();
}
