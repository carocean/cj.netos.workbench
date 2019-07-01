package cj.netos.workbench.args;

public class MenuItem {
	String agency;
	int sort;
	public MenuItem() {
		// TODO Auto-generated constructor stub
	}
	public MenuItem(String agency, int sort) {
		super();
		this.agency = agency;
		this.sort = sort;
	}
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	
}
