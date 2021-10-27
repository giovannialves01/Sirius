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
@Table(name="tb_remark")
public class Remark {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="rem_name")
	public String name;
		
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="block_id")
	private Block block;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	
}
