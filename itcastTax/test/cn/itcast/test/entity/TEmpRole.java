package cn.itcast.test.entity;

/**
 * TEmpRole entity. @author MyEclipse Persistence Tools
 */

public class TEmpRole implements java.io.Serializable {

	// Fields

	private TEmpRoleId id;
	private Integer state;

	// Constructors

	/** default constructor */
	public TEmpRole() {
	}

	/** minimal constructor */
	public TEmpRole(TEmpRoleId id) {
		this.id = id;
	}

	/** full constructor */
	public TEmpRole(TEmpRoleId id, Integer state) {
		this.id = id;
		this.state = state;
	}

	// Property accessors

	public TEmpRoleId getId() {
		return this.id;
	}

	public void setId(TEmpRoleId id) {
		this.id = id;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}