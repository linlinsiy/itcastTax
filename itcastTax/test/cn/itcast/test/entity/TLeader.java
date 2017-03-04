package cn.itcast.test.entity;

/**
 * TLeader entity. @author MyEclipse Persistence Tools
 */

public class TLeader implements java.io.Serializable {

	// Fields

	private String empId;
	private TEmp TEmp;
	private String deptId;
	private String name;
	private String position;

	// Constructors

	/** default constructor */
	public TLeader() {
	}

	/** minimal constructor */
	public TLeader(TEmp TEmp) {
		this.TEmp = TEmp;
	}

	/** full constructor */
	public TLeader(TEmp TEmp, String deptId, String name, String position) {
		this.TEmp = TEmp;
		this.deptId = deptId;
		this.name = name;
		this.position = position;
	}

	// Property accessors

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public TEmp getTEmp() {
		return this.TEmp;
	}

	public void setTEmp(TEmp TEmp) {
		this.TEmp = TEmp;
	}

	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}