package model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class UUIDEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	
	@Column(insertable = false, columnDefinition = "uuid default uuid_generate_v4()", unique = true)
	protected UUID uuid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid == null ? null : uuid.toString();
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
}
