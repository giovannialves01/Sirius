package fatec.api.Sirius.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="tb_document")
public class Document {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="doc_name")
	private String name;		

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
