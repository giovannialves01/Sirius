package fatec.api.Sirius.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name="tb_block")
public class Block {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="blo_name")
	public String name;
		
	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="subsection_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Subsection subsection;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Subsection getSubsection() {
		return subsection;
	}

	public void setSubsection(Subsection subsection) {
		this.subsection = subsection;
	}

	public int getId() {
		return id;
	}

	
	
}
