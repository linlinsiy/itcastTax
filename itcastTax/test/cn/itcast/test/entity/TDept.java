package cn.itcast.test.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * TDept entity. @author MyEclipse Persistence Tools
 */

public class TDept implements java.io.Serializable {

	// Fields

	private String deptId;
	private TOrd TOrd;
	private String name;
	private Set TEmps = new HashSet(0);

	// Constructors

	/** default constructor */
	public TDept() {
	}

	/** minimal constructor */
	public TDept(TOrd TOrd) {
		this.TOrd = TOrd;
	}

	/** full constructor */
	public TDept(TOrd TOrd, String name, Set TEmps) {
		this.TOrd = TOrd;
		this.name = name;
		this.TEmps = TEmps;
	}

	// Property accessors

	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public TOrd getTOrd() {
		return this.TOrd;
	}

	public void setTOrd(TOrd TOrd) {
		this.TOrd = TOrd;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getTEmps() {
		return this.TEmps;
	}

	public void setTEmps(Set TEmps) {
		this.TEmps = TEmps;
	}

}