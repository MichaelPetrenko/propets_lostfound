package telran.lostfound.api;

public class AccountDto {

	public String phone;
	public String email;

	public AccountDto(String phone, String email) {
		super();

		this.phone = phone;
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
