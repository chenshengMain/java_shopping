package domain;

import java.io.Serializable;

public class User  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3174627010157630063L;
	/**
	 *  
  `uid` VARCHAR(32) NOT NULL,
  `username` VARCHAR(20) DEFAULT NULL,
  `password` VARCHAR(20) DEFAULT NULL,
  
  `name` VARCHAR(20) DEFAULT NULL,
  `email` VARCHAR(30) DEFAULT NULL,
  `telephone` VARCHAR(20) DEFAULT NULL,
  
  `birthday` DATE DEFAULT NULL,
  `sex` VARCHAR(10) DEFAULT NULL,
  `state` INT(11) DEFAULT NULL,#0用户未被激活  1用户被激活
  
  `code` VARCHAR(64) DEFAULT NULL,
  PRIMARY KEY (`uid`)
	 */
	
	private String uid;
	private String username;
	private String password;
	
	private String name;
	private String email;
	
	private String birthday;
	private String sex;
	private int state;//是否激活
	
	private String code;//激活码

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", password=" + password + ", name=" + name + ", email="
				+ email + ", birthday=" + birthday + ", sex=" + sex + ", state=" + state + ", code=" + code + "]";
	}
	
	
	
	
}
