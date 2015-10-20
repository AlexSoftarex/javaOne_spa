package model.nojsonb;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import model.UUIDEntity;

@Table
@Entity
public class Note extends UUIDEntity{
	
	@Column
	private String text;
	
	@OneToMany(mappedBy="parentNote", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Note> children = new HashSet<>();
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private Note parentNote;	

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private Client client;
	
	@Transient
	private Long parentId;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Set<Note> getChildren() {
		return children;
	}

	public void setChildren(Set<Note> children) {
		this.children = children;
	}

	public Note getParentNote() {
		return parentNote;
	}

	public void setParentNote(Note parentNote) {
		this.parentNote = parentNote;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Long getParentId() {
	    return parentId;
	}

	public void setParentId(Long parentId) {
	    this.parentId = parentId;
	}
}
