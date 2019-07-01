package cj.netos.workbench.args;

import java.util.ArrayList;
import java.util.List;

public class Viewport {
	String code;
	String workbench;
	String path;
	Titlebar titlebar;
	String container;
	List<MenuItem> navigation;
	public Viewport() {
		navigation=new ArrayList<MenuItem>();
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getWorkbench() {
		return workbench;
	}
	public void setWorkbench(String workbench) {
		this.workbench = workbench;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Titlebar getTitlebar() {
		return titlebar;
	}
	public void setTitlebar(Titlebar titlebar) {
		this.titlebar = titlebar;
	}
	public String getContainer() {
		return container;
	}
	public void setContainer(String container) {
		this.container = container;
	}
	public List<MenuItem> getNavigation() {
		return navigation;
	}
	
	
}
