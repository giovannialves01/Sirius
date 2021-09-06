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
@Table(name="tb_sections") 
public class Section {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="doc_id")
	private Document nameDoc;
	
	@Column(unique=true)
	private String nameSection;		
	
	@Column(unique=true)
	private String pathSection;	 

	public String getNameSection() {
		return nameSection;
	}

	public void setNameSection(String nameSection) {
		this.nameSection = nameSection;
	}
	
	public String getPathSection() {
		return pathSection;
	}

	public void setPathSection(String pathSection) {
		this.pathSection = pathSection;
	}

	public Document getDocument() {
		return nameDoc;
	}

	public void setDocument(Document nameDoc) {
		this.nameDoc = nameDoc;
	}
	

	
}
	