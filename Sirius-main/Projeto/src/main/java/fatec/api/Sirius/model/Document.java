package fatec.api.Sirius.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="tb_documents")
public class Document {

	//private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="user_id")
	private User name;

	@Column(unique=true)
	private String nameDoc;	
	
	@Column(nullable = false)
	private String path;

	public String getNameDoc() {
		return nameDoc;
	}

	public void setNameDoc(String nameDoc) {
		this.nameDoc = nameDoc;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public User getUser() {
		return name;
	}

	public void setUser(User name) {
		this.name = name;
	}
	
	
	


	
}
