package model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@MappedSuperclass
public class BaseClient extends UUIDEntity {
	
	@NotNull(message = "Name is required")
	@Size(min = 3, message = "Length must be over 3 symbols")
	@Column
	private String name;

	@NotNull(message = "Second name is required")
	@Size(min = 3, message = "Length must be over 3 symbols")
	@Column
	private String secondName;

	@NotNull(message = "Address is required")
	@Size(min = 3, message = "Length must be over 3 symbols")
	@Column
	private String address;

	@Min(value = 0, message = "You are to young")
	@Max(value = 120, message = "You are to old")
	@NotNull
	@Column
	private Byte age;

	@NotNull
	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
			+ "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
			+ "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Invalid email")
	@Column(unique = true)
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Byte getAge() {
		return age;
	}

	public void setAge(Byte age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
