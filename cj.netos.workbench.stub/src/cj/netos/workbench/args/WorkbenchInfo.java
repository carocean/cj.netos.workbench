package cj.netos.workbench.args;

public class WorkbenchInfo {
	String wbid;
	String name;
	String desc;
	String owner;//工作台归属
	String home;
	String theme;
	long ctime;
	public String getWbid() {
		return wbid;
	}
	public void setWbid(String wbid) {
		this.wbid = wbid;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public long getCtime() {
		return ctime;
	}
	public void setCtime(long ctime) {
		this.ctime = ctime;
	}
	
	
}
