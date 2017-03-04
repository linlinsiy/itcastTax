package cn.itcast.test.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * TEmp entity. @author MyEclipse Persistence Tools
 */

public class TEmp implements java.io.Serializable {

	// Fields

	private String empId;
	private TDept TDept;
	private String name;
	private Set TLeaders = new HashSet(0);
	private Set TEmpRoles = new HashSet(0);

	// Constructors

	/** default constructor */
	public TEmp() {
	}

	/** minimal constructor */
	public TEmp(TDept TDept) {
		this.TDept = TDept;
	}

	/** full constructor */
	public TEmp(TDept TDept, String name, Set TLeaders, Set TEmpRoles) {
		this.TDept = TDept;
		this.name = name;
		this.TLeaders = TLeaders;
		this.TEmpRoles = TEmpRoles;
	}

	// Property accessors

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public TDept getTDept() {
		return this.TDept;
	}

	public void setTDept(TDept TDept) {
		this.TDept = TDept;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getTLeaders() {
		return this.TLeaders;
	}

	public void setTLeaders(Set TLeaders) {
		this.TLeaders = TLeaders;
	}

	public Set getTEmpRoles() {
		return this.TEmpRoles;
	}

	public void setTEmpRoles(Set TEmpRoles) {
		this.TEmpRoles = TEmpRoles;
	}

}