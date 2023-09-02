package sondv.shop.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	private int productId;
	private String name;
	private int quantity;
	private int unitPrice;
	private String img;
	private String description;
	private double discount;
	private Date enteredDate;
	private short status;
	private int categoryId;
	
}
