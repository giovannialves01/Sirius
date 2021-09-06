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
@Table(name="tb_blocks")
public class Block {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="section_id")
	private Section nameSection;

	@Column(unique=true)
	public String nameBlock;
	
	public String pathBlock;
	
	

	
	
	public String getBlock() {
		return nameBlock;
	}

	public void setBlock(String nameBlock) {
		this.nameBlock = nameBlock;
	}

	public String getPath() {
		return pathBlock;
	}

	public void setPath(String pathBlock) {
		this.pathBlock = pathBlock;
	}

	public Section getSection() {
		return nameSection;
	}

	public void setSection(Section nameSection) {
		this.nameSection = nameSection;
	}
	

	
}
