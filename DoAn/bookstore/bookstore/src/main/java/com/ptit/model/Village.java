package com.ptit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name="village")
@Data
public class Village {
	
	@Id
	@Column(name="village_id")
	private String villageId; 
	
	@Column(name="village_name")
	@Size(max=45)
	private String villageName; 
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="district_id")
	private District district; 
	
	@Column(name="village_type")
	private String villageType; 
	
}
















